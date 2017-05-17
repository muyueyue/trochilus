package thread;

import download.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import persistence.ConsolePrint;
import utils.Config;
import utils.Method;
import utils.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiahao on 17-4-3.
 *
 * @author jiahao.pjh@gmail.com
 */
public class CrawlStartURLQueueTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(CrawlStartURLQueueTask.class);

    private String xPath;

    private String prefix;

    public CrawlStartURLQueueTask(String xPath, String prefix){
        this.xPath = xPath;
        this.prefix = prefix;
    }

    @Override
    public void run(){
        try{
            URLQueue urlQueue = URLQueue.getInstance();
            while(true){
                String startUrl = urlQueue.getStartQueue().poll();
                if(startUrl == null){
                    Thread.sleep(Config.errorSleepTime);
                }
                Request request = new Request(startUrl, Method.GET);
                Html html = Downloader.getHtml(request);
                if(html == null){
                    continue;
                }
                if(!this.prefix.equals("")){
                    List<String> temp = html.xPath(this.xPath);
                    List<String> targetUrls = new ArrayList<>();
                    for(String url : temp){
                        targetUrls.add(this.prefix.concat(url));
                    }
                    urlQueue.addURLToCacheTargetQueue(targetUrls);
                }
                urlQueue.addURLToCacheTargetQueue(html.xPath(this.xPath));
                urlQueue.addURLToFinishQueue(startUrl);
                Thread.sleep(5000);
            }
        }catch (Exception e){
            logger.error("爬虫任务出错: {}", e);
        }
    }
}

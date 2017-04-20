package thread;

import download.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import persistence.ConsolePrint;
import utils.Config;
import utils.Method;
import utils.Request;

/**
 * Created by jiahao on 17-4-3.
 *
 * @author jiahao.pjh@gmail.com
 */
public class CrawlStartURLQueueTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(CrawlStartURLQueueTask.class);

    private String xPath;

    public CrawlStartURLQueueTask(String xPath){
        this.xPath = xPath;
    }

    @Override
    public void run(){
        try{
            URLQueue urlQueue = URLQueue.getInstance();
            while(urlQueue.getStartQueueSize() != 0){
                String startUrl = urlQueue.getStartQueue().poll();
                if(startUrl == null){
                    Thread.sleep(Config.errorSleepTime);
                }
                Request  request = new Request(startUrl, Method.GET);
                Html html = new Html(Downloader.getHtml(request));
                ConsolePrint consolePrint = new ConsolePrint(html);
                consolePrint.printByXpath("targetUrl", this.xPath);
                urlQueue.addURLToTargetQueue(html.select(this.xPath));
                urlQueue.addURLToFinishQueue(startUrl);
            }
        }catch (Exception e){
            logger.error("爬虫任务出错: {}", e);
        }
    }
}

package pkulaw;

import download.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import thread.URLQueue;
import utils.Config;
import utils.Method;
import utils.Request;
import utils.StringUtil;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by jiahao on 17-5-27.
 *
 * @author jiahao.pjh@gmail.com
 */
public class StartUrlsTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(StartUrlsTask.class);

    @Override
    public void run(){
        BlockingQueue<String> startUrlsQueue = URLQueue.getInstance().getStartQueue();
        BlockingQueue<String> targetUrlsQueue = URLQueue.getInstance().getTargetQueue();
        String pre = "http://www.pkulaw.cn";
        try {
            while (true){
                if(startUrlsQueue.size() == 0){
                    //从Mongdb中获取startUrl
                }
                String startUrl = startUrlsQueue.poll();
                if(StringUtil.isEmpty(startUrl)){
                    Thread.sleep(5000);
                    continue;
                }
                Request request = new Request(startUrl, Method.GET);
                Html html = Downloader.getHtml(request);
                if(html == null){
                    continue;
                }
                List<String> targetUrlsList = html.xPath("//dl[@class='contentList']/dd/a/@href");
                if(targetUrlsList== null || targetUrlsList.size() == 0){
                    continue;
                }
                for(String targetUrl : targetUrlsList){
                    String url = pre.concat(targetUrl);
                    logger.info("targetUrl:{}", url);
                    targetUrlsQueue.offer(url);
                }
                Thread.sleep(5000);
            }
        }catch (Exception e){
            logger.info("解析startUrls出错:", e);
        }

    }
}

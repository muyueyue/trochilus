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
public class CrawlTargetQueueTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(CrawlTargetQueueTask.class);

    @Override
    public void run(){
        try {
            URLQueue urlQueue = URLQueue.getInstance();
            while (true){
                String targetUrl = urlQueue.getTargetQueue().poll();
                if(targetUrl == null){
                    Thread.sleep(Config.errorSleepTime);
                    continue;
                }
                Request request = new Request(targetUrl, Method.GET);
                Html html = new Html(Downloader.getHtml(request));
                ConsolePrint consolePrint = new ConsolePrint(html);
                consolePrint.printByXpath("content", "//div[@id=zoom]/p/text()");
            }
        }catch (Exception e){
            logger.error("爬虫任务出错: {}", e);
        }
    }
}

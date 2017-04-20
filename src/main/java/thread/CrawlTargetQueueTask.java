package thread;

import com.alibaba.fastjson.JSONObject;
import download.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import persistence.ConsolePrint;
import utils.Config;
import utils.Method;
import utils.Request;

import java.util.List;

/**
 * Created by jiahao on 17-4-3.
 *
 * @author jiahao.pjh@gmail.com
 */
public class CrawlTargetQueueTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(CrawlTargetQueueTask.class);

    private List<JSONObject> keyRegexMethod;

    private String pip;

    public CrawlTargetQueueTask(List<JSONObject> keyRegexMethod, String pip){
        this.keyRegexMethod = keyRegexMethod;
        this.pip = pip;
    }

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
                for(JSONObject jsonObject : keyRegexMethod){

                }
            }
        }catch (Exception e){
            logger.error("爬虫任务出错: {}", e);
        }
    }
}

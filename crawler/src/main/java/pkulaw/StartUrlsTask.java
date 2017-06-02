package pkulaw;

import download.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import persistence.MongoDBJDBC;
import thread.ThreadPool;
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
                try {
                    logger.info("startUrls队列的大小为:{}", startUrlsQueue.size());
                    String startUrl = startUrlsQueue.poll();
                    if(targetUrlsQueue.size() > 10){
                        continue;
                    }
                    if(CreateStartUrls.getTargetUrls(targetUrlsQueue, startUrl, pre)){
                        Thread.sleep(1000);
                    }
                }catch (Exception e){
                    OutputException.output("/home/hadoop/hadoop/spider/logs/error.log", "解析startUrls出错:" + e.toString());
                    //OutputException.output("/home/jiahao/myjar/error.log", "解析targetUrls出错:" + e.toString());
                    logger.error("下载解析startUrls出错:", e);
                }
            }
        }catch (Exception e){
            ThreadPool.getInstance().execute(new StartUrlsTask());
            OutputException.output("/home/hadoop/hadoop/spider/logs/error.log", "解析startUrls出错:" + e.toString());
            //OutputException.output("/home/jiahao/myjar/error.log", "解析targetUrls出错:" + e.toString());
            logger.error("下载解析startUrls出错:", e);
        }
    }
}

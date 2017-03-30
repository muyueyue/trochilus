package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.URLQueue;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 操作队列的工具类
 * 提供向带爬取队列和已完成队列中添加url的方法
 * Created by jiahao on 17-3-30.
 */
public class QueueUtil {

    private static final Logger  logger = LoggerFactory.getLogger(QueueUtil.class);

    public static void addURLToTargetQueue(List<String> url){
        if(url == null || url.size() == 0){
            return;
        }
        URLQueue urlQueue = URLQueue.getInstance();
        BlockingQueue targetQueue = urlQueue.getTargetQueue();
        for(String string : url){
            if(urlQueue.isInTargetQueue(string) || urlQueue.isInFinishQueue(string)){
                continue;
            }
            targetQueue.offer(string);
            logger.info("向待爬取队列中添加URL: {}", string);
        }
    }

    public static void addURLToTargetQueue(String url){
        if(StringUtil.isNotURL(url)){
            return;
        }
        URLQueue urlQueue = URLQueue.getInstance();
        if(urlQueue.isInTargetQueue(url) || urlQueue.isInFinishQueue(url)){
            return;
        }
        BlockingQueue targetQueue = urlQueue.getTargetQueue();
        targetQueue.offer(url);
        logger.info("向待爬取队列中添加URL: {}", url);
    }

    public static void addURLToFinishQueue(String url){
        if(StringUtil.isNotURL(url)){
            return;
        }
        URLQueue urlQueue = URLQueue.getInstance();
        BlockingQueue finishQueue = urlQueue.getFinishQueue();
        finishQueue.offer(url);
        logger.info("向已完成队列中添加URL: {}", url);
    }
}

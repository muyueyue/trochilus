package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 单例
 * targetQueue存储待爬取的url
 * finishQueue存储已爬取的url
 * Created by jiahao on 17-3-30.
 */
public class URLQueue {

    private Logger logger = LoggerFactory.getLogger(URLQueue.class);

    private static URLQueue urlQueue;

    private BlockingQueue<String> targetQueue;

    private BlockingQueue<String> finishQueue;

    private URLQueue(){
        targetQueue = new LinkedBlockingDeque();
        finishQueue = new LinkedBlockingDeque();
    }

    public static synchronized URLQueue getInstance(){
        if(urlQueue == null){
            urlQueue = new URLQueue();
        }
        return urlQueue;
    }

    public void clearTargetQueue(){
        targetQueue.clear();
        logger.info("目标队列已清空");
    }

    public void clearFinishQueue(){
        finishQueue.clear();
        logger.info("已爬取队列已清空");
    }

    public Boolean isInTargetQueue(String url){
        if(targetQueue.contains(url)){
            return true;
        }
        return false;
    }

    public Boolean isInFinishQueue(String url){
        if(finishQueue.contains(url)){
            return true;
        }
        return false;
    }

    public long getTargetQueueSize(){
        return targetQueue.size();
    }

    public long getFinishQueueSize(){
        return finishQueue.size();
    }

    public BlockingQueue<String> getTargetQueue(){
        return targetQueue;
    }

    public BlockingQueue<String> getFinishQueue(){
        return finishQueue;
    }
}

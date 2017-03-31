package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtil;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
/**
 * 单例
 * targetQueue存储待爬取的url队列
 * finishQueue存储已爬取的url队列
 * 一系列操作队列的方法
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
        if(StringUtil.isEmpty(url)){
            return null;
        }

        if(targetQueue.contains(url)){
            return true;
        }
        return false;
    }

    public Boolean isInFinishQueue(String url){
        if(StringUtil.isEmpty(url)){
            return null;
        }
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

    public void addURLToTargetQueue(List<String> url){
        if(url == null || url.size() == 0){
            return;
        }
        for(String string : url){
            if(isInTargetQueue(string) || isInFinishQueue(string)){
                continue;
            }
            targetQueue.offer(string);
            logger.info("向待爬取队列中添加URL: {}", string);
        }
    }

    public void addURLToTargetQueue(String url){
        if(StringUtil.isNotURL(url)){
            return;
        }
        if(isInTargetQueue(url) || isInFinishQueue(url)){
            return;
        }
        targetQueue.offer(url);
        logger.info("向待爬取队列中添加URL: {}", url);
    }

    public void addURLToFinishQueue(String url){
        if(StringUtil.isNotURL(url)){
            return;
        }
        finishQueue.offer(url);
        logger.info("向已完成队列中添加URL: {}", url);
    }

    public BlockingQueue<String> getTargetQueue(){
        return targetQueue;
    }

    public BlockingQueue<String> getFinishQueue(){
        return finishQueue;
    }
}

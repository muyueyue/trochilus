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
 * startQueue起始爬取的url队列
 * finishQueue存储已爬取的url队列
 * 一系列操作队列的方法
 * Created by jiahao on 17-3-30.
 *
 * @author jiahao.pjh@gmail.com
 */
public class URLQueue {

    private Logger logger = LoggerFactory.getLogger(URLQueue.class);

    private static URLQueue urlQueue;

    private BlockingQueue<String> targetQueue;

    private BlockingQueue<String> cacheTargetQueue;

    private BlockingQueue<String> startQueue;

    private BlockingQueue<String> cacheStartQueue;

    private BlockingQueue<String> finishQueue;

    private URLQueue(){
        targetQueue = new LinkedBlockingDeque();
        startQueue = new LinkedBlockingDeque<>();
        finishQueue = new LinkedBlockingDeque();
        cacheStartQueue = new LinkedBlockingDeque<>();
        cacheTargetQueue = new LinkedBlockingDeque<>();
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

    public void clearStartQueue(){
        startQueue.clear();
        logger.info("起始URL队列已清空");
    }
    public void clearFinishQueue(){
        finishQueue.clear();
        logger.info("已爬取队列已清空");
    }

    public long getTargetQueueSize(){
        return targetQueue.size();
    }

    public long getStartQueueSize(){return startQueue.size();}

    public long getFinishQueueSize(){
        return finishQueue.size();
    }

    public void addURLToCacheTargetQueue(List<String> url){
        if(url == null || url.size() == 0){
            return;
        }
        for(String string : url){
            if (StringUtil.isNotURL(string)){
                continue;
            }
            cacheTargetQueue.offer(string);
            logger.info("向待爬取缓存队列中添加URL: {}", string);
        }
    }

    public void addURLToTargetQueue(List<String> url){
        if(url == null || url.size() == 0){
            return;
        }
        for(String string : url){
            if (StringUtil.isNotURL(string)){
                continue;
            }
            targetQueue.offer(string);
            logger.info("向待爬取队列中添加URL: {}", string);
        }
    }

    public void addToCacheStartQueue(List<String> url){
        if(url == null || url.size() == 0){
            return;
        }
        for(String string : url){
            if (StringUtil.isNotURL(string)){
                continue;
            }
            cacheStartQueue.offer(string);
            logger.info("向起始缓存队列中添加成功： {}", string);
        }
    }

    public void addToStartQueue(List<String> url){
        if(url == null || url.size() == 0){
            return;
        }
        for(String string : url){
            if (StringUtil.isNotURL(string)){
                continue;
            }
            startQueue.offer(string);
            logger.info("向起始队列中添加成功： {}", string);
        }
    }

    public void addURLToTargetQueue(String url){
        if(StringUtil.isNotURL(url)){
            return;
        }
        targetQueue.offer(url);
        logger.info("向待爬取队列中添加URL: {}", url);
    }

    public void addURLToCacheTargetQueue(String url){
        if(StringUtil.isNotURL(url)){
            return;
        }
        cacheTargetQueue.offer(url);
        logger.info("向待爬取缓存队列中添加URL: {}", url);
    }

    public void addToCacheStartQueue(String url){
        if(StringUtil.isNotURL(url)){
            return;
        }
        cacheStartQueue.offer(url);
        logger.info("向起始缓存队列中添加成功： {}", url);
    }

    public void addToStartQueue(String url){
        if(StringUtil.isNotURL(url)){
            return;
        }
        startQueue.offer(url);
        logger.info("向起始队列中添加成功： {}", url);
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

    public BlockingQueue<String> getStartQueue() {
        return startQueue;
    }

    public BlockingQueue<String> getFinishQueue(){
        return finishQueue;
    }

    public BlockingQueue<String> getCacheTargetQueue(){return cacheTargetQueue;}

    public BlockingQueue<String> getCacheStartQueue(){return cacheStartQueue;}
}

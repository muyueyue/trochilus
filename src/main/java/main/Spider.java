package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.CrawlStartURLQueueTask;
import thread.CrawlTargetQueueTask;
import thread.SpidersTask;
import thread.URLQueue;

/**
 * 爬虫的入口
 * Created by pjh on 2017/1/22.
 */
public class Spider {

    private static final Logger logger = LoggerFactory.getLogger(Spider.class);

    public static void main(String[] args){
        URLQueue urlQueue = URLQueue.getInstance();
        urlQueue.addToStartQueue("http://news.baidu.com/");
        CrawlStartURLQueueTask crawlStartURLQueueTask = new CrawlStartURLQueueTask("//ul[@class='ulist focuslistnews']/li/a/@href");
        Thread startThread = new Thread(crawlStartURLQueueTask);
        startThread.start();
        CrawlTargetQueueTask crawlTargetQueueTask = new CrawlTargetQueueTask();
        Thread targetThread = new Thread(crawlTargetQueueTask);
        targetThread.start();
    }
}

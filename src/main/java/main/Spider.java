package main;

import download.HttpClientDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import parse.Page;
import parse.Select;
import thread.SpidersTask;
import thread.URLQueue;
import utils.Method;
import utils.Request;

/**
 * 爬虫的入口
 * Created by pjh on 2017/1/22.
 */
public class Spider {

    private static final Logger logger = LoggerFactory.getLogger(Spider.class);

    public static void main(String[] args){
        URLQueue queue = URLQueue.getInstance();
        queue.addURLToTargetQueue("http://news.baidu.com/");
        SpidersTask task = new SpidersTask();
        Thread thread = new Thread(task);
        thread.start();
    }
}

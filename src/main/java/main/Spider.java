package main;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.Result;
import thread.CrawlStartURLQueueTask;
import thread.CrawlTargetQueueTask;
import thread.ThreadPool;
import thread.URLQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 爬虫的入口
 * Created by pjh on 2017/1/22.
 */
public class Spider {

    private static final Logger logger = LoggerFactory.getLogger(Spider.class);

    private int threadNum;

    private List<String> startUrl;

    private Result result;

    private String pip;

    private List<JSONObject> keyRegexMethod;

    public void Spider(){
        this.result = new Result();
        this.pip = "console";
        this.keyRegexMethod = new ArrayList<>();
    }

    public Spider addStartUrl(String startUrl){
        this.startUrl.add(startUrl);
        return this;
    }

    public Spider addStartUrl(List<String> startUrls){
        if(startUrls ==  null){
            logger.error("起始URL列表为空");
            return this;
        }
        for(String url : startUrls){
            this.startUrl.add(url);
        }
        return this;
    }

    public Spider thread(int threadNum){
        this.threadNum = threadNum;
        return this;
    }

    public Spider db(){
        this.pip = "db";
        return this;
    }

    public Spider file(){
        this.pip = "file";
        return this;
    }

    public Spider console(){
        this.pip = "console";
        return this;
    }

    public Spider addToTargetQueue(String regex){
        URLQueue urlQueue = URLQueue.getInstance();
        urlQueue.addToStartQueue(this.startUrl);
        CrawlStartURLQueueTask crawlStartURLQueueTask = new CrawlStartURLQueueTask(regex);
        ThreadPool.getInstance().execute(crawlStartURLQueueTask);
        return this;
    }

    public Spider putField(String key, String regex, String method){
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("key", key).fluentPut("regex", regex).fluentPut("method", method);
        this.keyRegexMethod.add(jsonObject);
        return this;
    }

    public void run(){
        for(int i = 0; i < this.threadNum; i++){
            CrawlTargetQueueTask crawlTargetQueueTask = new CrawlTargetQueueTask(this.keyRegexMethod, this.pip);
            ThreadPool.getInstance().execute(crawlTargetQueueTask);
        }
    }
}

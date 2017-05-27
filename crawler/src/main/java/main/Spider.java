package main;

import com.alibaba.fastjson.JSONObject;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.HBaseTask;
import persistence.Result;
import thread.*;
import utils.Config;
import utils.ParseMethod;
import utils.Util;

import javax.management.StringValueExp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 爬虫的入口
 * Created by pjh on 2017/1/22.
 *
 * @author jiahao.pjh@gmail.com
 */
public class Spider {

    private static final Logger logger = LoggerFactory.getLogger(Spider.class);

    private int threadNum;

    private List<String> startUrl;

    private Result result;

    private List<String> persistence;

    private List<JSONObject> keyRegexMethod;

    private SpiderService spiderService;

    public Spider(){
        spiderService = new SpiderService();
        this.startUrl = new ArrayList<>();
        this.result = new Result();
        this.persistence = new ArrayList<>();
        this.keyRegexMethod = new ArrayList<>();
        this.threadNum = 1;
        this.persistence.add("console");
    }

    public Spider addStartUrl(String startUrl){
        this.startUrl.add(startUrl);
        return this;
    }

    public Spider addStartUrl(String startUrl, int limit){
        if(startUrl.indexOf("{}") == -1){
            logger.error("参数格式异常");
            return null;
        }
        for(int i = 1; i <= limit; i++){
            String url = startUrl.replace("{}", String.valueOf(i));
            this.startUrl.add(url);
        }
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

    public Spider addUrlToTargeQueue(String url, int limit){
            if(url.indexOf("{}") == -1){
                logger.error("URL格式出错,缺少{}");
                return null;
            }
            URLQueue urlQueue = URLQueue.getInstance();
            for(int i = 1; i <= limit; i++){
                String temp = url.replace("{}", String.valueOf(i));
                urlQueue.addURLToCacheTargetQueue(temp);
            }
            return this;
    }

    public Spider thread(int threadNum){
        this.threadNum = threadNum;
        return this;
    }

    public Spider db(JSONObject dbConfig) throws DBException{
        if(this.persistence.contains("db")){
            return this;
        }
        if(!dbConfig.containsKey("mongoDBHost") ||
                !dbConfig.containsKey("databaseName") ||
                !dbConfig.containsKey("dbCollection")){
            throw new DBException("数据库配置文件异常");
        }
        Config.mongoDBHost = dbConfig.getString("mongoDBHost");
        if(dbConfig.containsKey("mongoDBPort")){
            Config.mongoDBPort = dbConfig.getIntValue("mongoDBPort");

        }
        Config.databaseName = dbConfig.getString("databaseName");
        Config.dbCollection = dbConfig.getString("dbCollection");
        this.persistence.add("db");
        return this;
    }

    public Spider file(String filePath){
        if(this.persistence.contains("file")){
            return this;
        }
        this.persistence.add("file");
        Config.filePath = filePath;
        return this;
    }

    public Spider console(){
        if(this.persistence.contains("console")){
            return this;
        }
        this.persistence.add("console");
        return this;
    }

    public Spider addToTargetQueue(String regex){
        URLQueue urlQueue = URLQueue.getInstance();
        urlQueue.addToCacheStartQueue(this.startUrl);
        CrawlStartURLQueueTask crawlStartURLQueueTask = new CrawlStartURLQueueTask(regex, "");
        ThreadPool.getInstance().execute(crawlStartURLQueueTask);
        return this;
    }

    public Spider addToTargetQueue(String regex, String prefix){
        URLQueue urlQueue = URLQueue.getInstance();
        urlQueue.addToCacheStartQueue(this.startUrl);
        CrawlStartURLQueueTask crawlStartURLQueueTask = new CrawlStartURLQueueTask(regex, prefix);
        ThreadPool.getInstance().execute(crawlStartURLQueueTask);
        return this;
    }

    public Spider putField(String key, String regex, ParseMethod method){
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("key", key).fluentPut("regex", regex).fluentPut("method", method.toString());
        this.keyRegexMethod.add(jsonObject);
        return this;
    }

    public Spider putField(String key, String regex, ParseMethod method, boolean isPartition){
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("key", key)
                .fluentPut("regex", regex)
                .fluentPut("method", method.toString())
                .fluentPut("isPartition", isPartition);
        this.keyRegexMethod.add(jsonObject);
        return this;
    }

    public Spider setMasterInfo(String masterAddr){
        Config.masterAddr = masterAddr;
        return this;
    }

    public void run(){
        if(!spiderService.register()){
            logger.error("爬虫注册失败");
            return;
        }
        HttpTask.startHttpTask();
        ThreadPool pool = ThreadPool.getInstance();
        pool.execute(new GetCaseTask());
        pool.execute(new AddStartUrlTask());
        pool.execute(new HBaseTask());
        for(int i = 0; i < this.threadNum; i++){
            CrawlTargetQueueTask crawlTargetQueueTask = new CrawlTargetQueueTask(this.keyRegexMethod, this.persistence);
            pool.execute(crawlTargetQueueTask);
        }
    }
}

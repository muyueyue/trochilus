package thread;

import com.alibaba.fastjson.JSONObject;
import download.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import persistence.ConsolePrint;
import persistence.FilePersistence;
import persistence.MongoDBJDBC;
import utils.*;

import java.util.List;
import java.util.Map;

/**
 * Created by jiahao on 17-4-3.
 *
 * @author jiahao.pjh@gmail.com
 */
public class CrawlTargetQueueTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(CrawlTargetQueueTask.class);

    private List<JSONObject> keyRegexMethod;

    private List<String> persistence;

    public CrawlTargetQueueTask(List<JSONObject> keyRegexMethod, List<String> persistence){
        this.keyRegexMethod = keyRegexMethod;
        this.persistence = persistence;
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
                Html html = Downloader.getHtml(request);
                JSONObject result = new JSONObject();
                for(JSONObject jsonObject : keyRegexMethod){
                    if(jsonObject.getString("method") == ParseMethod.REGEX.toString()){
                        List<String> list = html.regex(jsonObject.getString("regex"));
                        result.put(jsonObject.getString("key"), StringUtil.listToString(list));
                    }else if(jsonObject.getString("method") == ParseMethod.XPATH.toString()){
                        List<String> list = html.xPath(jsonObject.getString("regex"));
                        result.put(jsonObject.getString("key"), StringUtil.listToString(list));
                    }else {
                        continue;
                    }
                }
                if(persistence.contains("console")){
                    for(Map.Entry<String, Object> entry : result.entrySet()){
                        ConsolePrint.print(entry.getKey(), (String)entry.getValue());
                    }
                }
                if(persistence.contains("file")){
                    for(Map.Entry<String, Object> entry : result.entrySet()){
                        FilePersistence.write(entry.getKey(), (String)entry.getValue());
                    }
                }
                if(persistence.contains("db")){
                    MongoDBJDBC.insert(result);
                }
            }
        }catch (Exception e){
            logger.error("爬虫任务出错: {}", e);
        }
    }
}

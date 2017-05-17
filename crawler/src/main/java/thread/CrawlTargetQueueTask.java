package thread;

import com.alibaba.fastjson.JSONObject;
import download.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import persistence.ConsolePrint;
import persistence.DataPersistence;
import persistence.FilePersistence;
import utils.*;
import java.util.List;

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
                if(html == null){
                    continue;
                }
                JSONObject result = new JSONObject();
                for(JSONObject jsonObject : keyRegexMethod){
                    if(jsonObject.getString("method") == ParseMethod.REGEX.toString()){
                        List<String> list = html.regex(jsonObject.getString("regex"));
                        if(jsonObject.containsKey("isPartition") && jsonObject.getBoolean("isPartition")){
                            for(int i = 0; i < list.size(); i++){
                                result.put(jsonObject.getString("key").concat(String.valueOf(i)), list.get(i));
                            }
                        }else {
                            result.put(jsonObject.getString("key"), StringUtil.listToString(list));
                        }
                    }else if(jsonObject.getString("method") == ParseMethod.XPATH.toString()){
                        List<String> list = html.xPath(jsonObject.getString("regex"));
                        if(jsonObject.containsKey("isPartition") && jsonObject.getBoolean("isPartition")){
                            for(int i = 0; i < list.size(); i++){
                                result.put(jsonObject.getString("key").concat(String.valueOf(i)), list.get(i));
                            }
                        }else {
                            result.put(jsonObject.getString("key"), StringUtil.listToString(list));
                        }
                    }else {
                        continue;
                    }
                }
                if(persistence.contains("console")){
                    ConsolePrint.print(result);
                }
                if(persistence.contains("file")){
                    FilePersistence.write(result);
                }
                if(persistence.contains("db")){
                    DataPersistence.insert(result);
                }
                Thread.sleep(1000);
            }
        }catch (Exception e){
            logger.error("爬虫任务出错: {}", e);
        }
    }
}

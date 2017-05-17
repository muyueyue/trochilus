package redis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.URLQueue;
import utils.Config;
import utils.Method;
import utils.Request;
import utils.Response;

import java.util.HashMap;

/**
 * Created by jiahao on 17-5-3.
 *
 * @author jiahao.pjh@gmail.com
 */
public class GetStartUrlsTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(GetStartUrlsTask.class);

    @Override
    public void run(){
        URLQueue urlQueue = URLQueue.getInstance();
        while (true){
            try {
                Thread.sleep(1000);
                Request request = new Request(Config.masterAddr.concat("/master/url/getStartUrl?start=0&end=0&spiderId=" + Config.spiderId), Method.GET);
                Response response = request.send();
                if(response.isSuccess()){
                    /*JSONArray startUrls = response.getJsonArrayValue("content");
                    if(startUrls == null && startUrls.size() == 0){
                        continue;
                    }
                    for(int i = 0; i < startUrls.size(); i++){
                        urlQueue.addToStartQueue(startUrls.getJSONObject(i).getString("startUrl"));
                    }*/
                    JSONObject startUrl = response.getJsonObject("content");
                    if(startUrl == null){
                        continue;
                    }
                    urlQueue.addToStartQueue(startUrl.getString("startUrl"));
                }else {
                    logger.error("获取Master中的startUrl失败");
                }
            }catch (Exception e){
                logger.error("获取Master中的startUrl出错 {}", e);
            }
        }
    }
}

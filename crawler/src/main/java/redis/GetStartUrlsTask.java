package redis;

import com.alibaba.fastjson.JSONArray;
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
                Request request = new Request(Config.masterAddr.concat("/master/url/getStartUrl?start=1&end=10&spiderId=" + Config.spiderId), Method.GET);
                Response response = request.send();
                if(response.isSuccess()){
                    JSONArray startUrls = response.getJsonArrayValue("content").getJSONArray(0);
                    if(startUrls == null){
                        continue;
                    }
                    for(int i = 0; i < startUrls.size(); i++){
                        urlQueue.addToStartQueue(startUrls.getJSONObject(i).getString("startUrl"));
                    }
                }
                Thread.sleep(5000);
            }catch (Exception e){
                logger.error("从Master中获取startUrl出错 {}", e);
            }
        }
    }
}

package redis;

import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.URLQueue;
import utils.Config;
import utils.Method;
import utils.Request;
import utils.Response;

/**
 * Created by jiahao on 17-5-3.
 *
 * @author jiahao.pjh@gmail.com
 */
public class GetTargetUrlsTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(GetTargetUrlsTask.class);

    @Override
    public void run(){
        URLQueue urlQueue = URLQueue.getInstance();
        while (true){
            try {
                Request request = new Request(Config.masterAddr.concat("/master/url/getTargetUrl?start=1&end=10&spiderId=" + Config.spiderId), Method.GET);
                Response response = request.send();
                if(response.isSuccess()){
                    JSONArray targetUrls = response.getJsonArrayValue("content").getJSONArray(0);
                    if(targetUrls == null){
                        continue;
                    }
                    for(int i = 0; i < targetUrls.size(); i++){
                        urlQueue.addURLToTargetQueue(targetUrls.getJSONObject(i).getString("targetUrl"));
                    }
                }
                Thread.sleep(5000);
            }catch (Exception e){
                logger.error("获取Master中的targetUrl出错{}", e);
            }
        }
    }
}

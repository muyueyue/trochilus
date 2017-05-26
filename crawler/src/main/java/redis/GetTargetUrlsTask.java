package redis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import manage.WorkInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.URLQueue;
import utils.*;

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
                Thread.sleep(500);
                logger.info("向Master获取targetUrl");
                Request request = new Request(Config.masterAddr.concat("/master/url/getTargetUrl?start=0&end=0&spiderId=" + Config.spiderId), Method.GET);
                Response response = request.send();
                if(response.isSuccess()){
                    /*JSONArray targetUrls = response.getJsonArrayValue("content");
                    if(targetUrls == null && targetUrls.size() == 0){
                        continue;
                    }
                    for(int i = 0; i < targetUrls.size(); i++){
                        urlQueue.addURLToTargetQueue(targetUrls.getJSONObject(i).getString("targetUrl"));
                    }*/
                    JSONObject targetUrl = response.getJsonObject("content");
                    if(targetUrl == null || StringUtil.isEmpty(targetUrl.getString("targetUrl"))){
                        continue;
                    }
                    urlQueue.addURLToTargetQueue(targetUrl.getString("targetUrl"));
                    WorkInfo.getInstance().setTargetUrls(1);
                }else {
                    logger.error("获取Master中的targetUrl失败");
                }
            }catch (Exception e){
                logger.error("获取Master中的targetUrl出错{}", e);
            }
        }
    }
}

package redis;

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
                Request request = new Request(Config.masterAddr.concat("/master/url/getTargetUrl?spiderId=" + Config.spiderId), Method.GET);
                Response response = request.send();
                if(response.isSuccess()){
                    String targetUrl = response.getJsonArrayValue("content").getJSONObject(0).getString("targetUrl");
                    urlQueue.addURLToTargetQueue(targetUrl);
                }
                Thread.sleep(1000);
            }catch (Exception e){
                logger.error("获取Master中的targetUrl出错");
            }
        }
    }
}

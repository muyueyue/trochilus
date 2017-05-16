package redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.URLQueue;
import utils.Config;
import utils.Method;
import utils.Request;
import utils.Response;

import java.util.concurrent.BlockingQueue;

/**
 * Created by jiahao on 17-5-3.
 *
 * @author jiahao.pjh@gmail.com
 */
public class AddToTargetUrlsTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(AddToTargetUrlsTask.class);

    @Override
    public void run(){
        BlockingQueue<String> cacheTargetQueue = URLQueue.getInstance().getCacheTargetQueue();
        while (true){
            try {
                Thread.sleep(5000);
                String url = cacheTargetQueue.poll();
                if (url == null){
                    continue;
                }
                Request request = new Request(Config.masterAddr.concat("/master/url/addTargetUrl?spiderId=" + Config.spiderId), Method.POST);
                request.setParams("targetUrl", url);
                Response response = request.send();
                if(response.isSuccess()){
                    logger.info("向Master中添加带爬取的URL成功");
                }else {
                    logger.error("向Master中添加带爬取的URL失败");
                }
            }catch (Exception e){
                logger.error("向Master中添加带爬取的URL出错{}", e);
            }
        }
    }
}

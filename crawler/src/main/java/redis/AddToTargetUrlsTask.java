package redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.URLQueue;
import utils.Config;
import utils.Method;
import utils.Request;

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
                String url = cacheTargetQueue.poll();
                Request request = new Request(Config.masterAddr.concat("/master/url/addTargetUrl" + Config.spiderId), Method.POST);
                request.setParams("targetUrl", url);
                request.send();
                Thread.sleep(1000);
            }catch (Exception e){
                logger.error("向Master中添加带爬取的URL出错");
            }
        }
    }
}

package redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.URLQueue;
import utils.Config;
import utils.Method;
import utils.Request;
import utils.StringUtil;

import java.util.concurrent.BlockingQueue;

/**
 * Created by jiahao on 17-5-3.
 *
 * @author jiahao.pjh@gmail.com
 */
public class AddToStartUrlsTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(AddToStartUrlsTask.class);

    @Override
    public void run(){
        BlockingQueue<String> cacheStartQueue = URLQueue.getInstance().getCacheStartQueue();
        while (true){
            try {
                String startUrl = cacheStartQueue.poll();
                Request request =  new Request(Config.masterAddr.concat("/master/url/addStartUrl"), Method.POST);
                request.setParams("startUrl", startUrl);
                request.send();
                Thread.sleep(5000);
            }catch (Exception e){
                logger.error("向Master中添加startUrl出错{}", e);
            }
        }
    }
}

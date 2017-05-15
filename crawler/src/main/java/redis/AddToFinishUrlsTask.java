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
public class AddToFinishUrlsTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(AddToFinishUrlsTask.class);

    @Override
    public void run(){
        BlockingQueue<String> finishQueue = URLQueue.getInstance().getFinishQueue();
        while (true){
            try {
                String finishUrl = finishQueue.poll();
                Request request = new Request(Config.masterAddr.concat("/master/url/addFinishUrl" + Config.spiderId), Method.POST);
                request.setParams("finishUrl", finishUrl);
                request.send();
                Thread.sleep(5000);
            }catch (Exception e){
                logger.error("向Master中添加完成爬取的URL出错 {}", e);
            }
        }
    }
}

package redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.URLQueue;
import utils.*;

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
                Thread.sleep(500);
                String startUrl = cacheStartQueue.poll();
                if(startUrl == null){
                    continue;
                }
                Request request =  new Request(Config.masterAddr.concat("/master/url/addStartUrl?spiderId=" + Config.spiderId), Method.POST);
                request.setParams("startUrl", startUrl);
                Response response = request.send();
                if(response.isSuccess()){
                    logger.info("向Master中添加startUrl:{}成功", startUrl);
                }else {
                    logger.error("向Master中添加startUrl:{}失败", startUrl);
                }
            }catch (Exception e){
                logger.error("向Master中添加startUrl出错{}", e);
            }
        }
    }
}

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
public class AddToFinishUrlsTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(AddToFinishUrlsTask.class);

    @Override
    public void run(){
        BlockingQueue<String> finishQueue = URLQueue.getInstance().getFinishQueue();
        while (true){
            try {
                Thread.sleep(500);
                String finishUrl = finishQueue.poll();
                if(finishUrl == null){
                    continue;
                }
                Request request = new Request(Config.masterAddr.concat("/master/url/addFinishUrl?spiderId=" + Config.spiderId), Method.POST);
                request.setParams("finishUrl", finishUrl);
                Response response = request.send();
                if(response.isSuccess()){
                    logger.info("向Master中添加完成爬取的URL:{}成功", finishUrl);
                }else {
                    logger.error("向Master中添加完成爬取的URL:{}失败", finishUrl);
                }
            }catch (Exception e){
                logger.error("向Master中添加完成爬取的URL出错 {}", e);
            }
        }
    }
}

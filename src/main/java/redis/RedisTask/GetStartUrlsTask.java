package redis.RedisTask;

import redis.RedisClient;
import thread.URLQueue;

import java.util.concurrent.BlockingQueue;

/**
 * Created by jiahao on 17-5-3.
 *
 * @author jiahao.pjh@gmail.com
 */
public class GetStartUrlsTask implements Runnable{

    @Override
    public void run(){
        URLQueue urlQueue = URLQueue.getInstance();
        while (true){
            String url = RedisClient.getStartUrl();
            urlQueue.addURLToTargetQueue(url);
        }
    }
}

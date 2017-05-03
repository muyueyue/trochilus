package redis.RedisTask;

import redis.RedisClient;
import thread.URLQueue;

/**
 * Created by jiahao on 17-5-3.
 *
 * @author jiahao.pjh@gmail.com
 */
public class GetTargetUrlsTask implements Runnable{

    @Override
    public void run(){
        URLQueue instance = URLQueue.getInstance();
        while (true){
            String url = RedisClient.getTargetUrl();
            instance.addURLToTargetQueue(url);
        }
    }
}

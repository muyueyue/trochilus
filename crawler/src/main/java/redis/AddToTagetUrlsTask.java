package redis;

import thread.URLQueue;
import utils.StringUtil;

import java.util.concurrent.BlockingQueue;

/**
 * Created by jiahao on 17-5-3.
 *
 * @author jiahao.pjh@gmail.com
 */
public class AddToTagetUrlsTask implements Runnable{

    @Override
    public void run(){
        BlockingQueue<String> cacheTargetQueue = URLQueue.getInstance().getCacheTargetQueue();
        while (true){
            String url = cacheTargetQueue.poll();
        }
    }
}

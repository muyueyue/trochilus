package redis;

import thread.URLQueue;
import utils.StringUtil;

import java.util.concurrent.BlockingQueue;

/**
 * Created by jiahao on 17-5-3.
 *
 * @author jiahao.pjh@gmail.com
 */
public class AddToStartUrlsTask implements Runnable{

    @Override
    public void run(){
        BlockingQueue<String> cacheStartQueue = URLQueue.getInstance().getCacheStartQueue();
        while (true){

        }
    }
}

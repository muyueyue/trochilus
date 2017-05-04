package redis;

import thread.URLQueue;

import java.util.concurrent.BlockingQueue;

/**
 * Created by jiahao on 17-5-3.
 *
 * @author jiahao.pjh@gmail.com
 */
public class AddToFinishUrlsTask implements Runnable{

    @Override
    public void run(){
        BlockingQueue<String> finishQueue = URLQueue.getInstance().getFinishQueue();
        while (true){

        }
    }
}

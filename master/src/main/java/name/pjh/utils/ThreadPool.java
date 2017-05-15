package name.pjh.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jiahao on 17-5-15.
 *
 * @author jiahao.pjh@gmail.com
 */
public class ThreadPool {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPool.class);

    private static ThreadPool instance;

    private ExecutorService executorService;

    private  ThreadPool(){executorService = Executors.newCachedThreadPool();}

    public synchronized ThreadPool getInstance(){
        if(instance == null){
            instance = new ThreadPool();
        }
        return instance;
    }

    public void excute(Runnable task){
        executorService.execute(task);
    }
}

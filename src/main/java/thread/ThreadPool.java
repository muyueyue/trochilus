package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *　单例
 * 线程池的封装，用于提供线程
 * Created by pjh on 2017/2/10.
 */
public class ThreadPool {

    private static ThreadPool threadPool;

    private static ExecutorService executorService;

    private ThreadPool(){
        executorService = Executors.newCachedThreadPool();
    }

    public static ThreadPool getInstance(){
        if(threadPool == null){
            threadPool = new ThreadPool();
        }
        return threadPool;
    }

    public void execute(Runnable task){
        threadPool.execute(task);
    }

    public void shutdown(){
        threadPool.shutdown();
    }
}

import cycletask.Task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by pjh on 2017/3/9.
 * 周期任务的入口，任务每5秒执行一次
 */
public class Main {
    public static void main(String[] args){
        Task task = new Task();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(task, 5, TimeUnit.SECONDS);
    }
}

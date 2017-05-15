package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.*;

/**
 * Created by jiahao on 17-5-15.
 *
 * @author jiahao.pjh@gmail.com
 */
public class HttpTask{

    private static final Logger logger = LoggerFactory.getLogger(HttpTask.class);

    public static void startHttpTask(){
        ThreadPool instance = ThreadPool.getInstance();
        instance.execute(new AddToStartUrlsTask());
        instance.execute(new AddToTargetUrlsTask());
        instance.execute(new AddToFinishUrlsTask());
        instance.execute(new GetStartUrlsTask());
        instance.execute(new GetTargetUrlsTask());
        logger.info("Worker向Master的请求已经全部启动！");
    }
}

package name.pjh.task.Method;

import name.pjh.service.RedisService;
import name.pjh.spider.SpiderPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.BlockingQueue;

/**
 * Created by jiahao on 17-5-6.
 *
 * @author jiahao.pjh@gmail.com
 *
 * 当爬虫所在的机器正常工作时，删除给定时间前的缓存URL
 */
public class QueueHandle{

    private static final Logger logger = LoggerFactory.getLogger(QueueHandle.class);

    private static RedisService redisService = new RedisService();

    public static void deleteUrls(String spiderId, long time){
        SpiderPool pool = SpiderPool.getInstance();
        BlockingQueue<String> backupStartUrlQueue = pool.getSpider(spiderId).getBackupStartUrl();
        BlockingQueue<String> backupTargetUrlQueue = pool.getSpider(spiderId).getBackupTargetUrl();
        for(;;){
            String startUrl = backupStartUrlQueue.peek();
            long temp = Long.valueOf(startUrl.substring(0, startUrl.indexOf("http")));
            if(time - temp > 1800000){
                backupStartUrlQueue.poll();
            }else {
                break;
            }
        }
        for(;;){
            String tagetUrl = backupTargetUrlQueue.peek();
            long temp = Long.valueOf(tagetUrl.substring(0, tagetUrl.indexOf("http")));
            if(time - temp > 1800000){
                backupTargetUrlQueue.poll();
            }else {
                break;
            }
        }
    }

    public static void reAddBackupStartUrl(String spiderId){
        SpiderPool pool = SpiderPool.getInstance();
        BlockingQueue<String> startUrlQueue = pool.getSpider(spiderId).getBackupStartUrl();
        for(;;){
            String startUrl = startUrlQueue.poll();
            if(startUrl == null){
                break;
            }
            startUrl = startUrl.substring(startUrl.indexOf("http"), startUrl.length());
            redisService.addStartUrl(startUrl);
        }
    }

    public static void reAddBackupTargetUrl(String spiderId){
        SpiderPool pool = SpiderPool.getInstance();
        BlockingQueue<String> targetUrlQueue = pool.getSpider(spiderId).getBackupTargetUrl();
        for(;;){
            String targetUrl = targetUrlQueue.poll();
            if (targetUrl == null){
                break;
            }
            targetUrl = targetUrl.substring(targetUrl.indexOf("http"), targetUrl.length());
            redisService.addTargetUrl(targetUrl);
        }
    }
}

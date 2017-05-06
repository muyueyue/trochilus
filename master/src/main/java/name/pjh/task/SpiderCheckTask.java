package name.pjh.task;

import name.pjh.spider.SpiderInfo;
import name.pjh.spider.SpiderPool;
import name.pjh.task.Method.QueueHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.List;

/**
 * Created by jiahao on 17-5-6.
 *
 * @author jiahao.pjh@gmail.com
 *
 * 监测爬虫所在机器是否宕机，并采取相应的策略
 * 　
 * 该任务定时执行
 */
public class SpiderCheckTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(SpiderCheckTask.class);

    @Override
    public void run(){
        try {
            SpiderPool pool = SpiderPool.getInstance();
            List<SpiderInfo> spiderInfos = pool.getSpiderInfoList();
            for(SpiderInfo spiderInfo : spiderInfos){
                long currentTime = System.currentTimeMillis();
                if(currentTime - spiderInfo.getLastTime() >= 1800000){
                    //将缓存的URL写入Redis
                    QueueHandle.reAddBackupStartUrl(spiderInfo.getSpiderId());
                    QueueHandle.reAddBackupTargetUrl(spiderInfo.getSpiderId());
                }else if(currentTime - spiderInfo.getLastTime() >= 600000){
                    boolean clientIsAccess = InetAddress.getByName(spiderInfo.getClientIp()).isReachable(10000);
                    if(!clientIsAccess){
                        //将缓存的URL写入Redis
                        QueueHandle.reAddBackupStartUrl(spiderInfo.getSpiderId());
                        QueueHandle.reAddBackupTargetUrl(spiderInfo.getSpiderId());
                    }
                }else if(currentTime - spiderInfo.getLastTime() <= 60000){
                    //删除三十分钟前缓存的URL
                    QueueHandle.deleteUrls(spiderInfo.getSpiderId(), currentTime);
                }else {
                    //不做处理
                }
            }
        }catch (Exception e){
            logger.info("Exception {}", e);
        }
    }
}

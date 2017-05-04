package name.pjh.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiahao on 17-5-4.
 *
 * @author jiahao.pjh@gmail.com
 *
 * 单例，爬虫的集合，存储所有分布的爬虫
 */
public class SpiderPool {

    private static final Logger logger = LoggerFactory.getLogger(SpiderPool.class);

    private static SpiderPool instance;

    private  static List<SpiderInfo> spiderInfoList = new ArrayList<>();

    private SpiderPool(){
    }

    public static synchronized SpiderPool getInstance(){
        if(instance == null){
            instance =  new SpiderPool();
        }
        return instance;
    }

    public List<SpiderInfo> getSpiderInfoList(){
        return spiderInfoList;
    }

    public synchronized Boolean add(SpiderInfo spiderInfo){
        if (getSpider(spiderInfo.getSpiderId()) != null){
            logger.error("爬虫　{}　已存在", spiderInfo.getSpiderId());
            return false;
        }
        spiderInfoList.add(spiderInfo);
        logger.info("爬虫 {} 注册成功", spiderInfo.getSpiderId());
        return true;
    }

    public SpiderInfo getSpider(String spiderId){
        if(spiderInfoList == null){
            logger.error("获取爬虫 {} 失败", spiderId);
            return null;
        }
        for(SpiderInfo spiderInfo : spiderInfoList){
            if(spiderInfo.getSpiderId().equals(spiderId)){
                return spiderInfo;
            }
        }
        return null;
    }
}

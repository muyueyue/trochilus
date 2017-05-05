package name.pjh.service;


import com.sun.org.apache.regexp.internal.RE;
import name.pjh.spider.SpiderInfo;
import name.pjh.spider.SpiderPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by jiahao on 17-5-5.
 *
 * @author jiahao.pjh@gmail.com
 */

@Service
public class SpiderService {

    private static final Logger logger = LoggerFactory.getLogger(SpiderService.class);

    public boolean createSpider(String spiderId){
        SpiderInfo spiderInfo = new SpiderInfo(spiderId);
        spiderInfo.setLastTime(System.currentTimeMillis());
        SpiderPool pool = SpiderPool.getInstance();
        if(pool.add(spiderInfo)){
            return true;
        }
        return false;
    }
}

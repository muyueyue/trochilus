package name.pjh.service;


import com.alibaba.fastjson.JSONObject;
import name.pjh.spider.SpiderInfo;
import name.pjh.spider.SpiderPool;
import name.pjh.utils.Config;
import name.pjh.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

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

    public boolean workInfo(JSONObject param){
        if(param.containsKey("spiderId") &&
                SpiderPool.getInstance().getSpider(param.getString("spiderId")) != null){
            SpiderInfo spider = SpiderPool.getInstance().getSpider(param.getString("spiderId"));
            spider.setStartUrlsNum(param.getIntValue("startUrlsNum"));
            spider.setTargetUrlsNum(param.getIntValue("targetUrlsNum"));
            spider.setFinishStartUrlsNum(param.getIntValue("finishStartUrlsNum"));
            spider.setFinishTargetUrls(param.getIntValue("finishTargetUrls"));
            return true;
        }
        return false;
    }

    public JSONObject getAllWorkInfo(){
        long startUrls = 0, targetUrls = 0, finishStartUrls = 0, finishTargetUrls = 0;
        List<SpiderInfo> spiderList = SpiderPool.getInstance().getSpiderInfoList();
        if(spiderList == null || spiderList.size() == 0){
            logger.error("未获取到任何爬虫信息");
            return null;
        }
        for(SpiderInfo spider : spiderList){
            startUrls = startUrls + spider.getStartUrlsNum();
            targetUrls = targetUrls + spider.getStartUrlsNum();
            finishStartUrls = finishStartUrls + spider.getFinishStartUrlsNum();
            finishTargetUrls = finishTargetUrls + spider.getFinishTargetUrlsNum();
        }
        JSONObject result = new JSONObject();
        result.fluentPut("startUrlsTotal", Config.startUlrsTotal)
                .fluentPut("targetUrlsTotal", Config.targetUrlslTotal)
                .fluentPut("startUrls", startUrls)
                .fluentPut("targetUrls", targetUrls)
                .fluentPut("finishStartUrls", finishStartUrls)
                .fluentPut("finishTargetUrls", finishTargetUrls);
        return result;
    }

    public  JSONObject getSpiderWorkInfo(String spiderId){
        if(StringUtil.isEmpty(spiderId)){
            logger.error("爬虫的唯一标识为空");
            return null;
        }
        SpiderInfo spider = SpiderPool.getInstance().getSpider(spiderId);
        if(spider == null){
            logger.error("未获取到相应爬虫信息");
            return null;
        }
        JSONObject result = new JSONObject();
        result.fluentPut("startUrls", spider.getStartUrlsNum())
                .fluentPut("targetUrls", spider.getTargetUrlsNum())
                .fluentPut("finishStartUrls", spider.getFinishStartUrlsNum())
                .fluentPut("finishTargetUrls", spider.getFinishTargetUrlsNum());
        return result;
    }
}

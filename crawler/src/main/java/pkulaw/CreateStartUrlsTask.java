package pkulaw;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ThreadPool;
import thread.URLQueue;

import java.util.concurrent.BlockingQueue;

/**
 * Created by jiahao on 17-6-2.
 *
 * @author jiahao.pjh@gmail.com
 */
public class CreateStartUrlsTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(CreateStartUrlsTask.class);

    @Override
    public void run(){
        String config = ReadConfig.read();
        JSONArray configArray = new JSONArray();
        try {
            configArray = JSONArray.parseArray(config);
        }catch (Exception e){
            logger.error("解析配置文件出错，请检查配置文件数据格式:", e);
            System.exit(1);
        }
        if(configArray == null || configArray.size() < 1){
            logger.error("配置文件为空");
            System.exit(1);
        }
        BlockingQueue<String> targetUrls = URLQueue.getInstance().getTargetQueue();
        BlockingQueue<String> startUrls = URLQueue.getInstance().getStartQueue();
        for(int i = 0 ; i < configArray.size(); i++){
            try {
                JSONObject configJson = configArray.getJSONObject(i);
                String startTime  = configJson.getString("startTime").trim();
                String endTime  = configJson.getString("endTime").trim();
                String gap = configJson.getString("gap").trim();
                String caseCode = configJson.getString("caseCode").trim();
                String parentCaseCode;
                int pageSize = 50, pageIndex = 500;
                if(configJson.containsKey("parentCaseCode")){
                    parentCaseCode = configJson.getString("parentCaseCode").trim();
                }else {
                    parentCaseCode = caseCode.substring(0, 3);
                }
                if(configJson.containsKey("pageSize")){
                    pageSize = configJson.getIntValue("pageSize");
                }
                if(configJson.containsKey("pageIndex")){
                    pageIndex = configJson.getIntValue("pageIndex");
                }
                JSONArray times = CreateStartUrls.format(startTime, endTime, gap);
                logger.info("拆分的时间为:{}", times.toString());
                if(times == null || times.size() < 1){
                    logger.error("时间拆分的结果为空");
                    continue;
                }
                for(int j = 0; j < times.size(); j++){
                    try {
                        while (!(targetUrls.size() < 10 && startUrls.size() < 5)){
                        }
                        CreateStartUrls.create(times.getJSONObject(j), parentCaseCode, caseCode, pageSize, pageIndex);
                        Thread.sleep(1000);
                    }catch (Exception e){
                        logger.error("构造startUrls的任务出现异常，已被捕获:", e);
                    }
                }
            }catch (Exception e){
                logger.error("构造startUrls的任务出现异常，已被捕获:", e);
            }
        }
        try {
            while(true){
                if(targetUrls.size() == 0 && startUrls.size() == 0){
                    logger.info("爬虫任务结束");
                    System.exit(0);
                }
                Thread.sleep(60000);
            }
        }catch (Exception e){
        }
    }
}

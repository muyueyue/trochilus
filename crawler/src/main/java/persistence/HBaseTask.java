package persistence;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ResultQueue;
import utils.Config;
import utils.Method;
import utils.Request;

/**
 * Created by jiahao on 17-5-25.
 *
 * @author jiahao.pjh@gmail.com
 */
public class HBaseTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(HBaseTask.class);

    @Override
    public void run(){
        try {
            while (true){
                Thread.sleep(500);
                JSONObject data = ResultQueue.getResult();
                if(data == null){
                    continue;
                }
                Request request = new Request(Config.HBaseUrls, Method.POST);
                request.setParams("rowKey", data.getString("rowKey"))
                        .setParams("title", data.getString("title"))
                        .setParams("info", data.getString("info"))
                        .setParams("content", data.getString("content"))
                        .setParams("url", data.getString("url"));
                request.send();
                logger.info("数据插入HBase成功");
            }
        }catch (Exception e){
            logger.error("数据插入Hbase出错:{}", e);
        }
    }
}

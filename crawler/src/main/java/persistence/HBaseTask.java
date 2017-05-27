package persistence;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ResultQueue;
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
            String baseUrl = "http://172.20.94.101:18070/insertHbase";
            while (true){
                Thread.sleep(5000);
                for(int i = 0; i < 20; i++){
                    JSONObject data = ResultQueue.getResult();
                    Request request = new Request(baseUrl, Method.POST);
                    request.setParams("rowKey", data.getString("rowKey"))
                    .setParams("title", data.getString("title"))
                    .setParams("info", data.getString("info"))
                    .setParams("content", data.getString("content"))
                    .setParams("url", data.getString("url"));
                }
            }
        }catch (Exception e){
        }
    }
}

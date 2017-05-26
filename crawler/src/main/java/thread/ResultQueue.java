package thread;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jiahao on 17-5-25.
 *
 * @author jiahao.pjh@gmail.com
 */
public class ResultQueue {

    private static final Logger logger = LoggerFactory.getLogger(ResultQueue.class);

    private static BlockingQueue<JSONObject> result;

    static {
        result = new LinkedBlockingQueue<>();
    }

    public static void addResult(JSONObject data){
        result.offer(data);
    }

    public static JSONObject getResult(){
        JSONObject data = result.poll();
        if(data != null){
            return data;
        }else {
            logger.error("从结果队列中获取数据失败");
            return null;
        }
    }
}

package persistence;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ResultQueue;

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
            Thread.sleep(10000);
            for(int i = 0; i < 20; i++){
                JSONObject data = ResultQueue.getResult();
            }
        }catch (Exception e){
        }
    }
}

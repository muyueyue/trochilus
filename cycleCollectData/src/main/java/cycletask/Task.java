package cycletask;

import com.alibaba.fastjson.JSONObject;
import com.dtwave.common.http.request.HttpResponse;
import com.dtwave.common.http.request.Request;
import org.slf4j.Logger;
import parameter.FormatParameter;
import util.RequestPath;
import util.RestUtil;


/**
 * Created by pjh on 2017/3/8.
 * 发送请求的任务类
 */
public class Task implements Runnable{

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Task.class);

    private FormatParameter formatParameter = new FormatParameter();

    @Override
    public void run(){
        try {
            JSONObject parameter = formatParameter.parameter();
            HttpResponse response = new Request(RequestPath.serveUrl).body(parameter.toString()).POST();
            if(RestUtil.isSuccess(response)){
                logger.info("请求成功");
            }else{
                logger.error("请求失败：{}", RestUtil.errorMsg(response));
            }
        }catch (Exception e){
            logger.error("定时任务执行出现异常：", e);
        }

    }
}

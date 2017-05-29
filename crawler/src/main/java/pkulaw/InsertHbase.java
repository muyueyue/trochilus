package pkulaw;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Method;
import utils.Request;
import utils.Response;
import utils.StringUtil;

/**
 * Created by jiahao on 17-5-25.
 *
 * @author jiahao.pjh@gmail.com
 */
public class InsertHbase{

    private static final Logger logger = LoggerFactory.getLogger(InsertHbase.class);

    private static String baseUrl = "http://172.20.94.107:18070/insertHbase";

    public static boolean insert(JSONObject data){
        try {
            Request request = new Request(baseUrl, Method.POST);
            request.setParams("rowkey", data.getString("rowKey"))
                    .setParams("title", data.getString("title"))
                    .setParams("info", data.getString("info"))
                    .setParams("content", data.getString("content"))
                    .setParams("url", data.getString("url"));
            Response response = request.send();
            if (isInsertSuccess(response)){
                logger.info("数据插入Hbase成功");
                return true;
            }else {
                logger.error("数据插入Hbase失败");
                return false;
            }
        }catch (Exception e){
            logger.error("数据插入Hbase出现异常");
            return false;
        }
    }

    public static boolean isInsertSuccess(Response response){
        if (response.getStatus() >= 200 && response.getStatus() < 300){
            String content = response.getContent();
            if(StringUtil.isNotEmpty(content)){
                JSONObject jsonObject = JSONObject.parseObject(content);
                if(jsonObject.containsKey("sucess") && jsonObject.getBoolean("sucess")){
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
}

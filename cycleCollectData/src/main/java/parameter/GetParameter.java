package parameter;

import com.alibaba.fastjson.JSONArray;
import com.dtwave.common.http.request.HttpResponse;
import com.dtwave.common.http.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestPath;
import util.RestUtil;

/**
 * Created by pjh on 2017/3/9.
 */
public class GetParameter{

    private static final Logger logger = LoggerFactory.getLogger(GetParameter.class);

    /**
     * 获取所有的meter
     * @return
     */
    public JSONArray queryMeter(){
        HttpResponse response = new Request(RequestPath.queryMeter).GET();
        if(RestUtil.isSuccess(response)){
            return response.jsonArrayValue("content");
        }
        logger.error("获取meter出错：{}", RestUtil.errorMsg(response));
        return null;
    }

    /**
     * 获取meter的参数信息
     * @return
     */
    public JSONArray queryMeterRegConf(){
        HttpResponse response = new Request(RequestPath.queryMeterRegConf).GET();
        if(RestUtil.isSuccess(response)){
            return response.jsonArrayValue("content");
        }
        logger.error("获取meter的参数信息出错：{}", RestUtil.errorMsg(response));
        return null;
    }

    /**
     * 统计每个meter的寄存器数量
     * @return
     */
    public JSONArray countReg(){
        HttpResponse response = new Request(RequestPath.countReg).GET();
        if(RestUtil.isSuccess(response)){
            return response.jsonArrayValue("content");
        }
        logger.error("统计每个meter的寄存器数量出错：{}", RestUtil.errorMsg(response));
        return null;
    }

    /**
     * 获取每个meter的起始寄存器地址
     * @return
     */
    public JSONArray queryRegAddrStart(){
        HttpResponse response = new Request(RequestPath.queryRegAddrStart).GET();
        if(RestUtil.isSuccess(response)){
            return response.jsonArrayValue("content");
        }
        logger.error("获取每个meter的起始寄存器地址出错：{}", RestUtil.errorMsg(response));
        return null;
    }

}

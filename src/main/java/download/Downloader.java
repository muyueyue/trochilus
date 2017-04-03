package download;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Request;
import utils.Util;

import java.io.IOException;

/**
 * Created by pjh on 2017/1/22.
 */
public class Downloader {

    private static final Logger logger = LoggerFactory.getLogger(Downloader.class);

    public static String getHtml(Request request){
        try{
            if(request == null){
                return null;
            }
            logger.info("正在爬取 {} 页面", request.getUrl());
            HttpResponse httpResponse = request.send();
            if(httpResponse == null || httpResponse.getEntity() == null){
                return null;
            }
            HttpEntity entity = httpResponse.getEntity();
            byte[] bytes = EntityUtils.toByteArray(entity);
            if(bytes.length == 0){
                return null;
            }
            String charSet = Util.getEncoding(bytes);
            if(charSet == null){
                charSet = "UTF-8";
            }
            String html = new String(bytes, charSet);
            return html;
        }catch (IOException e){
            logger.error("获取网页源代码出错：{}", e.toString());
            return null;
        }
    }

    public JSONArray getJsonArray(String url){
        return null;
    }

    public JSONObject getJsonObject(String url){
        return null;
    }
}

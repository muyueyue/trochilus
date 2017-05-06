package download;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import utils.Request;
import utils.Response;
import utils.Util;

import java.io.IOException;

/**
 * Created by pjh on 2017/1/22.
 *
 * @author jiahao.pjh@gmail.com
 */
public class Downloader {

    private static final Logger logger = LoggerFactory.getLogger(Downloader.class);

    public static Html getHtml(Request request){
        try{
            if(request == null){
                return null;
            }
            logger.info("正在爬取页面: {}", request.getUrl());
            Response response = request.send();
            if(response == null){
                return null;
            }
            String html = response.getContent();
            if(html == null){
                return null;
            }
            return new Html(html);
        }catch (Exception e){
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

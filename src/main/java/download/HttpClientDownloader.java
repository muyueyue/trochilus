package download;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Page;
import utils.Config;
import utils.Method;
import utils.Request;
import utils.Util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pjh on 2017/1/22.
 */
public class HttpClientDownloader implements  Downloader{

    private static final Logger logger = LoggerFactory.getLogger(HttpClientDownloader.class);

    @Override
    public String getHtml(Request request){
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

    @Override
    public JSONArray getJsonArray(String url){
        return null;
    }

    @Override
    public JSONObject getJsonObject(String url){
        return null;
    }
}

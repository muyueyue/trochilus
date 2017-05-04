package name.pjh.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import name.pjh.redis.RedisClient;

import java.util.List;

/**
 * Created by jiahao on 17-5-4.
 *
 * @author jiahao.pjh@gmail.com
 */
public class RedisService {

    public JSONObject getStartUrl(){
        String url = RedisClient.getStartUrl();
        JSONObject data = new JSONObject();
        data.put("startUrl", url);
        return data;
    }

    public JSONArray getStartUrls(int start, int end){
        List<String> urls = RedisClient.getStartUrls(start, end);
        if(urls == null){
            return null;
        }
        JSONArray data = new JSONArray();
        for(String url : urls){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("startUrl", url);
            data.add(jsonObject);
        }
        return data;
    }

    public JSONObject getTargetUrl(){
        String url = RedisClient.getTargetUrl();
        JSONObject data = new JSONObject();
        data.put("targetUrl", url);
        return data;
    }

    public JSONArray getTargetUrls(int start, int end){
        List<String> urls = RedisClient.getTargetUrls(start, end);
        if(urls == null){
            return null;
        }
        JSONArray data = new JSONArray();
        for(String url : urls){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("targetUrl", url);
            data.add(jsonObject);
        }
        return data;
    }
}

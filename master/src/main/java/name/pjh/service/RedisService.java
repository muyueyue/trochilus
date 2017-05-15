package name.pjh.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import name.pjh.redis.RedisClient;
import name.pjh.spider.SpiderInfo;
import name.pjh.spider.SpiderPool;
import name.pjh.utils.StringUtil;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by jiahao on 17-5-4.
 *
 * @author jiahao.pjh@gmail.com
 */

@Service
public class RedisService {

    public JSONObject getStartUrl(String spiderId){
        String url = RedisClient.getStartUrl();
        SpiderInfo spiderInfo = SpiderPool.getInstance().getSpider(spiderId);
        String pre = String.valueOf(System.currentTimeMillis());
        spiderInfo.getBackupStartUrl().offer(pre.concat(url));
        JSONObject data = new JSONObject();
        data.put("startUrl", url);
        return data;

    }

    public JSONArray getStartUrls(String spiderId, int start, int end){
        synchronized (RedisService.class){
            List<String> urls = RedisClient.getStartUrls(start, end);
            if(urls == null){
                return null;
            }
            SpiderInfo spiderInfo = SpiderPool.getInstance().getSpider(spiderId);
            String pre = String.valueOf(System.currentTimeMillis());
            JSONArray data = new JSONArray();
            for(String url : urls){
                spiderInfo.getBackupStartUrl().offer(pre.concat(url));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("startUrl", url);
                data.add(jsonObject);
            }
            return data;
        }
    }

    public JSONObject getTargetUrl(String spiderId){
        String url = RedisClient.getTargetUrl();
        SpiderInfo spiderInfo = SpiderPool.getInstance().getSpider(spiderId);
        String pre = String.valueOf(System.currentTimeMillis());
        spiderInfo.getBackupTargetUrl().offer(pre.concat(url));
        JSONObject data = new JSONObject();
        data.put("targetUrl", url);
        return data;
    }

    public JSONArray getTargetUrls(String spiderId, int start, int end){
        synchronized (RedisService.class){
            List<String> urls = RedisClient.getTargetUrls(start, end);
            if(urls == null){
                return null;
            }
            SpiderInfo spiderInfo = SpiderPool.getInstance().getSpider(spiderId);
            String pre = String.valueOf(System.currentTimeMillis());
            JSONArray data = new JSONArray();
            for(String url : urls){
                spiderInfo.getBackupTargetUrl().offer(pre.concat(url));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("targetUrl", url);
                data.add(jsonObject);
            }
            return data;
        }
    }

    public void addStartUrl(JSONObject jsonObject){
        if(jsonObject == null){
            return;
        }
        String url = jsonObject.getString("startUrl");
        if(StringUtil.isNotURL(url) || StringUtil.urlIsRepetition(url)){
            return;
        }
        RedisClient.addToStartUrls(url);
    }

    public void addStartUrl(JSONArray jsonArray){
        if(jsonArray == null){
            return;
        }
        for(int i = 0; i < jsonArray.size(); i++){
            String url = jsonArray.getJSONObject(i).getString("startUrl");
            if(StringUtil.isNotURL(url) || StringUtil.urlIsRepetition(url)){
                continue;
            }
            RedisClient.addToStartUrls(url);
        }
    }
    public void addStartUrl(String url){
        RedisClient.addToStartUrls(url);
    }

    public void addTargetUrl(String url){
        RedisClient.addToTargetUrls(url);
    }

    public void addTargetUrl(JSONObject jsonObject){
        if(jsonObject == null){
            return;
        }
        String url = jsonObject.getString("targetUrl");
        if(StringUtil.isNotURL(url) || StringUtil.urlIsRepetition(url)){
            return;
        }
        RedisClient.addToTargetUrls(url);
    }

    public void addTargetUrl(JSONArray jsonArray){
        if(jsonArray == null){
            return;
        }
        for(int i = 0; i < jsonArray.size(); i++){
            String url = jsonArray.getJSONObject(i).getString("targetUrl");
            if(StringUtil.isNotURL(url) || StringUtil.urlIsRepetition(url)){
                continue;
            }
            RedisClient.addToTargetUrls(url);
        }
    }

    public void addFinishUrl(JSONObject jsonObject){
        if(jsonObject == null){
            return;
        }
        String url = jsonObject.getString("finishUrl");
        if(StringUtil.isNotURL(url) || StringUtil.urlIsRepetition(url)){
            return;
        }
        RedisClient.addToFinishUrls(url);
    }

    public void addFinishUrl(JSONArray jsonArray){
        if(jsonArray == null){
            return;
        }
        for(int i = 0; i < jsonArray.size(); i++){
            String url = jsonArray.getJSONObject(i).getString("finishUrl");
            if(StringUtil.isNotURL(url) || StringUtil.urlIsRepetition(url)){
                continue;
            }
            RedisClient.addToFinishUrls(url);
        }
    }
}

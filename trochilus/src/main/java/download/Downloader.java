package download;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import utils.Request;

/**
 * 此接口为爬虫下载网页源代码的接口
 * Created by pjh on 2017/1/22.
 */
public interface Downloader {

    /**
     * 获取页面的html代码
     * @return
     */
    String getHtml(Request request);

    /**
     * 获取JS动态获取JSONArray数据
     * @return
     */
    JSONArray getJsonArray(String url);


    /**
     * 获取JS动态获取JSONObject数据
     * @return
     */
    JSONObject getJsonObject(String url);

}

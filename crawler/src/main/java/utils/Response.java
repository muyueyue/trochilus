package utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jiahao on 17-5-6.
 *
 * @author jiahao.pjh@gmail.com
 */
public class Response {

    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private HttpResponse httpResponse;

    private String content;

    public Response(HttpResponse httpResponse){
        this.httpResponse = httpResponse;
        setContent();
    }

    public void setContent(){
        try {
            if(this.httpResponse == null || this.httpResponse.getEntity() == null){
                return;
            }
            HttpEntity entity = this.httpResponse.getEntity();
            byte[] bytes = EntityUtils.toByteArray(entity);
            if(bytes.length == 0){
                return;
            }
            String charSet = Util.getEncoding(bytes);
            if(charSet == null){
                charSet = "UTF-8";
            }
            this.content = new String(bytes, charSet);
        }catch (Exception e){
            logger.error("解析请求的返回出现异常");
        }
    }

    public int getStatus(){
        return httpResponse.getStatusLine().getStatusCode();
    }
    public boolean isSuccess(){
        JSONObject jsonObject = JSONObject.parseObject(this.content);
        return jsonObject.containsKey("isSuccess") && jsonObject.getBooleanValue("isSuccess");
    }

    public JSONArray getJsonArrayValue(String key){
        JSONObject jsonObject = JSONObject.parseObject(this.content);
        if(jsonObject.containsKey(key)){
            return jsonObject.getJSONArray(key);
        }
        return null;
    }

    public JSONObject getJsonObject(String key){
        JSONObject jsonObject = JSONObject.parseObject(this.content);
        if(jsonObject.containsKey(key)){
            return jsonObject.getJSONObject(key);
        }
        return null;
    }

    public String getContent() {
        return content;
    }

    public HttpResponse getHttpResponse() {

        return httpResponse;
    }
}

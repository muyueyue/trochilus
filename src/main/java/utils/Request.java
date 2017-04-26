package utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by pjh on 2017/1/22.
 *
 * @author jiahao.pjh@gmail.com
 */
public class Request {

    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    /**
     * Http请求的地址
     */
    private String url;

    /**
     * http的请求方法
     */
    private Method method;

    /**
     * 存储请求的参数
     */
    private HashMap<String, Object> params;

    /**
     * 设置请求的编码格式，默认为UTF-8
     */
    private String charset = Config.charset;

    /**
     * 设置实体的媒体类型，默认为application/json
     */
    private String contentType = Config.contentType;

    /**
     * 请求的超时时间，默认为10000毫秒
     */
    private Integer connectionTimeout = Config.connectionTimeout;

    /**
     * 设置传输超时时间，默认为5000毫秒
     */
    private Integer socketTimeout = Config.socketTimeout;

    public Request(){}

    public static Logger getLogger() {
        return logger;
    }

    public String getUrl() {
        return url;
    }

    public Method getMethod() {
        return method;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public String getCharset() {
        return charset;
    }

    public String getContentType() {
        return contentType;
    }

    public Request(String url, Method method){
        this.url = url;

        this.method = method;
    }

    public Request setUrl(String url) {
        this.url = url;
        return this;
    }

    public Request setMethod(Method method) {
        this.method = method;
        return this;
    }

    public Request setParams(HashMap<String, Object> queryParams) {
        this.params = queryParams;
        return this;
    }

    public Request setParams(String key, Object value){
        this.params.put(key, value);
        return this;
    }

    public Request setCharset(String charset){
        this.charset = charset;
        return this;
    }

    public Request setContentType(String contentType){
        this.contentType = contentType;
        return this;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    /**
     * 生成post请求
     * @return
     */
    public HttpPost post(){
        if(!this.url.startsWith("http")){
            logger.error("URL：{} 格式异常, 爬取的URL应为完整的格式, 须包含协议(Http, Https)", this.url);
            return null;
        }
        HttpPost httpPost = new HttpPost(this.url);
        JSONObject jsonObject = new JSONObject();
        if(this.params != null){
            Iterator iterator = this.params.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry entry = (Map.Entry) iterator.next();
                jsonObject.put((String) entry.getKey(), entry.getValue());
            }
        }
        try {
            StringEntity entity = new StringEntity(jsonObject.toString());
            entity.setContentEncoding(this.charset);
            entity.setContentType(this.contentType);
            httpPost.setEntity(entity);
        }catch (Exception e){
            logger.error("初始化POST请求出错: {}", e.toString());
            return null;
        }
        httpPost.setHeader("User-Agent", Config.userAgent);
        return httpPost;
    }

    /**
     * 生成get请求
     * @return
     */
    public HttpGet get(){
        if(!this.url.startsWith("http")){
            logger.error("URL：{} 格式异常, 爬取的URL应为完整的格式, 须包含协议(Http, Https)", this.url);
            return null;
        }
        if(this.params != null){
            String string = "";
            Iterator iterator = this.params.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry entry = (Map.Entry) iterator.next();
                string.concat(entry.getKey() + "=" + entry.getValue());
            }
            this.url.concat("?" + string);
        }
        HttpGet httpGet = new HttpGet(this.url);
        httpGet.setHeader("User-Agent", Config.userAgent);
        return httpGet;
    }

    /**
     * 发送http请求
     * @return
     */
    public HttpResponse send(){
        HttpClient httpClient = new DefaultHttpClient();
        HttpHost proxy = new HttpHost("183.95.80.165", 8080);
        httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeout);
        //httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        HttpResponse httpResponse;
        try{
            if(this.method == Method.POST){
                HttpPost post = post();
                if(post == null){
                    return null;
                }
                httpResponse = httpClient.execute(post);
            }else if(this.method == Method.GET){
                HttpGet get = get();
                if(get == null){
                    return null;
                }
                httpResponse = httpClient.execute(get);
            }else {
                logger.error("Http请求方法出现异常，未找到POST或GET方法");
                return null;
            }
        }catch (Exception e){
                logger.error("Http请求发送出错: {}", e.toString());
                return null;
        }
        return httpResponse;
    }
}

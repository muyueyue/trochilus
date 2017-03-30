package parse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Method;
import utils.Request;
import utils.StringUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * 每个page对应一个爬取的网页
 * Created by pjh on 2017/2/7.
 */
public class Page {

    private static final Logger logger = LoggerFactory.getLogger(Page.class);

    private String url;

    private Html html;

    private String json;

    public Page(String url, Html html){
            this.url = url;
            this.html = html;
    }
    public Page setUrl(String url){
        this.url = url;
        return this;
    }

    public Page setJson(String json){
        this.json = json;
        return this;
    }

    public String getUrl(){
        return this.url;
    }

    public String getJson(){
        return this.json;
    }

    public Html getHtml(){
        return this.html;
    }

    public Page setHtml(Html html){
        this.html = html;
        return this;
    }
}

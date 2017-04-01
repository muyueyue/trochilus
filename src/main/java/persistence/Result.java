package persistence;

import com.sun.org.apache.regexp.internal.RE;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import us.codecraft.xsoup.Xsoup;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jiahao on 17-4-1.
 */
public class Result {

    private static final Logger logger = LoggerFactory.getLogger(Result.class);

    private Document document;

    public Result(Html html){
        this.document = html.getDocument();
    }

    public HashMap<String, String> getResultById(String id){
        if(this.document == null){
            return null;
        }
        Element element = this.document.getElementById(id);
        if(element == null){
            return null;
        }
        String text = element.text();
        HashMap resultMap = new HashMap();
        resultMap.put(id, text);
        return resultMap;
    }

    public HashMap<String, String> getResultByClassName(String className){
        if(this.document == null){
            return null;
        }
        HashMap<String, String> resultMap = new HashMap();
        Elements elements = this.document.getElementsByClass(className);
        for(Element element : elements){
            if(element != null){
               resultMap.put(className, element.text());
            }
        }
        return resultMap;
    }

    public HashMap<String, String> getResultByTagName(String tagName){
        if(this.document == null){
            return null;
        }
        HashMap<String, String> resultMap = new HashMap<>();
        Elements elements = this.document.getElementsByTag(tagName);
        for(Element element : elements){
            if(element != null){
                resultMap.put(tagName, element.text());
            }
        }
        return resultMap;
    }

    public List<String> select(String xPath){
        if(this.document == null){
            return null;
        }
        return Xsoup.select(this.document, xPath).list();
    }
}

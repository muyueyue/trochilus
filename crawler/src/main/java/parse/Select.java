package parse;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.xsoup.Xsoup;
import utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 对Html解析的类，获取符合用户的内容
 * Created by pjh on 2017/2/10.
 *
 * @author jiahao.pjh@gmail.com
 */
public class Select {

    private static final Logger logger = LoggerFactory.getLogger(Select.class);

    public static List<String> links(Document document){
        if(document == null){
            return null;
        }
        Elements elements = document.select("a[href]");
        List<String> links = new ArrayList<>();
        String regex = "[a-zA-z]+://[^\\s]*";
        for(Element element : elements){
            String href = element.attr("href");
            if(!href.matches(regex) && !links.contains(href)){
                continue;
            }
            links.add(href);
        }
        return links;
    }


    public static List<String> links(Document document, String regex){
        if(document == null){
            return null;
        }
        Elements elements = document.select("a[href]");
        List<String> links = new ArrayList<>();
        for (Element element : elements){
            String href = element.attr("href");
            if(!href.matches(regex) || links.contains(href)){
                continue;
            }
            links.add(href);
        }
        return links;
    }

    public static List<String> xPath(Document document, String xPath){
        if(document == null || StringUtil.isEmpty(xPath)){
            return null;
        }
        List<String> stringList = Xsoup.select(document, xPath).list();
        return stringList;
    }

    public static List<String> regex(String regex){
        return null;
    }
}

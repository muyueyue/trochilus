package parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 对html解析的类，获取符合用户的内容
 * Created by pjh on 2017/2/10.
 */
public class Select {

    private static final Logger logger = LoggerFactory.getLogger(Select.class);

    private Page page;

    public Select(){}

    public Select(Page page){
        this.page = page;
    }

    public String getContent(String cssQuery){
        Document document = page.getHtml().getDocument();
        Elements elements = document.select(cssQuery);
        String content = "";
        for(Element element : elements){
            content = element.text() + '\n';
        }
        return content;
    }

    public List<String> links(Document document){
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

    public List<String> links(Document document, String regex){
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
}

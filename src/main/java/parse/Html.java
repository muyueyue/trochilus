package parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by pjh on 2017/2/10.
 */
public class Html {

    private static final Logger logger = LoggerFactory.getLogger(Html.class);

    private Document document;

    public Html(String text){
        try{
            this.document = Jsoup.parse(text);
        }catch (Exception e){
            logger.error("Jsoup解析文本出错: {}", e.toString());
        }
    }

    public Document getDocument(){
        return this.document;
    }

    public List<String> allLinks(){
        Select select = new Select();
        return select.links(this.document);
    }

    public List<String> linksForRegx(String regx){
        Select select = new Select();
        return select.links(this.document, regx);
    }

    public List<String> select(String xPath){
        Select select = new Select();
        return select.select(this.document, xPath);
    }
}

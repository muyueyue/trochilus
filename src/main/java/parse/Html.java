package parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by pjh on 2017/2/10.
 *
 * @author jiahao.pjh@gmail.com
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

    public List<String> xPath(String xPath){
        return Select.xPath(this.document, xPath);
    }

    public List<String> regex(String regex){
        return Select.regex(regex);
    }
    public List<String> links(){
        return Select.links(this.document);
    }

    public List<String> links(String regex){
        return Select.links(this.document, regex);
    }

}

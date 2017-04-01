package persistence;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import utils.StringUtil;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jiahao on 17-4-1.
 */
public class DataPersistence {

    private static final Logger logger = LoggerFactory.getLogger(DataPersistence.class);

    private Html html;

    public DataPersistence(Html html){
        this.html = html;
    }

    public void addToDBById(String id){
        if(this.html == null || StringUtil.isEmpty(id)){
            logger.error("内容为空！");
            return;
        }
        Result result = new Result(this.html);
        HashMap<String, String> resultMap = result.getResultById(id);
        if(resultMap == null){
            logger.error("内容为空");
            return;
        }
        MongoDBJDBC.insert(resultMap);
    }

    public void addToDBByClassName(String className){
        if(this.html == null || StringUtil.isEmpty(className)){
            logger.error("内容为空！");
            return;
        }
        Result result = new Result(this.html);
        HashMap<String, String> resultMap = result.getResultByClassName(className);
        if(resultMap == null){
            logger.error("内容为空");
            return;
        }
        MongoDBJDBC.insert(resultMap);
    }

    public void addToDBByTagName(String tagName){
        if(this.html == null || StringUtil.isEmpty(tagName)){
            logger.error("内容为空！");
            return;
        }
        Result result = new Result(this.html);
        HashMap<String, String> resultMap = result.getResultByTagName(tagName);
        if(resultMap == null){
            logger.error("内容为空");
            return;
        }
        MongoDBJDBC.insert(resultMap);
    }

    public void addToDBByXpath(String key, String xPath){
        if(this.html == null || StringUtil.isEmpty(xPath)){
            logger.error("内容为空！");
            return;
        }
        Result result = new Result(this.html);
        List<String> values = result.select(xPath);
        for(String string : values){
            MongoDBJDBC.insert(key, string);
        }
    }
}

package main.example;

import com.alibaba.fastjson.JSONObject;
import exception.DBException;
import main.Spider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ParseMethod;
/**
 * Created by jiahao on 17-4-22.
 *
 * @author jiahao.pjh@gmail.com
 */
public class SpiderTest {

    private final static Logger logger = LoggerFactory.getLogger(SpiderTest.class);

    public static void main(String[] args) throws DBException{
        Spider spider = new Spider();
        JSONObject dbConfig = new JSONObject();
        dbConfig.fluentPut("mongoDBHost", "127.0.0.1")
                .fluentPut("mongoDBPort", 27017)
                .fluentPut("databaseName", "spiders")
                .fluentPut("dbCollection", "spiders");
        spider.addStartUrl("http://www.jianshu.com/")
                .addToTargetQueue("//div[@class='content']/a/@href", "http://www.jianshu.com")
                .putField("title", "//div[@class='article']/h1[@class='title']/text()", ParseMethod.XPATH)
                .putField("author", "//div[@class='author']/div[@class='info']/span[@class='name']/a/text()", ParseMethod.XPATH)
                .putField("content", "//div[@class='show-content']/p/text()", ParseMethod.XPATH)
                .thread(1)
                .db(dbConfig)
                .run();

    }
}

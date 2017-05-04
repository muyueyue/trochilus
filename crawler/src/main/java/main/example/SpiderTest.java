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
      /*JSONObject dbConfig = new JSONObject();
        dbConfig.fluentPut("mongoDBHost", "127.0.0.1")
                .fluentPut("mongoDBPort", 27017)
                .fluentPut("databaseName", "spiders")
                .fluentPut("dbCollection", "spiders");*/
        spider.addStartUrl("https://segmentfault.com/questions?page={}", 30)
                .addToTargetQueue("//div[@class='summary']/h2[@class='title']/a/@href", "https://segmentfault.com")
                .putField("title", "//h1[@id='questionTitle']/a/text()", ParseMethod.XPATH)
                .putField("detail", "//div[@class='question fmt']/p/text()", ParseMethod.XPATH)
                .putField("answers", "//div[@class='answer fmt]/p/text()", ParseMethod.XPATH, true)
                .thread(10)
                .run();
    }
}

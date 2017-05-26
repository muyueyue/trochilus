package main.example;

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
    public static void main(String[] args) throws DBException{
        Spider spider = new Spider();
        spider.addToTargetQueue("//dl[@class='contentList']/dd/a/@href", "http://www.pkulaw.cn")
                .putField("content", "//allText()", ParseMethod.XPATH)
                .thread(20)
                .setMasterInfo("http://127.0.0.1:8080")
                .run();
    }
}

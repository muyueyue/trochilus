package main.example;

import main.Spider;
import utils.ParseMethod;
/**
 * Created by jiahao on 17-4-22.
 *
 * @author jiahao.pjh@gmail.com
 */
public class SpiderTest {
    public static void main(String[] args){
        Spider spider = new Spider();
        spider.addStartUrl("https://segmentfault.com/")
                .addToTargetQueue("//section[@class='stream-list__item']/div[@class='summary']/h2[@class='title']/a/@href", "https://segmentfault.com")
                .putField("title", "//h1[@id='questionTitle']/a/text()", ParseMethod.XPATH)
                .putField("detail", "//div[@class='question fmt']/p/text()", ParseMethod.XPATH)
                .thread(5)
                .file("/home/jiahao/data/test")
                .run();
    }
}

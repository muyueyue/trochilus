package dynamicdata;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import java.util.List;

/**
 * Created by jiahao on 17-5-16.
 *
 * @author jiahao.pjh@gmail.com
 *
 * 提供一个获取动态数据的封装
 * 核心为使用Selenium的Java API模拟浏览器
 * 然后对浏览器进行操作
 * 此功能会消耗较多的CPU资源
 */
public class SeleniumDownloader {

    private static final Logger logger = LoggerFactory.getLogger(SeleniumDownloader.class);

    public SeleniumDownloader(){
        System.setProperty("webdriver.chrome.driver","/home/jiahao/tool/chromedriver");
    }

    public Html getHtml(String url){
        try {
            WebDriver webDriver = new ChromeDriver();
            webDriver.get(url);
            Thread.sleep(5000);
            List<WebElement> elements = webDriver.findElements(By.xpath("//div[@class='title-box']/a/@href"));
            for(WebElement element : elements){
                System.out.println("result: " + element.getText());
            }
            return null;
        }catch (Exception e){
            logger.error("获取动态加载的数据出错:{}", e);
            return null;
        }
    }
}

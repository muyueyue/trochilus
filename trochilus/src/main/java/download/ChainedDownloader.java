package download;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.BloomFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 链式爬取网页，从目标网站开始，根据爬取的深度依次爬取网站
 * Created by pjh on 2017/1/23.
 */
public class ChainedDownloader {

    private static final Logger logger = LoggerFactory.getLogger(ChainedDownloader.class);

    /**
     * 存储待爬取的网站地址
     */
    private List<String> links = new ArrayList<>();

    /**
     * 从当前网站爬取的深度,默认为1
     */
    private int deep = 1;

    /**
     * 计数本次链式爬取的总链接数
     */
    private int count = 1;

    private HttpClientDownloader httpClientDownloader = new HttpClientDownloader();

    public void chainedDownload(String url){

       /* BloomFilter urlFilter = new BloomFilter();
        BloomFilter webPageFilter = new BloomFilter();

        if(deep < 1){
            logger.error("爬取的网页深度不能小于1");
            return;
        }
        links.add(url);
        List<String> targetLinks = new ArrayList<>();
        while(deep > 0){
           logger.info("新的一层爬取开始");
           for(String link : links){
               String html = httpClientDownloader.getHtml(link);
               if(html == null){
                   continue;
               }
               if(urlFilter.contains(link) && webPageFilter.contains(html)){
                    logger.info("URL：{} 循坏爬取", link);
                    continue;
               }
               logger.info("URL：{} 爬取完成", link);
               urlFilter.add(link);
               webPageFilter.add(html);
               if(deep == 1){
                   continue;
               }
               Document document = Jsoup.parse(html);
               Elements elements = document.select("a[href]");
               StringBuffer href = new StringBuffer();
               String regex = "[a-zA-z]+://[^\\s]*";
               for(Element element : elements){
                   href.delete(0, href.length());
                   href.append(element.attr("href"));
                   if(href.toString().matches(regex)){
                       if(!targetLinks.contains(href.toString())){
                           targetLinks.add(href.toString());
                           count++;
                           if(count % 500 == 0){
                               System.gc(); //通知JVM尽快执行GC，但JVM并不一定会马上执行GC。
                           }
                           logger.info("add {} to targetLink", href.toString());
                       }
                   }
               }
           }
           links.clear();
           links.addAll(targetLinks);
           targetLinks.clear();
           deep--;
           logger.info("当前层爬取结束");
        }*/
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }

    public int getCount() {
        return count;
    }
}

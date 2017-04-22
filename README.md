# trochilus

[阅读中文](https://github.com/muyueyue/trochilus/blob/master/README-zh.md)

> A lightweight crawler kernel based on Java.
This framework provides some sort of crawler operations that allow users to complete their business with simple code

## Get started

### You can start a crawler by creating a new Spider object and setting the crawler's related business information.

#### As follows:
```
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
```


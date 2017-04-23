# trochilus

[Readme in Chinese](https://github.com/muyueyue/trochilus/blob/master/README.md)

##　简介

> 一个基于Java的轻量级爬虫内核。这个框架提供了一系列的爬虫操作，使用者通过简单的代码编写就能实现自己的业务。

## 开始使用

### 您可以通过新建一个Spider对象，并配置自己的相关业务信息来启动爬虫

#### 如下：

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

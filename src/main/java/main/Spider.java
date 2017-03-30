package main;

import download.HttpClientDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import parse.Page;
import utils.Method;
import utils.Request;

import java.util.List;

/**
 * 爬虫的入口
 * Created by pjh on 2017/1/22.
 */
public class Spider {

    private static final Logger logger = LoggerFactory.getLogger(Spider.class);



    public static void main(String[] args){

        Request statrRequest = new Request("http://news.baidu.com/", Method.GET);
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        Html html = new Html(httpClientDownloader.getHtml(statrRequest));
        Page page = new Page(statrRequest.getUrl(), html);
        /*page.setTargetRequests(html.linksForRegx("[a-zA-z]+://[^\\s]*"), Method.GET);*/

    }
}

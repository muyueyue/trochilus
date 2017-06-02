package pkulaw;

import com.alibaba.fastjson.JSONObject;
import download.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import thread.ThreadPool;
import thread.URLQueue;
import utils.Config;
import utils.Method;
import utils.Request;
import utils.StringUtil;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by jiahao on 17-5-27.
 *
 * @author jiahao.pjh@gmail.com
 */
public class TargetUrlsTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(TargetUrlsTask.class);

    @Override
    public void run(){
        try {
            BlockingQueue<String> targetUrlsQueue = URLQueue.getInstance().getTargetQueue();
            BlockingQueue<String> startUrlsQueue = URLQueue.getInstance().getStartQueue();
            JSONObject result = new JSONObject();
            String  realUrl = "http://www.pkulaw.cn/case/FullText/_getFulltext";
            while (true){
                try {
                    if(Config.urlIndex > Config.endId && targetUrlsQueue.size() == 0 && startUrlsQueue.size() == 0){
                        logger.info("爬虫任务全部完成");
                        System.exit(0);
                    }
                    logger.info("目前targetUrls队列的大小为:{}", targetUrlsQueue.size());
                    String targetUrl = targetUrlsQueue.poll();
                    if(StringUtil.isEmpty(targetUrl)){
                        logger.error("targetUrls队列目前为空");
                        Thread.sleep(5000);
                        continue;
                    }
                    String temp = targetUrl.substring(targetUrl.indexOf("pfnl"), targetUrl.indexOf(".html"));
                    String library = temp.substring(0, 4);
                    String gid = temp.substring(temp.indexOf("_") + 1, temp.length());
                    Request request_1 = new Request(realUrl, Method.POST);
                    Request request_2 = new Request(targetUrl, Method.GET);
                    request_1.setParams("library", library)
                            .setParams("gid", gid)
                            .setParams("loginSucc", "0");
                    Html html_1 = Downloader.getHtml(request_1);
                    Html html_2 = Downloader.getHtml(request_2);
                    if(html_1 == null || html_2 == null){
                        logger.error("根据targetUrls获取的页面数据为空");
                        continue;
                    }
                    List<String> list = html_1.xPath("//allText()");
                    List<String> list_2 = html_2.xPath("//table[@class='articleInfo']/allText()");
                    List<String> list_3 = html_2.xPath("//table[@class='articleInfo']/tbody/tr[1]/td[2]/a/text()");
                    String rowKey = html_2.xPathOne("//table[@class='articleInfo']/tbody/tr[2]/td[2]/text()");
                    String title = html_1.xPathOne("//font[@class='MTitle']/text()");
                    result.put("content", StringUtil.listToString(list));
                    result.put("info", StringUtil.listToString(list_2));
                    String ay = "";
                    for(String string : list_3){
                        ay = ay + string + "/";
                    }
                    rowKey = rowKey + "-" + ay;
                    result.put("rowKey", rowKey);
                    result.put("title", title);
                    result.put("url", targetUrl);
                    logger.info("data:{}", result.toString());
                    /*if(!InsertHbase.insert(result)){
                        targetUrlsQueue.offer(targetUrl);
                    }*/
                }catch (Exception e){
                    OutputException.output("/home/hadoop/hadoop/spider/logs/error.log", "解析targetUrls出错:" + e.toString());
                    //OutputException.output("/home/jiahao/myjar/error.log", "解析targetUrls出错:" + e.toString());
                    logger.error("解析targetUrls出错:", e);
                }
            }
        }catch (Exception e){
            ThreadPool.getInstance().execute(new TargetUrlsTask());
            OutputException.output("/home/hadoop/hadoop/spider/logs/error.log", "解析targetUrls出错:" + e.toString());
            //OutputException.output("/home/jiahao/myjar/error.log", "解析targetUrls出错:" + e.toString());
            logger.error("解析targetUrls出错:", e);
        }
    }
}

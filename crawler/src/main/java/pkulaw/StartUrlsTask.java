package pkulaw;

import download.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import persistence.MongoDBJDBC;
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
public class StartUrlsTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(StartUrlsTask.class);

    @Override
    public void run(){
        BlockingQueue<String> startUrlsQueue = URLQueue.getInstance().getStartQueue();
        BlockingQueue<String> targetUrlsQueue = URLQueue.getInstance().getTargetQueue();
        String pre = "http://www.pkulaw.cn";
        try {
            while (true){
                if(Config.urlIndex >= Config.endId){
                    logger.info("爬虫的startUrl全部获取完毕");
                    continue;
                }
                if(startUrlsQueue.size() == 0){
                    //从Mongdb中获取startUrl
                    logger.info("正在从Mongodb中获取startUrls");
                    logger.info("----------------->urlIndex:{}", Config.urlIndex);
                    List<String> startUrls = MongoDBJDBC.getStartUrls("starturls", Config.urlIndex);
                    if(startUrls == null || startUrls.size() == 0){
                        logger.error("从Mongodb中获取的startUrls为空");
                        continue;
                    }
                    for(String startUrl : startUrls){
                        logger.info("获取到的startUrl为:{}", startUrl);
                        startUrlsQueue.offer(startUrl);
                    }
                    Config.urlIndex = Config.urlIndex + Config.urlSize;
                }
                logger.info("startUrls队列的大小为:{}", startUrlsQueue.size());
                String startUrl = startUrlsQueue.poll();
                if(StringUtil.isEmpty(startUrl)){
                    logger.error("startUrls队列为空");
                    Thread.sleep(5000);
                    continue;
                }
                Request request = new Request(startUrl, Method.GET);
                Html html = Downloader.getHtml(request);
                if(html == null){
                    logger.error("下载startUrl的结果为空");
                    Thread.sleep(5000);
                    continue;
                }
                List<String> targetUrlsList = html.xPath("//dl[@class='contentList']/dd/a/@href");
                if(targetUrlsList== null || targetUrlsList.size() == 0){
                    logger.error("未解析出targetUrl");
                    Thread.sleep(5000);
                    continue;
                }
                for(String targetUrl : targetUrlsList){
                    String url = pre.concat(targetUrl);
                    logger.info("解析出的targetUrl为:{}", url);
                    targetUrlsQueue.offer(url);
                }
                Thread.sleep(5000);
            }
        }catch (Exception e){
            ThreadPool.getInstance().execute(new StartUrlsTask());
            OutputException.output("/home/hadoop/hadoop/spider/logs/error.log", "解析startUrls出错:" + e.toString());
            logger.info("下载解析startUrls出错:", e);
        }
    }
}

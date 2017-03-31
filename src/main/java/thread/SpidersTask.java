package thread;

import download.HttpClientDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import parse.Page;
import parse.Select;
import utils.Method;
import utils.Request;

/**
 *
 * Created by jiahao on 17-3-31.
 */
public class SpidersTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SpidersTask.class);

    @Override
    public void run(){
        URLQueue urlQueue = URLQueue.getInstance();
        while (true){
            try {
                String url = urlQueue.getTargetQueue().poll();
                Request request = new Request(url, Method.GET);
                HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
                Html html = new Html(httpClientDownloader.getHtml(request));
                Page page = new Page(request.getUrl(), html);
                Select select = new Select(page);
                urlQueue.addURLToTargetQueue(select.links(html.getDocument()));
                urlQueue.addURLToFinishQueue(url);
                Thread.sleep(500);
            }catch (Exception e){
                logger.error("爬虫任务出错: {}", e);
                return;
            }
        }
    }
}

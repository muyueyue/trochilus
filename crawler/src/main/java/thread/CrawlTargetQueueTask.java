package thread;

import com.alibaba.fastjson.JSONObject;
import download.Downloader;
import manage.WorkInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import persistence.ConsolePrint;
import persistence.DataPersistence;
import persistence.FilePersistence;
import utils.*;
import java.util.List;

/**
 * Created by jiahao on 17-4-3.
 *
 * @author jiahao.pjh@gmail.com
 */
public class CrawlTargetQueueTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(CrawlTargetQueueTask.class);

    private List<JSONObject> keyRegexMethod;

    private List<String> persistence;

    public CrawlTargetQueueTask(List<JSONObject> keyRegexMethod, List<String> persistence){
        this.keyRegexMethod = keyRegexMethod;
        this.persistence = persistence;
    }

    @Override
    public void run(){
        try {
            URLQueue urlQueue = URLQueue.getInstance();
            while (true){
                String targetUrl = urlQueue.getTargetQueue().poll();
                if(targetUrl == null){
                    Thread.sleep(Config.errorSleepTime);
                    continue;
                }
                String temp = targetUrl.substring(targetUrl.indexOf("pfnl"), targetUrl.indexOf(".html"));
                String library = temp.substring(0, 4);
                String gid = temp.substring(temp.indexOf("_") + 1, temp.length());
                String  realUrl = "http://www.pkulaw.cn/case/FullText/_getFulltext";
                Request request_1 = new Request(realUrl, Method.POST);
                Request request_2 = new Request(targetUrl, Method.GET);
                request_1.setParams("library", library)
                        .setParams("gid", gid)
                        .setParams("loginSucc", "0");
                Html html_1 = Downloader.getHtml(request_1);
                Html html_2 = Downloader.getHtml(request_2);
                if(html_1 == null || html_2 == null){
                    continue;
                }
                WorkInfo.getInstance().setFinishTargetUrls(1);
                JSONObject result = new JSONObject();
                for(JSONObject jsonObject : keyRegexMethod){
                    if(jsonObject.getString("method") == ParseMethod.REGEX.toString()){
                        List<String> list = html_1.regex(jsonObject.getString("regex"));
                        List<String> list_2 = html_2.regex(jsonObject.getString("regex"));
                        if(jsonObject.containsKey("isPartition") && jsonObject.getBoolean("isPartition")){
                            for(int i = 0; i < list.size(); i++){
                                result.put(jsonObject.getString("key").concat(String.valueOf(i)), list.get(i));
                            }
                            for(int i = 0; i < list_2.size(); i++){
                                result.put(jsonObject.getString("key").concat(String.valueOf(i)), list_2.get(i));
                            }
                        }else {
                            result.put(jsonObject.getString("key"), StringUtil.listToString(list));
                            result.put(jsonObject.getString("key"), StringUtil.listToString(list_2));
                        }
                    }else if(jsonObject.getString("method") == ParseMethod.XPATH.toString()){
                        List<String> list = html_1.xPath(jsonObject.getString("regex"));
                        List<String> list_2 = html_2.xPath("//table[@class='articleInfo']/allText()");
                        List<String> list_3 = html_2.xPath("//table[@class='articleInfo']/tbody/tr[1]/td[2]/a/text()");
                        String rowKey = html_2.xPathOne("//table[@class='articleInfo']/tbody/tr[2]/td[2]/text()");
                        String title = html_2.xPathOne("//div[@class='article']/h3/text()");
                        if(jsonObject.containsKey("isPartition") && jsonObject.getBoolean("isPartition")){
                            for(int i = 0; i < list.size(); i++){
                                result.put(jsonObject.getString("key").concat(String.valueOf(i)), list.get(i));
                            }
                            for(int i = 0; i < list_2.size(); i++){
                                result.put("info".concat(String.valueOf(i)), list_2.get(i));
                            }
                            String ay = "";
                            for(String string : list_3){
                                ay = ay + string;
                            }
                            rowKey = rowKey + "-" + ay;
                            result.put("rowKey", rowKey);
                            result.put("title", title);
                            result.put("url", targetUrl);
                        }else {
                            result.put(jsonObject.getString("key"), StringUtil.listToString(list));
                            result.put("info", StringUtil.listToString(list_2));
                            String ay = "";
                            for(String string : list_3){
                                ay = ay + string + "/";
                            }
                            rowKey = rowKey + "-" + ay;
                            result.put("rowKey", rowKey);
                            result.put("title", title);
                            result.put("url", targetUrl);
                        }
                    }else {
                        continue;
                    }
                }
                if(persistence.contains("console")){
                    ConsolePrint.print(result);
                    //ResultQueue.addResult(result);
                }
                if(persistence.contains("file")){
                    FilePersistence.write(result);
                }
                if(persistence.contains("db")){
                    DataPersistence.insert(result);
                }
                Thread.sleep(1000);
            }
        }catch (Exception e){
            logger.error("爬虫任务出错: {}", e);
        }
    }
}

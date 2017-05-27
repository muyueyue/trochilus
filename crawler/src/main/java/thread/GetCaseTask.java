package thread;

import com.alibaba.fastjson.JSONObject;
import download.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import persistence.MongoDBJDBC;
import redis.GetTargetUrlsTask;
import utils.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiahao on 17-5-26.
 *
 * @author jiahao.pjh@gmail.com
 */
public class GetCaseTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(GetCaseTask.class);

    @Override
    public void run(){
        BlockingQueue<String> caseQueue = CaseQueue.getCaseQueue();
        caseQueue.offer("001");
        CaseQueue.putSet("001");

        caseQueue.offer("002");
        CaseQueue.putSet("002");

        caseQueue.offer("003");
        CaseQueue.putSet("003");

        caseQueue.offer("005");
        CaseQueue.putSet("005");

        caseQueue.offer("006");
        CaseQueue.putSet("006");

        caseQueue.offer("007");
        CaseQueue.putSet("007");
        int count = 0;
        long rowId = 0;
        String baseUrl = "http://www.pkulaw.cn/case/Search/Cluster?Menu=CASE&IsFullTextSearch=False&MatchType=Exact&Keywords=&FilterItems.CourtGrade=&FilterItems.TrialStep=&FilterItems.DocumentAttr=&FilterItems.TrialStepCount=&FilterItems.LastInstanceDate=&FilterItems.CriminalPunish=&FilterItems.SutraCase=&FilterItems.CaseGistMark=&FilterItems.ForeignCase=&FilterItems.CourtGrade=&FilterItems.TrialStep=&FilterItems.DocumentAttr=&FilterItems.TrialStepCount=&FilterItems.LastInstanceDate=&FilterItems.CriminalPunish=&FilterItems.SutraCase=&FilterItems.CaseGistMark=&FilterItems.ForeignCase=&Library=PFNL&OrderByIndex=0&GroupByIndex=0&ShowType=1&ClassCodeKey={}%2C%2C&X-Requested-With=XMLHttpRequest";
        while (true){
            try {
                Thread.sleep(100);
                if(count == 100){
                    break;
                }
                String caseCodeKey = caseQueue.poll(5, TimeUnit.SECONDS);
                JSONObject caseCodeKeyJson = new JSONObject();
                JSONObject startUrlsJson = new JSONObject();
                if(StringUtil.isEmpty(caseCodeKey)){
                    count++;
                    continue;
                }
                count = 0;
                Request request = new Request(baseUrl.replace("{}", caseCodeKey), Method.GET);
                Html html = Downloader.getHtml(request);
                List<String> caseCodeKeys = html.xPath("//div[@class='classify'][1]/div[@class='classifyListMain']/ul[@class='classifyList']/li/html()");
                for(String string : caseCodeKeys){
                    String value = string.substring(string.indexOf("cluster_code=\"") + 14, string.indexOf("\"><i class=\"c-icon\">"));
                    Matcher m = Pattern.compile("(\\d+)").matcher(string);
                    String total = "";
                    while (m.find()){
                        total = m.group();
                    }
                    if(StringUtil.isEmpty(value) || StringUtil.isEmpty(total)){
                        continue;
                    }
                    if(!CaseQueue.putSet(value)){
                        continue;
                    }
                    logger.info("{}----->{}------>{}", string, value, total);
                    caseQueue.offer(value);
                    caseCodeKeyJson.fluentPut("codeKey", value)
                            .fluentPut("total", total);
                    MongoDBJDBC.insert("casecodekey", caseCodeKeyJson);
                    int pageSize = 50, pageIndex;
                    int totalRow = Integer.valueOf(total).intValue();
                    if(totalRow % pageSize == 0){
                        pageIndex = totalRow / pageSize;
                        if(pageIndex > 500){
                            pageIndex = 500;
                        }
                    }else {
                        pageIndex = totalRow / pageSize + 1;
                        if(pageIndex > 500){
                            pageIndex = 500;
                        }
                    }
                    for(int i = 0; i < pageIndex; i++){
                        String startUrl = "http://www.pkulaw.cn/case/Search/Record?Menu=CASE&IsFullTextSearch=False&MatchType=Exact&Keywords=&OrderByIndex=0&GroupByIndex=0&ShowType=1&ClassCodeKey="+value+"%2C%2C&OrderByIndex=0&GroupByIndex=0&ShowType=1&ClassCodeKey="+value+"%2C%2C&Library=PFNL&FilterItems.CourtGrade=&FilterItems.TrialStep=&FilterItems.DocumentAttr=&FilterItems.TrialStepCount=&FilterItems.LastInstanceDate=&FilterItems.CriminalPunish=&FilterItems.SutraCase=&FilterItems.CaseGistMark=&FilterItems.ForeignCase=&SubKeyword=%E5%9C%A8%E7%BB%93%E6%9E%9C%E7%9A%84%E6%A0%87%E9%A2%98%E4%B8%AD%E6%A3%80%E7%B4%A2&GroupIndex=&GroupValue=&TitleKeywords=&FullTextKeywords=&Pager.PageSize="+pageSize+"&Pager.PageIndex="+i+"&X-Requested-With=XMLHttpRequest";
                        rowId++;
                        startUrlsJson.fluentPut("rowId", String.valueOf(rowId))
                                .fluentPut("startUrl", startUrl)
                                .fluentPut("status", "0");
                        MongoDBJDBC.insert("starturls", startUrlsJson);
                    }
                }
            }catch (Exception e){
                logger.error("获取caseCodeKey出错:{}", e);
            }
        }
    }
}

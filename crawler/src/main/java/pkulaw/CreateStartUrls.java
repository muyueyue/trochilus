package pkulaw;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import download.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import thread.URLQueue;
import utils.Method;
import utils.Request;
import utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by jiahao on 17-6-2.
 *
 * @author jiahao.pjh@gmail.com
 */
public class CreateStartUrls {

    private static final Logger logger = LoggerFactory.getLogger(CreateStartUrls.class);

    public static void create(JSONObject config, String parentCaseCode, String caseCode, int pageSize, int pageIndex){
        BlockingQueue<String> startUrls = URLQueue.getInstance().getStartQueue();
        if(config == null){
            logger.error("时间格式有误");
            return;
        }
        String startTime = config.getString("startTime");
        String endTime = config.getString("endTime");
        for(int i = 0; i < pageIndex; i++){
            String startUrl = "http://www.pkulaw.cn/case/Search/Record?Menu=CASE&IsFullTextSearch=False&MatchType=Exact&Keywords=&IsAdv=True&AdvTitleMatchType=Exact&AdvFullTextMatchType=Exact&AdvSearchDic.Title=&AdvSearchDic.FullText=&AdvSearchDic.Category="+parentCaseCode+"%2C&AdvSearchDic.CaseFlag=&AdvSearchDic.LastInstanceCourt=&AdvSearchDic.CourtGrade=&AdvSearchDic.Judge=&AdvSearchDic.Agent=&AdvSearchDic.TrialStep=&AdvSearchDic.DocumentAttr=&AdvSearchDic.TrialStepCount=&AdvSearchDic.LastInstanceDate=%7B%22Start%22%3A%22"+startTime+"%22%2C%22End%22%3A%22"+endTime+"%22%7D&AdvSearchDic.Core=&AdvSearchDic.DisputedIssues=&AdvSearchDic.CaseGist=&AdvSearchDic.CaseGrade=&AdvSearchDic.CriminalPunish=&AdvSearchDic.Accusation=&AdvSearchDic.Criminal=&AdvSearchDic.CivilLaw=&AdvSearchDic.CaseGistMark=&AdvSearchDic.GuidingCase=&AdvSearchDic.GuidingCaseNO=&AdvSearchDic.GuidingCaseDoc=&AdvSearchDic.IssueDate=%7B%22Start%22%3A%22%22%2C%22End%22%3A%22%22%7D&AdvSearchDic.ForeignCase=&OrderByIndex=0&GroupByIndex=0&ShowType=1&ClassCodeKey="+caseCode+"%2C%2C%2B&OrderByIndex=0&GroupByIndex=0&ShowType=1&ClassCodeKey="+caseCode+"%2C%2C%2B&Library=PFNL&SubKeyword=%E5%9C%A8%E7%BB%93%E6%9E%9C%E7%9A%84%E6%A0%87%E9%A2%98%E4%B8%AD%E6%A3%80%E7%B4%A2&GroupIndex=&GroupValue=&TitleKeywords=&FullTextKeywords=&Pager.PageSize="+pageSize+"&Pager.PageIndex="+i+"&X-Requested-With=XMLHttpRequest";
            startUrls.offer(startUrl);
        }
    }

    public static JSONArray format(String startTime, String endTime, String gap){
        try {
            JSONArray times = new JSONArray();
            if(gap.equals("A")){
                JSONObject time = new JSONObject();
                time.fluentPut("startTime", startTime)
                    .fluentPut("endTime", endTime);
                times.add(time);
                return times;
            }
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy.MM.dd");
            Calendar calendar = Calendar.getInstance();
            while (true){
                JSONObject time = new JSONObject();
                time.fluentPut("startTime", startTime);
                calendar.setTime(dateFormat.parse(startTime));
                if(gap.equals("D")){
                    calendar.add(Calendar.DATE, 1);
                }else if(gap.equals("M")){
                    calendar.add(Calendar.MONTH, 1);
                }else {
                    calendar.add(Calendar.YEAR, 1);
                }
                if(dateFormat.format(calendar.getTime()).toString().compareTo(endTime) > 0){
                    time.fluentPut("endTime", endTime);
                    times.add(time);
                    break;
                }else {
                    time.fluentPut("endTime", dateFormat.format(calendar.getTime()).toString());
                    startTime = dateFormat.format(calendar.getTime()).toString();
                }
                times.add(time);
            }
            return times;
        }catch (Exception e){
            logger.error("解析时间出错");
            return null;
        }
    }

    public static boolean getTargetUrls(BlockingQueue<String> targetUrlsQueue, String startUrl, String pre){
        if(StringUtil.isEmpty(startUrl)){
            logger.error("startUrl为空");
            return false;
        }
        Request request = new Request(startUrl, Method.GET);
        Html html = Downloader.getHtml(request);
        if(html == null){
            logger.error("下载startUrl的结果为空");
            return false;
        }
        List<String> targetUrlsList = html.xPath("//dl[@class='contentList']/dd/a/@href");
        if(targetUrlsList== null || targetUrlsList.size() == 0){
            logger.error("未解析出targetUrl");
            return false;
        }
        for(String targetUrl : targetUrlsList){
            String url = pre.concat(targetUrl);
            logger.info("解析出的targetUrl为:{}", url);
            targetUrlsQueue.offer(url);
        }
        return true;
    }

    public static void main(String[] args) {
        JSONArray times = new CreateStartUrls().format("2015.05.12", "2017.06.22", "M");
        logger.info(times.toJSONString());
    }
}

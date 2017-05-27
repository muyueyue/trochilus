package thread;

import download.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import redis.GetTargetUrlsTask;
import utils.*;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

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
        BlockingQueue<String> baskupQueue = CaseQueue.getBackupCaseQueue();
        caseQueue.offer("001");
        baskupQueue.offer("001");
        CaseQueue.putSet("001");

        caseQueue.offer("002");
        baskupQueue.offer("002");
        CaseQueue.putSet("002");

        caseQueue.offer("003");
        baskupQueue.offer("003");
        CaseQueue.putSet("003");

        caseQueue.offer("005");
        baskupQueue.offer("005");
        CaseQueue.putSet("005");

        caseQueue.offer("006");
        baskupQueue.offer("006");
        CaseQueue.putSet("006");

        caseQueue.offer("007");
        baskupQueue.offer("007");
        CaseQueue.putSet("007");
        //int k = 6;
        int count = 0;
        String baseUrl = "http://www.pkulaw.cn/case/Search/Cluster?Menu=CASE&IsFullTextSearch=False&MatchType=Exact&Keywords=&FilterItems.CourtGrade=&FilterItems.TrialStep=&FilterItems.DocumentAttr=&FilterItems.TrialStepCount=&FilterItems.LastInstanceDate=&FilterItems.CriminalPunish=&FilterItems.SutraCase=&FilterItems.CaseGistMark=&FilterItems.ForeignCase=&FilterItems.CourtGrade=&FilterItems.TrialStep=&FilterItems.DocumentAttr=&FilterItems.TrialStepCount=&FilterItems.LastInstanceDate=&FilterItems.CriminalPunish=&FilterItems.SutraCase=&FilterItems.CaseGistMark=&FilterItems.ForeignCase=&Library=PFNL&OrderByIndex=0&GroupByIndex=0&ShowType=1&ClassCodeKey={}%2C%2C&X-Requested-With=XMLHttpRequest";
        while (true){
            try {
                if(count == 100){
                    break;
                }
                String caseCodeKey = caseQueue.poll(5, TimeUnit.SECONDS);
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
                    if(!CaseQueue.putSet(value)){
                        continue;
                    }
                    caseQueue.offer(value);
                    baskupQueue.offer(value);
                    /*k++;
                    logger.info("------->{}----------->{}", k, value);*/
                }
            }catch (Exception e){
                logger.error("获取caseCodeKey出错:{}", e);
            }
        }
    }

    public static void main(String[] args) {
        GetCaseTask task = new GetCaseTask();
        task.run();
    }
}

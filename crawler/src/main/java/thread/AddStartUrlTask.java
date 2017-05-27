package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CaseQueue;
import utils.StringUtil;

import java.util.concurrent.BlockingQueue;

/**
 * Created by jiahao on 17-5-22.
 *
 * @author jiahao.pjh@gmail.com
 */
public class AddStartUrlTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(AddStartUrlTask.class);

    @Override
    public void run(){
        try {
            int count = 1;
            URLQueue instance = URLQueue.getInstance();
            BlockingQueue<String> backupCaseQueue = CaseQueue.getBackupCaseQueue();
            while (true){
                String caseCodeKey = backupCaseQueue.poll();
                if(StringUtil.isEmpty(caseCodeKey)){
                    continue;
                }
                for(int j = 0; j<= 500; j++){
                    if(count % 100 == 0){
                        Thread.sleep(100000);
                    }
                    instance.addToCacheStartQueue("http://www.pkulaw.cn/case/Search/Record?Menu=CASE&IsFullTextSearch=False&MatchType=Exact&Keywords=&OrderByIndex=0&GroupByIndex=0&ShowType=1&ClassCodeKey="+caseCodeKey+"%2C%2C&OrderByIndex=0&GroupByIndex=0&ShowType=1&ClassCodeKey="+caseCodeKey+"%2C%2C&Library=PFNL&FilterItems.CourtGrade=&FilterItems.TrialStep=&FilterItems.DocumentAttr=&FilterItems.TrialStepCount=&FilterItems.LastInstanceDate=&FilterItems.CriminalPunish=&FilterItems.SutraCase=&FilterItems.CaseGistMark=&FilterItems.ForeignCase=&SubKeyword=%E5%9C%A8%E7%BB%93%E6%9E%9C%E7%9A%84%E6%A0%87%E9%A2%98%E4%B8%AD%E6%A3%80%E7%B4%A2&GroupIndex=&GroupValue=&TitleKeywords=&FullTextKeywords=&Pager.PageSize=20&Pager.PageIndex="+j+"&X-Requested-With=XMLHttpRequest");
                    count++;
                }
            }
        }catch (Exception e){
            logger.error("向Master中添加StartUrl中出错:{}", e);
        }
    }
}

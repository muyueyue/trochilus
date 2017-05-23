package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jiahao on 17-5-22.
 *
 * @author jiahao.pjh@gmail.com
 */
public class AddStartUrlTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(AddStartUrlTask.class);

    @Override
    public void run(){
        URLQueue instance = URLQueue.getInstance();
        for(int i = 1; i <= 7; i++){
            if(i == 4){
                continue;
            }
            for(int j = 1; j <= 100; j++){
                instance.addToCacheStartQueue("http://www.pkulaw.cn/case/Search/Record?Menu=CASE&IsFullTextSearch=False&MatchType=Exact&Keywords=&OrderByIndex=0&GroupByIndex=0&ShowType=1&ClassCodeKey=00" + i + "%2C%2C&OrderByIndex=0&GroupByIndex=0&ShowType=1&ClassCodeKey=00" + i + "%2C%2C&Library=PFNL&FilterItems.CourtGrade=&FilterItems.TrialStep=&FilterItems.DocumentAttr=&FilterItems.TrialStepCount=&FilterItems.LastInstanceDate=&FilterItems.CriminalPunish=&FilterItems.SutraCase=&FilterItems.CaseGistMark=&FilterItems.ForeignCase=&SubKeyword=%E5%9C%A8%E7%BB%93%E6%9E%9C%E7%9A%84%E6%A0%87%E9%A2%98%E4%B8%AD%E6%A3%80%E7%B4%A2&GroupIndex=&GroupValue=&TitleKeywords=&FullTextKeywords=&Pager.PageSize=20&Pager.PageIndex=" + j + "&X-Requested-With=XMLHttpRequest");
            }
        }
    }
}

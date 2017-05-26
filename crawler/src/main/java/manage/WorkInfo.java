package manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by jiahao on 17-5-23.
 *
 * @author jiahao.pjh@gmail.com
 *
 * 对爬虫的工作状态进行描述
 */
public class WorkInfo {

    private static final Logger logger = LoggerFactory.getLogger(WorkInfo.class);

    private static WorkInfo instance;

    private volatile static long startUrlsNum;

    private volatile static long targetUrlsNum;

    private volatile static long finishStartUrlsNum;

    private volatile static long finishTargetUrls;

    private WorkInfo(){}

    public synchronized static WorkInfo getInstance(){
        if(instance == null){
            instance = new WorkInfo();
        }
        return instance;
    }

    public long getStartUrlsNum() {
        return startUrlsNum;
    }

    public void setStartUrlsNum(long num) {
        startUrlsNum = startUrlsNum + num;
    }

    public long getTargetUrls() {
        return targetUrlsNum;
    }

    public void setTargetUrls(long num) {
        targetUrlsNum = targetUrlsNum + num;
    }

    public long getFinishStartUrlsNum() {
        return finishStartUrlsNum;
    }

    public void setFinishStartUrlsNum(long num) {
        finishStartUrlsNum = finishStartUrlsNum + num;
    }

    public long getFinishTargetUrls() {
        return finishTargetUrls;
    }

    public void setFinishTargetUrls(long num) {
        finishTargetUrls = finishTargetUrls + num;
    }
}

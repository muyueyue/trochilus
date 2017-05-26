package name.pjh.spider;

import name.pjh.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by jiahao on 17-5-4.
 *
 * @author jiahao.pjh@gmail.com
 */
public class SpiderInfo {

    private static final Logger logger = LoggerFactory.getLogger(SpiderInfo.class);

    private String spiderId;

    private BlockingQueue<String> backupStartUrl;

    private BlockingQueue<String> backupTargetUrl;

    private long lastTime;

    private String clientIp;

    private long startUrlsNum;

    private long targetUrlsNum;

    private long finishStartUrlsNum;

    private long finishTargetUrlsNum;

    public SpiderInfo(String spiderId){
        this.spiderId = spiderId;
        this.backupStartUrl = new LinkedBlockingDeque<>();
        this.backupTargetUrl = new LinkedBlockingDeque<>();
    }

    public BlockingQueue<String> getBackupStartUrl() {
        return backupStartUrl;
    }

    public BlockingQueue<String> getBackupTargetUrl() {
        return this.backupTargetUrl;
    }

    public String getSpiderId() {
        return spiderId;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {

        this.lastTime = lastTime;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {

        this.clientIp = clientIp;
    }

    public long getStartUrlsNum() {
        return startUrlsNum;
    }

    public void setStartUrlsNum(long startUrlsNum) {
        this.startUrlsNum = startUrlsNum;
    }

    public long getTargetUrlsNum() {
        return targetUrlsNum;
    }

    public void setTargetUrlsNum(long targetUrlsNum) {
        this.targetUrlsNum = targetUrlsNum;
    }

    public long getFinishStartUrlsNum() {
        return finishStartUrlsNum;
    }

    public void setFinishStartUrlsNum(long finishStartUrlsNum) {
        this.finishStartUrlsNum = finishStartUrlsNum;
    }

    public long getFinishTargetUrlsNum() {
        return finishTargetUrlsNum;
    }

    public void setFinishTargetUrls(long finishTargetUrls) {
        this.finishTargetUrlsNum = finishTargetUrls;
    }
}

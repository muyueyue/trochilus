package utils;

/**
 * Created by jiahao on 17-4-26
 *
 * @author jiahao.pjh@gmail.com
 */
public class Site {

    private boolean proxy;

    private long sleepTime;

    private long retryTime;

    public Site(){}

    public Site setProxy(){
        this.proxy = true;
        return this;
    }

    public Site setSleepTime(long sleepTime){
        this.sleepTime = sleepTime;
        return this;
    }

    public Site setRetryTime(){
        this.retryTime = retryTime;
        return this;
    }

}

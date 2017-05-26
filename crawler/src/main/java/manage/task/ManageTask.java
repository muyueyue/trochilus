package manage.task;

import manage.WorkInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Config;
import utils.Method;
import utils.Request;
import utils.Response;

/**
 * Created by jiahao on 17-5-23.
 *
 * @author jiahao.pjh@gmail.com
 */
public class ManageTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(ManageTask.class);

    @Override
    public void run(){
        while (true){
            try{
                Thread.sleep(60000);
                WorkInfo instance = WorkInfo.getInstance();
                Request request = new Request(Config.masterAddr.concat("/master/spider/workInfo?spiderId=" + Config.spiderId), Method.POST);
                request.setParams("spiderId", Config.spiderId)
                .setParams("startUrlsNum", instance.getStartUrlsNum())
                .setParams("targetUrlsNum", instance.getTargetUrls())
                .setParams("finishStartUrlsNum", instance.getFinishStartUrlsNum())
                .setParams("finishTargetUrls", instance.getFinishTargetUrls());
                Response response = request.send();
                if(response.isSuccess()){
                    logger.info("向Master发送爬虫的工作情况成功");
                }else {
                    logger.error("向Master发送爬虫的工作情况失败");
                }
            }catch (Exception e){
                logger.error("向Master发送爬虫的工作情况出错");
            }
        }
    }
}

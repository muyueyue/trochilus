package main.example;

import exception.DBException;
import main.Spider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pkulaw.HBaseTask;
import pkulaw.StartUrlsTask;
import pkulaw.TargetUrlsTask;
import thread.GetCaseTask;
import thread.ThreadPool;
import utils.Config;
import utils.ParseMethod;
/**
 * Created by jiahao on 17-4-22.
 *
 * @author jiahao.pjh@gmail.com
 */
public class SpiderTest {
    public static void main(String[] args) throws DBException{
        /*Spider spider = new Spider();
        spider.addToTargetQueue("//dl[@class='contentList']/dd/a/@href", "http://www.pkulaw.cn")
                .putField("content", "//allText()", ParseMethod.XPATH)
                .thread(20)
                .setMasterInfo("http://127.0.0.1:8080")
                .run();*/
        /*GetCaseTask task = new GetCaseTask();
        task.run();*/
        if(args.length < 3){
            System.out.println("请输入urlIndex,endId,threadNum");
            System.exit(1);
        }
        Config.urlIndex = Long.valueOf(args[0]);
        Config.endId = Long.valueOf(args[1]);
        int threadNum = Integer.valueOf(args[2]);
        ThreadPool pool = ThreadPool.getInstance();
        pool.execute(new StartUrlsTask());
        for(int i = 0; i < threadNum; i++){
            pool.execute(new TargetUrlsTask());
        }
        pool.execute(new HBaseTask());
    }
}

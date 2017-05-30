package main.example;

import exception.DBException;
import org.apache.log4j.PropertyConfigurator;
import pkulaw.StartUrlsTask;
import pkulaw.TargetUrlsTask;
import thread.ThreadPool;
import utils.Config;
/**
 * Created by jiahao on 17-4-22.
 *
 * @author jiahao.pjh@gmail.com
 */
public class SpiderTest {
    public static void main(String[] args) throws DBException{
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
    }
}

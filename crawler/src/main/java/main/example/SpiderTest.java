package main.example;

import exception.DBException;
import org.apache.log4j.PropertyConfigurator;
import pkulaw.CreateStartUrlsTask;
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
        if(args.length < 2){
            System.out.println("请输入配置文件路径,threadNum");
            System.exit(1);
        }
        Config.configFilePath = args[0];
        int threadNum = Integer.valueOf(args[1]);
        ThreadPool pool = ThreadPool.getInstance();
        pool.execute(new CreateStartUrlsTask());
        pool.execute(new StartUrlsTask());
        for(int i = 0; i < threadNum; i++){
            pool.execute(new TargetUrlsTask());
        }
    }
}

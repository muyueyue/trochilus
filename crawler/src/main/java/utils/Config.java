package utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 全局的配置类
 * Created by pjh on 2017/1/28.
 *
 * @author jiahao.pjh@gmail.com
 */
public class Config {

    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2";

    public static Integer connectionTimeout = 10000;

    public static Integer socketTimeout = 50000;

    public static String charset = "UTF-8";

    public static String contentType = "application/json";

    public static String mongoDBHost = "localhost";

    public static int mongoDBPort = 27017;

    public static String databaseName = "spiders";

    public static String dbCollection = "casecodekey";

    public static String filePath = "";

    public static long errorSleepTime = 1000;

    public static String proxyUrl = "http://www.xicidaili.com/nn/{}";

    public static String redisIP = "127.0.0.1";

    public static int redisPort = 6379;

    public static String redisTargetUrls = "targetUrls";

    public static String redisFinishUrls = "finishUrls";

    public static String redisStartUrls = "startUrls";

    public static String urlHash = "urlHash";

    public static String masterAddr;

    public static String spiderId;

    public static String spiderIdPath = "/home/hadoop/hadoop/spider/spiderId";

    public static BlockingQueue<String> caseQueue = new LinkedBlockingQueue<>();

    public static int urlSize = 20;

    public static long startId;

    public static long endId;
}

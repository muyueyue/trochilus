package utils;

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

    public static String dbCollection = "spiders";

    public static String filePath = "";

    public static long errorSleepTime = 10000;
}

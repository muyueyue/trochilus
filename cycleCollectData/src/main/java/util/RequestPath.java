package util;



/**
 * 数据库操作请求的地址
 * Created by pjh on 2017/3/9.
 */
public class RequestPath {

    /**
     * 数据库操作请求
     */
    public static String queryMeter = Config.DB_PREFIX + "queryMeter";

    public static String queryMeterRegConf = Config.DB_PREFIX + "queryMeterRegConf";

    public static String countReg = Config.DB_PREFIX + "countReg";

    public static String queryRegAddrStart = Config.DB_PREFIX + "queryRegAddrStart";

    /**
     * server的地址
     */
    public static String serveUrl = "";
}

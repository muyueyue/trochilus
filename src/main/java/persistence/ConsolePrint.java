package persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import parse.Select;
import utils.StringUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 将爬取的内容向控制台打印
 * Created by jiahao on 17-4-1.
 *
 * @author jiahao.pjh@gmail.com
 */
public class ConsolePrint {

    private static final Logger logger = LoggerFactory.getLogger(ConsolePrint.class);

    public static void print(String key, String value){
        logger.info("{} ：", key);
        logger.info("{}", value);
    }

    public static void print(String string){
        logger.info("{}", string);
    }
}


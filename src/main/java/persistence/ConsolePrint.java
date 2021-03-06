package persistence;

import com.alibaba.fastjson.JSONObject;
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
 * Created by jiahao on 17-4-1.
 *
 *  Print the contents of the crawl to the console
 *
 * @author jiahao.pjh@gmail.com
 */
public class ConsolePrint {

    private static final Logger logger = LoggerFactory.getLogger(ConsolePrint.class);

    public static void print(String key, String value){
        logger.info("{}: {}", key, value);
    }

    public static void print(JSONObject jsonObject){
        logger.info(jsonObject.toJSONString() + '\n');
    }

    public static void print(String string){
        logger.info("{}", string);
    }
}


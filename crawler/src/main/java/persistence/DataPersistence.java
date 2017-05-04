package persistence;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parse.Html;
import utils.StringUtil;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jiahao on 17-4-1.
 *
 * Write the results to the database
 *
 * @author jiahao.pjh@gmail.com
 */
public class DataPersistence {

    private static final Logger logger = LoggerFactory.getLogger(DataPersistence.class);

    public static void insert(JSONObject jsonObject){
        MongoDBJDBC.insert(jsonObject);
    }
}

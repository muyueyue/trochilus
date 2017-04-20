package persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiahao on 17-4-1.
 */
public class Result {

    private static final Logger logger = LoggerFactory.getLogger(Result.class);

    private Map<String, Object> fields = new HashMap<>();

    public Result put(String key, Object value){
        if(key == null){
            logger.error("key为空");
            return this;
        }
        this.fields.put(key, value);
        return this;
    }

    public Map<String, Object> getAll(){
        return this.fields;
    }

    public Result clear(){
        this.fields.clear();
        return this;
    }
}

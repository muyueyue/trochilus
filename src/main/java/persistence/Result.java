package persistence;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jiahao on 17-4-1.
 */
public class Result {

    private static final Logger logger = LoggerFactory.getLogger(Result.class);

    private JSONArray fields = new JSONArray();

  public Result put(JSONObject jsonObject){
       this.fields.add(jsonObject);
       return this;
  }

    public JSONArray getAll(){
        return this.fields;
    }

    public Result clear(){
        this.fields.clear();
        return this;
    }
}

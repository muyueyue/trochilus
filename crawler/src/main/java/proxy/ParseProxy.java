package proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Method;
import utils.Request;
import utils.Response;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiahao on 17-4-27.
 *
 * @author jiahao.pjh@gmail.com
 *
 */
public class ParseProxy implements Runnable{

    private final static Logger logger = LoggerFactory.getLogger(ParseProxy.class);

    private String filePath;

    public ParseProxy(String filePath){
        this.filePath = filePath;
    }

    @Override
    public void run(){
        try {
            List<String> list = new ArrayList<>();
            FileReader fileReader = new FileReader(this.filePath);
            BufferedReader reader = new BufferedReader(fileReader);
            String str;
            int count = 0;
            while ((str = reader.readLine()) != null && count < 30){
                list.add(str);
                count++;
            }
            reader.close();
            fileReader.close();
            JSONArray jsonArray = new JSONArray();
            for(String string : list){
                JSONObject jsonObject = JSON.parseObject(string);
                for(int i = 99 ; i > 0; i--){
                    String IP = jsonObject.getString("IP".concat(String.valueOf(i)));
                    String port = jsonObject.getString("port".concat(String.valueOf(i)));
                    JSONObject proxy = new JSONObject().fluentPut("IP", IP).fluentPut("port", port);
                    Response response = new Request("http://www.baidu.com", Method.GET)
                            .setProxy(proxy)
                            .setSocketTimeout(5000)
                            .send();
                    logger.info("IP: {} port: {}", IP, port);
                    if(response != null){
                        jsonArray.add(proxy);
                        logger.info("request success!");
                    }
                }
            }
            FileWriter fileWriter = new FileWriter(this.filePath);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for(int i = 0; i < jsonArray.size(); i++){
               writer.write(jsonArray.get(i).toString());
            }
            writer.close();
            fileWriter.close();
        }catch (Exception e){

        }

    }
}

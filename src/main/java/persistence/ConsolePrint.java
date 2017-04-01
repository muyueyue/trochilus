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
 * 将爬取的内容向控制台打印出来
 * Created by jiahao on 17-4-1.
 */
public class ConsolePrint {

    private static final Logger logger = LoggerFactory.getLogger(ConsolePrint.class);

    private Html html;

    public ConsolePrint(Html html){
        this.html = html;
    }

    public void printById(String id){
        if(this.html == null || StringUtil.isEmpty(id)){
            System.out.println("内容为空!");
            return;
        }
        Result result = new Result(this.html);
        HashMap<String, String> resultMap = result.getResultById(id);
        if(resultMap == null){
            System.out.println("内容为空!");
            return;
        }
        Iterator iterator = resultMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
            System.out.println("id: " + entry.getKey());
            System.out.println("text: " + entry.getValue());
        }
    }

    public void printByClassName(String className){
        if(this.html == null || StringUtil.isEmpty(className)){
            System.out.println("内容为空!");
            return;
        }
        Result result = new Result(this.html);
        HashMap<String, String> resultMap = result.getResultByClassName(className);
        if(resultMap == null){
            System.out.println("内容为空!");
            return;
        }
        Iterator iterator =  resultMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
            System.out.println("className: " + entry.getKey());
            System.out.println("text: " + entry.getValue());
        }
    }

    public void printByTagName(String tagName){
        if(this.html == null || StringUtil.isEmpty(tagName)){
            System.out.println("内容为空!");
            return;
        }
        Result result = new Result(html);
        HashMap<String, String> resultMap = result.getResultByTagName(tagName);
        if(resultMap == null){
            System.out.println("内容为空!");
            return;
        }
        Iterator iterator = resultMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
            System.out.println("tagName: " + entry.getKey());
            System.out.println("text: " + entry.getValue());
        }
    }

    public void printByXpath(String key, String xPath){
        if(this.html == null || StringUtil.isEmpty(xPath)){
            System.out.println("内容为空！");
            return;
        }
        Result result = new Result(this.html);
        List<String> values = result.select(xPath);
        for(String string : values){
            System.out.println("key: " + key);
            System.out.println("value: " + string);
        }
    }
}


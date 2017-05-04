package parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahao on 17-4-21.
 *
 * @author jiahao.pjh@gmail.com
 *
 * 提供一系列对页面操作的抽象
 */
public class Page {

    private String allLinks;

    private List<String> linksRegex;

    private Map<String, String > xPath;

    private Map<String, String> regex;

    public Page(){
        this.linksRegex = new ArrayList<>();
        this.xPath = new HashMap<>();
        this.regex = new HashMap<>();
    }

    public Page allLinks(){
        this.allLinks = "allLinks";
        return this;
    }

    public Page linksRegex(String regex){
        this.linksRegex.add(regex);
        return  this;
    }

    public Page xPath(String key, String value){
        this.xPath.put(key, value);
        return this;
    }

    public Page regex(String key, String value) {
        this.regex.put(key, value);
        return this;
    }
}

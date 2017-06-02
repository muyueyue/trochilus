package main.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Method;
import utils.Request;
import utils.Response;

/**
 * Created by jiahao on 17-5-23.
 *
 * @author jiahao.pjh@gmail.com
 */
public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
       /* Request request = new Request("http://www.pkulaw.cn/case/FullText/_getFulltext", Method.POST);
        request.setParams("library","pfnl")
                .setParams("gid", "1970324875435986")
                .setParams("loginSucc","0");
        Response response = request.send();
        //System.out.println(response.getContent());
        Request re = new Request("http://127.0.0.1:8080/master/spider/test", Method.POST);
        re.setParams("content", response.getContent());
        re.send();*/

     /* String url = "http://www.pkulaw.cn/case/pfnl_1970324846018188.html?match=Exact";
      String temp = url.substring(url.indexOf("pfnl"), url.indexOf(".html"));
      String library = temp.substring(0, 4);
      String gid = temp.substring(temp.indexOf("_") + 1, temp.length());
      System.out.println("library:" + library + "    " + "gid:" + gid);*/
     long start = System.currentTimeMillis() / 1000;
     for(int i = 0; i < 1000000; i++){
         System.out.println("info");
     }
        System.out.println(System.currentTimeMillis() / 1000 - start);
    }
}

package name.pjh.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import name.pjh.service.RedisService;
import name.pjh.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiahao on 17-5-4.
 *
 * @author jiahao.pjh@gmail.com
 */
@RestController
@RequestMapping("/master/url")
public class RedisAPI {

    private static final Logger logger = LoggerFactory.getLogger(RedisAPI.class);

    @Autowired
    private RedisService redisService;

    @RequestMapping("/getStartUrl")
    public Response getStartUrl(@RequestParam("spiderId") String spiderId,
                                @RequestParam(value = "start", defaultValue = "0") int start,
                                @RequestParam(value = "end", defaultValue = "0") int end){
        logger.info("收到爬虫 {}　请求起始URL的命令", spiderId);
        if(start == 0 && end == 0){
            JSONObject content = redisService.getStartUrl(spiderId);
            if(content == null){
                return new Response().error().errorMeg("获取起始URL出错");
            }
            return new Response().success().content(content);
        }else {
            JSONArray content = redisService.getStartUrls(spiderId, start, end);
            if(content == null){
                return new Response().error().errorMeg("获取起始URL出错");
            }
            return new Response().success().content(content);
        }
    }

    @RequestMapping("/getTargetUrl")
    public Response getTargetUrl(@RequestParam("spiderId") String spiderId,
                                 @RequestParam(value = "start", defaultValue = "0") int start,
                                 @RequestParam(value = "end", defaultValue = "0") int end){
        logger.info("收到爬虫{}获取待爬取URL的命令", spiderId);
        if(start == 0 && end == 0){
            JSONObject content = redisService.getTargetUrl(spiderId);
            if(content ==  null){
                return new Response().error().errorMeg("获取待爬取的URL出错");
            }
            return new Response().success().content(content);
        }else {
         JSONArray content = redisService.getTargetUrls(spiderId, start, end);
         if(content == null){
             return new Response().error().errorMeg("获取待爬取的URL出错");
         }
         return new Response().success().content(content);
        }
    }

    @RequestMapping("/addStartUrl")
    public Response addStartUrl(@RequestBody String body){
        logger.info("收到向Redis添加起始URL的命令 {}", body);
        return null;
    }

    @RequestMapping("/addTargetUrl")
    public Response addTargetUrl(){
        return null;
    }

}

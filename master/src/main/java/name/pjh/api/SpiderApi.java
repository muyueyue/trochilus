package name.pjh.api;

import com.alibaba.fastjson.JSONObject;
import name.pjh.service.RedisService;
import name.pjh.service.SpiderService;
import name.pjh.spider.SpiderInfo;
import name.pjh.spider.SpiderPool;
import name.pjh.utils.Response;
import name.pjh.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jiahao on 17-5-4.
 *
 * @author jiahao.pjh@gmail.com
 */
@RestController
@RequestMapping("/master/spider")
public class SpiderApi {

    private static final Logger logger = LoggerFactory.getLogger(SpiderApi.class);

    @Autowired
    private SpiderService spiderService;

    @PostMapping("/register")
    public Response register(@RequestBody String body){
        logger.info("收到注册爬虫的命令 {}", body);
        JSONObject param = JSONObject.parseObject(body);
        String spiderId = param.getString("spiderId");
        if(StringUtil.isEmpty(spiderId)){
            return new Response().error().errorMeg("爬虫缺少唯一标识，注册失败");
        }
       if(spiderService.createSpider(spiderId)){
            return new Response().success();
       }
        return new Response().error().errorMeg("爬虫已存在，注册失败");
    }

    @PostMapping("/workInfo")
    public Response workInfo(@RequestBody String body){
        logger.info("收到爬虫报告工作情况的命令:{}", body);
        JSONObject param = JSONObject.parseObject(body);
        if (spiderService.workInfo(param)){
            return new Response().success();
        }
        return new Response().error().errorMeg("更新爬虫的工作情况出错");
    }

    @GetMapping("/getSpiderWorkInfo")
    public Response getSpiderWorkInfo(@RequestParam("spiderId") String spiderId){
        logger.info("收到查询爬虫{}工作情况的命令", spiderId);
        JSONObject content = spiderService.getSpiderWorkInfo(spiderId);
        if(content == null){
            return new Response().error().errorMeg("获取爬虫的工作情况出错");
        }
        return new Response().success().content(content);
    }

    @GetMapping("/getAllSpiderWorkInfo")
    public Response getSpiderWorkInfo(){
        logger.info("收到查询所有爬虫工作情况的命令");
        JSONObject content = spiderService.getAllWorkInfo();
        if(content == null){
            return new Response().error().errorMeg("获取所有爬虫的工作情况出错");
        }
        return new Response().success().content(content);
    }

}

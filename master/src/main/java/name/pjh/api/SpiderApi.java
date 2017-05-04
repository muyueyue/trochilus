package name.pjh.api;

import com.alibaba.fastjson.JSONObject;
import name.pjh.service.RedisService;
import name.pjh.spider.SpiderInfo;
import name.pjh.spider.SpiderPool;
import name.pjh.utils.Response;
import name.pjh.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiahao on 17-5-4.
 *
 * @author jiahao.pjh@gmail.com
 */
@RestController
@RequestMapping("/master/spider")
public class SpiderApi {

    private static final Logger logger = LoggerFactory.getLogger(SpiderApi.class);

    @PostMapping("/register")
    public Response register(@RequestBody String body){
        logger.info("收到注册爬虫的命令 {}", body);
        JSONObject param = JSONObject.parseObject(body);
        String spiderId = param.getString("spiderId");
        if(StringUtil.isEmpty(spiderId)){
            return new Response().error().errorMeg("爬虫缺少唯一标识，注册失败");
        }
        SpiderInfo spider = new SpiderInfo(spiderId);
        SpiderPool pool = SpiderPool.getInstance();
        if(pool.add(spider)){
            return new Response().success();
        }
        return new Response().error().errorMeg("爬虫已存在，注册失败");
    }
}

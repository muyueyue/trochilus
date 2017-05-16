package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.*;

/**
 * Created by jiahao on 17-5-15.
 *
 * @author jiahao.pjh@gmail.com
 */
public class SpiderService {

    private static final Logger logger = LoggerFactory.getLogger(SpiderService.class);

    /**
     * 生成爬虫的唯一标识
     * @return
     */
    public boolean getSpiderId(){
        String spiderId = Util.getRandomString();
        if(StringUtil.isEmpty(spiderId)){
            return false;
        }
        Config.spiderId = spiderId;
        logger.info("生成的爬虫唯一标识为: {}", spiderId);
        return true;
    }

    public boolean register(){
        logger.info("正在注册爬虫");
        getSpiderId();
        Request request = new Request(Config.masterAddr.concat("/master/spider/register"), Method.POST);
        request.setParams("spiderId", Config.spiderId);
        Response response = request.send();
        if(response.isSuccess()){
            logger.info("爬虫注册成功");
            return true;
        }
        logger.error("爬虫注册失败");
        return false;
    }
}

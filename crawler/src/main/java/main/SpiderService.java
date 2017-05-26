package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

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
        try {
            String spiderId = Util.getRandomString();
            if(StringUtil.isEmpty(spiderId)){
                return false;
            }
            Config.spiderId = spiderId;
            File file = new File(Config.spiderIdPath);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(spiderId);
            bufferedWriter.close();
            logger.info("生成的爬虫唯一标识为: {}", spiderId);
            return true;
        }catch (Exception e){
            logger.error("存放spiderId出错");
            return false;
        }
    }

    public boolean register(){
        logger.info("正在注册爬虫");
        getSpiderId();
        Request request = new Request(Config.masterAddr.concat("/master/spider/register?spiderId=" + Config.spiderId), Method.POST);
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

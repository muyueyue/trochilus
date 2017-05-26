package name.pjh.redis;

import name.pjh.utils.Config;
import name.pjh.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by jiahao on 17-5-3.
 *
 * @author jiahao.pjh@gmail.com
 *
 * redis的操作的简单封装
 */
public class RedisClient {

    private final static Logger logger = LoggerFactory.getLogger(RedisClient.class);

    public static void addToTargetUrls(String url){
        Jedis jedis = RedisConnection.getJedisCon();
        try {
            jedis.lpush(Config.redisTargetUrls, url);
            Config.targetUrlslTotal++;
        }catch (Exception e){
            logger.error("添加targetUrl出错:{}", e);
        }finally {
            RedisConnection.returnJedis(jedis);
        }

    }

    public static void addToStartUrls(String url){
        Jedis jedis = RedisConnection.getJedisCon();
        try {
            jedis.lpush(Config.redisStartUrls, url);
            Config.startUlrsTotal++;
        }catch (Exception e){
            logger.error("添加起始URL出错: {}", e);
        }finally {
            RedisConnection.returnJedis(jedis);
        }
    }

    public static void addToStartUrls(List<String> urls){
        if(urls == null){
            return;
        }
        Jedis jedis = RedisConnection.getJedisCon();
        try {
            for(String url : urls){
                jedis.lpush(Config.redisStartUrls, url);
                Config.startUlrsTotal++;
            }
        }catch (Exception e){
          logger.error("添加起始URL出错: {}", e);
        } finally {
            RedisConnection.returnJedis(jedis);
        }
    }

    public static String getStartUrl(){
        Jedis jedis = RedisConnection.getJedisCon();
        try {
            String startUrl = jedis.lpop(Config.redisStartUrls);
            if(StringUtil.isEmpty(startUrl)){
                logger.error("获取到的startUrl为空");
            }
            return startUrl;
        }catch (Exception e){
            logger.error("获取到的startUrl出错:{}", e);
            return null;
        }finally {
            RedisConnection.returnJedis(jedis);
        }
    }

    public static List<String> getStartUrls(int start, int end){
        Jedis jedis = RedisConnection.getJedisCon();
        try {
            List<String> list = jedis.lrange(Config.redisStartUrls, start, end);
            if(list == null){
                logger.error("获取到的startUrl为空");
            }
            for(int i = start; i <= end; i++){
                jedis.lpop(Config.redisStartUrls);
            }
            return list;
        }catch (Exception e){
            logger.error("获取到的startUrl出错:{}", e);
            return null;
        }finally {
            RedisConnection.returnJedis(jedis);
        }
    }

    public static void addToTargetUrls(List<String> urls){
        if(urls == null){
            return;
        }
        Jedis jedis = RedisConnection.getJedisCon();
        try {
            for(String url : urls){
                jedis.lpush(Config.redisTargetUrls, url);
                Config.targetUrlslTotal++;
            }
        }catch (Exception e){
            logger.error("添加targetUrl失败");
        }finally {
            RedisConnection.returnJedis(jedis);
        }
    }

    public static String getTargetUrl(){
        Jedis jedis = RedisConnection.getJedisCon();
        try {
            String targetUrl = jedis.lpop(Config.redisTargetUrls);
            if(StringUtil.isEmpty(targetUrl)){
                logger.error("获取的targetUrl为空");
            }
            return targetUrl;
        }catch (Exception e){
            logger.error("获取的targetUrl出错:{}", e);
            return null;
        }finally {
            RedisConnection.returnJedis(jedis);
        }
    }

    public static List<String> getTargetUrls(int start, int end){
        Jedis jedis = RedisConnection.getJedisCon();
        try {
            List<String> list = jedis.lrange(Config.redisTargetUrls, start, end);
            if(list == null){
                logger.error("获取的targetUrl为空");
            }
            for(int i = start; i <= end; i++){
                jedis.lpop(Config.redisTargetUrls);
            }
            return list;
        }catch (Exception e){
            logger.error("获取的targetUrl出错:{}", e);
            return null;
        }finally {
            RedisConnection.returnJedis(jedis);
        }
    }

    public static void addToFinishUrls(String url){
        if(StringUtil.isEmpty(url)){
            return;
        }
        Jedis jedis = RedisConnection.getJedisCon();
        try {
            jedis.sadd(Config.redisFinishUrls, url);
        }catch (Exception e){
            logger.error("添加finishUrls出错:{}", e);
        } finally {
            RedisConnection.returnJedis(jedis);
        }
    }

    public static void addToFinishUrls(List<String> urls){
        if (urls == null){
            return;
        }
        Jedis jedis = RedisConnection.getJedisCon();
        try {
            for(String url : urls){
                jedis.sadd(Config.redisFinishUrls, url);
            }
        }catch (Exception e){
            logger.error("添加finishUrls出错:{}", e);
        }finally {
            RedisConnection.returnJedis(jedis);
        }
    }
}

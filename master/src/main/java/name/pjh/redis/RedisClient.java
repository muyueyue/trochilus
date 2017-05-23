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
        RedisConnection.getJedisCon().lpush(Config.redisTargetUrls, url);
    }

    public static void addToStartUrls(String url){
        Jedis jedis = RedisConnection.getJedisCon();
        RedisConnection.getJedisCon().lpush(Config.redisStartUrls, url);
        RedisConnection.returnJedis(jedis);
    }

    public static void addToStartUrls(List<String> urls){
        if(urls == null){
            return;
        }
        Jedis jedis = RedisConnection.getJedisCon();
        for(String url : urls){
            jedis.lpush(Config.redisStartUrls, url);
        }
        RedisConnection.returnJedis(jedis);
    }

    public static String getStartUrl(){
        Jedis jedis = RedisConnection.getJedisCon();
        String startUrl = jedis.lpop(Config.redisStartUrls);
        if(StringUtil.isEmpty(startUrl)){
            logger.error("获取到的startUrl为空");
        }
        RedisConnection.returnJedis(jedis);
        return startUrl;
    }

    public static List<String> getStartUrls(int start, int end){
        Jedis jedis = RedisConnection.getJedisCon();
        List<String> list = jedis.lrange(Config.redisStartUrls, start, end);
        if(list == null){
            logger.error("获取到的startUrl为空");
        }
        for(int i = start; i <= end; i++){
            jedis.lpop(Config.redisStartUrls);
        }
        RedisConnection.returnJedis(jedis);
        return list;
    }

    public static void addToTargetUrls(List<String> urls){
        if(urls == null){
            return;
        }
        Jedis jedis = RedisConnection.getJedisCon();
        for(String url : urls){
            jedis.lpush(Config.redisTargetUrls, url);
        }
        RedisConnection.returnJedis(jedis);
    }

    public static String getTargetUrl(){
        Jedis jedis = RedisConnection.getJedisCon();
        String targetUrl = jedis.lpop(Config.redisTargetUrls);
        if(StringUtil.isEmpty(targetUrl)){
            logger.error("获取的targetUrl为空");
        }
        RedisConnection.returnJedis(jedis);
        return targetUrl;
    }

    public static List<String> getTargetUrls(int start, int end){
        Jedis jedis = RedisConnection.getJedisCon();
        List<String> list = jedis.lrange(Config.redisTargetUrls, start, end);
        if(list == null){
            logger.error("获取的targetUrl为空");
        }
        for(int i = start; i <= end; i++){
            jedis.lpop(Config.redisTargetUrls);
        }
        RedisConnection.returnJedis(jedis);
        return list;
    }

    public static void addToFinishUrls(String url){
        Jedis jedis = RedisConnection.getJedisCon();
        jedis.sadd(Config.redisFinishUrls, url);
        RedisConnection.returnJedis(jedis);
    }

    public static void addToFinishUrls(List<String> urls){
        if (urls == null){
            return;
        }
        Jedis jedis = RedisConnection.getJedisCon();
        for(String url : urls){
            jedis.sadd(Config.redisFinishUrls, url);
        }
        RedisConnection.returnJedis(jedis);
    }
}

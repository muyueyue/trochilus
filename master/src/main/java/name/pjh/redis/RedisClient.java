package name.pjh.redis;

import name.pjh.utils.Config;
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
        RedisConnection.getJedisCon().lpush(Config.redisStartUrls, url);
    }

    public static void addToStartUrls(List<String> urls){
        if(urls == null){
            return;
        }
        Jedis jedis = RedisConnection.getJedisCon();
        for(String url : urls){
            jedis.lpush(Config.redisStartUrls, url);
        }
    }

    public static String getStartUrl(){
        return RedisConnection.getJedisCon().lpop(Config.redisStartUrls);
    }

    public static List<String> getStartUrls(int start, int end){
        return RedisConnection.getJedisCon().lrange(Config.redisStartUrls, start, end);
    }

    public static void addToTargetUrls(List<String> urls){
        if(urls == null){
            return;
        }
        Jedis jedis = RedisConnection.getJedisCon();
        for(String url : urls){
            jedis.lpush(Config.redisTargetUrls, url);
        }
    }

    public static String getTargetUrl(){
        return RedisConnection.getJedisCon().lpop(Config.redisTargetUrls);
    }

    public static List<String> getTargetUrls(int start, int end){
        return RedisConnection.getJedisCon().lrange(Config.redisTargetUrls, start, end);
    }

    public static void addToFinishUrls(String url){
        RedisConnection.getJedisCon().sadd(Config.redisFinishUrls, url);
    }

    public static void addToFinishUrls(List<String> urls){
        if (urls == null){
            return;
        }
        Jedis jedis = RedisConnection.getJedisCon();
        for(String url : urls){
            jedis.sadd(Config.redisFinishUrls, url);
        }
    }
}

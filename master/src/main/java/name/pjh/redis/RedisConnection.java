package name.pjh.redis;

import name.pjh.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PreDestroy;

/**
 * Created by jiahao on 17-5-3.
 *
 * @author jiahao.pjh@gmail.com
 *
 * 一个Redis连接池
 */
public class RedisConnection {

    private final static Logger logger = LoggerFactory.getLogger(RedisConnection.class);

    private static JedisPool jedisPool;

    static {
        try{
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(-1);
            config.setMaxIdle(-1);
            config.setMaxWaitMillis(10000);
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);
            jedisPool = new JedisPool(config, Config.redisIP, Config.redisPort, 0);
        }catch (Exception e){
            logger.error("初始化Redis连接池出错: {}", e);
        }
    }

    /**
     * 获取一个Redis连接
     * @return
     */
    public static synchronized Jedis getJedisCon(){
        if(jedisPool ==  null){
            return null;
        }
        Jedis jedis = jedisPool.getResource();
        if(jedis == null){
            logger.error("Redis客户端资源不足，需等待");
        }
        return jedis;
    }

    @PreDestroy
    public void close(){
        jedisPool.close();
    }
}

package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.BitSet;


/**
 * Created by pjh on 2017/2/4.
 * 布隆过滤器（Bloom Filter）是1970年由Burton Howard Bloom提出的。
 * 它实际上是一个很长的二进制向量和一系列随机映射函数。
 * 布隆过滤器可以用于检索一个元素是否在一个集合中。
 * 它的优点是空间效率和查询时间都远远超过一般的算法，缺点是有一定的误识别率和删除困难。
 * 在此用于实现判别一个页面是否爬取过，避免爬虫的循环爬取
 */
public class BloomFilter {

    private static final Logger logger = LoggerFactory.getLogger(BloomFilter.class);

    private static final int DEFAULT_SIZE = 2 << 40;
    private static final int[] seeds = new int[] { 7, 113, 213, 3111, 397, 611,532 };  //不同的函数seed
    private BitSet bits = new BitSet(DEFAULT_SIZE);

    private Hash[] func = new Hash[seeds.length];   //func 函数数组

    public BloomFilter() {
        for (int i = 0; i < seeds.length; i++) func[i] = new Hash(DEFAULT_SIZE, seeds[i]);   //初始化func函数数组
    }

    public static class Hash {

        private int cap;
        private int seed;
        public Hash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        public int hash(String value) {
            int result = 0;
            int len = value.length();
            for (int i = 0; i < len; i++) result = seed * result + value.charAt(i); //一般的hash算法
            return (cap - 1) & result;   //把值的范围控制在cap内
        }
    }


    public void add(String url) {    //bloom filter 添加url值
        for (Hash f : func) bits.set(f.hash(url), true);
    }

    public boolean contains(String value) {   //判断 bloom filter 是否包含url值
        if (value == null) {return false;}
        boolean ret = true;
        for (Hash f : func) ret = ret && bits.get(f.hash(value));
        return ret;
    }


    public static void main(String[] args) {
        String[] urls = new String[]{"addsdsdfdvcuuyttyetrmnmnkywdgds","addsdsdfdvcuuyttyetrmnmnkywdgds","addssdfdvcuuyttyetrmnmnkywdgds","addsdds"};  //测试数据
        BloomFilter filter = new BloomFilter();
        for(String value : urls){
            value = value.trim();
            logger.info("filter.contains("+value+"):"+filter.contains(value));
            filter.add(value);
            logger.info("filter.add("+value+"):"+filter.contains(value));
            logger.info("----------------------------------------------------");
        }
    }
}

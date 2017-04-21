package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * 关于对字符串操作的工具类
 * 包含对字符串操作的常用方法
 * Created by pjh on 2017/1/22.
 *
 * @author jiahao.pjh@gmail.com
 */
public class StringUtil {

    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

    /**
     * 将 InputStream 转为 String
     * @param inputStream
     * @param charset
     * @return String
     */
    public static String inputStreamToString(InputStream inputStream, String charset) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset));
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            logger.error("inputStream转String出错：{}", e.toString());
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("关闭流出错：{}", e.toString());
            }
        }
    }

    /**
     * 将 InputStream 转为 String
     * @param inputStream
     * @return String
     */
    public static String inputStreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            logger.error("inputStream转String出错：{}", e.toString());
            e.printStackTrace();
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("关闭流出错：{}", e.toString());
            }
        }
    }

    /**
     * 将 String 转为 InputStream
     * @param string
     * @return InputStream
     */
    public static InputStream stringToInpuStream(String string){
        try{
            InputStream inputStream  = new ByteArrayInputStream(string.getBytes());
            return inputStream;
        }catch (Exception e){
            logger.error("{} 转InputStream出错", string);
            return null;
        }
    }

    /**
     * 判断一个字符串为空
     * @param string
     * @return true or false
     */
    public static boolean isEmpty(String string){
        if(string == null || string.trim().equals("")){
            return true;
        }
        return false;
    }


    /**
     *  判断一个字符串不为空
     * @param string
     * @return true or false
     */
    public static boolean isNotEmpty(String string){
        if(string == null || string.trim().equals("")){
            return false;
        }
        return true;
    }

    /**
     * 判断一个字符串是url
     * @param string
     * @return true or false
     */
    public static boolean isURL(String string){
        String regex = "[a-zA-z]+://[^\\s]*";
        if(string.matches(regex)){
            return true;
        }
        return false;
    }

    /**
     * 判断一个字符串不是url
     * @param string
     * @return true or false
     */
    public static boolean isNotURL(String string){
        String regx = "[a-zA-z]+://[^\\s]*";
        if(string.matches(regx)){
            return false;
        }
        return true;
    }

    /**
     * 将一个List<String>转化为一个String
     * @param list
     * @return
     */
    public static String listToString(List<String> list){
        if(list == null){
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(String string : list){
            stringBuilder.append(string + '\n');
        }
        return stringBuilder.toString();
    }
}

package utils;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Random;


/**
 * Created by pjh on 2017/2/4.
 *
 * @author jiahao.pjh@gmail.com
 */
public class Util {

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    private static String baseString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 获取网页的页面编码，此处使用IBM的icu4j开源包解析出编码
     * @param data
     * @return
     * @throws IOException
     */
    public static String getEncoding(byte[] data) throws IOException {
        CharsetDetector detector = new CharsetDetector();
        detector.setText(data);
        CharsetMatch match = detector.detect();
        String encoding = match.getName();
        return encoding;
    }

    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static String getRandomString() {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for(int i = 0; i < 30; i++) {
            sb.append(baseString.charAt( random.nextInt(baseString.length() ) ) );
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandomString());
    }
}

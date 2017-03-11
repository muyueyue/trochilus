package utils;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;


/**
 * Created by pjh on 2017/2/4.
 */
public class Util {

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

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
}

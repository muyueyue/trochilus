package pkulaw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Config;

import java.io.*;

/**
 * Created by jiahao on 17-6-2.
 *
 * @author jiahao.pjh@gmail.com
 */
public class ReadConfig {

    private static final Logger logger = LoggerFactory.getLogger(ReadConfig.class);

    private static File file;

    private static BufferedReader bufferedReader;

    static {
        try {
            file = new File(Config.configFilePath);
            if(!file.exists()){
                logger.error("no file found");
                System.exit(1);
            }
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");
            bufferedReader = new BufferedReader(read);
        }catch (IOException e){
        }
    }

    public static String read(){
        try {
            String config = "", temp;

            while ((temp = bufferedReader.readLine()) != null){
                config = config + temp;
            }

            return config;
        }catch (IOException e){
            logger.error("read file error:{}", e.toString());
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(read());
    }
}

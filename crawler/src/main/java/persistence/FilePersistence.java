package persistence;

import com.alibaba.fastjson.JSONObject;
import utils.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by jiahao on 17-4-21.
 *
 * Provides a set of methods for writing results to
 * local file system
 *
 * @author jiahao.pjh@gmail.com
 */
public class FilePersistence {

    private static File file;

    private static PrintStream printStream;

    static {
        try {
            file = new File(Config.filePath);
            printStream = new PrintStream(new FileOutputStream(file, true));
        }catch (IOException e){
        }
    }

    public static void write(String key, String value) throws IOException{
        PrintStream printStream = new PrintStream(new FileOutputStream(file, true));
        printStream.append(key + ":" + '\n');
        printStream.append(value + '\n');
    }

    public static void write(JSONObject jsonObject) throws IOException{
        printStream.append(jsonObject.toJSONString() + '\n');
    }
}

package persistence;

import utils.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by jiahao on 17-4-21.
 *
 * @author jiahao.pjh@gmail.com
 */
public class FilePersistence {

    public static void write(String key, String value) throws IOException{
        File file = new File(Config.filePath);
        PrintStream printStream = new PrintStream(new FileOutputStream(file));
        printStream.append(key + ":" + '\n');
        printStream.append(value + '\n');
    }
}

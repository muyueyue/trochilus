package pkulaw;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by jiahao on 17-5-30.
 *
 * @author jiahao.pjh@gmail.com
 */
public class OutputException {

    public static void output(String filePath, String e){
        try {
            File file = new File(filePath);
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(e + '\n');
            bufferedWriter.close();
            fileWriter.close();
        }catch (IOException ex){
            System.out.println(ex.toString());
        }
    }
}

package utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jiahao on 17-5-26.
 *
 * @author jiahao.pjh@gmail.com
 */
public class CaseQueue {

    private static BlockingQueue<String> caseQueue;

    private static BlockingQueue<String> backupCaseQueue;

    private static Set<String> caseSet;

    static {
        caseQueue = new LinkedBlockingQueue<>();
        caseSet = new HashSet<>();
    }

    public static BlockingQueue<String> getCaseQueue() {
        return caseQueue;
    }

    public static BlockingQueue<String> getBackupCaseQueue(){return backupCaseQueue;}

    public static boolean putSet(String value) {
        if(caseSet.contains(value)){
            return false;
        }
        caseSet.add(value);
        return true;
    }

    public static boolean caseIsExisit(String value){
        return caseSet.contains(value);
    }
}

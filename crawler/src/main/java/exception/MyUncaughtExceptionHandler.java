package exception;

/**
 * Created by jiahao on 17-5-30.
 *
 * @author jiahao.pjh@gmail.com
 */
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("线程出现逃逸异常" + e.toString());
    }
}

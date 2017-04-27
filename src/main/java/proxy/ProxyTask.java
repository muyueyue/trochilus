package proxy;

/**
 * Created by jiahao on 17-4-27.
 *
 * @author jiahao.pjh@gmail.com
 */
public class ProxyTask {
    public static void main(String[] args){
        Thread thread = new Thread(new ParseProxy("/home/jiahao/data/proxy"));
        thread.start();
    }
}

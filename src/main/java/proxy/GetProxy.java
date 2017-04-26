package proxy;

import main.Spider;
import utils.Config;
import utils.ParseMethod;

/**
 * Created by jiahao on 17-4-25.
 *
 * @author jiahao.pjh@gmail.com
 */
public class GetProxy {
    public static void main(String[] args){
        Spider proxySpider = new Spider();
        proxySpider.addUrlToTargeQueue(Config.proxyUrl, 100)
                .putField("IP", "//table[@id='ip_list']/tbody/tr/td[2]/text()", ParseMethod.XPATH, true)
                .putField("port", "//table[@id='ip_list']/tbody/tr/td[3]/text()", ParseMethod.XPATH, true)
                .thread(1)
                .run();
    }
}

package DynamicData.example;

import DynamicData.SeleniumDownloader;

/**
 * Created by jiahao on 17-5-16.
 *
 * @author jiahao.pjh@gmail.com
 */
public class DynamicTest {
    public static void main(String[] args) {
        SeleniumDownloader seleniumDownloader = new SeleniumDownloader();
        seleniumDownloader.getHtml("http://www.toutiao.com");
    }
}

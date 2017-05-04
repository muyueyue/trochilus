package name.pjh.api;

import name.pjh.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiahao on 17-5-4.
 *
 * @author jiahao.pjh@gmail.com
 */
@RestController
@RequestMapping("/master/url")
public class RedisAPI {

    private static final Logger logger = LoggerFactory.getLogger(RedisAPI.class);

    @RequestMapping("/getStartUrl")
    public Response getStartUrl(){

        return null;
    }

    @RequestMapping("/getTargetUrl")
    public Response getTargetUrl(){
        return null;
    }

    @RequestMapping("/addStartUrl")
    public Response addStartUrl(){
        return null;
    }

    @RequestMapping("/addTargetUrl")
    public Response addTargetUrl(){
        return null;
    }
}

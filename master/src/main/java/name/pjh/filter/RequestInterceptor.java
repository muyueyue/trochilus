package name.pjh.filter;

import name.pjh.spider.SpiderInfo;
import name.pjh.spider.SpiderPool;
import name.pjh.utils.Response;
import name.pjh.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jiahao on 17-5-6.
 *
 * @author jiahao.pjh@gmail.com
 */
public class RequestInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String spiderId = httpServletRequest.getParameter("spiderId");
        String path = httpServletRequest.getRequestURI();
        if(StringUtil.isEmpty(spiderId)){
            if(path.indexOf("getAllSpiderWorkInfo") == -1){
                logger.error("未获取到爬虫的身份标识");
                return false;
            }
            return true;
        }
        String clientIp = httpServletRequest.getRemoteAddr();
        SpiderInfo spiderInfo = SpiderPool.getInstance().getSpider(spiderId);
        if(spiderInfo == null){
            if(path.indexOf("register") == -1){
                return false;
            }
            return true;
        }
        spiderInfo.setLastTime(System.currentTimeMillis());
        spiderInfo.setClientIp(clientIp);
        return true;
    }

    /**
     *
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}


package util;

import com.dtwave.common.http.request.HttpResponse;

/**
 * Created by pjh on 2017/3/8.
 */
public class RestUtil {
    //判断是否出错
    public static boolean isSuccess(HttpResponse response) {
        if (response.isError()) {
            return false;
        }
        return response.bodyJSON().getBoolean("success");
    }

    //从http请求中提取错误码信息
    public static String errorMsg(HttpResponse response) {
        if (isSuccess(response)) {
            return null;
        }
        if (response.isError()) {
            return response.errorInfo();
        }
        return response.bodyJSON().getString("errorMsg");
    }
}

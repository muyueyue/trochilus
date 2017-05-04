package name.pjh.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by jiahao on 17-5-4.
 *
 * @author jiahao.pjh@gmail.com
 */
public class Response {

    private boolean isSuccess;

    private Object content;

    private String errorMeg;

    public Response(){
    }

    public Response success(){
        this.isSuccess = true;
        return this;
    }

    public Response error(){
        this.isSuccess = false;
        return this;
    }

    public Response content(Object content){
        this.content = content;
        return this;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public Object getContent() {
        return content;
    }

    public String getErrorMeg() {
        return errorMeg;
    }

    public Response errorMeg(String errorMeg){
        this.errorMeg = errorMeg;
        return this;
    }

    public static void main(String[] args) {
        Response response = new Response();
        response.success().content("{\"name\":\"pjh\",\"age\":22}");
        System.out.println(response.toString());
    }
}

package tdh.ifm.android.imatch.app.base;

import java.io.Serializable;

/**
 * Author：gwx
 * Create at：2017/4/24 16:56
 */
public class BaseResponse<T> implements Serializable{

    /**
     * 接口调用返回是否成功
     */
    private boolean success;

    /**
     * 接口调用返回消息
     */
    private String message;

    /**
     * 接口调用返回代码
     */
    private String code;

    /**
     * 接口调用返回内容
     */
    private T body;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}

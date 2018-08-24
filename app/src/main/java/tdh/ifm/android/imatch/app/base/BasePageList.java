package tdh.ifm.android.imatch.app.base;

import java.io.Serializable;

/**
 * Author：gwx
 * Create at：2017/5/5 15:16
 */
public class BasePageList<T> implements Serializable{

    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

package tdh.ifm.android.imatch.app.base;

/**
 * Author：gwx
 * Create at：2017/4/24 19:11
 */
public interface BaseView {

    void showProgress();

    void hideprogress();

    void showFailure(String str, Throwable t);

    void showResState(int code);

}

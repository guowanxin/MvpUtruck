package tdh.ifm.android.imatch.app.view;

import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.base.BaseView;
import tdh.ifm.android.imatch.app.bean.User;

/**
 * Author：gwx
 * Create at：2017/5/8 17:19
 */
public interface LoginView extends BaseView{
    void onLoginSuccess(BaseResponse<User> baseResponse);
    void onSendLoginCodeSuccess(BaseResponse baseResponse);
}

package tdh.ifm.android.imatch.app.presenter;

import android.content.Context;

import tdh.ifm.android.imatch.app.base.BasePresenter;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.RequestLogin;
import tdh.ifm.android.imatch.app.bean.RequestPhone;
import tdh.ifm.android.imatch.app.bean.User;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.view.LoginView;

/**
 * Author：gwx
 * Create at：2017/5/8 17:22
 */
public class LoginPresenterImpl implements UserModel.OnLoginInListener, BasePresenter{
    private LoginView loginView;
    public UserModel userModel;


    public LoginPresenterImpl(Context context, LoginView loginView) {
        this.loginView = loginView;
        userModel = new UserModelImpl(context);

    }

    public void login(RequestLogin requestLogin) {
        loginView.showProgress();
        userModel.loginIn(requestLogin,this);
    }

    public void sendCode(RequestPhone requestPhone) {
        loginView.showProgress();
        userModel.sendLoginCode(requestPhone,this);
    }

    @Override
    public void onFailure(String str, Throwable t) {
        if (loginView == null) {
            return;
        }
        loginView.showFailure(str, t);
        loginView.hideprogress();
    }

    @Override
    public void onResState(int code) {
        if (loginView == null) {
            return;
        }
        loginView.showResState(code);
        loginView.hideprogress();
    }

    @Override
    public void onDestory() {
        loginView = null;
        if (userModel != null) {
            userModel = null;
        }
    }

    @Override
    public void onLoginInSuccess(BaseResponse<User> baseResponse) {
        if (loginView == null) {
            return;
        }
        if (baseResponse != null) {
            loginView.onLoginSuccess(baseResponse);
        }
        loginView.hideprogress();
    }

    @Override
    public void onSendLoginCodeSuccess(BaseResponse baseResponse) {
        if (loginView == null) {
            return;
        }
        if (baseResponse != null) {
            loginView.onSendLoginCodeSuccess(baseResponse);
        }
        loginView.hideprogress();
    }
}

package tdh.ifm.android.imatch.app.presenter;

import android.content.Context;

import tdh.ifm.android.imatch.app.base.BasePresenter;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.RegisterDriver;
import tdh.ifm.android.imatch.app.bean.RequestPhone;
import tdh.ifm.android.imatch.app.bean.User;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.view.RegisterDriverView;

/**
 * Created by tdh on 2017/5/8.
 */

public class RegisterDriverPresenterImpl implements UserModel.OnRegisterDriverListener,UserModel.OnRegisterCodeListener,BasePresenter {

    private RegisterDriverView registerDriverView;
     public UserModel userModel;

    public RegisterDriverPresenterImpl(Context context, RegisterDriverView registerDriverView) {
        this.registerDriverView = registerDriverView;
        userModel=new UserModelImpl(context);
    }

    public void registerDriver(RegisterDriver registerDriver) {
        registerDriverView.showProgress();
        userModel.registerDriver(registerDriver,this);
    }

    public void sendRegisterCode(RequestPhone requestPhone) {
        registerDriverView.showProgress();
        userModel.sendRegisterCode(requestPhone,this);
    }

    @Override
    public void onRegisterDriverSuccess(BaseResponse<User> baseResponse) {
        if (registerDriverView == null) {
            return;
        }
        if (baseResponse != null) {
            registerDriverView.hideprogress();
            registerDriverView.onRegisterDriverSuccess(baseResponse);
        } else {
            registerDriverView.showFailure("服务器出错了", null);
        }
    }

    @Override
    public void onSendRegisterCodeSuccess(BaseResponse baseResponse) {
        if (registerDriverView == null) {
            return;
        }
        if (baseResponse != null) {
            registerDriverView.onSendRegisterCode(baseResponse);
        }
        registerDriverView.hideprogress();
    }

    @Override
    public void onFailure(String str, Throwable t) {
    }

    @Override
    public void onResState(int code) {
    }

    @Override
    public void onDestory() {
        registerDriverView = null;
        if (userModel != null) {
            userModel = null;
        }
    }
}

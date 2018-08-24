package tdh.ifm.android.imatch.app.presenter;

import android.content.Context;

import tdh.ifm.android.imatch.app.base.BasePresenter;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.RegisterDriver;
import tdh.ifm.android.imatch.app.bean.RequestPhone;
import tdh.ifm.android.imatch.app.bean.User;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.view.RegisterAgentView;

/**
 * Created by tdh on 2017/5/12.
 */

public class RegisterAgentPresenterImpl implements UserModel.OnRegisterAgentListener,UserModel.OnRegisterCodeListener,BasePresenter {

    private RegisterAgentView registerAgentView;
    private UserModel userModel;

    public RegisterAgentPresenterImpl(Context context,RegisterAgentView registerAgentView){
        this.registerAgentView=registerAgentView;
        userModel=new UserModelImpl(context);
    }

    public void registerAgent(RegisterDriver registerDriver){
        registerAgentView.showProgress();
        userModel.registerAgent(registerDriver,this);
    }

    public void sendRegisterCode(RequestPhone requestPhone) {
        registerAgentView.showProgress();
        userModel.sendRegisterCode(requestPhone,this);
    }

    @Override
    public void onFailure(String str, Throwable t) {
    }

    @Override
    public void onResState(int code) {
    }

    @Override
    public void onDestory() {
        registerAgentView = null;
        if (userModel != null) {
            userModel = null;
        }
    }

    @Override
    public void onRegisterAgentSuccess(BaseResponse<User> baseResponse) {
        if (registerAgentView == null) {
            return;
        }
        if (baseResponse != null) {
            registerAgentView.hideprogress();
            registerAgentView.onRegisterAgentSuccess(baseResponse);
        } else {
            registerAgentView.showFailure("服务器出错了", null);
        }
    }

    @Override
    public void onSendRegisterCodeSuccess(BaseResponse baseResponse) {
        if (registerAgentView == null) {
            return;
        }
        if (baseResponse != null) {
            registerAgentView.onSendRegisterCode(baseResponse);
        }
        registerAgentView.hideprogress();
    }
}

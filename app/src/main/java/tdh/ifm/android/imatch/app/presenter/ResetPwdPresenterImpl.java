package tdh.ifm.android.imatch.app.presenter;

import android.content.Context;

import tdh.ifm.android.imatch.app.base.BasePresenter;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.RequestPhone;
import tdh.ifm.android.imatch.app.bean.RequestResetPwd;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.view.ResetPwdView;

/**
 * Created by tdh on 2017/5/9.
 */

public class ResetPwdPresenterImpl implements UserModel.OnResetPwdListener,BasePresenter {

    private ResetPwdView resetPwdView;
    private UserModel userModel;

    public ResetPwdPresenterImpl(Context context,ResetPwdView resetPwdView){
        this.resetPwdView=resetPwdView;
        userModel=new UserModelImpl(context);
    }

    public void resetPassword(RequestResetPwd requestResetPwd){
        resetPwdView.showProgress();
        userModel.resetPassword(requestResetPwd,this);
    }

    public void sendPasswordCode(RequestPhone requestPhone){
        resetPwdView.showProgress();
        userModel.sendPasswordCode(requestPhone,this);
    }
    @Override
    public void onFailure(String str, Throwable t) {
    }

    @Override
    public void onResState(int code) {
    }

    @Override
    public void onDestory() {
        resetPwdView = null;
        if (userModel != null) {
            userModel = null;
        }
    }

    @Override
    public void onResetPwdSuccess(BaseResponse baseResponse) {
        if (resetPwdView == null) {
            return;
        }
        if (baseResponse != null) {
            resetPwdView.hideprogress();
            resetPwdView.onResetPwdSuccess(baseResponse);
        } else {
            resetPwdView.showFailure("服务器出错了", null);
        }
    }

    @Override
    public void onSendPasswordCodeSuccess(BaseResponse baseResponse) {
        if (resetPwdView == null) {
            return;
        }
        if (baseResponse != null) {
            resetPwdView.onSendPasswordCodeSuccess(baseResponse);
        }
        resetPwdView.hideprogress();
    }
}

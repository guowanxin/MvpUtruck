package tdh.ifm.android.imatch.app.presenter;

import android.content.Context;

import tdh.ifm.android.imatch.app.base.BasePresenter;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.RegisterShipper;
import tdh.ifm.android.imatch.app.bean.RequestPhone;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.view.RegisterCompanyView;

/**
 * Created by tdh on 2017/5/11.
 */

public class RegisterCompanyPresenterImpl implements UserModel.OnRegisterCompanyListener,UserModel.OnRegisterCodeListener,BasePresenter{

    private RegisterCompanyView registerCompanyView;
    private UserModel userModel;

    public  RegisterCompanyPresenterImpl(Context context,RegisterCompanyView registerCompanyView){
        this.registerCompanyView=registerCompanyView;
        userModel=new UserModelImpl(context);
    }

    public void registerCompany(RegisterShipper registerShipper){
        registerCompanyView.showProgress();
        userModel.registerCompany(registerShipper,this);
    }
    public void sendRegisterCode(RequestPhone requestPhone) {
        registerCompanyView.showProgress();
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
        registerCompanyView = null;
        if (userModel != null) {
            userModel = null;
        }
    }

    @Override
    public void onRegisterCompanySuccess(BaseResponse baseResponse) {
        if (registerCompanyView == null) {
            return;
        }
        if (baseResponse != null) {
            registerCompanyView.hideprogress();
            registerCompanyView.onRegisterCompanySuccess(baseResponse);
        } else {
            registerCompanyView.showFailure("服务器出错了", null);
        }
    }

    @Override
    public void onSendRegisterCodeSuccess(BaseResponse baseResponse) {
        if (registerCompanyView == null) {
            return;
        }
        if (baseResponse != null) {
            registerCompanyView.onSendRegisterCode(baseResponse);
        }
        registerCompanyView.hideprogress();
    }
}

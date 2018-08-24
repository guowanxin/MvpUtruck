package tdh.ifm.android.imatch.app.presenter;

import android.content.Context;

import com.tdh.common.utils.Const;

import tdh.ifm.android.imatch.app.base.BasePresenter;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.RegisterShipper;
import tdh.ifm.android.imatch.app.bean.RequestPhone;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.view.RegisterShiperView;

/**
 * Created by tdh on 2017/5/11.
 */

public class RegisterShipperPresenterImpl implements UserModel.OnRegisterShipperListener,UserModel.OnRegisterCodeListener,BasePresenter {

    private RegisterShiperView registerShiperView;
    private UserModel userModel;

    public RegisterShipperPresenterImpl(Context context,RegisterShiperView registerShiperView){
        this.registerShiperView=registerShiperView;
        userModel=new UserModelImpl(context);
    }

    public void registerShipper(RegisterShipper registerShipper){
        registerShiperView.showProgress();
        userModel.registerShipper(registerShipper,this);
    }

    public void sendRegisterCode(RequestPhone requestPhone) {
        registerShiperView.showProgress();
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
        registerShiperView = null;
        if (userModel != null) {
            userModel = null;
        }
    }

    @Override
    public void onRegisterShipperSuccess(BaseResponse baseResponse) {
        if (registerShiperView == null) {
            return;
        }
        if (baseResponse != null) {
            registerShiperView.hideprogress();
            registerShiperView.onRegisterShipperSuccess(baseResponse);
        } else {
            registerShiperView.showFailure("服务器出错了", null);
        }

    }

    @Override
    public void onSendRegisterCodeSuccess(BaseResponse baseResponse) {
        if (registerShiperView == null) {
            return;
        }
        if (baseResponse != null) {
            registerShiperView.onSendRegisterCode(baseResponse);
        }
        registerShiperView.hideprogress();
    }
}

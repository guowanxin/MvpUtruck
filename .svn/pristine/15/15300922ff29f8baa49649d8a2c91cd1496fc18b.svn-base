package tdh.ifm.android.imatch.app.presenter;

import android.content.Context;

import java.util.List;

import tdh.ifm.android.imatch.app.base.BasePresenter;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.RequestUpdateControl;
import tdh.ifm.android.imatch.app.bean.TransportControl;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.view.TransportControlView;

/**
 * Author：gwx
 * Create at：2017/5/18 18:43
 */
public class TransportControlPresenterImpl implements UserModel.OnTransportControlListener,BasePresenter {
    private TransportControlView transportControlView;
    public UserModel userModel;

    public TransportControlPresenterImpl(Context context, TransportControlView transportControlView){
        this.transportControlView=transportControlView;
        userModel=new UserModelImpl(context);
    }

    public void getControlList() {
        transportControlView.showProgress();
        userModel.getControlList(this);
    }

    public void updateControl(RequestUpdateControl request) {
        transportControlView.showProgress();
        userModel.updateControl(request,this);
    }

    @Override
    public void onFailure(String str, Throwable t) {
        if (transportControlView == null) {
            return;
        }
        transportControlView.hideprogress();
        transportControlView.showFailure(str, t);
    }

    @Override
    public void onResState(int code) {
        if (transportControlView == null) {
            return;
        }
        transportControlView.showResState(code);
        transportControlView.hideprogress();
    }

    @Override
    public void onDestory() {
        transportControlView = null;
        if (userModel != null) {
            userModel = null;
        }
    }

    @Override
    public void onTransportControlListSuccess(BaseResponse<List<TransportControl>> baseResponse) {
        if (transportControlView == null) {
            return;
        }
        if (baseResponse != null) {
            transportControlView.onTransportControlListSuccess(baseResponse);
        }
        transportControlView.hideprogress();
    }

    @Override
    public void onUpdateControlSuccess(BaseResponse baseResponse) {
        if (transportControlView == null) {
            return;
        }
        if (baseResponse != null) {
            transportControlView.onUpdateControlSuccess(baseResponse);
        }
        transportControlView.hideprogress();
    }
}

package tdh.ifm.android.imatch.app.presenter;

import android.content.Context;

import java.util.List;

import tdh.ifm.android.imatch.app.base.BasePageList;
import tdh.ifm.android.imatch.app.base.BasePresenter;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.MyMessage;
import tdh.ifm.android.imatch.app.bean.RequestMessage;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.view.MyMessageDetailView;

/**
 * Created by tdh on 2017/5/16.
 */

public class MyMessageDetailPresenterImpl implements UserModel.OnMyMessageDetailListener,BasePresenter {

    private MyMessageDetailView myMessageDetailView;
    private UserModel userModel;

    public MyMessageDetailPresenterImpl(Context context,MyMessageDetailView myMessageDetailView){
        this.myMessageDetailView=myMessageDetailView;
        userModel=new UserModelImpl(context);
    }

    public void myMessageDetail(RequestMessage request){
        myMessageDetailView.showProgress();
        userModel.myMessageDetail(request,this);
    }
    @Override
    public void onDestory() {

    }

    @Override
    public void onMyMessageDetailSuccess(BaseResponse<BasePageList<List<MyMessage>>> baseResponse) {
        if (myMessageDetailView == null) {
            return;
        }
        if (baseResponse != null) {
            myMessageDetailView.onMyMessageDetailSuccess(baseResponse);
        }else {

        }
        myMessageDetailView.hideprogress();
    }

    @Override
    public void onFailure(String str, Throwable t) {

    }

    @Override
    public void onResState(int code) {

    }

}

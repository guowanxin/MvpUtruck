package tdh.ifm.android.imatch.app.presenter;

import android.content.Context;

import java.util.List;

import tdh.ifm.android.imatch.app.base.BasePageList;
import tdh.ifm.android.imatch.app.base.BasePresenter;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.MyMessage;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.view.MyMessageListView;

/**
 * Created by tdh on 2017/5/16.
 */

public class MyMessageListPresenterImpl implements UserModel.OnMyMessageListener,BasePresenter {

    private MyMessageListView myMessageListView;
    private UserModel userModel;

    public MyMessageListPresenterImpl(Context context,MyMessageListView myMessageListView){
        this.myMessageListView=myMessageListView;
        userModel=new UserModelImpl(context);
    }

    public void myMessageList(){
        myMessageListView.showProgress();
        userModel.myMessageList(this);
    }
    @Override
    public void onFailure(String str, Throwable t) {
        if (myMessageListView == null) {
            return;
        }
        myMessageListView.showFailure(str, t);
        myMessageListView.hideprogress();
    }

    @Override
    public void onResState(int code) {
        if (myMessageListView == null) {
            return;
        }
        myMessageListView.showResState(code);
        myMessageListView.hideprogress();
    }


    @Override
    public void onDestory() {
        myMessageListView = null;
        if (userModel != null) {
            userModel = null;
        }
    }

    @Override
    public void onMyMessageSuccess(BaseResponse<List<MyMessage>> baseResponse) {
        if (myMessageListView == null) {
            return;
        }
        if (baseResponse != null) {
            myMessageListView.onMyMessageSuccess(baseResponse);
        }else {

        }
        myMessageListView.hideprogress();
    }
}

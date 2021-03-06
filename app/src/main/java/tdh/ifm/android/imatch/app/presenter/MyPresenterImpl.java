package tdh.ifm.android.imatch.app.presenter;

import android.content.Context;

import tdh.ifm.android.imatch.app.base.BasePresenter;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.MemberInfo;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.view.MyView;

/**
 * Author：gwx
 * Create at：2017/5/8 20:02
 */
public class MyPresenterImpl implements UserModel.OnQueryMemberListener,BasePresenter {

    private MyView myView;
    public UserModel userModel;


    public MyPresenterImpl(Context context, MyView myView) {
        this.myView = myView;
        userModel = new UserModelImpl(context);

    }

    public void queryMember() {
        myView.showProgress();
        userModel.queryMember(this);
    }

    @Override
    public void onFailure(String str, Throwable t) {
        if (myView == null) {
            return;
        }
        myView.showFailure(str, t);
        myView.hideprogress();
    }

    @Override
    public void onResState(int code) {
        if (myView == null) {
            return;
        }
        myView.showResState(code);
        myView.hideprogress();
    }

    @Override
    public void onDestory() {
        myView = null;
        if (userModel != null) {
            userModel = null;
        }
    }


    @Override
    public void onQueryMemberSuccess(BaseResponse<MemberInfo> baseResponse) {
        if (myView == null) {
            return;
        }
        if (baseResponse != null) {
            myView.onQueryMemberSuccess(baseResponse);
        }
        myView.hideprogress();
    }
}

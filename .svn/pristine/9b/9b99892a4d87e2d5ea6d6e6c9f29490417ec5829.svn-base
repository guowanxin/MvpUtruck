package tdh.ifm.android.imatch.app.presenter;

import android.content.Context;

import tdh.ifm.android.imatch.app.base.BasePresenter;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.CompanyInfos;
import tdh.ifm.android.imatch.app.bean.PersonInfo;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.view.UserTypeMessageView;

/**
 * Created by tdh on 2017/5/10.
 */

public class UserTypePresenterImpl implements UserModel.OnPersonMessageListener, BasePresenter {

    private UserTypeMessageView userTypeMessageView;
    private UserModel userModel;

    public UserTypePresenterImpl(Context context, UserTypeMessageView userTypeMessageView) {
        this.userTypeMessageView = userTypeMessageView;
        userModel=new UserModelImpl(context);
    }

    public void personMessage(){
        userTypeMessageView.showProgress();
        userModel.personMessage(this);
    }

    public void companyMessage() {
        userTypeMessageView.showProgress();
        userModel.companyMessage(this);
    }

    public void updatePersonMessage(PersonInfo personInfo){
        userTypeMessageView.showProgress();
        userModel.updatePersonMessage(personInfo, this);
    }

    public void updateCompanyMessage(CompanyInfos companyInfos){
        userTypeMessageView.showProgress();
        userModel.updateCompanyMessage(companyInfos, this);
    }

    @Override
    public void onFailure(String str, Throwable t) {
        if (userTypeMessageView == null) {
            return;
        }
        userTypeMessageView.hideprogress();
        userTypeMessageView.showFailure(str, t);
    }

    @Override
    public void onResState(int code) {
        if (userTypeMessageView == null) {
            return;
        }
        userTypeMessageView.showResState(code);
        userTypeMessageView.hideprogress();
    }

    @Override
    public void onDestory() {
        userTypeMessageView = null;
        if (userModel != null) {
            userModel = null;
        }
    }

    @Override
    public void onPersonMessageSuccess(BaseResponse baseResponse) {
        if (userTypeMessageView == null) {
            return;
        }
        if (baseResponse != null) {
            userTypeMessageView.onPersonMessageSuccess(baseResponse);
        }
        userTypeMessageView.hideprogress();
    }

    @Override
    public void onCompanyMessageSuccess(BaseResponse baseResponse) {
        if (userTypeMessageView == null) {
            return;
        }
        if (baseResponse != null) {
            userTypeMessageView.onCompanyMessageSuccess(baseResponse);
        }
        userTypeMessageView.hideprogress();
    }

    @Override
    public void onUpdatePersonMessageSuccess(BaseResponse baseResponse) {
        if (userTypeMessageView == null) {
            return;
        }
        if (baseResponse != null) {
            userTypeMessageView.onUpdatePersonMessageSuccess(baseResponse);
        }
        userTypeMessageView.hideprogress();
    }

    @Override
    public void onUpdateCompanyMessageSuccess(BaseResponse baseResponse) {
        if (userTypeMessageView == null) {
            return;
        }
        if (baseResponse != null) {
            userTypeMessageView.onUpdateCompanyMessageSuccess(baseResponse);
        }
        userTypeMessageView.hideprogress();
    }
}

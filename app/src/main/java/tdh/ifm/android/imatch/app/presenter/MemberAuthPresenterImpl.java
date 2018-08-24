package tdh.ifm.android.imatch.app.presenter;

import android.content.Context;

import java.util.List;

import tdh.ifm.android.imatch.app.base.BasePresenter;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.FileType;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.view.MemberAuthView;

/**
 * Author：gwx
 * Create at：2017/5/10 11:43
 */
public class MemberAuthPresenterImpl implements UserModel.OnMemberAuthInfoListener, BasePresenter{

    private MemberAuthView memberAuthView;
    public UserModel userModel;


    public MemberAuthPresenterImpl(Context context, MemberAuthView memberAuthView) {
        this.memberAuthView = memberAuthView;
        userModel = new UserModelImpl(context);

    }

    public void queryMemberAuthInfo() {
        memberAuthView.showProgress();
        userModel.queryMemberAuthInfo(this);
    }

    public void completionMemberAuthInfo(List<FileType> fileTypes) {
        memberAuthView.showProgress();
        userModel.completionMemberAuthInfo(fileTypes,this);
    }

    @Override
    public void onFailure(String str, Throwable t) {
        if (memberAuthView == null) {
            return;
        }
        memberAuthView.showFailure(str, t);
        memberAuthView.hideprogress();
    }

    @Override
    public void onResState(int code) {
        if (memberAuthView == null) {
            return;
        }
        memberAuthView.showResState(code);
        memberAuthView.hideprogress();
    }

    @Override
    public void onDestory() {
        memberAuthView = null;
        if (userModel != null) {
            userModel = null;
        }
    }

    @Override
    public void onQueryMemberAuthInfoSuccess(BaseResponse<List<FileType>> baseResponse) {
        if (memberAuthView == null) {
            return;
        }
        if (baseResponse != null) {
            memberAuthView.onQueryMemberAuthInfoSuccess(baseResponse);
        }
        memberAuthView.hideprogress();
    }

    @Override
    public void onCompletionMemberAuthInfoSuccess(BaseResponse baseResponse) {
        if (memberAuthView == null) {
            return;
        }
        if (baseResponse != null) {
            memberAuthView.onCompletionMemberAuthInfoSuccess(baseResponse);
        }
        memberAuthView.hideprogress();
    }
}

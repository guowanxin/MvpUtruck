package tdh.ifm.android.imatch.app.presenter;

import android.content.Context;

import java.util.List;

import tdh.ifm.android.imatch.app.base.BasePageList;
import tdh.ifm.android.imatch.app.base.BasePresenter;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.FriendInfo;
import tdh.ifm.android.imatch.app.bean.RequestDeleteFriend;
import tdh.ifm.android.imatch.app.bean.RequestFriendList;
import tdh.ifm.android.imatch.app.bean.RequestFriend;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.view.FriendView;


public class FriendPresenterImpl implements UserModelImpl.OnFriendListener,UserModel.OnDeleteFriendListener,BasePresenter{

    private FriendView friendView;
    public UserModel userModel;


    public FriendPresenterImpl(Context context, FriendView friendView) {
        this.friendView = friendView;
        userModel = new UserModelImpl(context);
    }

    public void friendList(RequestFriendList request) {
        friendView.showProgress();
        userModel.getFriendList(request,this);
    }


    public void deleteFriend(RequestDeleteFriend request) {
        friendView.showProgress();
        userModel.deleteFriend(request,this);
    }


    @Override
    public void onFriendListSuccess(BaseResponse<BasePageList<List<FriendInfo>>> baseResponse) {
        if (friendView == null) {
            return;
        }
        if (baseResponse != null) {
            friendView.onFriendListSuccess(baseResponse);
        }else {

        }
        friendView.hideprogress();
    }

    @Override
    public void onDeleteFriendSuccess(BaseResponse baseResponse) {
        if (friendView == null) {
            return;
        }
        if (baseResponse != null) {
            friendView.onDeleteFriendSuccess(baseResponse);
        }else {

        }
        friendView.hideprogress();
    }

    @Override
    public void onFailure(String str, Throwable t) {
        if (friendView == null) {
            return;
        }
        friendView.showFailure(str, t);
        friendView.hideprogress();
    }

    @Override
    public void onResState(int code) {
        if (friendView == null) {
            return;
        }
        friendView.showResState(code);
        friendView.hideprogress();
    }


    @Override
    public void onDestory() {
        friendView = null;
        if (userModel != null) {
            userModel = null;
        }
    }
}

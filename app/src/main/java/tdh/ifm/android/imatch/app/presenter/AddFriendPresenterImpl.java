package tdh.ifm.android.imatch.app.presenter;

import android.content.Context;

import java.util.List;

import tdh.ifm.android.imatch.app.base.BasePresenter;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.RequestAddFriendSearch;
import tdh.ifm.android.imatch.app.bean.FriendInfo;
import tdh.ifm.android.imatch.app.bean.RequestFriend;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.view.AddFriendView;

/**
 * Created by tdh on 2017/5/8.
 */

public class AddFriendPresenterImpl implements UserModel.OnAddFriendList,BasePresenter{

    private AddFriendView friendView;
    public UserModel userModel;

    public AddFriendPresenterImpl(Context context,AddFriendView friendView){
        this.friendView=friendView;
        userModel=new UserModelImpl(context);
    }

    public void addFriendSearch(RequestAddFriendSearch requestAddFriendSearch) {
        friendView.showProgress();
        userModel.addFriendSearch(requestAddFriendSearch,this);
    }

    public void addFriend(RequestFriend request) {
        friendView.showProgress();
        userModel.addFriend(request,this);
    }


    @Override
    public void onAddFriendSearchSuccess(BaseResponse<List<FriendInfo>> baseResponse) {
        if (friendView == null) {
            return;
        }
        if (baseResponse != null) {
            friendView.onAddFriendSearchSuccess(baseResponse);
        }else {

        }
        friendView.hideprogress();
    }

    @Override
    public void onAddFriendSuccess(BaseResponse baseResponse) {
        if (friendView == null) {
            return;
        }
        if (baseResponse != null) {
            friendView.onAddFriendSuccess(baseResponse);
        }else {

        }
        friendView.hideprogress();
    }

    @Override
    public void onFailure(String str, Throwable t) {
        if (friendView == null) {
            return;
        }
        friendView.hideprogress();
        friendView.showFailure(str, t);
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

package tdh.ifm.android.imatch.app.view;


import java.util.List;

import tdh.ifm.android.imatch.app.base.BasePageList;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.base.BaseView;
import tdh.ifm.android.imatch.app.bean.FriendInfo;

public interface FriendView extends BaseView {

    void onFriendListSuccess(BaseResponse<BasePageList<List<FriendInfo>>> baseResponse);

    void onDeleteFriendSuccess(BaseResponse baseResponse);

}

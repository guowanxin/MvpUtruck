package tdh.ifm.android.imatch.app.view;

import java.util.List;

import tdh.ifm.android.imatch.app.base.BasePageList;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.base.BaseView;
import tdh.ifm.android.imatch.app.bean.MyMessage;

/**
 * Created by tdh on 2017/5/16.
 */

public interface MyMessageListView extends BaseView{
    void onMyMessageSuccess(BaseResponse<List<MyMessage>> baseResponse);
}

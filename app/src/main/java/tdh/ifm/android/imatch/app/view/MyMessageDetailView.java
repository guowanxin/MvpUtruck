package tdh.ifm.android.imatch.app.view;

import java.util.List;

import tdh.ifm.android.imatch.app.base.BasePageList;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.base.BaseView;
import tdh.ifm.android.imatch.app.bean.MyMessage;

/**
 * Created by tdh on 2017/5/16.
 */

public interface MyMessageDetailView extends BaseView {
    void onMyMessageDetailSuccess(BaseResponse<BasePageList<List<MyMessage>>> baseResponse);
}

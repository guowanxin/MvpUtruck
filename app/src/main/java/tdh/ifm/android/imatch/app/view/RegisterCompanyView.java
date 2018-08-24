package tdh.ifm.android.imatch.app.view;

import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.base.BaseView;

/**
 * Created by tdh on 2017/5/11.
 */

public interface RegisterCompanyView extends BaseView{
    void onRegisterCompanySuccess(BaseResponse baseResponse);
    void onSendRegisterCode(BaseResponse baseResponse);
}

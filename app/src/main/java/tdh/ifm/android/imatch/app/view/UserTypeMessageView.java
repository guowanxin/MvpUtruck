package tdh.ifm.android.imatch.app.view;

import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.base.BaseView;
import tdh.ifm.android.imatch.app.bean.CompanyInfos;
import tdh.ifm.android.imatch.app.bean.PersonInfo;

/**
 * Created by tdh on 2017/5/10.
 */

public interface UserTypeMessageView extends BaseView{
    void onPersonMessageSuccess(BaseResponse<PersonInfo> baseResponse);
    void onCompanyMessageSuccess(BaseResponse<CompanyInfos> baseResponse);

    void onUpdatePersonMessageSuccess(BaseResponse baseResponse);
    void onUpdateCompanyMessageSuccess(BaseResponse baseResponse);
}

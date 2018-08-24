package tdh.ifm.android.imatch.app.ui.activity.driver;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdh.common.utils.APPLog;
import com.tdh.common.utils.CommonUtil;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.base.BaseActivity;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.CompanyInfos;
import tdh.ifm.android.imatch.app.bean.PersonInfo;
import tdh.ifm.android.imatch.app.presenter.UserTypePresenterImpl;
import tdh.ifm.android.imatch.app.ui.view.MyTitleView;
import tdh.ifm.android.imatch.app.utils.Constants;
import tdh.ifm.android.imatch.app.utils.ImageLoaderUtils;
import tdh.ifm.android.imatch.app.utils.RegExpConstants;
import tdh.ifm.android.imatch.app.utils.SharedPreferencesUtil;
import tdh.ifm.android.imatch.app.utils.Util;
import tdh.ifm.android.imatch.app.view.UserTypeMessageView;

/**
 * xyf
 * 修改会员信息
 * Created by tdh on 2017/5/4.
 */

public class UpdateMemberInfoActivity extends BaseActivity implements UserTypeMessageView{
    @BindView(R.id.titleview)
    MyTitleView titleview;
    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.ll_person_message)
    RelativeLayout llPersonSyS;
    @BindView(R.id.et_plate_number)
    EditText etPlateNumber;
    @BindView(R.id.et_load)
    EditText etLoad;
    @BindView(R.id.et_car_length)
    EditText etCarLength;
    @BindView(R.id.et_car_type)
    EditText etCarType;

    @BindView(R.id.ll_enterprise_message)
    RelativeLayout llEnterpriseSys;
    @BindView(R.id.et_enterprise_name)
    EditText etEnterpriseName;
    @BindView(R.id.et_telephone)
    EditText etTelephone;

    @BindString(R.string.txt_update_member_info)
    String title;
    @BindView(R.id.tv_member_level)
    TextView tvMemberLevel;

    private String userType;
    private String level;

    private double weight = 0;

    private UserTypePresenterImpl userTypePresenter;
    private PersonInfo personInfo;
    private CompanyInfos companyInfos;

    @Override
    public int getContentViewId() {
        return R.layout.activity_update_member_info;
    }

    @Override
    public void initTitleView() {
        super.initTitleView();
        titleview.getTv_title().setText(title);
        titleview.getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        personInfo =new PersonInfo();
        companyInfos =new CompanyInfos();
        userTypePresenter=new UserTypePresenterImpl(context,this);

        userType = SharedPreferencesUtil.getValue(Constants.USERTYPE,"");
        if (userType.equals(Constants.USERTYPE_S)) {
            llPersonSyS.setVisibility(View.GONE);
            llEnterpriseSys.setVisibility(View.VISIBLE);
            etName.setFocusable(false);
            etEnterpriseName.setFocusable(false);
            userTypePresenter.companyMessage();

        }else if (userType.equals(Constants.USERTYPE_C)) {
            llPersonSyS.setVisibility(View.VISIBLE);
            llEnterpriseSys.setVisibility(View.GONE);

            userTypePresenter.personMessage();


        }else if (userType.equals(Constants.USERTYPE_AGENT)) {
            llPersonSyS.setVisibility(View.GONE);
            llEnterpriseSys.setVisibility(View.GONE);


        }else if (userType.equals(Constants.USERTYPE_L)) {
            llPersonSyS.setVisibility(View.GONE);
            llEnterpriseSys.setVisibility(View.VISIBLE);

            userTypePresenter.companyMessage();

        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            level = bundle.getString(Constants.LEVEL, "");
            String userName = bundle.getString(Constants.USERNAME, "");
            if (!TextUtils.isEmpty(userName)) {
                etName.setText(userName);
            }
            if (level.equals(Constants.LEVEL_PRIMARY)) {
                tvMemberLevel.setText("初级");
            }else if (level.equals(Constants.LEVEL_HIGHER)) {
                tvMemberLevel.setText("高级");
            }
        }
    }

    private boolean isnullPerson(){
        if (TextUtils.isEmpty(getValue(etName))) {
            CommonUtil.getToast(context, getString(R.string.prompt_name));
            return false;
        }
        if (TextUtils.isEmpty(getValue(etPlateNumber))) {
            CommonUtil.getToast(context, getString(R.string.prompt_plate_number));
            return false;
        }
        if (!getValue(etPlateNumber).matches(RegExpConstants.ISTRUCKERNO)) {
            CommonUtil.getToast(context, getString(R.string.hint_effective_no));
            return false;
        }
        if (TextUtils.isEmpty(getValue(etLoad))) {
            CommonUtil.getToast(context, getString(R.string.prompt_load));
            return false;
        } else {
            weight = Double.parseDouble(getValue(etLoad));
            if (weight <= 0 || weight > 999) {
                CommonUtil.getToast(context, getResources().getString(R.string.hint_error_weight));
                return false;
            }
        }
        if (TextUtils.isEmpty(getValue(etCarLength))){
            CommonUtil.getToast(context,getString(R.string.prompt_car_length));
            return false;
        }
        if (TextUtils.isEmpty(getValue(etCarType))){
            CommonUtil.getToast(context,getString(R.string.prompt_car_type));
            return false;
        }
        return true;
    }

    private boolean isnullCompany(){
        if (TextUtils.isEmpty(getValue(etName))) {
            CommonUtil.getToast(context, getString(R.string.prompt_name));
            return false;
        }
        if (TextUtils.isEmpty(getValue(etEnterpriseName))) {
            CommonUtil.getToast(context, getString(R.string.prompt_enterprise_name));
            return false;
        }
        return true;
    }

    //选择车长
    @OnClick(R.id.ll_car_length)
    void showVanlength() {
        Util.showDialogData(context, Constants.stkLenKeys, Constants.stkLenValues, "请选择车长", etCarLength);
    }

    //选择车型
    @OnClick(R.id.ll_car_type)
    void showVanType() {
        Util.showDialogData(context, Constants.tkcartypeKeys, Constants.tkcarTypeValues, "请选择车型", etCarType);
    }

    @OnClick(R.id.ll_member_level)
    public void onLlMemberLevelClicked() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.LEVEL,level);
        Util.intentToActivity(context,MemberLevelActivity.class,10,bundle);
    }

    @OnClick(R.id.btn_submit_audit)
    public void onBtnSubmitAuditClicked() {
        if (userType.equals(Constants.USERTYPE_S)) {
            if (!isnullCompany()){
                return;
            }
            companyInfos.setUserName(getValue(etName));
            companyInfos.setCompanyName(getValue(etEnterpriseName));
            companyInfos.setFixedTel(getValue(etTelephone));
            userTypePresenter.updateCompanyMessage(companyInfos);

        }else if (userType.equals(Constants.USERTYPE_C)) {
            if (!isnullPerson()){
                return;
            }
            personInfo.setMemberName(getValue(etName));
            personInfo.setVehiclePlateNo(getValue(etPlateNumber));
            personInfo.setVehicleLoad(getValue(etLoad));
            personInfo.setVehicleLength(Util.getKeyByValue(Constants.stkLenKeys, Constants.stkLenValues,getValue(etCarLength)));
            personInfo.setVehicleModel(Util.getKeyByValue(Constants.tkcartypeKeys, Constants.tkcarTypeValues,getValue(etCarType)));
            userTypePresenter.updatePersonMessage(personInfo);

        }else if (userType.equals(Constants.USERTYPE_AGENT)) {

            personInfo.setMemberName(getValue(etName));
            userTypePresenter.updatePersonMessage(personInfo);

        }else if (userType.equals(Constants.USERTYPE_L)) {
            if (!isnullCompany()){
                return;
            }
            companyInfos.setUserName(getValue(etName));
            companyInfos.setCompanyName(getValue(etEnterpriseName));
            companyInfos.setFixedTel(getValue(etTelephone));
            userTypePresenter.updateCompanyMessage(companyInfos);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 10) {
//            level = data.getStringExtra(Constants.LEVEL);
//            if (Constants.LEVEL_PRIMARY.equals(level)) {
//                tvMemberLevel.setText("初级");
//            }else if (Constants.LEVEL_HIGHER.equals(level)) {
//                tvMemberLevel.setText("高级");
//            }
            setResult(10);
            finish();
        }
    }

    @Override
    public void showProgress() {
        dialogShow();
    }

    @Override
    public void hideprogress() {
        dialogHide();
    }

    @Override
    public void showFailure(String str, Throwable t) {
        APPLog.error(str, t);
    }

    @Override
    public void showResState(int code) {
        Util.setResCode(context, code);
    }

    @Override
    public void onPersonMessageSuccess(BaseResponse<PersonInfo> baseResponse) {
        if (baseResponse.isSuccess()) {
            if (baseResponse.getBody() != null) {
                personInfo = baseResponse.getBody();
                if (!TextUtils.isEmpty(personInfo.getVehiclePlateNo())) {
                    etPlateNumber.setText(personInfo.getVehiclePlateNo());
                }
                if (!TextUtils.isEmpty(personInfo.getVehicleLoad())) {
                    etLoad.setText(personInfo.getVehicleLoad());
                }
                if (!TextUtils.isEmpty(personInfo.getVehicleLength())) {
                    etCarLength.setText(Util.getValueByKey(Constants.stkLenKeys, Constants.stkLenValues,personInfo.getVehicleLength()));
                }
                if (!TextUtils.isEmpty(personInfo.getVehicleModel())) {
                    etCarType.setText(Util.getValueByKey(Constants.tkcartypeKeys, Constants.tkcarTypeValues,personInfo.getVehicleModel()));
                }
            }
        }else {
            if (!TextUtils.isEmpty(baseResponse.getMessage())){
                CommonUtil.getToast(context,baseResponse.getMessage());
            }
        }
    }

    @Override
    public void onCompanyMessageSuccess(BaseResponse<CompanyInfos> baseResponse) {
        if (baseResponse.isSuccess()) {
            if (baseResponse.getBody() != null) {
                companyInfos = baseResponse.getBody();
                if (!TextUtils.isEmpty(companyInfos.getCompanyName())) {
                    etEnterpriseName.setText(companyInfos.getCompanyName());
                }
                if (!TextUtils.isEmpty(companyInfos.getFixedTel())) {
                    etTelephone.setText(companyInfos.getFixedTel());
                }
            }
        } else {
            if (!TextUtils.isEmpty(baseResponse.getMessage())){
                CommonUtil.getToast(context,baseResponse.getMessage());
            }
        }

    }

    @Override
    public void onUpdatePersonMessageSuccess(BaseResponse baseResponse) {
        if (baseResponse.isSuccess()) {
            finish();
            CommonUtil.getToast(context,baseResponse.getMessage());
        }
        if (!TextUtils.isEmpty(baseResponse.getMessage())){
            CommonUtil.getToast(context,baseResponse.getMessage());
        }
    }

    @Override
    public void onUpdateCompanyMessageSuccess(BaseResponse baseResponse) {
        if (baseResponse.isSuccess()) {
            finish();
            CommonUtil.getToast(context,baseResponse.getMessage());
        }
        if (!TextUtils.isEmpty(baseResponse.getMessage())){
            CommonUtil.getToast(context,baseResponse.getMessage());
        }
    }
}

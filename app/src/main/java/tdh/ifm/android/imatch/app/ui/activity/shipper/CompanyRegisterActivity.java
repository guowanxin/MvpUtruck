package tdh.ifm.android.imatch.app.ui.activity.shipper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdh.common.utils.APPLog;
import com.tdh.common.utils.CommonUtil;

import butterknife.BindBitmap;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.base.BaseActivity;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.RegisterShipper;
import tdh.ifm.android.imatch.app.bean.RequestPhone;
import tdh.ifm.android.imatch.app.presenter.RegisterCompanyPresenterImpl;
import tdh.ifm.android.imatch.app.ui.activity.driver.MemberLevelEditActivity;
import tdh.ifm.android.imatch.app.ui.view.IdentifyCode;
import tdh.ifm.android.imatch.app.ui.view.MyTitleView;
import tdh.ifm.android.imatch.app.utils.Constants;
import tdh.ifm.android.imatch.app.utils.RegExpConstants;
import tdh.ifm.android.imatch.app.utils.SharedPreferencesUtil;
import tdh.ifm.android.imatch.app.utils.Util;
import tdh.ifm.android.imatch.app.view.RegisterCompanyView;

/**
 * Created by tdh on 2017/5/11.
 */

public class CompanyRegisterActivity extends BaseActivity implements RegisterCompanyView{

    @BindView(R.id.titleview)
    MyTitleView titleview;

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.img_pwd)
    ImageView imgPwd;
    @BindView(R.id.et_enterprise_name)
    EditText etEnterpriseName;
    @BindView(R.id.et_telephone)
    EditText etTelephone;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.tv_code)
    TextView tvCode;

    @BindString(R.string.title_register_company)
    String title;

    @BindBitmap(R.mipmap.login_pwd_visible)
    Bitmap pwdVisible;
    @BindBitmap(R.mipmap.login_pwd_invisible)
    Bitmap pwdInVisible;

    /** 密码是否可见  默认不可见 **/
    private boolean isPwdVisible;

    private String mMobile;
    private String mPassword;

    private IdentifyCode iCode;

    private RegisterShipper registerShipper;
    private RegisterCompanyPresenterImpl registerCompanyPresenter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_shipper_register;
    }

    @Override
    public void initData() {
        registerShipper=new RegisterShipper();
        registerCompanyPresenter=new RegisterCompanyPresenterImpl(context,this);
        iCode = new IdentifyCode(60000, 1000,tvCode , (Activity) context);
    }

    @Override
    public void initTitleView() {
        titleview.getTv_title().setText(title);
        titleview.getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.img_pwd)
    public void onIvPwdClicked() {
        if (isPwdVisible) {
            /** 密码不可见 **/
            isPwdVisible = false;
            imgPwd.setImageBitmap(pwdInVisible);
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }else {
            /** 密码可见 **/
            isPwdVisible = true;
            imgPwd.setImageBitmap(pwdVisible);
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    public boolean isnull(){
        if (TextUtils.isEmpty(getValue(etMobile))) {
            CommonUtil.getToast(context, getString(R.string.prompt_mobile));
            return false;
        }
        if (!getValue(etMobile).matches(RegExpConstants.ISMOBILE)) {
            CommonUtil.getToast(context, getString(R.string.error_mobile_format));
            return false;
        }
        if (TextUtils.isEmpty(getValue(etPassword))) {
            CommonUtil.getToast(context, getString(R.string.prompt_password));
            return false;
        }
        if (!getValue(etPassword).matches(RegExpConstants.ISPASSWORD)) {
            CommonUtil.getToast(context, getString(R.string.error_password_format));
            return false;
        }
        if (TextUtils.isEmpty(getValue(etName))) {
            CommonUtil.getToast(context, getString(R.string.prompt_name));
            return false;
        }
        if (TextUtils.isEmpty(getValue(etEnterpriseName))){
            CommonUtil.getToast(context,getString(R.string.prompt_enterprise_name));
            return false;
        }
        if (TextUtils.isEmpty(getValue(etVerificationCode))) {
            CommonUtil.getToast(context, getString(R.string.prompt_login_code));
            return false;
        }
        return true;
    }

    private boolean isMobileValidate() {
        if (TextUtils.isEmpty(mMobile)) {
            CommonUtil.getToast(context, getString(R.string.prompt_mobile));
            return false;
        }
        if (!getValue(etMobile).matches(RegExpConstants.ISMOBILE)) {
            CommonUtil.getToast(context, getString(R.string.error_mobile_format));
            return false;
        }
        return  true;
    }

    //获取验证码
    @OnClick(R.id.tv_code)
    void onSetCode(){
        mMobile = getValue(etMobile);
        if (isMobileValidate()) {
            RequestPhone requestPhone = new RequestPhone();
            requestPhone.setMobile(mMobile);
            registerCompanyPresenter.sendRegisterCode(requestPhone);
            iCode.start();// 开始计时
        }
    }

    @OnClick(R.id.btn_shipper_register)
    void onShipperRegidter(){
        mPassword=getValue(etPassword);
        if(!isnull()){
            return;
        }
        registerShipper.setMobile(getValue(etMobile));
        registerShipper.setPassword(mPassword);
        registerShipper.setUsername(getValue(etName));
        registerShipper.setCompanyName(getValue(etEnterpriseName));
        registerShipper.setFixedTel(getValue(etTelephone));
        registerShipper.setCode(getValue(etVerificationCode));
        registerCompanyPresenter.registerCompany(registerShipper);
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
    public void onSendRegisterCode(BaseResponse baseResponse) {
        if (baseResponse.isSuccess()) {
            CommonUtil.getToast(context,baseResponse.getMessage());
        } else {
            CommonUtil.getToast(context, baseResponse.getMessage());
            reStart();
        }
    }

    @Override
    public void onRegisterCompanySuccess(BaseResponse baseResponse) {
        if (baseResponse.isSuccess()) {
            SharedPreferencesUtil.setValue(Constants.PHONE,mMobile);
            SharedPreferencesUtil.setValue(Constants.PASSWORD,mPassword);
            SharedPreferencesUtil.setValue(Constants.USERTYPE,Constants.USERTYPE_L);
            Util.intentToActivity(context, MemberLevelEditActivity.class);
            ((Activity) context).finish();

        } else {
            if (!TextUtils.isEmpty(baseResponse.getMessage())){
                CommonUtil.getToast(context,baseResponse.getMessage());
            }
        }
    }

    private void reStart() {
        if (null != iCode) {
            iCode.cancel();
            iCode.onFinish();
        }
    }
}

package tdh.ifm.android.imatch.app.ui.activity.driver;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdh.common.utils.APPLog;
import com.tdh.common.utils.CommonUtil;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.OnClick;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.base.BaseActivity;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.RegisterDriver;
import tdh.ifm.android.imatch.app.bean.RequestPhone;
import tdh.ifm.android.imatch.app.bean.User;
import tdh.ifm.android.imatch.app.presenter.RegisterDriverPresenterImpl;
import tdh.ifm.android.imatch.app.ui.activity.WebViewByCookieActivity;
import tdh.ifm.android.imatch.app.ui.view.IdentifyCode;
import tdh.ifm.android.imatch.app.ui.view.MyTitleView;
import tdh.ifm.android.imatch.app.utils.Constants;
import tdh.ifm.android.imatch.app.utils.NetContant;
import tdh.ifm.android.imatch.app.utils.RegExpConstants;
import tdh.ifm.android.imatch.app.utils.SharedPreferencesUtil;
import tdh.ifm.android.imatch.app.utils.Util;
import tdh.ifm.android.imatch.app.view.RegisterDriverView;

/**
 * xyf
 * 司机注册
 * Created by tdh on 2017/4/28
 */

public class RegisterDriverActivity extends BaseActivity implements RegisterDriverView {

    @BindView(R.id.titleview)
    MyTitleView titleview;

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.img_pwd)
    ImageView imgPwd;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_plate_number)
    EditText etPlateNumber;
    @BindView(R.id.et_car_length)
    EditText etCarLength;
    @BindView(R.id.et_load)
    EditText etLoad;
    @BindView(R.id.et_car_type)
    EditText etCarType;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.btn_drivate_register)
    Button btnDrivateRegister;

    @BindBitmap(R.mipmap.login_pwd_visible)
    Bitmap pwdVisible;
    @BindBitmap(R.mipmap.login_pwd_invisible)
    Bitmap pwdInVisible;

    /** 密码是否可见  默认不可见 **/
    private boolean isPwdVisible;

    private IdentifyCode iCode;

    private String mMobile;
    private String mPassword;

    private RegisterDriver registerDriver;
    private RegisterDriverPresenterImpl registerDriverPresenter;

    private double weight = 0;
    @Override
    public int getContentViewId() {
        return R.layout.activity_driver_register;
    }

    @Override
    public void initData() {
        registerDriver = new RegisterDriver();
        registerDriverPresenter = new RegisterDriverPresenterImpl(context, this);
        iCode = new IdentifyCode(60000, 1000,tvCode , (Activity) context);
    }

    @Override
    public void initTitleView() {
        titleview.getTv_title().setText(R.string.title_register_driver);
        titleview.getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean isNull() {
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
        if (TextUtils.isEmpty(getValue(etPlateNumber))) {
            CommonUtil.getToast(context, getString(R.string.prompt_plate_number));
            return false;
        }
        if (!getValue(etPlateNumber).matches(RegExpConstants.ISTRUCKERNO)) {
            CommonUtil.getToast(context, getString(R.string.hint_effective_no));
            return false;
        }
        if (TextUtils.isEmpty(getValue(etCarLength))){
            CommonUtil.getToast(context,getString(R.string.prompt_car_length));
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
        if (TextUtils.isEmpty(getValue(etCarType))) {
            CommonUtil.getToast(context, getString(R.string.prompt_car_type));
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

    //选择车长
    @OnClick(R.id.ll_car_length)
    void showVanlength() {
        Util.showDialogData(context, Constants.stkLenKeys, Constants.stkLenValues, "请选择车长",etCarLength);
    }

    //选择车型
    @OnClick(R.id.ll_car_type)
    void showVanType() {
        Util.showDialogData(context,Constants.tkcartypeKeys, Constants.tkcarTypeValues, "请选择车型",etCarType);
    }

    //用户协议
    @OnClick(R.id.tv_user_agree)
    void ontvUserAgree() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, NetContant.HTML_USERAGREE);
        Util.intentToActivity(context, WebViewByCookieActivity.class,bundle);
    }

    //获取验证码
    @OnClick(R.id.tv_code)
    void onSetCode(){
        mMobile = getValue(etMobile);
        if (isMobileValidate()) {
            RequestPhone requestPhone = new RequestPhone();
            requestPhone.setMobile(mMobile);
            registerDriverPresenter.sendRegisterCode(requestPhone);
            iCode.start();// 开始计时
        }
    }

    @OnClick(R.id.btn_drivate_register)
    void register(){
        mPassword=getValue(etPassword);
        if (!isNull()) {
            return;
        }
        registerDriver.setMobile(getValue(etMobile));
        registerDriver.setPassword(mPassword);
        registerDriver.setUsername(getValue(etName));
        registerDriver.setVehiclePlateNo(getValue(etPlateNumber));
        registerDriver.setCode(getValue(etVerificationCode));
        registerDriver.setVehicleLoad(getValue(etLoad));
        registerDriver.setVehicleLength(Util.getKeyByValue(Constants.stkLenKeys, Constants.stkLenValues,getValue(etCarLength)));
        registerDriver.setVehicleModel(Util.getKeyByValue(Constants.tkcartypeKeys, Constants.tkcarTypeValues,getValue(etCarType)));
        registerDriverPresenter.registerDriver(registerDriver);
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
    public void onRegisterDriverSuccess(BaseResponse<User> baseResponse) {
        if (baseResponse.isSuccess()) {
            User user = baseResponse.getBody();
            if (user != null && !TextUtils.isEmpty(user.getToken())) {
                SharedPreferencesUtil.setValue(Constants.TOKEN,user.getToken());
            }
            SharedPreferencesUtil.setValue(Constants.PHONE,mMobile);
            SharedPreferencesUtil.setValue(Constants.PASSWORD,mPassword);
            SharedPreferencesUtil.setValue(Constants.PLATENO,getValue(etPlateNumber));
            SharedPreferencesUtil.setValue(Constants.USERTYPE,Constants.USERTYPE_C);
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

package tdh.ifm.android.imatch.app.ui.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.tdh.common.utils.APPLog;
import com.tdh.common.utils.CommonUtil;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.OnClick;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.base.BaseFragment;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.RequestLogin;
import tdh.ifm.android.imatch.app.bean.RequestPhone;
import tdh.ifm.android.imatch.app.bean.User;
import tdh.ifm.android.imatch.app.presenter.LoginPresenterImpl;
import tdh.ifm.android.imatch.app.ui.activity.MainActivity;
import tdh.ifm.android.imatch.app.ui.activity.AccountTypeActivity;
import tdh.ifm.android.imatch.app.ui.activity.SetPasswordActivity;
import tdh.ifm.android.imatch.app.ui.activity.WebViewByCookieActivity;
import tdh.ifm.android.imatch.app.ui.view.IdentifyCode;
import tdh.ifm.android.imatch.app.utils.Constants;
import tdh.ifm.android.imatch.app.utils.RegExpConstants;
import tdh.ifm.android.imatch.app.utils.SharedPreferencesUtil;
import tdh.ifm.android.imatch.app.utils.Util;
import tdh.ifm.android.imatch.app.view.LoginView;

/**
 * Author：gwx
 * Create at：2017/5/3 14:52
 */
public class LoginFragment extends BaseFragment implements LoginView{

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.iv_pwd)
    ImageView ivPwd;
    @BindView(R.id.tv_code)
    TextView tvCode;

    @BindBitmap(R.mipmap.login_pwd_visible)
    Bitmap pwdVisible;
    @BindBitmap(R.mipmap.login_pwd_invisible)
    Bitmap pwdInVisible;

    private int type;
    /** 密码是否可见  默认不可见 **/
    private boolean isPwdVisible;

    private IdentifyCode iCode;

    private String mMobile;
    private String mPassword;

    private LoginPresenterImpl loginPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        type = bundle.getInt("type", 0);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initData() {
        loginPresenter = new LoginPresenterImpl(context,this);

        if (type == 0) {
            ivPwd.setVisibility(View.VISIBLE);
            tvCode.setVisibility(View.GONE);
            etPwd.setHint(R.string.prompt_login_password);
        }else {
            ivPwd.setVisibility(View.GONE);
            tvCode.setVisibility(View.VISIBLE);
            etPwd.setHint(R.string.prompt_login_code);
        }

        iCode = new IdentifyCode(60000, 1000, tvCode, (Activity) context);

        mMobile = SharedPreferencesUtil.getValue(Constants.PHONE,"");
        mPassword = SharedPreferencesUtil.getValue(Constants.PASSWORD,"");
        if (!TextUtils.isEmpty(mMobile) && !TextUtils.isEmpty(mPassword)) {
            etPhone.setText(mMobile);
            etPwd.setText(mPassword);
        }
    }

    @OnClick(R.id.iv_pwd)
    public void onIvPwdClicked() {
        if (isPwdVisible) {
            /** 密码不可见 **/
            isPwdVisible = false;
            ivPwd.setImageBitmap(pwdInVisible);
            etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }else {
            /** 密码可见 **/
            isPwdVisible = true;
            ivPwd.setImageBitmap(pwdVisible);
            etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    @OnClick(R.id.tv_code)
    public void onTvCodeClicked() {
        mMobile = getValue(etPhone);
        if (isMobileValidate()) {
            RequestPhone requestPhone = new RequestPhone();
            requestPhone.setMobile(mMobile);
            loginPresenter.sendCode(requestPhone);
            iCode.start();// 开始计时
        }
    }

    @OnClick(R.id.btn_login)
    public void onBtnLoginClicked() {
        mMobile = getValue(etPhone);
        mPassword = getValue(etPwd);
        if (isnull()){
            RequestLogin requestLogin = new RequestLogin();
            requestLogin.setUsername(mMobile);
            requestLogin.setPassword(mPassword);
            if (type == 0) {
                requestLogin.setLoginType("1");
            }else {
                requestLogin.setLoginType("2");
            }
            loginPresenter.login(requestLogin);
        }
    }

    @OnClick(R.id.ll_register)
    public void onLlRegisterClicked() {
        Util.intentToActivity(context, AccountTypeActivity.class);
//        Util.intentToActivity(context, WebViewByCookieActivity.class);
    }

    @OnClick(R.id.tv_forget_password)
    public void onTvForgetPasswordClicked() {
        Util.intentToActivity(context, SetPasswordActivity.class);
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
        Util.setResCode(context,code);
    }

    @Override
    public void onLoginSuccess(BaseResponse<User> baseResponse) {
        if (baseResponse.isSuccess()) {
            User user = baseResponse.getBody();
            if (user == null) {
                return;
            }
            if (type == 0) {
                SharedPreferencesUtil.setValue(Constants.PHONE,mMobile);
                SharedPreferencesUtil.setValue(Constants.PASSWORD,mPassword);
            }
            SharedPreferencesUtil.setValue(Constants.USERID,user.getProfileId());
            if (!TextUtils.isEmpty(user.getPlateNo())) {
                SharedPreferencesUtil.setValue(Constants.PLATENO,user.getPlateNo());
            }
            if (!TextUtils.isEmpty(user.getToken())) {
                SharedPreferencesUtil.setValue(Constants.TOKEN,user.getToken());
            }
            if (!TextUtils.isEmpty(user.getUsrTypeCd())) {
                SharedPreferencesUtil.setValue(Constants.USERTYPE,user.getUsrTypeCd());
            }else {
                SharedPreferencesUtil.setValue(Constants.USERTYPE,Constants.USERTYPE_C);
            }

            Util.intentToActivity(context, MainActivity.class);
            ((Activity) context).finish();
        }else {
            if (!TextUtils.isEmpty(baseResponse.getMessage())){
                CommonUtil.getToast(context,baseResponse.getMessage());
            }
        }
    }

    @Override
    public void onSendLoginCodeSuccess(BaseResponse baseResponse) {
        if (baseResponse.isSuccess()) {
            CommonUtil.getToast(context, baseResponse.getMessage());
        } else {
            CommonUtil.getToast(context, baseResponse.getMessage());
            reStart();
        }
    }

    private boolean isnull() {
        if (TextUtils.isEmpty(mMobile)) {
            CommonUtil.getToast(context,getString(R.string.prompt_mobile));
            return false;
        }
        if (TextUtils.isEmpty(mPassword)) {
            if (type == 0) {
                CommonUtil.getToast(context,getString(R.string.prompt_password));
            }else {
                CommonUtil.getToast(context,getString(R.string.prompt_login_code));
            }
            return false;
        }
        if (type == 0) {
            if (!mPassword.matches(RegExpConstants.ISPASSWORD)) {
                CommonUtil.getToast(context,getString(R.string.error_password_format));
                return false;
            }
        }
        return true;
    }

    private boolean isMobileValidate() {
        if (TextUtils.isEmpty(mMobile)) {
            CommonUtil.getToast(context, getString(R.string.prompt_mobile));
            return false;
        }
        return true;
    }

    private void reStart() {
        if (null != iCode) {
            iCode.cancel();
            iCode.onFinish();
        }
    }

}

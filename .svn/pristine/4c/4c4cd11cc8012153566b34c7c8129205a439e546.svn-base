package tdh.ifm.android.imatch.app.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import com.tdh.common.utils.APPLog;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.base.BaseActivity;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.model.UserModel;
import tdh.ifm.android.imatch.app.model.UserModelImpl;
import tdh.ifm.android.imatch.app.presenter.LogoutPresenterImpl;
import tdh.ifm.android.imatch.app.ui.view.MyTitleView;
import tdh.ifm.android.imatch.app.update.CheckUpdateTask;
import tdh.ifm.android.imatch.app.utils.Util;
import tdh.ifm.android.imatch.app.view.LogoutView;

/**
 * Created by tdh on 2017/5/4.
 */

public class SettingsActivity extends BaseActivity implements LogoutView{
    @BindView(R.id.titleview)
    MyTitleView titleview;
    @BindView(R.id.tv_version_number)
    TextView tvVersionNumber;

    @BindString(R.string.txt_setting)
    String title;

    private LogoutPresenterImpl logoutPresenter;


    @Override
    public int getContentViewId() {
        return R.layout.activity_settings;
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
        tvVersionNumber.setText("V"+getAppVersionName());

        logoutPresenter = new LogoutPresenterImpl(context,this);
    }

    @OnClick(R.id.ll_update_password)
    public void onUpdatePwd() {
        Util.intentToActivity(context, SetPasswordActivity.class);
    }

    @OnClick(R.id.ll_check_update)
    public void onCheck() {
        new CheckUpdateTask(context, true).execute();
    }

    @OnClick(R.id.ll_about_us)
    public void onAbout() {
        Util.intentToActivity(context, AboutUsActivity.class);
    }

    @OnClick(R.id.btn_exit_login)
    public void onLoginOutClick() {
        Util.exitLogin(context);
        logoutPresenter.logout();
    }

    /**
     * 返回当前程序版本名
     */
    private String getAppVersionName() {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            APPLog.error("VersionInfo", "Exception", e);
        }
        return versionName;
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
    public void onLoginOutSuccess(BaseResponse baseResponse) {
        if (baseResponse != null) {

        }
    }
}

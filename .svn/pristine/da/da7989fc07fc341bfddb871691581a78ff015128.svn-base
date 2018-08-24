package tdh.ifm.android.imatch.app.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import com.tdh.common.utils.APPLog;

import butterknife.BindString;
import butterknife.BindView;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.base.BaseActivity;
import tdh.ifm.android.imatch.app.ui.view.MyTitleView;

/**
 * Created by tdh on 2017/5/25.
 */

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.titleview)
    MyTitleView titleview;
    @BindView(R.id.tv_version_number)
    TextView tvVersionNumber;

    @BindString(R.string.txt_aabout_us)
    String title;

    @Override
    public int getContentViewId() {
        return R.layout.activity_about_us;
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
}

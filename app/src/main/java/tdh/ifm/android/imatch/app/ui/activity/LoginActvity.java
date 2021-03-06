package tdh.ifm.android.imatch.app.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.igexin.sdk.PushManager;
import com.tdh.common.utils.APPLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.base.BaseActivity;
import tdh.ifm.android.imatch.app.service.DemoIntentService;
import tdh.ifm.android.imatch.app.service.DemoPushService;
import tdh.ifm.android.imatch.app.ui.fragment.LoginFragment;
import tdh.ifm.android.imatch.app.ui.view.MyTitleView;
import tdh.ifm.android.imatch.app.ui.view.TabFragmentPagerAdapter;
import tdh.ifm.android.imatch.app.utils.NetContant;

/**
 * Author：gwx
 * Create at：2017/5/3 13:42
 */
public class LoginActvity extends BaseActivity {
    @BindView(R.id.titleview)
    MyTitleView titleview;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindString(R.string.title_login)
    String title;
    @BindString(R.string.txt_login_1)
    String string1;
    @BindString(R.string.txt_login_2)
    String string2;

    private TabFragmentPagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private List<String> titles;

    private LoginFragment loginFragment1;
    private LoginFragment loginFragment2;

    private static final int REQUEST_PERMISSION = 0;
    // DemoPushService.class 自定义服务名称, 核心服务
    private Class userPushService = DemoPushService.class;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initTitleView() {
        super.initTitleView();
        titleview.getBack().setVisibility(View.GONE);
        titleview.getTv_title().setText(title);
    }

    @Override
    public void initData() {
        checkPermission();

        initTabFragment();
    }

    private void initTabFragment() {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add(string1);
        titles.add(string2);

        loginFragment1 = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 0);
        loginFragment1.setArguments(bundle);
        fragments.add(loginFragment1);

        loginFragment2 = new LoginFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type", 1);
        loginFragment2.setArguments(bundle2);
        fragments.add(loginFragment2);

        pagerAdapter = new TabFragmentPagerAdapter(
                getSupportFragmentManager(), fragments, titles);
        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(0);
        tablayout.setupWithViewPager(viewpager);
    }

    private void checkPermission() {
        PackageManager pkgManager = getPackageManager();
        // 读写 sd card 权限非常重要, android6.0默认禁止的, 建议初始化之前就弹窗让用户赋予该权限
        boolean sdCardWritePermission =
                pkgManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        // read phone state用于获取 imei 设备信息
        boolean phoneSatePermission =
                pkgManager.checkPermission(Manifest.permission.READ_PHONE_STATE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        APPLog.error(NetContant.TAG, "sdCardWritePermission is " + sdCardWritePermission +
                "  and phoneSatePermission is " + phoneSatePermission);
        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission || !phoneSatePermission) {
            requestPermission();
        }else {
            // com.getui.demo.DemoPushService 为第三方自定义推送服务
            PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
        }
        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.
                WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
            } else {
                APPLog.error(NetContant.TAG, "We highly recommend that you need to grant the special permissions before initializing the SDK, otherwise some "
                        + "functions will not work");
                PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}

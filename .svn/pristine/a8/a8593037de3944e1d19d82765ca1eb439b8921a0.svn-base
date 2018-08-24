package tdh.ifm.android.imatch.app.base;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.tdh.common.utils.APPLog;
import com.tdh.common.utils.SharedAppContext;
import com.tencent.smtt.sdk.QbSdk;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.ArrayList;
import java.util.List;

import tdh.ifm.android.imatch.app.utils.Constants;
import tdh.ifm.android.imatch.app.utils.NetContant;

/**
 * Author：gwx
 * Create at：2017/4/20 19:47
 */
public class BaseApplication extends Application {

    public List<Activity> listActivity = new ArrayList<Activity>();
    private static BaseApplication instance;

    //单例模式中获取唯一的MyApplication实例
    public static BaseApplication getInstance() {
        if (instance == null) {
            instance = new BaseApplication();
        }
        return instance;
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {

        if (!listActivity.contains(activity)) {
            listActivity.add(activity);
        }

    }

    public void removeActivity(Activity activity) {
        if (listActivity != null && activity != null) {
            listActivity.remove(activity);
        }
    }

    //遍历所有Activity并finish
    public void exit() {
        if (listActivity != null && listActivity.size() > 0) {
            for (Activity activity : listActivity) {
                if (activity != null) {
                    activity.finish();
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        SharedAppContext.setContentContext(getApplicationContext());

        //代码自动适配初始化
        AutoLayoutConifg.getInstance().useDeviceSize().init(this);

        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                APPLog.info(NetContant.TAG, " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        });

    }

}

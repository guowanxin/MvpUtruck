package tdh.ifm.android.imatch.app.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import tdh.ifm.android.imatch.app.R;


/**
 * Created by guowanxin on 15/11/27.
 */
public class SystemBarUtil {


    /**
     * 自定义状态栏
     * @param context
     */
    public static void setSystemBar(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true,context);
            SystemBarTintManager tintManager = new SystemBarTintManager((Activity)context);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.title_background);//通知栏所需颜色
        }
    }

    /**
     * 自定义状态栏
     * @param context
     */
    public static void setSystemBar(Context context,int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true,context);
            SystemBarTintManager tintManager = new SystemBarTintManager((Activity)context);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);//通知栏所需颜色
        }
    }

    /**设置状态栏属性
     * @param on
     */
    private static void setTranslucentStatus(boolean on,Context context) {
        Window win = ((Activity)context).getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}

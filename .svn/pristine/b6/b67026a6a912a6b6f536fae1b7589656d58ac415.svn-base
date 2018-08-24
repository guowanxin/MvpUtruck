package com.tdh.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TDH on 2016/12/13.
 */

public class CommonUtil {

    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 判断是否有网
     *
     * @param context
     * @return
     */
    public static boolean IsHaveInternet(final Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 提示网络连接失败
     *
     * @param context
     */
    public static void getErroToast(Context context) {
        try {
            Toast toast = Toast.makeText(context, Const.NO_NETWORK, Toast.LENGTH_SHORT);
            LinearLayout linearLayout = (LinearLayout) toast.getView();
            TextView messageTextView = (TextView) linearLayout.getChildAt(0);
            messageTextView.setTextSize(15);
            toast.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void getToast(Context context, String string) {
        try {
            Toast toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
            LinearLayout linearLayout = (LinearLayout) toast.getView();
            TextView messageTextView = (TextView) linearLayout.getChildAt(0);
            messageTextView.setTextSize(15);
            toast.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void getLongToast(Context context, String string) {
        try {
            Toast.makeText(context, string, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 解析字符串,并将其转换成时间. Format @see {@value #FORMAT_DATETIME}
     *
     * @param str
     * @return
     * @throws
     */
    public static Date parseDateTime(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);

        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            if (APPLog.isWarnable()) {
                APPLog.warn("Error parseDateTime", e);
            }
        }

        return null;
    }

}

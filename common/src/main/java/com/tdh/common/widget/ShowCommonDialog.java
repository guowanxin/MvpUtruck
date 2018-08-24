package com.tdh.common.widget;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tdh.common.R;

public abstract class ShowCommonDialog {
    private static Context context;
    private static Dialog progressDialog;
    private static View showClickView;
    private static TextView tvTitle;
    private static TextView tvMsg;
    private static Button btnConfirm;
    private static Button btnCancel;

    private static Toast toast;

    /**
     * 初始化dialog
     *
     * @param context
     */
    @SuppressWarnings("static-access")
    public ShowCommonDialog(Context context) {
        this.context = context;
    }

    /**
     *
     * 初始化dialog
     *
     * @param xiaomi
     *            ************1、 手机机型适配，建议使用true（ 小米手机必须使用true） ************ 2、
     *            在当前页面显示显示时，建议使用true ************ 3、 在非当前页面显示时，必须使用false
     *            ************ 4、 除去小米手机之外均可使用false
     * @return
     */
    public synchronized Dialog initCommonDialog(boolean xiaomi) {
        final Dialog dialog = new Dialog(context, R.style.dialogCustom);
        showClickView = LayoutInflater.from(context).inflate(R.layout.dialog_common, null);
        dialog.setContentView(showClickView);
        Window win = dialog.getWindow();

        win.getDecorView().setPadding(50, 0, 50, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        win.setAttributes(lp);
        if (!xiaomi) {
            win.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        // tvTitle = (TextView)
        // showClickView.findViewById(R.id.dialog_common_title);
        tvMsg = (TextView) showClickView.findViewById(R.id.dialog_common_msg);
        btnConfirm = (Button) showClickView.findViewById(R.id.dialog_common_confirm);
        btnCancel = (Button) showClickView.findViewById(R.id.dialog_common_cancel);
        btnConfirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                onClickCommonDialogButtonListen(true, dialog);
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                onClickCommonDialogButtonListen(false, dialog);
            }
        });
        return dialog;
    }

    /**
     * 展现完整的带有确认取消按钮的dialog
     *
     * @param dialog
     * @param title
     * @param msg
     * @param confirm
     *            为null时默认”确定“
     * @param cancel
     *            为null时默认”取消“
     * @param cancelable
     * @return
     */
    public synchronized void showConfirmCancelDialog(Dialog dialog, String title, String msg, String confirm, String cancel,
                                                     boolean cancelable) {
        if (null != dialog && dialog.isShowing()) {
            return;
        }
        // tvTitle.setText((null == title) || title.length() < 1 ?
        // tvTitle.getText() : title);
        tvMsg.setText(msg);
        btnConfirm.setText((null == confirm) || confirm.length() < 1 ? btnConfirm.getText() : confirm);
        btnCancel.setText((null == cancel) || cancel.length() < 1 ? btnCancel.getText() : cancel);
        dialog.setCancelable(cancelable);
        try {
            dialog.show();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     *
     * 展现只带确认按钮的dialog
     *
     * @param dialog
     * @param title
     * @param msg
     * @param confirm
     *            为null时默认”确定“
     * @param cancelable
     * @return
     */
    public synchronized void showConfirmDialog(Dialog dialog, String title, String msg, String confirm, boolean cancelable) {
        if (null != dialog && dialog.isShowing()) {
            return;
        }
        // tvTitle.setText((null == title) || title.length() < 1 ?
        // tvTitle.getText() : title);
        tvMsg.setText(msg);
        btnConfirm.setText(null == confirm ? btnConfirm.getText() : confirm);
        btnCancel.setVisibility(View.GONE);
        dialog.setCancelable(cancelable);
        try {
            dialog.show();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * 关闭对话框dialog
     *
     * @param dialog
     */
    public static synchronized void closeCommonDialog(Dialog dialog) {
        // if (null != dialogList&&dialogList.size()>0) {
        // dialogList.get(dialogList.size()-1).dismiss();
        // }
        if (null != dialog) {
            dialog.dismiss();
        }
    }

    /**
     * 关闭进度条progressDialog
     */
    public synchronized static void closeProgressDialog() {
        if (null != progressDialog) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }

    public abstract void onClickCommonDialogButtonListen(boolean confirm, Dialog dialog);


}

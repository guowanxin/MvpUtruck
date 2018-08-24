package tdh.ifm.android.imatch.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import tdh.ifm.android.imatch.app.R;

/**
 * Author：gwx
 * Create at：2017/5/16 13:38
 */
public class CommonProgressDialog {

    private Context context;
    private Dialog loadingDialog;
    private View view;
    private ImageView spaceshipImage;
    private Animation hyperspaceJumpAnimation;
    private TextView tipTextView;


    public CommonProgressDialog(Context context) {
        this.context = context;

        initCommonDialog("");
    }

    public CommonProgressDialog(Context context,String msg) {
        this.context = context;

        initCommonDialog(msg);
    }

    public synchronized void initCommonDialog(String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);// 加载布局
        spaceshipImage = (ImageView) view.findViewById(R.id.img);
        tipTextView = (TextView) view.findViewById(R.id.tipTextView);
        if (!TextUtils.isEmpty(msg)) {
            tipTextView.setText(msg);
        }
        // 加载动画
        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 使用ImageView显示动画

        loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(true);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
    }

    public void dialogShow() {
        if (spaceshipImage != null) {
            spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        }
        tipTextView.setText("加载中…");
        if (loadingDialog != null) {
            loadingDialog.show();
        }
    }

    public void dialogShow(String msg) {
        if (spaceshipImage != null) {
            spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        }
        if (!TextUtils.isEmpty(msg)) {
            tipTextView.setText(msg);
        }
        if (loadingDialog != null) {
            loadingDialog.show();
        }
    }

    public void dialogHide() {
        if (spaceshipImage != null) {
            spaceshipImage.clearAnimation();
        }
        if (loadingDialog != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
    }

    public void clearDialog() {
        if (spaceshipImage != null) {
            spaceshipImage = null;
        }
        if (loadingDialog != null) {
            loadingDialog = null;
        }
    }
}

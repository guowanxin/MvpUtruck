package tdh.ifm.android.imatch.app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdh.ifm.android.imatch.app.utils.CommonProgressDialog;
import tdh.ifm.android.imatch.app.utils.SystemBarUtil;

/**
 * Author：gwx
 * Create at：2017/4/20 19:46
 */
public abstract class BaseActivity extends AutoLayoutActivity {

    public Context context;

    public CommonProgressDialog dialog;

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        context = this;
        BaseApplication.getInstance().addActivity(this);

        unbinder = ButterKnife.bind(this);

        SystemBarUtil.setSystemBar(this);

        dialog = new CommonProgressDialog(context);

        initTitleView();
        initData();
    }

    public abstract int getContentViewId();

    public void initTitleView() {

    }

    public abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

        dialog.clearDialog();

        BaseApplication.getInstance().removeActivity(this);
        System.gc();
    }

    /**
     * 弹出加载框
     */
    public void dialogShow() {
        dialog.dialogShow();
    }

    /**
     * 取消加载框
     */
    public void dialogHide() {
        dialog.dialogHide();
    }

    public String getValue(EditText text) {
        if (null != text && View.VISIBLE == text.getVisibility()) {
            return text.getText().toString().trim();
        } else {
            return "";
        }
    }

    public String getValue(TextView text) {
        if (null != text && View.VISIBLE == text.getVisibility()) {
            return text.getText().toString();
        } else {
            return "";
        }
    }

}

package tdh.ifm.android.imatch.app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdh.ifm.android.imatch.app.utils.CommonProgressDialog;

/**
 * Author：gwx
 * Create at：2017/4/24 16:56
 */
public abstract class BaseFragment extends Fragment {

    protected Context context;

    public CommonProgressDialog dialog;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(getContentViewId(), container, false);

        unbinder = ButterKnife.bind(this,view);

        dialog = new CommonProgressDialog(context);

        initTitleView();

        initData();

        return view;
    }

    public abstract int getContentViewId();

    /**
     * 初始化标题栏
     */
    public void initTitleView() {

    }

    /**
     * 初始化数据
     */
    public abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

        dialog.clearDialog();
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


    /**
     * EditText取值
     */
    public String getValue(EditText text) {
        if (null != text && View.VISIBLE == text.getVisibility()) {
            return text.getText().toString().trim();
        } else {
            return "";
        }
    }

    /**
     * TextView取值
     */
    public String getValue(TextView text) {
        if (null != text && View.VISIBLE == text.getVisibility()) {
            return text.getText().toString();
        } else {
            return "";
        }
    }

}

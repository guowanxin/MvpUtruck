package tdh.ifm.android.imatch.app.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.tdh.common.utils.APPLog;
import com.tdh.common.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.base.BaseActivity;
import tdh.ifm.android.imatch.app.base.BasePageList;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.MyMessage;
import tdh.ifm.android.imatch.app.presenter.MyMessageListPresenterImpl;
import tdh.ifm.android.imatch.app.ui.adapter.MyMessageAdapter;
import tdh.ifm.android.imatch.app.ui.view.MyTitleView;
import tdh.ifm.android.imatch.app.utils.Util;
import tdh.ifm.android.imatch.app.view.MyMessageListView;

/**
 * 我的消息
 * Author：xyf
 * Created by tdh on 2017/5/3.
 */

public class MyMessageActivity extends BaseActivity implements MyMessageListView{

    @BindView(R.id.titleview)
    MyTitleView titleview;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindString(R.string.txt_my_message)
    String title;

    private MyMessageAdapter adapter;

    private List<MyMessage> myMessageList;
    private MyMessageListPresenterImpl myMessageListPresenter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_mymessage;
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
        recycler.setLayoutManager(new LinearLayoutManager(context));

        myMessageList=new ArrayList<>();
        myMessageListPresenter =new MyMessageListPresenterImpl(context,this);
        myMessageListPresenter.myMessageList();
        adapter = new MyMessageAdapter(context);
        recycler.setAdapter(adapter);
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
        CommonUtil.getToast(context, "获取数据失败");
    }

    @Override
    public void showResState(int code) {
        Util.setResCode(context, code);
    }

    @Override
    public void onMyMessageSuccess(BaseResponse<List<MyMessage>> baseResponse) {
        if (baseResponse.isSuccess()) {
            if (baseResponse.getBody() != null && baseResponse.getBody().size() > 0) {
                myMessageList = baseResponse.getBody();
            } else {
                myMessageList.clear();
                CommonUtil.getToast(context, "暂无数据");
            }
            adapter.setData(myMessageList);
            adapter.notifyDataSetChanged();
        } else {
            if (!TextUtils.isEmpty(baseResponse.getMessage())) {
                CommonUtil.getToast(context, baseResponse.getMessage());
            }
        }
    }
}

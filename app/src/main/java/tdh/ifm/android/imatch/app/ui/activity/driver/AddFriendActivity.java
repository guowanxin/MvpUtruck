package tdh.ifm.android.imatch.app.ui.activity.driver;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.tdh.common.utils.APPLog;
import com.tdh.common.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.base.BaseActivity;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.RequestAddFriendSearch;
import tdh.ifm.android.imatch.app.bean.FriendInfo;
import tdh.ifm.android.imatch.app.bean.RequestFriend;
import tdh.ifm.android.imatch.app.bean.RequestFriendList;
import tdh.ifm.android.imatch.app.presenter.AddFriendPresenterImpl;
import tdh.ifm.android.imatch.app.ui.adapter.AddFriendAdapter;
import tdh.ifm.android.imatch.app.ui.view.MyTitleView;
import tdh.ifm.android.imatch.app.utils.Util;
import tdh.ifm.android.imatch.app.view.AddFriendView;

/**
 * 添加好友
 * Author：xyf
 * Create at：2017/5/4 20:25
 */
public class AddFriendActivity extends BaseActivity implements AddFriendView{

    @BindView(R.id.titleview)
    MyTitleView titleview;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.et_search)
    EditText etSearch;

    @BindString(R.string.txt_add_friend)
    String title;

    private List<FriendInfo> friendInfos;

    private AddFriendPresenterImpl addFriendPresenter;

    private AddFriendAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_addfriend;
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

        friendInfos = new ArrayList<>();
        addFriendPresenter = new AddFriendPresenterImpl(context, this);

        adapter = new AddFriendAdapter(context);
        recycler.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    RequestAddFriendSearch requestFriendList = new RequestAddFriendSearch();
                    requestFriendList.setSearchKey(getValue(etSearch));
                    addFriendPresenter.addFriendSearch(requestFriendList);

                }
                return true;
            }
        });

        adapter.setListener(new AddFriendAdapter.onClickAddFriendListener() {
            @Override
            public void clickAddFriend(FriendInfo friendInfo) {
                RequestFriend request = new RequestFriend();
                request.setBuddyMemberId(String.valueOf(friendInfo.getBuddyMemberId()));
                addFriendPresenter.addFriend(request);
            }
        });

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
    public void onAddFriendSearchSuccess(BaseResponse<List<FriendInfo>> baseResponse) {
        if (baseResponse.isSuccess()) {
            if (baseResponse.getBody() != null && baseResponse.getBody().size() > 0) {
                friendInfos = baseResponse.getBody();
            } else {
                friendInfos.clear();
                CommonUtil.getToast(context, "暂无此人");
            }
            adapter.setData(friendInfos);
            adapter.notifyDataSetChanged();
        } else {
            if (!TextUtils.isEmpty(baseResponse.getMessage())) {
                CommonUtil.getToast(context, baseResponse.getMessage());
            }
        }

    }

    @Override
    public void onAddFriendSuccess(BaseResponse baseResponse) {
        if (!TextUtils.isEmpty(baseResponse.getMessage())) {
            CommonUtil.getToast(context, baseResponse.getMessage());
        }
        if (baseResponse.isSuccess()) {
            setResult(10);
            finish();
        }
    }
}

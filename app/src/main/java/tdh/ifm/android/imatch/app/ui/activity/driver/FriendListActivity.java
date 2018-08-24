package tdh.ifm.android.imatch.app.ui.activity.driver;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.tdh.common.view.MySwipeRefreshLayout;
import com.tdh.common.widget.ShowCommonDialog;
import com.tdh.recyclerviewlibrary.EndlessRecyclerOnScrollListener;
import com.tdh.recyclerviewlibrary.LoadingFooter;
import com.tdh.recyclerviewlibrary.RecyclerViewStateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindBitmap;
import butterknife.BindString;
import butterknife.BindView;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.base.BaseActivity;
import tdh.ifm.android.imatch.app.base.BasePageList;
import tdh.ifm.android.imatch.app.base.BaseResponse;
import tdh.ifm.android.imatch.app.bean.FriendInfo;
import tdh.ifm.android.imatch.app.bean.RequestDeleteFriend;
import tdh.ifm.android.imatch.app.bean.RequestFriendList;
import tdh.ifm.android.imatch.app.presenter.FriendPresenterImpl;
import tdh.ifm.android.imatch.app.ui.adapter.FriendListAdapter;
import tdh.ifm.android.imatch.app.ui.view.MyTitleView;
import tdh.ifm.android.imatch.app.utils.Util;
import tdh.ifm.android.imatch.app.view.FriendView;

/**
 * 我的好友
 * Author：xyf
 * Created by tdh on 2017/5/4.
 */

public class FriendListActivity extends BaseActivity implements FriendView {

    @BindView(R.id.titleview)
    MyTitleView titleview;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.et_search)
    EditText etSearch;

    @BindString(R.string.txt_friend)
    String title;
    @BindBitmap(R.mipmap.friend_add)
    Bitmap bm_addfriend;

    @BindView(R.id.swipeLayout)
    MySwipeRefreshLayout swipeLayout;


    private int page=1;
    private FriendListAdapter adapter;

    private List<FriendInfo> friendList;

    private FriendPresenterImpl friendPresenter;



    /**
     * 判断是否是指定好友界面  有值代表是指定好友   没值代表是我的好友界面
     */
    private String userTypeCD;


    @Override
    public int getContentViewId() {
        return R.layout.activity_friend_list;
    }

    @Override
    public void initTitleView() {
        super.initTitleView();
        titleview.getTv_title().setText(title);
        titleview.getTv_right_image().setVisibility(View.VISIBLE);
        titleview.getTv_right_image().setImageBitmap(bm_addfriend);
        titleview.getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleview.getTv_right_image().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.intentToActivity(context,AddFriendActivity.class,10);
            }
        });
    }


    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userTypeCD = bundle.getString("userTypeCD", "");
        }
        //设置自动刷新
        swipeLayout.autoRefresh();
        //设置刷新回调属性
        Util.setSwipeLayout(context, swipeLayout);
        recycler.setLayoutManager(new LinearLayoutManager(context));

        friendList = new ArrayList<>();
        friendPresenter = new FriendPresenterImpl(context, this);

        adapter = new FriendListAdapter(context);
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
//                    page = 1;
//                    postHttp(page,getValue(etSearch));
                    if (!getValue(etSearch).equals("")) {
                        searchFilter(getValue(etSearch));
                    }else {
                        adapter.setData(friendList);
                    }
                }
                return true;
            }
        });

        adapter.setListener(new FriendListAdapter.onClickFriendListItemListener() {
            @Override
            public void onLongClickFriendListItem(final FriendInfo friendInfo) {
                //弹出删除框
                if (!TextUtils.isEmpty(userTypeCD)) {
                    return;
                }
                ShowCommonDialog dialog1 = new ShowCommonDialog(context) {
                    @Override
                    public void onClickCommonDialogButtonListen(boolean confirm, Dialog dialog) {
                        if(confirm){
                            //调用删除接口
                            RequestDeleteFriend requestFriend = new RequestDeleteFriend();
                            requestFriend.setUuid(String.valueOf(friendInfo.getUuid()));
                            friendPresenter.deleteFriend(requestFriend);
                            friendList.clear();
                        }
                        dialog.dismiss();
                    }
                };
                dialog1.showConfirmCancelDialog(dialog1.initCommonDialog(true), null,"是否删除好友", null, null,
                        true);
            }

            @Override
            public void onClickFriendListItem(FriendInfo friendInfo) {
                if (TextUtils.isEmpty(userTypeCD)) {
                    return;
                }
                Intent intent = getIntent();
                intent.putExtra("memberId",friendInfo.getBuddyMemberId());
                setResult(10,intent);
                finish();
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!CommonUtil.IsHaveInternet(context)) {
                    CommonUtil.getErroToast(context);
                    hideprogress();
                    return;
                }
                page = 1;
                postHttp(page,null);
            }
        });
//        recycler.addOnScrollListener(new EndlessRecyclerOnScrollListener(context){
//            @Override
//            public void onLoadNextPage(View view) {
//                super.onLoadNextPage(view);
//                if (!CommonUtil.IsHaveInternet(context)) {
//                    CommonUtil.getErroToast(context);
//                    RecyclerViewStateUtils.setFooterViewState(recycler, LoadingFooter.State.Normal);
//                    return;
//                }
//                postHttp(page,getValue(etSearch));
//            }
//        });


    }

    @Override
    public void showProgress() {
        dialogShow();
        swipeLayout.setRefreshing(true);
    }

    @Override
    public void hideprogress() {
        dialogHide();
        swipeLayout.setRefreshing(false);
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
    public void onFriendListSuccess(BaseResponse<BasePageList<List<FriendInfo>>> baseResponse) {
        if (baseResponse.isSuccess()) {
            if (baseResponse == null) {
                return;
            }
            if (page == 1) {
                friendList = baseResponse.getBody().getResult();
                if (friendList == null || friendList.size() == 0) {
                    friendList.clear();
                    CommonUtil.getToast(context, "暂无数据");
                }
                adapter.setData(friendList);
                page = 2;
            } else if (page > 1) {
                if (baseResponse.getBody() == null || baseResponse.getBody().getResult().size() == 0) {
                    return;
                }
                friendList.addAll(baseResponse.getBody().getResult());
                adapter.setData(friendList);
                page++;
            }

        } else {
            if (!TextUtils.isEmpty(baseResponse.getMessage())) {
                CommonUtil.getToast(context, baseResponse.getMessage());
            }
        }
    }
    @Override
    public void onDeleteFriendSuccess(BaseResponse baseResponse) {
        if (!TextUtils.isEmpty(baseResponse.getMessage())) {
            CommonUtil.getToast(context,baseResponse.getMessage());
        }
        if (baseResponse.isSuccess()) {
            page = 1;
            postHttp(page,null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 10) {
            if (!CommonUtil.IsHaveInternet(context)) {
                CommonUtil.getErroToast(context);
                hideprogress();
                return;
            }
            page = 1;
            postHttp(page,null);
        }
    }

    private void postHttp(int page, String searchKey) {
        RequestFriendList request = new RequestFriendList();
        request.setPage(page);
        if (!TextUtils.isEmpty(searchKey)) {
            request.setSearchKey(searchKey);
        }
        if (!TextUtils.isEmpty(userTypeCD)) {
            if (userTypeCD.contains(",")) {
                request.setUserTypeCds(userTypeCD.split(","));
            }else {
                request.setUserTypeCds(new String[]{userTypeCD});
            }
        }
        friendPresenter.friendList(request);
    }

    /**
     * 根据手机号或者姓名模糊搜索
     * @param searchKey
     */
    private void searchFilter(String searchKey) {
        if (friendList != null && friendList.size() > 0) {
            List<FriendInfo> friendInfos = new ArrayList<>();
            for (int i = 0;i<friendList.size();i++) {
                if (friendList.get(i).getMobile().contains(searchKey) || friendList.get(i).getFullName().contains(searchKey)) {
                    friendInfos.add(friendList.get(i));
                }
            }
            adapter.setData(friendInfos);
        }
    }

}

package tdh.ifm.android.imatch.app.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tdh.common.utils.APPLog;
import com.tdh.common.utils.CommonUtil;
import com.tdh.common.view.MySwipeRefreshLayout;

import butterknife.BindView;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.base.BaseFragment;
import tdh.ifm.android.imatch.app.ui.activity.MyMessageActivity;
import tdh.ifm.android.imatch.app.ui.activity.WebViewByCookieActivity;
import tdh.ifm.android.imatch.app.ui.activity.driver.AddFriendActivity;
import tdh.ifm.android.imatch.app.ui.activity.driver.AttentionLineActivity;
import tdh.ifm.android.imatch.app.ui.view.MyTitleView;
import tdh.ifm.android.imatch.app.ui.view.SwipeWebView;
import tdh.ifm.android.imatch.app.utils.Constants;
import tdh.ifm.android.imatch.app.utils.NetContant;
import tdh.ifm.android.imatch.app.utils.SharedPreferencesUtil;
import tdh.ifm.android.imatch.app.utils.Util;

/**
 * Author：gwx
 * Create at：2017/5/12 18:21
 */
public class WebViewFragment extends BaseFragment {

    @BindView(R.id.titleview)
    MyTitleView titleview;
    @BindView(R.id.webView)
    SwipeWebView webView;
    @BindView(R.id.swipeLayout)
    MySwipeRefreshLayout swipeLayout;

    private String url;
    private int flag;

    private MyWebClient webViewClient;

    SharedPreferences sharedPreferences;

    public FragmentListener mListener;
    //MainFragment开放的接口
    public interface FragmentListener{
        //跳到h5页面
        void toH5Page();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (FragmentListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        url = bundle.getString(Constants.URL);
        flag = bundle.getInt("flag");
        APPLog.error(NetContant.TAG, "url==========" + url);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_webview_swipelayout;
    }

    @Override
    public void initTitleView() {
        super.initTitleView();
        titleview.getBack().setVisibility(View.GONE);
        if (flag == 1) {
            titleview.getTv_right_image2().setVisibility(View.VISIBLE);
            titleview.getTv_right_image().setVisibility(View.VISIBLE);
            titleview.getTv_right_image2().setImageResource(R.mipmap.icon_friend);
            titleview.getTv_right_image().setImageResource(R.mipmap.icon_message);
            titleview.getTv_right_image2().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.intentToActivity(context, AddFriendActivity.class);
                }
            });
            titleview.getTv_right_image().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.intentToActivity(context, MyMessageActivity.class);
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!CommonUtil.IsHaveInternet(context)) {
            swipeLayout.setRefreshing(false);
            return;
        }
        Util.synCookies(context, url, sharedPreferences.getString("cookie", ""));
        webView.loadUrl(webView.getUrl());
    }

    @Override
    public void initData() {
        sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);

        Util.setSwipeLayout(context, swipeLayout);
        webView.setSwipeRefreshLayout(swipeLayout);

        setWeb(url);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!CommonUtil.IsHaveInternet(context)) {
                    swipeLayout.setRefreshing(false);
                    return;
                }
                Util.synCookies(context, url, sharedPreferences.getString("cookie", ""));
                webView.loadUrl(webView.getUrl());
            }
        });
    }

    private void setWeb(String url) {

        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        JavaScriptInterface myJavaScriptInterface = new JavaScriptInterface(context);
        webView.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
        webView.setWebChromeClient(new MyWebChromeClient());
        webViewClient = new MyWebClient();
        webView.setWebViewClient(webViewClient);

        Util.synCookies(context, url, sharedPreferences.getString("cookie", ""));

        webView.loadUrl(url);

    }

    public class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (swipeLayout == null) {
                return;
            }
            if (newProgress == 100) {
                //隐藏进度条
                swipeLayout.setRefreshing(false);
            } else {
                if (!swipeLayout.isRefreshing())
                    swipeLayout.setRefreshing(true);
            }

            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (titleview != null && titleview.getTv_title() != null) {
                if (title != null && !title.contains("优卡2.0")) {
                    titleview.getTv_title().setText(title);
                }
            }
        }

    }

    public class JavaScriptInterface {
        Context mContext;

        public JavaScriptInterface(Context c) {
            mContext = c;
        }

        //跳转到查找货源界面
        @JavascriptInterface
        public void intentToFindSource() {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.URL, NetContant.HTML_FINDSOURCE);
            Util.intentToActivity(context, WebViewByCookieActivity.class,bundle);
        }

        //跳转到运单详情界面
        @JavascriptInterface
        public void intentToOrderDetal(String orderId) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.URL, NetContant.HTML_ORDERDETAL+"?orderId="+orderId);
            Util.intentToActivity(context, WebViewByCookieActivity.class,bundle);
        }

        //跳转到已报价运单详情界面
        @JavascriptInterface
        public void intentToWaitDetal(String orderId) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.URL, NetContant.HTML_WAITDETAL+"?orderId="+orderId);
            Util.intentToActivity(context, WebViewByCookieActivity.class,bundle);
        }

        //跳转到等待成交界面
        @JavascriptInterface
        public void intentToWaitForPay() {
            Bundle bundle = new Bundle();
            if (SharedPreferencesUtil.getValue(Constants.USERTYPE,"").equals(Constants.USERTYPE_C)) {
                bundle.putString(Constants.URL, NetContant.HTML_WAITFORPAY_C);
            }else if (SharedPreferencesUtil.getValue(Constants.USERTYPE,"").equals(Constants.USERTYPE_AGENT)) {
                bundle.putString(Constants.URL, NetContant.HTML_WAITFORPAY_A);
            }
            Util.intentToActivity(context, WebViewByCookieActivity.class,bundle);
        }

        //跳转到运单界面
        @JavascriptInterface
        public void intentToOrderList() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }

        //跳转到关注线路界面
        @JavascriptInterface
        public void intentToAttentionLine() {
            Util.intentToActivity(context, AttentionLineActivity.class);
        }

    }

    class MyWebClient extends WebViewClient {

        @Override
        public void onFormResubmission(WebView view, Message dontResend, Message resend) {
            // TODO Auto-generated method stub
            resend.sendToTarget();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            Util.synCookies(context, url, sharedPreferences.getString("cookie", ""));
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
            super.onReceivedError(view, errorCode, description, failingUrl);
            APPLog.error(NetContant.TAG,"==errorCode=="+errorCode+"---description--"+description+"+++failingUrl++"+failingUrl);
            if (errorCode != 200) {
                if (TextUtils.isEmpty(description)) {
                    CommonUtil.getToast(context,"服务器连接失败，请重试");
                }else {
                    CommonUtil.getToast(context,description);
                }
            }

        }

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mListener.toH5Page();
                    break;
            }
        }
    };


}

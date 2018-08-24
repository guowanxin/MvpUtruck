package tdh.ifm.android.imatch.app.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tdh.common.utils.APPLog;
import com.tdh.common.utils.CommonUtil;
import com.tdh.common.utils.FileUtil;
import com.tdh.common.view.MySwipeRefreshLayout;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import tdh.ifm.android.imatch.app.R;
import tdh.ifm.android.imatch.app.base.BaseActivity;
import tdh.ifm.android.imatch.app.ui.view.SwipeWebView;
import tdh.ifm.android.imatch.app.utils.Constants;
import tdh.ifm.android.imatch.app.utils.FileUtils;
import tdh.ifm.android.imatch.app.utils.NetContant;
import tdh.ifm.android.imatch.app.utils.Util;

/**
 * Author：gwx
 * Create at：2017/5/15 13:45
 */
public class WebViewNoTitleActivity extends BaseActivity {
    @BindView(R.id.webView)
    SwipeWebView webView;
    @BindView(R.id.swipeLayout)
    MySwipeRefreshLayout swipeLayout;

    private String url;

    private MyWebClient webViewClient;

    SharedPreferences sharedPreferences;

    private boolean isPaySuccess;


    //照相
    private String path;
    private FileUtils fileUtils;

    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 101;

    @Override
    public int getContentViewId() {
        return R.layout.activity_webview_notitle;
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString(Constants.URL,"");
        APPLog.error(NetContant.TAG, "url==========" + url);

        sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        fileUtils = new FileUtils();

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

        JavaScriptInterface myJavaScriptInterface = new JavaScriptInterface(this);
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

        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;

            openXiangJi(FILECHOOSER_RESULTCODE);
        }

        // For Lollipop 5.0+ Devices
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback,
                                         WebChromeClient.FileChooserParams fileChooserParams) {
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }

            uploadMessage = filePathCallback;

            openXiangJi(REQUEST_SELECT_FILE);
            return true;
        }

        //For Android 4.1 only
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            openXiangJi(FILECHOOSER_RESULTCODE);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            openXiangJi(FILECHOOSER_RESULTCODE);
        }

    }

    public class JavaScriptInterface {
        Context mContext;

        public JavaScriptInterface(Context c) {
            mContext = c;
        }

        //返回
        @JavascriptInterface
        public void goBack() {
            finish();
        }

        //调起系统相机
        @JavascriptInterface
        public void openCamera() {
//            openXiangJi();
        }

        //调起系统相机
        @JavascriptInterface
        public void Hello(String asa) {
            APPLog.error(NetContant.TAG,"我进来了======"+asa);
        }

        //设置是否能够下拉
        @JavascriptInterface
        public void setReloadPage(boolean isReloadPage) {
            APPLog.error("------isReloadPage----"+isReloadPage);
            webView.setReloadPage(isReloadPage);
            Message message = new Message();
            message.what = 1;
            message.obj = isReloadPage;
            handler.sendMessage(message);
        }

        //支付成功时调用
        @JavascriptInterface
        public void paySuccessNotice() {
            isPaySuccess = true;
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
                finish();
            }

        }

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    boolean isReloadPage = (boolean) msg.obj;
                    if (isReloadPage) {
                        swipeLayout.setEnabled(true);
                    }else {
                        swipeLayout.setEnabled(false);
                    }
                    break;
            }
        }
    };

    /**
     * 打开相机
     */
    private void openXiangJi(int code) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(fileUtils.getSDPATH(),
                    String.valueOf(System.currentTimeMillis())+ ".jpg");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
            path = file.getAbsolutePath();
            FileUtil.startActionCapture(this,file,code);
        } else {
            CommonUtil.getToast(context, "没装SD卡");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                Uri result = resultCode != RESULT_OK ? null : Uri.fromFile(new File(path));
                if (result != null) {
                    uploadMessage.onReceiveValue(new Uri[]{result});
                    uploadMessage = null;
                }else {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = resultCode != RESULT_OK ? null : Uri.fromFile(new File(path));
            if (result != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }else {
                mUploadMessage.onReceiveValue(null);
                mUploadMessage = null;
            }
        }
    }
}

package tdh.ifm.android.imatch.app.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
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
import tdh.ifm.android.imatch.app.ui.activity.driver.FriendListActivity;
import tdh.ifm.android.imatch.app.ui.view.MyTitleView;
import tdh.ifm.android.imatch.app.ui.view.SwipeWebView;
import tdh.ifm.android.imatch.app.utils.Constants;
import tdh.ifm.android.imatch.app.utils.FileUtils;
import tdh.ifm.android.imatch.app.utils.NetContant;
import tdh.ifm.android.imatch.app.utils.SharedPreferencesUtil;
import tdh.ifm.android.imatch.app.utils.Util;

/**
 * Author：gwx
 * Create at：2017/5/8 14:40
 */
public class WebViewByCookieActivity extends BaseActivity {
    @BindView(R.id.titleview)
    MyTitleView titleview;
    @BindView(R.id.webView)
    SwipeWebView webView;
    @BindView(R.id.swipeLayout)
    MySwipeRefreshLayout swipeLayout;

    private String url;

    private MyWebClient webViewClient;

    SharedPreferences sharedPreferences;

    //照相
    private String path;
    private FileUtils fileUtils;

    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 101;

    @Override
    public int getContentViewId() {
        return R.layout.activity_webview_swipelayout;
    }

    @Override
    public void initTitleView() {
        super.initTitleView();
        titleview.getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uu = webView.getUrl();
                if (url.equals(uu) || (url + "/").equals(uu) || uu.contains("orderDetal")) {
                    finish();
                    return;
                }
                webView.goBack();   //后退
            }
        });
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
                Util.synCookies(context, webView.getUrl(), sharedPreferences.getString("cookie", ""));
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

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (titleview != null && titleview.getTv_title() != null) {
                if (title != null && !title.contains("优卡2.0")) {
                    titleview.getTv_title().setText(title);
                }
            }
        }

        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
//            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//            i.addCategory(Intent.CATEGORY_OPENABLE);
//            i.setType("image/*");
//            ((Activity) context).startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);

            open(FILECHOOSER_RESULTCODE);
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

            open(REQUEST_SELECT_FILE);
            return true;
        }

        //For Android 4.1 only
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            open(FILECHOOSER_RESULTCODE);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            open(FILECHOOSER_RESULTCODE);
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

        //弹出toast
        @JavascriptInterface
        public void showMessage(String message) {
            APPLog.error(NetContant.TAG,"===message==="+message);

            if (!TextUtils.isEmpty(message)) {
                CommonUtil.getToast(context,message);
            }
        }

        //选择城市
        @JavascriptInterface
        public void chooseCity(String cityCode,String cityName) {
            APPLog.error(NetContant.TAG,"cityCode==="+cityCode+"===cityName==="+cityName);
            Intent intent = getIntent();
            intent.putExtra("cityCode",cityCode);
            intent.putExtra("cityName",cityName);
            setResult(10,intent);
            finish();
        }

        //调起打电话界面
        @JavascriptInterface
        public void callPhone(String phone) {
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + phone));
            context.startActivity(intent);
        }
        //获取位置
        @JavascriptInterface
        public String getLocation() {
            if (SharedPreferencesUtil.getValue(Constants.LAT,"").equals("")) {
                return "39.929986,116.395645";
            }else {
                return SharedPreferencesUtil.getValue(Constants.LAT,"")+","+SharedPreferencesUtil.getValue(Constants.LON,"");
            }
        }

        //指定承运人
        @JavascriptInterface
        public void intentToAppointCarrier(String userTypeCD) {
            APPLog.error(NetContant.TAG,userTypeCD);

            Bundle bundle = new Bundle();
            bundle.putString("userTypeCD", userTypeCD);
            Util.intentToActivity(context, FriendListActivity.class,10,bundle);
        }

        //刷新界面
        @JavascriptInterface
        public void refreshWeb() {
            Util.synCookies(context, webView.getUrl(), sharedPreferences.getString("cookie", ""));
            webView.loadUrl(webView.getUrl());
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
            if (url.equals(NetContant.HTML_FINDSOURCE)) {
                titleview.getTv_right_title().setVisibility(View.VISIBLE);
                titleview.getTv_right_title().setText("历史报价");
                titleview.getTv_right_title().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.URL, NetContant.HTML_HISTORYORDER);
                        Util.intentToActivity(context, WebViewByCookieActivity.class,bundle);
                    }
                });
            }else {
                titleview.getTv_right_title().setVisibility(View.GONE);
            }
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

    private void open(int code){
        if (Build.VERSION.SDK_INT >= 23){
            try {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CAMERA)) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA},
                                13);
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA},
                                13);
                    }
                }else{
                    openXiangJi(code);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            openXiangJi(code);
        }
    }

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
        if (requestCode == 10 && resultCode == 10) {
            int memberId = data.getIntExtra("memberId",0);
            APPLog.error(NetContant.TAG,"====memberId==="+memberId);
            webView.loadUrl("javascript:goToAppointCarrier('"+memberId+"')");
        }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {  //表示按返回键
                String uu = webView.getUrl();
                if (url.equals(uu) || (url + "/").equals(uu) || uu.contains("orderDetal")) {
                    finish();
                    return super.onKeyDown(keyCode, event);
                }
                webView.goBack();   //后退

                return true;    //已处理
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}

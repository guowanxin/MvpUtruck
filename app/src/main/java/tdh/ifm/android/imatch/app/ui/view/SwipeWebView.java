package tdh.ifm.android.imatch.app.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.tdh.common.view.MySwipeRefreshLayout;


/**
 * Author：gwx
 * Create at：2017/3/23 20:16
 */
public class SwipeWebView extends WebView {

    private MySwipeRefreshLayout swipeRefreshLayout;
    private boolean isReloadPage = true;

    private Context context;

    public SwipeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setSwipeRefreshLayout(MySwipeRefreshLayout swipeRefreshLayout){
        this.swipeRefreshLayout = swipeRefreshLayout;

        setWebChromeClient(new WebChromeClient());
    }

    public void setReloadPage(boolean reloadPage) {
        isReloadPage = reloadPage;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.getScrollY() == 0){
            if (isReloadPage) {
                swipeRefreshLayout.setEnabled(true);
            }
        }else {
            swipeRefreshLayout.setEnabled(false);
        }
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                //隐藏进度条
                swipeRefreshLayout.setRefreshing(false);
            } else {
                if (!swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(true);
            }

            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

    }

}

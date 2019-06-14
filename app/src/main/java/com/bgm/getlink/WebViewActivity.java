package com.bgm.getlink;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bgm.getlink.model.PostLinkEntity;
import com.bgm.getlink.utils.StatusBarUtil;
import com.bgm.getlink.utils.Utilities;
import com.google.gson.Gson;

import java.io.File;
import java.lang.ref.WeakReference;

public class WebViewActivity extends AppCompatActivity {

    // 进度条
    private WebView webView;
    // 网页链接
    private String mId;
    private String mUrl;
    private String mGroup;
    private Toolbar mTitleToolBar;
    private TextView tvGunTitle;
    private String contentFilePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "content.txt";
    private PostLinkEntity postEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Log.d("wbin", "load url...");
        getIntentData();
        initTitle();
        if (mGroup.equals("Baidu")) {
            initWebView();
        } else {
            initWideWebView();
        }
        webView.loadUrl(mUrl);
//        getDataFromBrowser(getIntent());
    }

    private void getIntentData() {
        mUrl = getIntent().getStringExtra("mUrl");
        mId = getIntent().getStringExtra("id");
        mGroup = getIntent().getStringExtra("group");
        if (TextUtils.isEmpty(mGroup)) {
            mGroup = "Baidu";
        }
        processUrl();
        Log.d("wbin","url: " + mUrl);
        postEntity = new PostLinkEntity(mId);
        postEntity.setGroupKey(mGroup);
    }

    private void processUrl() {
        String url = mUrl;
        if (!url.startsWith("http") && url.contains("http")) {
            // 有http且不在头部
            url = url.substring(url.indexOf("http"), url.length());
        } else if (url.startsWith("www")) {
            // 以"www"开头
            url = "http://" + url;
        } else if (!url.startsWith("http") && (url.contains(".me") || url.contains(".com") || url.contains(".cn"))) {
            // 不以"http"开头且有后缀
            url = "http://www." + url;
        } else if (!url.startsWith("http") && !url.contains("www")) {
            // 输入纯文字 或 汉字的情况
            url = "http://m5.baidu.com/s?from=124n&word=" + url;
        }
        mUrl = url;
    }

    private void initTitle() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary), 0);
        webView = findViewById(R.id.webview_detail);
        mTitleToolBar = findViewById(R.id.title_tool_bar);
        tvGunTitle = findViewById(R.id.tv_gun_title);
        initToolBar();
    }

    private void initToolBar() {
        setSupportActionBar(mTitleToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitleToolBar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.actionbar_more));
        tvGunTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvGunTitle.setSelected(true);
            }
        }, 1900);
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWideWebView() {
        WebSettings ws = webView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 不缩放
        webView.setInitialScale(100);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否新窗口打开(加了后可能打不开网页)
//        ws.setSupportMultipleWindows(true);

        /* 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);
        webView.setWebChromeClient(new MyWebChromeClient(WebViewActivity.this));
        // 与js交互
//        webView.addJavascriptInterface(new MyJavascriptInterface(this), "injectedObject");
        webView.setWebViewClient(new LoadResourceCallbackWebClient(WebViewActivity.this));
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWebView() {
        WebSettings ws = webView.getSettings();
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(true);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setUseWideViewPort(true);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setWebChromeClient(new MyWebChromeClient(WebViewActivity.this));
        webView.setWebViewClient(new LoadResourceCallbackWebClient(WebViewActivity.this));
    }

    private static class MyWebChromeClient extends WebChromeClient {

        private WeakReference<WebViewActivity> mActivity;

        MyWebChromeClient(WebViewActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            WebViewActivity activity = mActivity.get();
            if (activity == null) return;
            Log.d("wbin", "title: " + title);
            if (!activity.mGroup.equals("TouTiao")) {
                activity.postEntity.setTitle(title);
            }
            super.onReceivedTitle(view, title);
        }
    }

    private static class LoadResourceCallbackWebClient extends WebViewClient{
        private WeakReference<WebViewActivity> mActivity;

        LoadResourceCallbackWebClient(WebViewActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            Log.d("wbin", "-----------------\n" + url);
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            //Log.d("wbin", "-----------------\n" + url);
            WebViewActivity activity = mActivity.get();
            if (activity == null) return;

            boolean isGetLink = false;
            if (activity.mGroup.equals("Baidu") && url.contains("baiduboxapp://utils?action")) {
                isGetLink = true;
            } else if (activity.mGroup.equals("UC") && url.contains("uclink://")) {
                isGetLink = true;
            } else if (activity.mGroup.equals("TouTiao") && url.contains("snssdk143://")) {
                isGetLink = true;
            }

            if (isGetLink) {
                Log.d("wbin", "-----------------\n" + url);
                try {
                    activity.postEntity.setLinkUrl(url);
                    Utilities.contentToTxt(activity.contentFilePath, new Gson().toJson(activity.postEntity));
                } catch (Exception e) {
                    Log.d("wbin", "post entity exception: " + e);
                }
                activity.finish();
            }
            super.onLoadResource(view, url);
        }
    }

    public void setTitle(String mTitle) {
        tvGunTitle.setText(mTitle);
    }


    /**
     * 使用singleTask启动模式的Activity在系统中只会存在一个实例。
     * 如果这个实例已经存在，intent就会通过onNewIntent传递到这个Activity。
     * 否则新的Activity实例被创建。
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        getDataFromBrowser(intent);
    }

    /**
     * 作为三方浏览器打开传过来的值
     * Scheme: https
     * host: www.jianshu.com
     * path: /p/1cbaf784c29c
     * url = scheme + "://" + host + path;
     */
    private void getDataFromBrowser(Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            try {
                String scheme = data.getScheme();
                String host = data.getHost();
                String path = data.getPath();
                String text = "Scheme: " + scheme + "\n" + "host: " + host + "\n" + "path: " + path;
                Log.e("data", text);
                String url = scheme + "://" + host + path;
                webView.loadUrl(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
/*        if (webView != null) {
            webView.onResume();
            // 支付宝网页版在打开文章详情之后,无法点击按钮下一步
            webView.resumeTimers();
            // 设置为横屏
            if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }*/
    }

    @Override
    public void finish() {
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.destroy();
            webView = null;
        }
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 打开网页:
     *
     * @param mContext 上下文
     * @param mUrl     要加载的网页url
     * @param mTitle   标题
     */
    public static void loadUrl(Context mContext, String mUrl, String mTitle) {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra("mUrl", mUrl);
        intent.putExtra("mTitle", mTitle == null ? "加载中..." : mTitle);
        mContext.startActivity(intent);
    }

    public static void loadUrl(Context mContext, String mUrl) {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra("mUrl", mUrl);
        intent.putExtra("mTitle", "详情");
        mContext.startActivity(intent);
    }
}

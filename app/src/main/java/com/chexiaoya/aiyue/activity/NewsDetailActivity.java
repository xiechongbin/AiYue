package com.chexiaoya.aiyue.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.bean.NewsDataBean;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

/**
 * Created by xcb on 2019/1/21.
 */
public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.tv_news_detail_back)
    ImageView tv_news_detail_back;
    @BindView(R.id.pb_progress)
    ProgressBar pb_progress;
    private NewsDataBean newsDataBean;

    public static void launch(Context context, NewsDataBean newsDataBean) {
        Bundle args = new Bundle();
        args.putSerializable("NewsDataBean", newsDataBean);
        Intent intent = new Intent();
        intent.putExtra("news", args);
        intent.setClass(context, NewsDetailActivity.class);
        context.startActivity(intent);
        //过度动画
        // ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(context, R.anim.anim_page_fade_in, R.anim.anim_page_fade_out);
        // ActivityCompat.startActivity(context, intent, compat.toBundle());
    }

    @Override
    public int getLayout() {
        return R.layout.activity_news_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
    }

    @Override
    public void initData() {
        tv_news_detail_back.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("news");
            if (bundle != null) {
                newsDataBean = (NewsDataBean) bundle.getSerializable("NewsDataBean");
            }
        }
        initWebView();
        if (newsDataBean != null) {
            String url = newsDataBean.getUrl();
            webview.loadUrl(url);
        }

    }


    private void initWebView() {
        WebSettings webSettings = webview.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        //优先使用缓存:
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
//        webview.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
                Logger.d("开始加载");
            }
        });

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
                Logger.d("加载完成");
            }
        });


        //步骤1：写一个html文件（error_handle.html），用于出错时展示给用户看的提示页面
        //步骤2：将该html文件放置到代码根目录的assets文件夹下
        //步骤3：复写WebViewClient的onRecievedError方法
        //该方法传回了错误码，根据错误类型可以进行不同的错误分类处理
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Logger.d("加载失败:errorCode = " + errorCode + ">>description = " + description + ">>failingUrl = " + failingUrl);
            }
        });

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pb_progress.setProgress(newProgress);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                Logger.d("onReceivedIcon");
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Logger.d("onReceivedTitle>>" + title);
                pb_progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_news_detail_back:
                this.finishAfterTransition();
                break;
        }
    }
}

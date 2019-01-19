package com.chexiaoya.aiyue.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.bean.NewsDataBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xcb on 2019/1/19.
 */
public class NewsDetailsFragment extends BaseFragment {

    @BindView(R.id.webview)
    WebView webview;
    Unbinder unbinder;
    private NewsDataBean newsDataBean;

    public static NewsDetailsFragment newInstance(NewsDataBean newsDataBean) {
        Bundle args = new Bundle();
        args.putSerializable("NewsDataBean", newsDataBean);
        NewsDetailsFragment fragment = new NewsDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.frag_news_details;
    }

    @Override
    public void initView(Object obj) {
        View rootView = (View) obj;
        unbinder = ButterKnife.bind(this, rootView);
    }

    private void initWebView() {
        WebSettings webSettings = webview.getSettings();

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
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
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
            }
        });


//步骤1：写一个html文件（error_handle.html），用于出错时展示给用户看的提示页面
//步骤2：将该html文件放置到代码根目录的assets文件夹下

//步骤3：复写WebViewClient的onRecievedError方法
//该方法传回了错误码，根据错误类型可以进行不同的错误分类处理
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }
        });

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        Bundle args = getArguments();
        if (args != null) {
            newsDataBean = (NewsDataBean) args.getSerializable("NewsDataBean");
        }
        if (newsDataBean != null) {
            String url = newsDataBean.getUrl();
            webview.loadUrl(url);
        }

    }


    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

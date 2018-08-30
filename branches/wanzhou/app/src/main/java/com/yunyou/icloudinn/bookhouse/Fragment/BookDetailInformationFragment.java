package com.yunyou.icloudinn.bookhouse.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.tencent.smtt.sdk.WebView;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;

public class BookDetailInformationFragment extends Fragment {
    private String mDetail;
    private WebView mWebView;
    public static BookDetailInformationFragment getInstance(String Content){
        Bundle bundle=new Bundle();
        BookDetailInformationFragment bookDetailInformationFragment=new BookDetailInformationFragment();
        bundle.putString("Content",Content);
        bookDetailInformationFragment.setArguments(bundle);
        return bookDetailInformationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments()!=null){
            mDetail=getArguments().getString("Content");
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.webview_layout,container,false);

        mWebView= (WebView) view.findViewById(R.id.webView);
        initWebView();
        return view;
    }

    private void initWebView() {
        mDetail="<style>img{max-width:100%;height:auto}" +
                "video{max-width:100%;height:auto}</style>"+mDetail;
        mWebView.loadDataWithBaseURL(null, mDetail, "text/html", "utf-8", null);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
        //mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setJavaScriptEnabled(true);//允许执行js
    }
}

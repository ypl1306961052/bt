
package com.yunyou.icloudinn.bookhouse.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;

/**
 * Created by 13069 on 2018/1/22.
 */

public class LookHouseAllActivity extends Activity {
    WebView webView;
    String url_var;
    private CustomProgress  customProgress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookhouseall);
        initWeb();
        initData();
        customProgress=new CustomProgress(this,R.style.Custom_Progress,"加载中",false);
        customProgress.show();
        webView.loadUrl(url_var);
        customProgress.dismiss();
    }

    private void initData() {
        Intent intent=getIntent();
        url_var=intent.getStringExtra("url");
    }

    private void initWeb() {
        webView=(WebView)findViewById(R.id.webView_lookHouseAll);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存

        webView.getSettings().setLoadWithOverviewMode(true);

    }
}

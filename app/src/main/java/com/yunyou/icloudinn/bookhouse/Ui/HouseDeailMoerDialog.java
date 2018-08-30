package com.yunyou.icloudinn.bookhouse.Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.widget.Button;

import com.tencent.smtt.sdk.WebView;
import com.yunyou.icloudinn.bookhouse.R;

public class HouseDeailMoerDialog extends DialogFragment {
    private String mContext;
    private WebView mWebView;

    public static HouseDeailMoerDialog getInstance(String context){
       Bundle bundle=new Bundle();
       bundle.putString("context",context);
       HouseDeailMoerDialog houseDeailMoerDialog=new HouseDeailMoerDialog();
       houseDeailMoerDialog.setArguments(bundle);
       return houseDeailMoerDialog;
   }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext=getArguments().getString("context");
        View view = inflater.inflate(R.layout.dialog_house_detail_more, container);
        mWebView= (WebView) view.findViewById(R.id.house_detail_information_webview);
        Button mClose= (Button) view.findViewById(R.id.close_webview_dialog);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        initWebView();
        return view;
    }

    private void initWebView() {
        mContext="<style>img{max-width:100%;height:auto}" +
                "video{max-width:100%;height:auto}</style>"+mContext;
        mWebView.loadDataWithBaseURL(null, mContext, "text/html", "utf-8", null);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
        //mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setJavaScriptEnabled(true);//允许执行js
    }
}

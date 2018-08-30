package com.yunyou.icloudinn.bookhouse.Ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyou.icloudinn.bookhouse.R;


public class CustomProgress extends Dialog {
    private Context context;
    private CharSequence message;
    private boolean cancelable;

    public CustomProgress(Context context) {
        super(context);
        this.context=context;
    }

    public CustomProgress(Context context, int theme, CharSequence message, boolean cancelable) {
        super(context, theme);
        this.context=context;
        this.message=message;
        this.cancelable=cancelable;
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        // 获取ImageView上的动画背景
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        // 开始动画
        spinner.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.my_progress_custom);
        if (message == null || message.length() == 0) {
           findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
        }
        // 按返回键是否取消
      setCancelable(cancelable);
        // 监听返回键处理

        // 设置居中
        getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        getWindow().setAttributes(lp);
        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }

    /**
     * 给Dialog设置提示信息
     *
     * @param message
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();

        }
    }

}

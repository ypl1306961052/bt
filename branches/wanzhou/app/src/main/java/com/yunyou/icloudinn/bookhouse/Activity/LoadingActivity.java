package com.yunyou.icloudinn.bookhouse.Activity;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.yunyou.icloudinn.bookhouse.R;

public class LoadingActivity extends AppCompatActivity implements Animator.AnimatorListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        LinearLayout splash = (LinearLayout) findViewById(R.id.splash);
        splash.setAlpha(0.5f);//设置界面的透明度为0.5f
        splash.animate().alpha(1.0f).setDuration(3000).setListener(this).start();//启动渐变动画
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }
    /**
     * 重写动画结束进行界面跳转
     */
    @Override
    public void onAnimationEnd(Animator animator) {
        Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        //overridePendingTransition();
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}

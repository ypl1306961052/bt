package com.yunyou.icloudinn.bookhouse.Activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.yunyou.icloudinn.bookhouse.R;

public class WelcomeActivity extends AppCompatActivity implements Animator.AnimatorListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
        // 退出全屏模式，解决从全屏到不是全屏的 Activity 的动画卡顿问题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
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

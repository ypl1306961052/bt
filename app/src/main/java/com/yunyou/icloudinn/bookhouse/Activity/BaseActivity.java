package com.yunyou.icloudinn.bookhouse.Activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.githang.statusbar.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.R;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        // 设置状态栏颜色
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.green1));
    }

    public void doBack(View view){
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        }else {
            onBackPressed();
        }
    }

}

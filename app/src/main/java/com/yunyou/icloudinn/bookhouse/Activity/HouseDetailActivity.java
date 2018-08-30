package com.yunyou.icloudinn.bookhouse.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.yunyou.icloudinn.bookhouse.Fragment.BaseFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.HouseDeatilMainFragment;

import org.greenrobot.eventbus.EventBus;


public class HouseDetailActivity extends FragmentUtilActivity {

    private int id;

    @Override
    protected BaseFragment getFirstFragment() {
        Intent intent= getIntent();
        id= intent.getExtras().getInt("id");
        return HouseDeatilMainFragment.getInstance(id);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}

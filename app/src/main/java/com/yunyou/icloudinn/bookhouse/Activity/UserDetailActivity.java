package com.yunyou.icloudinn.bookhouse.Activity;

import android.content.Context;
import android.content.Intent;

import com.yunyou.icloudinn.bookhouse.Fragment.BaseFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.DetaliFromCommentFragment;

public class UserDetailActivity extends FragmentUtilActivity {
    private int id;

    public static Intent getInstance(Context context, int id){
        Intent intent= new Intent(context,UserDetailActivity.class);
        intent.putExtra("id",id);
        return intent;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        id=getIntent().getIntExtra("id",0);
        return DetaliFromCommentFragment.getInstance(id);
    }
}

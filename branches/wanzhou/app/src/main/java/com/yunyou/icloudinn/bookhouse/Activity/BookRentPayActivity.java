package com.yunyou.icloudinn.bookhouse.Activity;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yunyou.icloudinn.bookhouse.Fragment.BaseFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.BecomeMenberFragmnet;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;

public class BookRentPayActivity extends FragmentUtilActivity {
    private boolean isAffirm;
    private String bookName;
    private int id,price;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.compat(this, getResources().getColor(R.color.green1));
        super.onCreate(savedInstanceState);

    }

    @Override
    protected BaseFragment getFirstFragment() {
        isAffirm=getIntent().getBooleanExtra("isAffirm",false);
        LogUtils.i("是否是租书1"+isAffirm);
        if (isAffirm){
            bookName=getIntent().getStringExtra("bookname");
            id=getIntent().getIntExtra("id",0);
            price=getIntent().getIntExtra("price",0);
        }
        if (isAffirm){
            return BecomeMenberFragmnet.getInstance(isAffirm,bookName,id,price);
        }else {
            return BecomeMenberFragmnet.getInstance(isAffirm);
        }

    }


}

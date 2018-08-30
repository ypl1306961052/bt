package com.yunyou.icloudinn.bookhouse.Activity;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yunyou.icloudinn.bookhouse.Fragment.BaseFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.BookRentFragment;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;

public class BookRentActivity extends FragmentUtilActivity {
    private int id,price;
    private String bookNmae;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("书名1"+bookNmae);
    }

    @Override
    protected BaseFragment getFirstFragment() {
        id=getIntent().getIntExtra("id",0);
        price=getIntent().getIntExtra("price",0);
        bookNmae=getIntent().getStringExtra("bookName");
        return BookRentFragment.getInstance(id,bookNmae,price);
    }


}

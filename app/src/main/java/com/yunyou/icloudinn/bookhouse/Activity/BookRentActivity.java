package com.yunyou.icloudinn.bookhouse.Activity;

import android.os.Bundle;

import com.yunyou.icloudinn.bookhouse.Fragment.BaseFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.BookRentFragment;
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

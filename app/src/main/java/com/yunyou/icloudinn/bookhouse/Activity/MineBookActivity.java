package com.yunyou.icloudinn.bookhouse.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyou.icloudinn.bookhouse.Fragment.MineDonateFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.MineRentFragment;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;

public class MineBookActivity extends BaseActivity implements View.OnClickListener {
    private boolean mChooseRent=true;
    private ImageView mRent,mDonate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_book);
        initView();
        initDefultFragment();
    }



    private void initView() {
        StatusBarCompat.compat(this, getResources().getColor(R.color.green1));
        View mBack=findViewById(R.id.back);
        TextView title= (TextView) findViewById(R.id.title);
        TextView bookRent= (TextView) findViewById(R.id.mine_book_rent);
        TextView bookDontate= (TextView) findViewById(R.id.mine_book_donate);
        mRent=(ImageView) findViewById(R.id.mine_book_rent_picture);
        mDonate=(ImageView) findViewById(R.id.mine_book_donate_picture);

        mBack.setOnClickListener(this);
        bookRent.setOnClickListener(this);
        bookDontate.setOnClickListener(this);
        title.setText("我的书");
    }




    private void initDefultFragment() {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.replace(R.id.mine_book_content, MineRentFragment.getInstacnce());
        fragmentTransaction.commit();
    }
    private void initDonateFragment(){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.replace(R.id.mine_book_content, MineDonateFragment.getInstance());
        fragmentTransaction.commit();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_book_rent:
                chooseRent();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.mine_book_donate:
                chooseDonate();
                break;

        }
    }
    private void chooseRent(){
        if (mChooseRent){
            return;
        }
        mRent.setVisibility(View.VISIBLE);
        mDonate.setVisibility(View.INVISIBLE);
        mChooseRent=true;
        initDefultFragment();
    }
    private void chooseDonate(){
        if (!mChooseRent){
            return;
        }
        mRent.setVisibility(View.INVISIBLE);
        mDonate.setVisibility(View.VISIBLE);
        mChooseRent=false;
        initDonateFragment();
    }
}

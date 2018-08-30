package com.yunyou.icloudinn.bookhouse.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyou.icloudinn.bookhouse.Fragment.BookCollectListFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.HouseCollectListFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.MineDonateFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.MineRentFragment;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;

public class CollectActivity extends AppCompatActivity implements View.OnClickListener {
    private View mContent;
    private boolean mChooseRent=true;
    private ImageView mRent,mDonate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initView();
        initDefultFragment();
    }

    private void initView() {
        StatusBarCompat.compat(this, getResources().getColor(R.color.green1));
        View mBack=findViewById(R.id.back);
        TextView title= (TextView) findViewById(R.id.title);
        TextView bookRent= (TextView) findViewById(R.id.mine_book_collect);
        TextView bookDontate= (TextView) findViewById(R.id.mine_house_collect);
        mRent=(ImageView) findViewById(R.id.mine_book_rent_picture);
        mDonate=(ImageView) findViewById(R.id.mine_book_donate_picture);
        mContent=findViewById(R.id.mine_book_content);
        mBack.setOnClickListener(this);
        bookRent.setOnClickListener(this);
        bookDontate.setOnClickListener(this);
        title.setText("我的收藏");
    }




    private void initDefultFragment() {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.replace(R.id.mine_book_content, HouseCollectListFragment.getInstance());
        fragmentTransaction.commit();
    }
    private void initDonateFragment(){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.replace(R.id.mine_book_content, BookCollectListFragment.getInstance());
        fragmentTransaction.commit();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_house_collect:
                chooseHouse();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.mine_book_collect:
                chooseBook();
                break;

        }
    }
    private void chooseHouse(){
        if (mChooseRent){
            return;
        }
        mRent.setVisibility(View.VISIBLE);
        mDonate.setVisibility(View.INVISIBLE);
        mChooseRent=true;
        initDefultFragment();
    }
    private void chooseBook(){
        if (!mChooseRent){
            return;
        }
        mRent.setVisibility(View.INVISIBLE);
        mDonate.setVisibility(View.VISIBLE);
        mChooseRent=false;
        initDonateFragment();
    }
}

package com.yunyou.icloudinn.bookhouse.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.Fragment.BookDetailCommentFramgnet;
import com.yunyou.icloudinn.bookhouse.Fragment.BookDetailInformationFragment;
import com.yunyou.icloudinn.bookhouse.JavaBean.BookDetailData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private int mInformationId;
    private ImageView mBookPicture,mDetailPicture,mCommentPicture;
    private TextView mAuthor,mComPany,mTime,mPirce,mRentNum,mRentPrice,mBookLocation,more1,more2;
    private BookDetailData bookDetailData;
    private boolean chooseInformation=true;
    private CustomProgress customProgress;
    private RecyclerView mUserList;
    private CommonAdapter<BookDetailData.DataBean.BookRentBean> mAdapter;
    private List<BookDetailData.DataBean.BookRentBean> mListData=new ArrayList<>();
    private ImageView mCollect;
    private boolean currentChoose=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_book_detail);
        Intent intent= getIntent();
        mInformationId= intent.getExtras().getInt("id");
        initMyView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initData();

    }

    private void initMyView() {
        StatusBarCompat.compat(this, getResources().getColor(R.color.green1));
        customProgress=new CustomProgress(this,R.style.Custom_Progress,"加载中",false);
        mBookPicture= (ImageView) findViewById(R.id.book_detail_image);
        mDetailPicture= (ImageView) findViewById(R.id.book_detil_information_picture);
        mCollect= (ImageView) findViewById(R.id.book_detail_collect);
        mCommentPicture= (ImageView) findViewById(R.id.book_detil_comment_picture);
        mAuthor= (TextView)findViewById(R.id.book_detail_author);
        mComPany= (TextView) findViewById(R.id.book_detail_publishing_company);
        mTime= (TextView) findViewById(R.id.book_detail_publishing_time);
        mPirce= (TextView) findViewById(R.id.book_detail_price);
        mRentNum= (TextView) findViewById(R.id.book_detail_rent_time);
        mRentPrice= (TextView) findViewById(R.id.book_detail_rent_price);
        mBookLocation= (TextView) findViewById(R.id.book_detail_place);
        mUserList= (RecyclerView) findViewById(R.id.read_user_list);
        more1= (TextView) findViewById(R.id.rent_user_more);
        more2= (TextView) findViewById(R.id.rent_user_more1);
        View back=findViewById(R.id.back);
        TextView title= (TextView) findViewById(R.id.title);
        View detail=findViewById(R.id.book_detail_layout);
        View comment=findViewById(R.id.book_comment_layout);
        View collect=findViewById(R.id.collect_layout);
        View recomment=findViewById(R.id.recommend_layout);
        View borrow=findViewById(R.id.borrow_layout);
        collect.setOnClickListener(this);
        recomment.setOnClickListener(this);
        borrow.setOnClickListener(this);
        back.setOnClickListener(this);
        detail.setOnClickListener(this);
        comment.setOnClickListener(this);
        title.setText("书本详情");
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mUserList.setLayoutManager(linearLayoutManager);
        if (mListData.size()==0){
            mListData.addAll(bookDetailData.getData().getBook_rent());
        }else {
            mListData.clear();
            mListData.addAll(bookDetailData.getData().getBook_rent());
        }

        LogUtils.i(bookDetailData.getData().getBook_rent().size()+"列表长度");
        if (bookDetailData.getData().getBook_rent().size()==0){
            more1.setVisibility(View.GONE);
            more2.setText("没有人借阅");
        }
        mAdapter=new CommonAdapter<BookDetailData.DataBean.BookRentBean>(this,R.layout.list_item_small_head_picture,mListData) {
            @Override
            protected void convert(ViewHolder holder, BookDetailData.DataBean.BookRentBean bookRentBean, int position) {
                Glide.with(BookDetailActivity.this)
                        .load(bookRentBean.getHead_img())
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.small_head));
            }
        };
        mUserList.setAdapter(mAdapter);
    }

    private void initData() {
        customProgress.show();
        if (MyApplication.getInstance().isLogin()){
            OkHttpUtils.get()
                    .url(Api.BOOK+mInformationId)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            customProgress.dismiss();
                            ToastUtils.showLongToast("网络错误" + e);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            customProgress.dismiss();
                            try {
                                JSONObject json = JSON.parseObject(response);
                                if (json.getInteger("code") == 100) {
                                    LogUtils.i("数据"+response);
                                    bookDetailData=JSON.parseObject(response,BookDetailData.class);
                                    loadData();
                                } else {
                                    ToastUtils.showLongToast( json.getString("msg"));
                                }
                            } catch (Exception e) {
                                ToastUtils.showLongToast("网络错误" + e);

                            }

                        }
                    });
        }else {
            OkHttpUtils.get()
                    .url(Api.BOOK+mInformationId)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            customProgress.dismiss();
                            ToastUtils.showLongToast("网络错误" + e);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            customProgress.dismiss();
                            try {
                                JSONObject json = JSON.parseObject(response);
                                if (json.getInteger("code") == 100) {
                                    LogUtils.i("数据"+response);
                                    bookDetailData=JSON.parseObject(response,BookDetailData.class);

                                    loadData();
                                } else {
                                    ToastUtils.showLongToast( json.getString("msg"));
                                }
                            } catch (Exception e) {
                                ToastUtils.showLongToast("网络错误" + e);

                            }

                        }
                    });
        }

    }

    private void loadData(){
        Glide.with(this)
                .load(Api.BASE_URL+bookDetailData.getData().getModel().getCover_img())
                .centerCrop()
                .into(mBookPicture);
        mAuthor.setText(bookDetailData.getData().getModel().getAuther());
        mComPany.setText(bookDetailData.getData().getModel().getPublisher());
        mTime.setText(bookDetailData.getData().getModel().getPublish_time()!=null?bookDetailData.getData().getModel().getPublish_time():"");
        mPirce.setText(bookDetailData.getData().getPrice()+"元");
        mRentNum.setText(String.valueOf(bookDetailData.getData().getRead_num()));
        mRentPrice.setText(String.valueOf(bookDetailData.getData().getRent_num()));
        mBookLocation.setText(bookDetailData.getData().getBook_house().getLocation());
        LogUtils.i("收藏"+bookDetailData.getData().isIs_collect());
        if (bookDetailData.getData().isIs_collect()){
            mCollect.setBackground(getResources().getDrawable(R.drawable.book_collect));
        }
        initList();
        if (currentChoose){
            initDefaultFragment();
        }else {
            initCommentFragment();
        }

    }

    private void initDefaultFragment() {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, BookDetailInformationFragment.getInstance(bookDetailData.getData().getModel().getIntro()));
        fragmentTransaction.commit();
    }

    private void initCommentFragment(){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, BookDetailCommentFramgnet.getInstance(bookDetailData));
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.book_detail_layout:
                chooseDetail();
                break;
            case R.id.book_comment_layout:
                chooseComment();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.collect_layout:
                if (MyApplication.getInstance().isLogin()){
                    LogUtils.i("点击收藏"+bookDetailData.getData().isIs_collect());
                    if (bookDetailData.getData().isIs_collect()){
                        doUnCollect();
                    }else {
                        doCollect();
                    }
                }else {
                    startActivity(new Intent(BookDetailActivity.this,LoginActivity.class));
                }
                
                break;
            case R.id.recommend_layout:
                if (MyApplication.getInstance().isLogin()){
                    Intent intent=new Intent(BookDetailActivity.this,BookCommentActivity.class);
                    intent.putExtra("id",bookDetailData.getData().getId());
                    intent.putExtra("type","2");
                    startActivity(intent);
                }else {
                    startActivity(new Intent(BookDetailActivity.this,LoginActivity.class));
                }
                break;
            case R.id.borrow_layout:
                if (MyApplication.getInstance().isLogin()){
                    if (MyApplication.getInstance().getUser().getData().getPhone()==null||MyApplication.getInstance().getUser().getData().getPhone().isEmpty()){
                        Intent intent=new Intent(this, LoginActivity.class);
                        intent.putExtra("bind",true);
                        startActivity(intent);
                    }else {
                        ifMember();
                    }
                }else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
        }
    }
    private void ifMember() {
        customProgress.show();
        OkHttpUtils.get()
                .url(Api.ISMENBER)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        customProgress.dismiss();
                        ToastUtils.showLongToast("网络错误" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        customProgress.dismiss();
                        try {
                            JSONObject json = JSON.parseObject(response);
                            LogUtils.i("你好"+json.getString("data"));
                            if (json.getInteger("code")==100) {
                                Intent intent =new Intent(BookDetailActivity.this,BookRentPayActivity.class);
                                intent.putExtra("isAffirm",true);
                                intent.putExtra("bookname",bookDetailData.getData().getModel().getName());
                                intent.putExtra("price",bookDetailData.getData().getPrice());
                                intent.putExtra("id",bookDetailData.getData().getId());
                                startActivity(intent);
                            } else {
                                ToastUtils.showLongToast(json.getString("msg"));
//                                Intent intent =new Intent(BookDetailActivity.this,BookRentPayActivity.class);
//                                intent.putExtra("isAffirm",false);
//                                startActivity(intent);
                            }
                        } catch (Exception e) {
                            ToastUtils.showLongToast("网络错误" + e);
                        }
                    }
                });
    }
    private void doUnCollect() {
        customProgress.show();
        OkHttpUtils.post()
                .url(Api.COLLECT)
                .addParams("source_id",String.valueOf(bookDetailData.getData().getId()))
                .addParams("type","2")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showLongToast("网络错误" + e);
                customProgress.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                customProgress.dismiss();
                try {
                    JSONObject json = JSON.parseObject(response);
                    if (json.getInteger("code") == 100) {
                        ToastUtils.showLongToast( "取消收藏成功");
                        bookDetailData.getData().setIs_collect(false);
                        mCollect.setBackground(getResources().getDrawable(R.drawable.book_uncollect));
                    } else {
                        ToastUtils.showLongToast( json.getString("msg"));
                    }
                } catch (Exception e) {
                    ToastUtils.showLongToast("网络错误" + e);
                }
            }
        });
    }

    private void doCollect() {
        customProgress.show();
        OkHttpUtils.post()
                .url(Api.COLLECT)
                .addParams("source_id",String.valueOf(bookDetailData.getData().getId()))
                .addParams("type","2")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showLongToast("网络错误" + e);
                customProgress.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                customProgress.dismiss();
                try {
                    JSONObject json = JSON.parseObject(response);
                    if (json.getInteger("code") == 100) {
                        ToastUtils.showLongToast( "收藏成功");
                        bookDetailData.getData().setIs_collect(true);
                        mCollect.setBackground(getResources().getDrawable(R.drawable.book_collect));
                    } else {
                        ToastUtils.showLongToast( json.getString("msg"));
                    }
                } catch (Exception e) {
                    ToastUtils.showLongToast("网络错误" + e);
                }
            }
        });
    }

    private void chooseComment() {
        if (chooseInformation){
            mDetailPicture.setVisibility(View.GONE);
            mCommentPicture.setVisibility(View.VISIBLE);
            chooseInformation=false;
            currentChoose=false;
            initCommentFragment();
        }
    }

    private void chooseDetail() {
        if (!chooseInformation){
            mDetailPicture.setVisibility(View.VISIBLE);
            mCommentPicture.setVisibility(View.GONE);
            chooseInformation=true;
            currentChoose=true;
            initDefaultFragment();
        }
    }


}

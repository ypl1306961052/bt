package com.yunyou.icloudinn.bookhouse.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yunyou.icloudinn.bookhouse.Adapter.CircleCommentAdapter;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.PageData;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

public class MyDynamicActivity extends BaseActivity  implements View.OnClickListener {

    private String TAG = "MyDynamicActivity.java";
    private XRecyclerView mCricleList;
    private TextView mName,mOrder,mRent,mUserCentra,message,mEmpty;
    private ImageView mUserPicture,mWirteMood;
    private CustomProgress customProgress;
    private CircleCommentAdapter mAdapter;
    private boolean isFrist = true;
    private JSONArray jsonArray = new JSONArray();
    private LinearLayout edittextbody;
    private EditText editText;
    private ImageView sendIv;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mine_main);

        userId = getIntent().getStringExtra("user_id");
        if(userId==null){
            UserData user = MyApplication.getInstance().getUser();
            if(user!=null){
               userId = user.getYunsu_id()+"";
            }else {
                Intent intent = new Intent();
                intent.setClass(this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
        ((TextView)findViewById(R.id.title)).setText("我的相册");
        LayoutInflater inflater = LayoutInflater.from(this);
        View header = inflater.inflate(R.layout.circle_list_header, null);
        initView(header);

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.circle_order:
                startActivity(new Intent(this, OrderActivity.class));
                break;
            case R.id.circle_rent:
                startActivity(new Intent(this, MineBookActivity.class));
                break;
            case R.id.circle_user_centra:
                startActivity(new Intent(this, UserCenterActivity.class));
                break;
            case R.id.circle_message:
                startActivity(new Intent(this, AttentionActivity.class));
                break;
            case R.id.write_mood:
                startActivity(new Intent(this, SendMoodActivity.class));
                break;
            case R.id.circle_picture:
                int userId = 0;
                try {
                    userId = MyApplication.getInstance().getUser().getYunsu_id();
                }catch (Exception e){
                }
                startActivity(UserDetailActivity.getInstance(this,userId));
        }
    }

    private void initView(View header) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mCricleList= (XRecyclerView) findViewById(R.id.circle_list);
        mName= (TextView) header.findViewById(R.id.circle_name);
        mOrder= (TextView) header.findViewById(R.id.circle_order);
        mRent= (TextView) header.findViewById(R.id.circle_rent);
        mUserCentra= (TextView) header.findViewById(R.id.circle_user_centra);
        message= (TextView) header.findViewById(R.id.circle_message);
        mWirteMood= (ImageView) findViewById(R.id.write_mood);
        mUserPicture= (ImageView) header.findViewById(R.id.circle_picture);
        edittextbody = (LinearLayout)findViewById(R.id.editTextBodyLl);
        editText = (EditText)findViewById(R.id.circleEt);
        sendIv = (ImageView)findViewById(R.id.sendIv);
        mEmpty= (TextView) findViewById(R.id.mood_choose_house);

        mUserPicture.setOnClickListener(this);
        mOrder.setVisibility(View.GONE);
        mRent.setVisibility(View.GONE);
        mUserCentra.setVisibility(View.GONE);
        message.setVisibility(View.GONE);
        mWirteMood.setOnClickListener(this);
        mCricleList.setLayoutManager(linearLayoutManager);
        mCricleList.addHeaderView(header);
        mCricleList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initList();
                mAdapter.updateData(jsonArray);
                mCricleList.refreshComplete();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    private void initList() {
        isFrist=false;
        mAdapter=new CircleCommentAdapter(jsonArray, this, customProgress,edittextbody,editText,sendIv);
        mCricleList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (edittextbody.getVisibility() == View.VISIBLE) {
                    edittextbody.setVisibility(View.GONE);
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
        mCricleList.setAdapter(mAdapter);
    }

    private void initData() {
        OkHttpUtils.get()
                .url(Api.MY_OR_TA_DYNAMIC + userId)
                .build()
                .execute(new JsonCallback<PageData<JSONObject>>(this,true) {

                    @Override
                    public void onResponse(PageData<JSONObject> page, int id) {
                        List<JSONObject> list = page.getData();
                        try {
                            if (list.size()!=0){
                                if (jsonArray.size()==0){
                                    jsonArray.addAll(list);
                                }else {
                                    jsonArray.clear();
                                    jsonArray.addAll(list);
                                }
                                if (isFrist){
                                    initList();
                                }else {
                                    mAdapter.notifyDataSetChanged();
                                }
                            }else {
                                if (jsonArray.size()==0){
                                    jsonArray.addAll(list);
                                }else {
                                    jsonArray.clear();
                                    jsonArray.addAll(list);
                                }
                                if (isFrist){
                                    initList();
                                }else {
                                    mAdapter.notifyDataSetChanged();
                                }
                                mEmpty.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            ToastUtils.showLongToast("数据异常！" );
                        }
                    }
                });
    }

}

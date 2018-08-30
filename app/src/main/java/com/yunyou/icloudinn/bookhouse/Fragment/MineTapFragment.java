package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.yunyou.icloudinn.bookhouse.Activity.AttentionActivity;
import com.yunyou.icloudinn.bookhouse.Activity.LoginActivity;
import com.yunyou.icloudinn.bookhouse.Activity.MineBookActivity;
import com.yunyou.icloudinn.bookhouse.Activity.OrderActivity;
import com.yunyou.icloudinn.bookhouse.Activity.SendMoodActivity;
import com.yunyou.icloudinn.bookhouse.Activity.UserCenterActivity;
import com.yunyou.icloudinn.bookhouse.Activity.UserDetailActivity;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeTextView;

public class MineTapFragment extends Fragment implements View.OnClickListener {

    private String TAG = "MineTapFragment.java";
    private UserData user;
    private XRecyclerView mCricleList;
    private TextView mName,mOrder,mRent,mUserCentra,mEmpty;
    private BGABadgeTextView circle_message;
    private ImageView mUserPicture,mWirteMood;
    private CustomProgress customProgress;
    private CircleCommentAdapter mAdapter;
    private boolean isFrist = true;
    private JSONArray jsonArray = new JSONArray();
    private LinearLayout edittextbody;
    private EditText editText;
    private ImageView sendIv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        user=MyApplication.getInstance().getUser();

        View view = inflater.inflate(R.layout.fragment_mine_main, container, false);
        View header = inflater.inflate(R.layout.circle_list_header, container, false);
        initView(view,header);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        user=MyApplication.getInstance().getUser();
        if( user ==null){
            mName.setVisibility(View.GONE);
            mUserPicture.setImageDrawable(getResources().getDrawable(R.drawable.user_head));
            mUserCentra.setVisibility(View.GONE);
        }else{
            Glide.with(getActivity()).load(user.getHead_img_url()).centerCrop().into(mUserPicture);
            mUserCentra.setVisibility(View.VISIBLE);
            mName.setVisibility(View.VISIBLE);
            if (user.getNickname()!=null){
                mName.setText(user.getNickname());
            }else {
                mName.setText(user.getAccount());
            }

        }

        initData();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.circle_order:
                startActivity(new Intent(getActivity(), OrderActivity.class));
                break;
            case R.id.circle_rent:
                startActivity(new Intent(getActivity(), MineBookActivity.class));
                break;
            case R.id.circle_user_centra:
                startActivity(new Intent(getActivity(), UserCenterActivity.class));
                break;
            case R.id.circle_message:
                if (circle_message.isShowBadge()){
                    circle_message.hiddenBadge();
                }
                startActivity(new Intent(getActivity(), AttentionActivity.class));
                break;
            case R.id.write_mood:
                startActivity(new Intent(getActivity(), SendMoodActivity.class));
                break;
            case R.id.circle_picture:
                if(user!=null){
                    startActivity(UserDetailActivity.getInstance(getActivity(),user.getYunsu_id()));
                }else {
                   startActivity(new Intent(getActivity(),LoginActivity.class));
                }

        }
    }
    /*
    * 获取新消息提醒
    * */
    private void getConversationMessage(){
        //获取新消息通知
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                circle_message.showTextBadge(""+list.size());//设置消息红点
                return false;
            }
        });

    }

    private void initPleaseLogin( View view) {
        View back=view.findViewById(R.id.back);
        back.setVisibility(View.INVISIBLE);
        TextView title= (TextView) view.findViewById(R.id.title);
        title.setText("我的");
        View login=view.findViewById(R.id.please_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        });
    }

    private void initView(View view,View header) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCricleList= (XRecyclerView) view.findViewById(R.id.circle_list);
        mName= (TextView) header.findViewById(R.id.circle_name);
        mOrder= (TextView) header.findViewById(R.id.circle_order);
        mRent= (TextView) header.findViewById(R.id.circle_rent);
        mUserCentra= (TextView) header.findViewById(R.id.circle_user_centra);
        circle_message= (BGABadgeTextView) header.findViewById(R.id.circle_message);
        mWirteMood= (ImageView) view.findViewById(R.id.write_mood);
        mUserPicture= (ImageView) header.findViewById(R.id.circle_picture);
        edittextbody = (LinearLayout)view.findViewById(R.id.editTextBodyLl);
        editText = (EditText)view.findViewById(R.id.circleEt);
        sendIv = (ImageView)view.findViewById(R.id.sendIv);
        mEmpty= (TextView) view.findViewById(R.id.mood_choose_house);

        View back=view.findViewById(R.id.back);
        back.setVisibility(View.INVISIBLE);
        TextView title= (TextView) view.findViewById(R.id.title);
        title.setText("我的");
        if(user!=null){
            Glide.with(getActivity()).load(user.getHead_img_url()).centerCrop().into(mUserPicture);//加载头像
            if (user.getNickname()!=null){
                mName.setText(user.getNickname());
            }else {
                mName.setText(user.getAccount());
            }
        }
        mUserPicture.setOnClickListener(this);
        mOrder.setOnClickListener(this);
        mRent.setOnClickListener(this);
        mUserCentra.setOnClickListener(this);
        circle_message.setOnClickListener(this);
        mWirteMood.setOnClickListener(this);
        mCricleList.setLayoutManager(linearLayoutManager);
        mCricleList.addHeaderView(header);
        //禁用下拉加载
        mCricleList.setLoadingMoreEnabled(false);
        //设置下拉刷新和下拉加载的监听
        mCricleList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
                mAdapter.updateData(jsonArray);
                mCricleList.refreshComplete();
            }
            @Override
            public void onLoadMore() {
            }
        });
        //获取新信息提醒
        getConversationMessage();
    }

    private void initList() {
        isFrist=false;
        mAdapter=new CircleCommentAdapter(jsonArray, getActivity(), customProgress,edittextbody,editText,sendIv);
        mCricleList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (edittextbody.getVisibility() == View.VISIBLE) {
                    edittextbody.setVisibility(View.GONE);
                    ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
        mCricleList.setAdapter(mAdapter);
    }

    //修改个人资料后更新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateProfile(UserData userData){
        mName.setText(userData.getNickname());
    }

    private void initData() {
        OkHttpUtils.get()
                .url(Api.CIRCLE_DYNAMIC)
                .build()
                .execute(new JsonCallback<PageData<JSONObject>>(getActivity(),true) {

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

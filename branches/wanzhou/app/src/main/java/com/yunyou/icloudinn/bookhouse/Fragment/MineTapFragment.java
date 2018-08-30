package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yunyou.icloudinn.bookhouse.Activity.AttentionActivity;
import com.yunyou.icloudinn.bookhouse.Activity.LoginActivity;
import com.yunyou.icloudinn.bookhouse.Activity.MineBookActivity;
import com.yunyou.icloudinn.bookhouse.Activity.OrderActivity;
import com.yunyou.icloudinn.bookhouse.Activity.SendMoodActivity;
import com.yunyou.icloudinn.bookhouse.Activity.UserCentraActivity;
import com.yunyou.icloudinn.bookhouse.Activity.UserDetailActivity;
import com.yunyou.icloudinn.bookhouse.Adapter.CircleCommentAdapter;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class MineTapFragment extends Fragment implements View.OnClickListener {

    private String TAG = "MineTapFragment.java";
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_mine_main, container, false);
            View header = inflater.inflate(R.layout.circle_list_header, container, false);
            customProgress = new CustomProgress(getActivity(),R.style.Custom_Progress,"加载中",false);
            initView(view,header);
            return view;
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
                startActivity(new Intent(getActivity(), OrderActivity.class));
                break;
            case R.id.circle_rent:
                startActivity(new Intent(getActivity(), MineBookActivity.class));
                break;
            case R.id.circle_user_centra:
                startActivity(new Intent(getActivity(), UserCentraActivity.class));
                break;
            case R.id.circle_message:
                startActivity(new Intent(getActivity(), AttentionActivity.class));
                break;
            case R.id.write_mood:
                startActivity(new Intent(getActivity(), SendMoodActivity.class));
                break;
            case R.id.circle_picture:
                int userId = 0;
                try {
                    userId = MyApplication.getInstance().getUser().getData().getYunsu_id();
                }catch (Exception e){
                }
                startActivity(UserDetailActivity.getInstance(getActivity(),userId));
        }
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
        message= (TextView) header.findViewById(R.id.circle_message);
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
        mUserPicture.setOnClickListener(this);
        mOrder.setOnClickListener(this);
        mRent.setOnClickListener(this);
        mUserCentra.setOnClickListener(this);
        message.setOnClickListener(this);
        mWirteMood.setOnClickListener(this);
        mCricleList.setLayoutManager(linearLayoutManager);
        mCricleList.addHeaderView(header);
    }

    private void initUser() {
        boolean notNull=MyApplication.getInstance().getUser().getData().getHead_img_url()!=null&&!(MyApplication.getInstance().getUser().getData().getHead_img_url().isEmpty());
        try {
            if (notNull){
                Glide.with(getActivity())
                        .load(MyApplication.getInstance().getUser().getData().getHead_img_url())
                        .centerCrop()
                        .into(mUserPicture);
            }
        }catch (Exception e){
            Log.w(TAG,e.toString());
        }

        boolean isUserNameNull=MyApplication.getInstance().getUser().getData().getNickname()!=null&&!(MyApplication.getInstance().getUser().getData().getNickname().isEmpty());
        if (isUserNameNull){
            mName.setText(MyApplication.getInstance().getUser().getData().getNickname());
        }else {
            mName.setText(MyApplication.getInstance().getUser().getData().getAccount());
        }
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

    private void initData() {
        customProgress.show();
        OkHttpUtils
                .get()
                .url(Api.CIRCLE_DYNAMIC)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        customProgress.dismiss();
                        ToastUtils.showLongToast(""+e);
                        LogUtils.i("错误"+e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        customProgress.dismiss();
                        try {
                            JSONObject json = JSON.parseObject(response);
                            if (json.getInteger("code") == 100) {
                                if (json.getJSONObject("data").getJSONArray("data").size()!=0){
                                    if (jsonArray.size()==0){
                                        jsonArray.addAll(json.getJSONObject("data").getJSONArray("data"));
                                    }else {
                                        jsonArray.clear();
                                        jsonArray.addAll(json.getJSONObject("data").getJSONArray("data"));
                                    }
                                    if (isFrist){
                                        initList();
                                    }else {
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }else {
                                    if (jsonArray.size()==0){
                                        jsonArray.addAll(json.getJSONObject("data").getJSONArray("data"));
                                    }else {
                                        jsonArray.clear();
                                        jsonArray.addAll(json.getJSONObject("data").getJSONArray("data"));
                                    }
                                    if (isFrist){
                                        initList();
                                    }else {
                                        mAdapter.notifyDataSetChanged();
                                    }
                                    mEmpty.setVisibility(View.VISIBLE);
                                }

                            } else {
                                ToastUtils.showLongToast( json.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtils.showLongToast("网络错误" + e);
                            LogUtils.i("错误"+e);
                        }
                    }
                });

    }

}

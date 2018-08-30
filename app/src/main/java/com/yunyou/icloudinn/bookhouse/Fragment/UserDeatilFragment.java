package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Activity.ConversationActivity;
import com.yunyou.icloudinn.bookhouse.Activity.HerBookActivity;
import com.yunyou.icloudinn.bookhouse.Activity.MyDynamicActivity;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.IMLoginUtils;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class UserDeatilFragment extends BaseFragment implements View.OnClickListener {//用户的：详情

    private UserData user;
    private ImageView mUserImage;
    private TextView mUsername, mUserRent, mUserDonate, mUserLive, mAttention;
    private Button user_detail_send_btn;
    private boolean isAttention;
    private Handler handler = new Handler();

    public static UserDeatilFragment getInstance(UserData user) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        bundle.putString("key", "user");
        UserDeatilFragment userDeatilFragment = new UserDeatilFragment();
        userDeatilFragment.setArguments(bundle);
        return userDeatilFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        user = (UserData) bundle.getSerializable("user");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initMyView(view);
        initUser();
    }


    private void initMyView(View view) {
        StatusBarCompat.compat(getActivity(), getResources().getColor(R.color.green1));
        View mBack = view.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        });
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText("Ta的详情");
        View picture = view.findViewById(R.id.user_detail_picture_layout);
        View rentBook = view.findViewById(R.id.user_detail_rent_layout);
        View donateBook = view.findViewById(R.id.user_detail_donate_layout);
        mUserImage = (ImageView) view.findViewById(R.id.user_detail_user_picutre);
        mUsername = (TextView) view.findViewById(R.id.user_detail_user_name);
        mUserDonate = (TextView) view.findViewById(R.id.user_detail_user_donate);
        mUserRent = (TextView) view.findViewById(R.id.user_detail_user_rent);
        mUserLive = (TextView) view.findViewById(R.id.user_detail_user_live_num);
        mAttention = (TextView) view.findViewById(R.id.user_detail_attention_user);
        user_detail_send_btn = (Button) view.findViewById(R.id.user_detail_send_btn);
        user_detail_send_btn.setOnClickListener(this);
        rentBook.setOnClickListener(this);
        donateBook.setOnClickListener(this);
        mAttention.setOnClickListener(this);
    }

    private void initUser() {
        if (user != null) {
            boolean isnull = !(user.getNickname() != null && user.getNickname().isEmpty());
            Glide.with(getHoldingActivity())
                    .load(user.getHead_img_url())
                    .centerCrop()
                    .placeholder(R.drawable.head_img)
                    .into(mUserImage);
            if (isnull) {
                mUsername.setText(user.getNickname());
            } else {
                mUsername.setText(user.getAccount());
            }
            mUserDonate.setText("捐" + user.getCheck_in_hotel_num() + "本");
            mUserRent.setText("租" + user.getRent_book_num() + "本");
            mUserLive.setText("住进" + user.getDonate_book_num() + "家民宿");
            if (user.getIs_concern() == 0) {
                mAttention.setText("关注");
                mAttention.setTextColor(getHoldingActivity().getResources().getColor(R.color.green1));
            } else {
                mAttention.setText("取消关注");
                mAttention.setTextColor(getHoldingActivity().getResources().getColor(R.color.grey));
            }
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_detail;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_detail_rent_layout:
                Intent intent = new Intent(getActivity(), HerBookActivity.class);
                if (user != null) {
                    intent.putExtra("userid", user.getYunsu_id());
                } else {
                    intent.putExtra("userid", user.getYunsu_id());
                }
                startActivity(intent);
                break;
            case R.id.user_detail_donate_layout:
                Intent intent1 = new Intent(getActivity(), HerBookActivity.class);
                if (user != null) {
                    intent1.putExtra("userid", user.getYunsu_id());
                } else {
                    intent1.putExtra("userid", user.getYunsu_id());
                }
                startActivity(intent1);
                break;
            case R.id.user_detail_picture_layout:
                startActivity(new Intent(getActivity(), MyDynamicActivity.class));
                break;
            case R.id.user_detail_attention_user:
                doCollect();
                break;
            case R.id.user_detail_send_btn:
                if (MyApplication.getInstance().isLogin()){
                    IMLoginUtils.initConversation(getHoldingActivity(),MyApplication.getInstance().getUser(),handler);
                    Intent conversationIntent = new Intent(getActivity(), ConversationActivity.class);
                    conversationIntent.putExtra("user",user);
                    startActivity(conversationIntent);

                }else {
                    ToastUtils.showShortToast("还未登录");
                }

                break;
        }
    }

    private void doCollect() {
        OkHttpUtils.get()
                .url(Api.ATTENTION + user.getYunsu_id())
                .build().execute(new StringCallback() {
                         @Override
                         public void onError(Call call, Exception e, int id) {
                         }

                         @Override
                         public void onResponse(String response, int id) {
                             JSONObject jsonObject = JSON.parseObject(response);
                             if (jsonObject.getInteger("code")==100){
                                 if (jsonObject.get("msg").equals("关注成功！")){
                                     isAttention = false;
                                     LogUtils.d("isAttention",jsonObject.get("msg"));
                                 }else {
                                     isAttention = true;
                                 }
                             }
                             //到主线程去更新UI
                          getActivity().runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  if (isAttention==false) {
                                      mAttention.setText("取消关注");
                                      mAttention.setTextColor(getHoldingActivity().getResources().getColor(R.color.grey));
                                  } else {
                                      mAttention.setText("关注");
                                      mAttention.setTextColor(getHoldingActivity().getResources().getColor(R.color.green1));
                                  }
                              }
                          });
                         }
        });
    }

}

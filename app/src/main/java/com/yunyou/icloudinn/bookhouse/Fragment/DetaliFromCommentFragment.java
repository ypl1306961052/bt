package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Activity.ConversationActivity;
import com.yunyou.icloudinn.bookhouse.Activity.HerBookActivity;
import com.yunyou.icloudinn.bookhouse.Activity.MyDynamicActivity;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.IMLoginUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;

public class DetaliFromCommentFragment extends BaseFragment implements View.OnClickListener {
    private ImageView mUserImage;
    private Button sendMsgBtn;
    private TextView mUsername,mUserRent,mUserDonate,mUserLive,mAttention;
    private boolean isAttention=false;
    private CustomProgress customProgress;
    private int id;
    private UserData user;
    private Handler handler = new Handler();

    public static DetaliFromCommentFragment getInstance(int id){
        DetaliFromCommentFragment detaliFromCommentFragment=new DetaliFromCommentFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        detaliFromCommentFragment.setArguments(bundle);
        return detaliFromCommentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id=getArguments().getInt("id");
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==2){
                    ToastUtils.showShortToast("登录成功");
                }
            }
        };
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initMyView(view);
        initData();

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        ToastUtils.showLongToast("登录返回");
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        if(!MyApplication.getInstance().isLogin()){
            getActivity().finish();
        }
    }

    private void initData() {
        OkHttpUtils.get()
                .url(Api.USER_DETAIL+id)
                .build()
                .execute(new JsonCallback<UserData>(getActivity(),true) {
                    @Override
                    public void onResponse(UserData data, int id) {
                        if(data!=null){
                            user = data;
                            initUser();
                        }
                    }
                });
    }

    private void initMyView(View view) {
        StatusBarCompat.compat(getActivity(), getResources().getColor(R.color.green1));
        customProgress=new CustomProgress(getActivity(), R.style.Custom_Progress,"加载中",false);
        View mBack=view.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        });
        TextView title= (TextView) view.findViewById(R.id.title);
        title.setText("Ta的详情");
        View rentBook=view.findViewById(R.id.user_detail_rent_layout);
        View donateBook=view.findViewById(R.id.user_detail_donate_layout);
        mUserImage= (ImageView) view.findViewById(R.id.user_detail_user_picutre);
        mUsername= (TextView) view.findViewById(R.id.user_detail_user_name);
        mUserDonate= (TextView) view.findViewById(R.id.user_detail_user_donate);
        mUserRent= (TextView) view.findViewById(R.id.user_detail_user_rent);
        mUserLive= (TextView) view.findViewById(R.id.user_detail_user_live_num);
        mAttention= (TextView) view.findViewById(R.id.user_detail_attention_user);
        sendMsgBtn = (Button) view.findViewById(R.id.user_detail_send_btn);
        sendMsgBtn.setOnClickListener(this);
        if (!MyApplication.getInstance().isLogin()){
            mAttention.setVisibility(View.GONE);
        }else if(MyApplication.getInstance().getUser().getYunsu_id()==id){
            mAttention.setVisibility(View.GONE);
            ((Button) view.findViewById(R.id.user_detail_send_btn)).setText("修改个人信息");
            title.setText("我的详情");
        }
        rentBook.setOnClickListener(this);
        donateBook.setOnClickListener(this);
        mAttention.setOnClickListener(this);
        view.findViewById(R.id.user_detail_picture_layout).setOnClickListener(this);
    }

    private void initUser() {
            boolean isnull=!(user.getNickname()!=null&&user.getNickname().isEmpty());
            Glide.with(getHoldingActivity())
                    .load(user.getHead_img_url())
                    .centerCrop()
                    .into(mUserImage);
            if (isnull){
                mUsername.setText(user.getNickname());
            }else {
                mUsername.setText(user.getYunsu_id()+"");
            }
            mUserDonate.setText("捐"+user.getDonate_book_num()+"本");
            mUserRent.setText("租"+user.getRent_book_num()+"本");
            mUserLive.setText("住进"+user.getCheck_in_hotel_num()+"家民宿");
            if (user.getIs_concern()==1){
                mAttention.setText("取消关注");
                mAttention.setTextColor(getHoldingActivity().getResources().getColor(R.color.grey2));
                isAttention=true;
            }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_detail;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_detail_rent_layout:
                Intent intent=new Intent(getActivity(), HerBookActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
            case R.id.user_detail_donate_layout:
                Intent intent1=new Intent(getActivity(), HerBookActivity.class);
                intent1.putExtra("id",id);
                startActivity(intent1);
                break;
            case R.id.user_detail_attention_user:
                doCollect();
                break;
            case R.id.user_detail_send_btn:
                if(MyApplication.getInstance().getUser().getYunsu_id()==id){
                    addNewFragment(ModifyUserProfileFragment.getInstance(user));
                }else {
                    if (MyApplication.getInstance().isLogin()){
                        IMLoginUtils.initConversation(getHoldingActivity(),MyApplication.getInstance().getUser(),handler);
                        Intent conversationIntent = new Intent(getActivity(), ConversationActivity.class);
                        conversationIntent.putExtra("user",user);
                        startActivity(conversationIntent);
                    }else {
                        ToastUtils.showShortToast("还未登录");
                    }
                }
                break;
            case R.id.user_detail_picture_layout:
                Intent intent2 = new Intent(getActivity(), MyDynamicActivity.class);
                intent2.putExtra("user_id",user.getYunsu_id()+"");
                startActivity(intent2);
                break;
        }
    }

    private void doCollect() {
        OkHttpUtils.get()
                .url(Api.ATTENTION +user.getYunsu_id())
                .build()
                .execute(new JsonCallback<String>(getActivity(),true) {
                    @Override
                    public void onResponse(String data, int id) {
                        if (isAttention){
                            isAttention=false;
                            mAttention.setText("取消关注");
                            mAttention.setTextColor(getHoldingActivity().getResources().getColor(R.color.grey2));
                            user.setIs_concern(0);
                        }else {
                            isAttention=true;
                            mAttention.setText("关注");
                            mAttention.setTextColor(getHoldingActivity().getResources().getColor(R.color.green1));
                            user.setIs_concern(1);
                        }
                    }
                });
    }

}

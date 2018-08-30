package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Activity.ConversationActivity;
import com.yunyou.icloudinn.bookhouse.Activity.HerBookActivity;
import com.yunyou.icloudinn.bookhouse.Callback.HttpCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.AttentionData;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserListData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.HttpUtil;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.zhy.adapter.recyclerview.base.ViewHolder;

public class UserDeatilFragment extends BaseFragment implements View.OnClickListener {
    private AttentionData.DataBeanX.DataBean.UserBean attentionUser;
    private UserListData.DataBeanX.DataBean listUser;
    private ImageView mUserImage;
    private TextView mUsername,mUserRent,mUserDonate,mUserLive,mAttention;
    private Button user_detail_send_btn;
    private boolean isAttention;
    private CustomProgress customProgress;
    private String type;

    public static UserDeatilFragment getInstance(AttentionData.DataBeanX.DataBean.UserBean userBean){
        Bundle bundle=new Bundle();
        bundle.putSerializable("attentionUserInformation",userBean);
        bundle.putString("key","attentionUserInformation");
        UserDeatilFragment userDeatilFragment=new UserDeatilFragment();
        userDeatilFragment.setArguments(bundle);
        return userDeatilFragment;
    }

    public static UserDeatilFragment getInstance(UserListData.DataBeanX.DataBean userBean){
        Bundle bundle=new Bundle();
        bundle.putSerializable("listUserInformation",userBean);
        bundle.putString("key","listUserInformation");
        UserDeatilFragment userDeatilFragment=new UserDeatilFragment();
        userDeatilFragment.setArguments(bundle);
        return userDeatilFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle=getArguments();
        type=bundle.getString("key");
        switch (type){
            case"attentionUserInformation" :
                attentionUser= (AttentionData.DataBeanX.DataBean.UserBean) bundle.getSerializable("attentionUserInformation");
                isAttention=true;
                break;
            case "listUserInformation":
                listUser= (UserListData.DataBeanX.DataBean) bundle.getSerializable("listUserInformation");
                break;
            default:
                break;
        }

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initMyView(view);
        initUser();
    }



    private void initMyView(View view) {
        StatusBarCompat.compat(getActivity(), getResources().getColor(R.color.green1));
        customProgress=new CustomProgress(getActivity(),R.style.Custom_Progress,"加载中",false);
        View mBack=view.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        });
        TextView title= (TextView) view.findViewById(R.id.title);
        title.setText("Ta的详情");
        View picture = view.findViewById(R.id.user_detail_picture_layout);
        View rentBook=view.findViewById(R.id.user_detail_rent_layout);
        View donateBook=view.findViewById(R.id.user_detail_donate_layout);
        mUserImage= (ImageView) view.findViewById(R.id.user_detail_user_picutre);
        mUsername= (TextView) view.findViewById(R.id.user_detail_user_name);
        mUserDonate= (TextView) view.findViewById(R.id.user_detail_user_donate);
        mUserRent= (TextView) view.findViewById(R.id.user_detail_user_rent);
        mUserLive= (TextView) view.findViewById(R.id.user_detail_user_live_num);
        mAttention= (TextView) view.findViewById(R.id.user_detail_attention_user);
        user_detail_send_btn = (Button) view.findViewById(R.id.user_detail_send_btn);
        user_detail_send_btn.setOnClickListener(this);
        rentBook.setOnClickListener(this);
        donateBook.setOnClickListener(this);
        mAttention.setOnClickListener(this);
    }

    private void initUser() {
            if (attentionUser!=null){
                boolean isnull=!(attentionUser.getNickname()!=null&&attentionUser.getNickname().isEmpty());
                Glide.with(getHoldingActivity())
                        .load(attentionUser.getHead_img_url())
                        .centerCrop()
                        .placeholder(R.drawable.head_img)
                        .into(mUserImage);
                if (isnull){
                    mUsername.setText(attentionUser.getNickname());
                    LogUtils.d("initUser",""+attentionUser.getNickname());
                    LogUtils.d("initUser",""+attentionUser.getAccount());
                }else {
                    mUsername.setText(attentionUser.getAccount());
                }
                mUserDonate.setText("捐"+attentionUser.getDonate_num()+"本");
                mUserRent.setText("租"+attentionUser.getDonate_num()+"本");
                mUserLive.setText("住进"+attentionUser.getDonate_num()+"家民宿");
                mAttention.setText("取消关注");
                mAttention.setTextColor(getHoldingActivity().getResources().getColor(R.color.grey2));
            }else {
                boolean isnull=!(listUser.getNickname()!=null&&listUser.getNickname().isEmpty());
                Glide.with(getHoldingActivity())
                        .load(listUser.getHead_img_url())
                        .centerCrop()
                        .placeholder(R.drawable.head_img)
                        .into(mUserImage);
                if (isnull){
                    mUsername.setText(listUser.getNickname());
                }else {
                    mUsername.setText(listUser.getAccount());
                }
                mUserDonate.setText("捐"+listUser.getCheck_in_hotel_num()+"本");
                mUserRent.setText("租"+listUser.getRent_book_num()+"本");
                mUserLive.setText("住进"+listUser.getDonate_book_num()+"家民宿");
                if (listUser.getIs_concern()==0){
                    isAttention=false;
                }else {
                    isAttention=true;
                    mAttention.setText("取消关注");
                    mAttention.setTextColor(getHoldingActivity().getResources().getColor(R.color.grey2));
                }
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
                if (attentionUser!=null){
                    intent.putExtra("userid",attentionUser.getYunsu_id());
                }else {
                    intent.putExtra("userid",listUser.getYunsu_id());
                }
                startActivity(intent);
                break;
            case R.id.user_detail_donate_layout:
                Intent intent1=new Intent(getActivity(), HerBookActivity.class);
                if (attentionUser!=null){
                    intent1.putExtra("userid",attentionUser.getYunsu_id());
                }else {
                    intent1.putExtra("userid",listUser.getYunsu_id());
                }
                startActivity(intent1);
                break;
            // TODO: 2017/10/11 相册页面跳转待实现
            case R.id.user_detail_picture_layout :
                //startActivity(new Intent(getActivity(),));
                break;
            case R.id.user_detail_attention_user:
                doCollect();
               break;
            case R.id.user_detail_send_btn:
                startActivity(new Intent(getActivity(), ConversationActivity.class));
                break;
        }
    }

    private void doCollect() {
        customProgress.show();
        HttpUtil.okHttpGet(true,Api.ATTENTION +(attentionUser!=null?attentionUser.getYunsu_id():listUser.getYunsu_id()), new HttpCallback() {
            @Override
            public void onFail() {
                customProgress.dismiss();

            }

            @Override
            public void onSuccess(String Data) {
                customProgress.dismiss();
                if (isAttention){
                    isAttention=false;
                    mAttention.setText("取消关注");
                    mAttention.setTextColor(getHoldingActivity().getResources().getColor(R.color.grey2));
                    if (attentionUser!=null){

                    }else {
                        listUser.setIs_concern(0);
                    }
                }else {
                    isAttention=true;
                    mAttention.setText("关注");
                    mAttention.setTextColor(getHoldingActivity().getResources().getColor(R.color.green1));
                    if (attentionUser!=null){

                    }else {
                        listUser.setIs_concern(1);
                    }
                }
            }
        });
    }

}

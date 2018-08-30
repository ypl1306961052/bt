package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Activity.CollectActivity;
import com.yunyou.icloudinn.bookhouse.Activity.MineBookActivity;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;

public class UserCentraFragment extends BaseFragment implements View.OnClickListener {
    private ImageView mUserImage;
    private TextView mName,mPoint;
    public static UserCentraFragment getInstance(){
        return new UserCentraFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        StatusBarCompat.compat(getActivity(), getResources().getColor(R.color.green1));
        initMyView(view);
        initUser();
        initData();
    }

    private void initMyView(View view) {
        View mBack=view.findViewById(R.id.back);
        TextView title= (TextView) view.findViewById(R.id.title);
        mUserImage= (ImageView) view.findViewById(R.id.user_centra_mian);
        mName= (TextView) view.findViewById(R.id.user_centra_name);
        mPoint=(TextView) view.findViewById(R.id.user_point);
        View order=view.findViewById(R.id.user_centra_mian_oder);
        View rent=view.findViewById(R.id.user_centra_mian_rent);
        View donate=view.findViewById(R.id.user_centra_mian_donate);
        View collect=view.findViewById(R.id.user_centra_mian_collect);
        View logout=view.findViewById(R.id.logout);
        title.setText("个人中心");
        order.setOnClickListener(this);
        rent.setOnClickListener(this);
        donate.setOnClickListener(this);
        collect.setOnClickListener(this);
        mBack.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    private void initUser() {
        UserData user = MyApplication.getInstance().getUser();
        if(user==null)return;
        boolean notNull=user.getData().getHead_img_url()!=null&&!(user.getData().getHead_img_url().isEmpty());
        if (notNull){
            Glide.with(getActivity())
                    .load(MyApplication.getInstance().getUser().getData().getHead_img_url())
                    .centerCrop()
                    .into(mUserImage);
        }
        boolean isUserNameNull=MyApplication.getInstance().getUser().getData().getNickname()!=null&&!(MyApplication.getInstance().getUser().getData().getNickname().isEmpty());
        if (isUserNameNull){
            mName.setText(MyApplication.getInstance().getUser().getData().getNickname());
        }else {
            mName.setText(MyApplication.getInstance().getUser().getData().getAccount());
        }
        mPoint.setText(String.valueOf(MyApplication.getInstance().getUser().getData().getPoint()));
    }

    private void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_centra_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                removeFragment();
                break;
            case R.id.user_centra_mian_oder:
                addNewFragment(MineHouseOrderFragment.getInstance());
                break;
            case R.id.user_centra_mian_donate:
                startActivity(new Intent(getHoldingActivity(),MineBookActivity.class));
                break;
            case R.id.user_centra_mian_rent:
                startActivity(new Intent(getHoldingActivity(),MineBookActivity.class));
                break;
            case R.id.user_centra_mian_collect:
                startActivity(new Intent(getHoldingActivity(),CollectActivity.class));
                break;
            case R.id.logout:
                MyApplication.getInstance().logout();
                ToastUtils.showLongToast("退出登录成功");
                 new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                            getActivity().finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


        }
    }
}

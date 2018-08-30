package com.yunyou.icloudinn.bookhouse.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomPopupWindow;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModifyUserProfileFragment extends BaseFragment {//


    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.imageView_head_img)
    ImageView imageViewHeadImg;
    @BindView(R.id.Arrow_head)
    ImageView ArrowHead;
    @BindView(R.id.modify_userProfile_head_layout)
    RelativeLayout modifyUserProfileHeadLayout;
    @BindView(R.id.textView_nickName)
    EditText textViewNickName;
    @BindView(R.id.Arrow_nick_name)
    ImageView ArrowNickName;
    @BindView(R.id.modify_userProfile_nickName_layout)
    RelativeLayout modifyUserProfileNickNameLayout;
    @BindView(R.id.textView_num)
    TextView textViewNum;
    @BindView(R.id.Arrow_num)
    ImageView ArrowNum;
    @BindView(R.id.modify_userProfile_num_layout)
    RelativeLayout modifyUserProfileNumLayout;
    @BindView(R.id.textView_qrCode)
    TextView textViewQrCode;
    @BindView(R.id.Arrow_qrCode)
    ImageView ArrowQrCode;
    @BindView(R.id.modify_userProfile_arCode_layout)
    RelativeLayout modifyUserProfileArCodeLayout;
    @BindView(R.id.textView_sex)
    TextView textViewSex;
    @BindView(R.id.Arrow_sex)
    ImageView ArrowSex;
    @BindView(R.id.modify_userProfile_sex_layout)
    RelativeLayout modifyUserProfileSexLayout;
    @BindView(R.id.textView_birthday)
    EditText textViewBirthday;
    @BindView(R.id.Arrow_birthday)
    ImageView ArrowBirthday;
    @BindView(R.id.modify_userProfile_birthday_layout)
    RelativeLayout modifyUserProfileBirthdayLayout;
    @BindView(R.id.textView_location)
    TextView textViewLocation;
    @BindView(R.id.Arrow_location)
    ImageView ArrowLocation;
    @BindView(R.id.modify_userProfile_location_layout)
    RelativeLayout modifyUserProfileLocationLayout;
    Unbinder unbinder;
    @BindView(R.id.modify_done)
    Button modifyDone;
    private UserData user;
    private CustomPopupWindow popupWindow;
    private View parentView;
    private View.OnClickListener onClickListener;
    private String sex;


    public static ModifyUserProfileFragment getInstance(UserData user) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("modifyUser", user);
        ModifyUserProfileFragment modifyUserProfileFragment = new ModifyUserProfileFragment();
        modifyUserProfileFragment.setArguments(bundle);
        return modifyUserProfileFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (UserData) getArguments().getSerializable("modifyUser");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //绑定控件
        unbinder = ButterKnife.bind(this, view);
        //注册EventBus
        EventBus.getDefault().register(this);
        this.parentView = view;
        title.setText("我的资料");
        if (user == null) {
            return;
        }
        //加载头像
        Glide.with(getActivity()).load(user.getHead_img_url())
                .placeholder(R.drawable.head_img)
                .into(imageViewHeadImg);
        ;//显示性别
        if (user.getSex()==null ){
            textViewSex.setText("");
        }else if (user.getSex().toString().equals("1")){
            textViewSex.setText("男");
        }else if (user.getSex().toString().equals("2")){
            textViewSex.setText("女");
        }
        textViewNickName.setText(user.getNickname()==null ? "无昵称" : user.getNickname().toString());//显示昵称
        textViewBirthday.setText(user.getAge()==null ? "未设置" : user.getAge().toString());//显示年龄
        textViewLocation.setText(user.getCity()==null ? "未设置" : user.getCity().toString());//显示地区

    }

    //初始化PopupWindow
    private void initPopupWindow(View view) {
        popupWindow = new CustomPopupWindow(getActivity(), onClickListener);
        popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    //提交修改资料
    private void initData() {
        HashMap<String, String> profileMap = new HashMap<>();
        profileMap.put("sex", textViewSex.getText().toString()=="男" ? "1" : "2");
        profileMap.put("age",textViewBirthday.getText().toString());
        profileMap.put("nickname", textViewNickName.getText().toString());
        OkHttpUtils.post().url(Api.USER_UPDATE)
                .params(profileMap)
                .build()
                .execute(new JsonCallback(getHoldingActivity(), true) {
                    @Override
                    public void onResponse(Object response, int id) {
                        ToastUtils.showShortToast("修改成功");

                    }
                });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_modify_user_profile;
    }

    @OnClick({R.id.back,R.id.modify_done, R.id.imageView_head_img, R.id.modify_userProfile_head_layout, R.id.modify_userProfile_nickName_layout, R.id.modify_userProfile_num_layout, R.id.modify_userProfile_arCode_layout, R.id.modify_userProfile_sex_layout, R.id.modify_userProfile_birthday_layout, R.id.modify_userProfile_location_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_head_img:
                break;
            case R.id.modify_userProfile_head_layout:
                break;
            case R.id.modify_userProfile_nickName_layout:
                break;
            case R.id.modify_userProfile_num_layout:
                break;
            case R.id.modify_userProfile_arCode_layout:
                break;
            case R.id.modify_userProfile_sex_layout:
                initPopupWindow(parentView);
                break;
            case R.id.modify_userProfile_birthday_layout:
                break;
            case R.id.modify_userProfile_location_layout:
                break;
            case R.id.modify_done:
                initData();
                user.setNickname(textViewNickName.getText().toString());
                EventBus.getDefault().post(user);
                //保存修改资料到SharedPreferences
                getHoldingActivity().onBackPressed();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void modifyProfile(UserData userData) {
        sex = userData.getSex();
        if (sex=="1"){
            textViewSex.setText("男");
        }else if (sex=="2"){
            textViewSex.setText("女");
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

}

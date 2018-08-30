package com.yunyou.icloudinn.bookhouse.Ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by function on 2017/10/20.
 */

public class CustomPopupWindow extends PopupWindow {
    @BindView(R.id.radio_man)
    RadioButton radioMan;
    @BindView(R.id.radio_woman)
    RadioButton radioWoman;
    private View mView;
    Unbinder unbinder;
    private String sex;
    private UserData userData = new UserData();

    public CustomPopupWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.popup_window, null);
        radioMan = (RadioButton) mView.findViewById(R.id.radio_man);
        radioWoman = (RadioButton) mView.findViewById(R.id.radio_woman);
        unbinder = ButterKnife.bind(this,mView);


        //设置PopupWindow的View
        this.setContentView(mView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Animation);
 /*       //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);*/
    }

    @Override
    public void dismiss() {
        super.dismiss();
        unbinder.unbind();
    }

    @OnClick({R.id.radio_man, R.id.radio_woman})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_man:
                sex = "1";
                userData.setSex(sex);
                EventBus.getDefault().post(userData);
                break;
            case R.id.radio_woman:
                sex = "2";
                userData.setSex(sex);
                EventBus.getDefault().post(userData);
                break;
        }
    }
}

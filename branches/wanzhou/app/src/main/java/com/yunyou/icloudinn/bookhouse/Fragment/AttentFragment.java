package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.PageData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;

public class AttentFragment extends BaseFragment implements View.OnClickListener {

    private TextView mAttentionTo,mToAttention;
    public static AttentFragment getInstance(){
        return new AttentFragment();
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

    private void initData() {
        initAttentionMeData();
        initMeAttentionData();
    }

    private void initAttentionMeData() {
        OkHttpUtils.get().tag(getActivity())
                .url(Api.ATTENTION_TO)
                .build()
                .execute(new JsonCallback<PageData<String>>(getActivity(), true) {
                    @Override
                    public void onResponse(PageData<String> page, int id) {
                        if(page!=null) {
                            mAttentionTo.setText("共关注" + page.getData().size() + "位友人");
                        }
                    }
                });

    }

    private void initMeAttentionData(){
        OkHttpUtils.get().tag(getActivity())
                .url(Api.TO_ATTENTION)
                .build()
                .execute(new JsonCallback<PageData<String>>(getActivity(), true) {
                    @Override
                    public void onResponse(PageData<String> page, int id) {
                        if(page!=null){
                            mToAttention.setText("共" + page.getData().size() + "人关注我");
                        }
                    }
                });

    }

    private void initMyView(View view) {
        StatusBarCompat.compat(getActivity(), getResources().getColor(R.color.green1));
        View mBack=view.findViewById(R.id.back);
        TextView title= (TextView) view.findViewById(R.id.title);
        mAttentionTo= (TextView) view.findViewById(R.id.attention_to_num);
        mToAttention= (TextView) view.findViewById(R.id.to_attention_num);
        View mAttentionTo=view.findViewById(R.id.attention_to_layout);
        View mToAttionTo=view.findViewById(R.id.to_attention_layout);
        mBack.setOnClickListener(this);
        mAttentionTo.setOnClickListener(this);
        mToAttionTo.setOnClickListener(this);
        title.setText("我的消息");
    }

    @Override
    public void onResume() {
        super.onResume();
        if(MyApplication.getInstance().isLogin()){
            initData();
        }else {
            getActivity().finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attention;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                removeFragment();
                break;
            case R.id.attention_to_layout:
                addNewFragment(MineAttentionFragment.getInstance(true));
                break;
            case R.id.to_attention_layout:
                addNewFragment(MineAttentionFragment.getInstance(false));
                break;
        }
    }

}

package com.yunyou.icloudinn.bookhouse.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.AttentionData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MineAttentionFragment extends BaseFragment implements View.OnClickListener {
    private boolean isAttentionMe=true;
    private RecyclerView mList;
    private List<AttentionData.DataBeanX.DataBean>mListData=new ArrayList<>();
    private CommonAdapter<AttentionData.DataBeanX.DataBean>mCommonAdapter;
    private CustomProgress customProgress;
    private boolean isFrist=true;
    public static MineAttentionFragment getInstance(boolean attentionMe){
        MineAttentionFragment mineAttentionFragment=new MineAttentionFragment();
        Bundle bundle=new Bundle();
        bundle.putBoolean("attention",attentionMe);
        mineAttentionFragment.setArguments(bundle);
        return mineAttentionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        isAttentionMe=bundle.getBoolean("attention");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initMyView(view);
        initList();
        if (isFrist){
            isFrist=false;
            if (isAttentionMe){
                initAttentionMeData();
            }else {
                initMeAttentionData();
            }
        }


    }


    private void initMyView(View view) {
        customProgress=new CustomProgress(getActivity(),R.style.Custom_Progress,"加载中",false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getHoldingActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList= (RecyclerView) view.findViewById(R.id.mine_attion_user_list);
        View mAddAttention=view.findViewById(R.id.mine_add_attion_layout);
        View mAddMore=view.findViewById(R.id.mine_add_more_layout);
        View mBack=view.findViewById(R.id.back);
        TextView title= (TextView) view.findViewById(R.id.title);
        mAddAttention.setOnClickListener(this);
        mAddMore.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mList.setLayoutManager(linearLayoutManager);
        title.setText("个人关注");
    }
    private void initList() {
        mCommonAdapter=new CommonAdapter<AttentionData.DataBeanX.DataBean>(getHoldingActivity(),R.layout.list_item_mine_attention_user,mListData) {
            @Override
            protected void convert(ViewHolder holder, final AttentionData.DataBeanX.DataBean dataBean, int position) {
                Glide.with(getHoldingActivity())
                        .load(dataBean.getUser().getHead_img_url())
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.item_attention_user_picutre));
                holder.getView(R.id.attention_user_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addNewFragment(UserDeatilFragment.getInstance(dataBean.getUser()));
                    }
                });
                holder.setText(R.id.item_attention_user_name,dataBean.getUser().getNickname()==null?  String.valueOf(dataBean.getUser().getYunsu_id()) :dataBean.getUser().getNickname());
                holder.setText(R.id.item_attention_user_rent,"租"+dataBean.getUser().getRent_num()+"本");
                holder.setText(R.id.item_attention_user_donate,"捐"+dataBean.getUser().getDonate_num()+"本");
                holder.setText(R.id.item_attention_user_live_num,"住进"+dataBean.getUser().getCheck_in_hotel_num()+"家民宿");
            }
        };
        mList.setAdapter(mCommonAdapter);
    }

    private void initAttentionMeData() {
        customProgress.show();
        OkHttpUtils.get()
                .url(Api.ATTENTION_TO)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        customProgress.dismiss();
                        ToastUtils.showLongToast("网络错误" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        customProgress.dismiss();
                        try {
                            JSONObject json = JSON.parseObject(response);
                            if (json.getInteger("code") == 100) {
                                if (mListData.size()==0){
                                    mListData.addAll((JSON.parseObject(response,AttentionData.class)).getData().getData());
                                    mCommonAdapter.notifyDataSetChanged();
                                }else {
                                    mListData.clear();
                                    mListData.addAll((JSON.parseObject(response,AttentionData.class)).getData().getData());
                                    mCommonAdapter.notifyDataSetChanged();
                                }
                            } else {
                                ToastUtils.showLongToast( json.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtils.showLongToast("网络错误" + e);
                        }

                    }
                });

    }

    private void initMeAttentionData(){
        OkHttpUtils.get()
                .url(Api.TO_ATTENTION)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showLongToast("网络错误" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject json = JSON.parseObject(response);
                            if (json.getInteger("code") == 100) {
                                if (mListData.size()==0){
                                    mListData.addAll((JSON.parseObject(response,AttentionData.class)).getData().getData());
                                    mCommonAdapter.notifyDataSetChanged();
                                }else {
                                    mListData.clear();
                                    mListData.addAll((JSON.parseObject(response,AttentionData.class)).getData().getData());
                                    mCommonAdapter.notifyDataSetChanged();
                                }
                            } else {
                                ToastUtils.showLongToast( json.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtils.showLongToast("网络错误" + e);
                        }

                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_attention_list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_add_attion_layout:
                addNewFragment(AddMoreAttentionFragment.getInstance());
                break;
            case R.id.mine_add_more_layout:
                addNewFragment(AddMoreAttentionFragment.getInstance());
                break;
            case R.id.back:
                removeFragment();
                break;
        }
    }
}

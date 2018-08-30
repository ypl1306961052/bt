package com.yunyou.icloudinn.bookhouse.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Callback.HttpCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserListData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Util.HttpUtil;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;

public class AddMoreAttentionFragment extends BaseFragment{
    private RecyclerView mList;
    private List<UserListData.DataBeanX.DataBean> mListData=new ArrayList<>();
    private CommonAdapter<UserListData.DataBeanX.DataBean> mCommonAdapter;
    private CustomProgress customProgress;

    public static AddMoreAttentionFragment getInstance(){
        return new AddMoreAttentionFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initMyView(view);
        initList();
        initData();
    }



    private void initMyView(View view) {
        customProgress=new CustomProgress(getActivity(),R.style.Custom_Progress,"加载中",false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getHoldingActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList= (RecyclerView) view.findViewById(R.id.list);
        mList.setLayoutManager(linearLayoutManager);
        View mBack=view.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        });
        TextView title= (TextView) view.findViewById(R.id.title);
        title.setText("添加更多");
    }

    private void initList() {
        mCommonAdapter=new CommonAdapter<UserListData.DataBeanX.DataBean>(getHoldingActivity(),R.layout.list_item_more_attention,mListData) {
            @Override
            protected void convert(final ViewHolder holder, final UserListData.DataBeanX.DataBean dataBean, final int position) {
                Glide.with(getHoldingActivity())
                        .load(dataBean.getHead_img_url())
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.more_attention_user_picutre));
                holder.getView(R.id.attention_more_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addNewFragment(UserDeatilFragment.getInstance(dataBean));
                    }
                });
                if (mListData.get(position).getIs_concern()==1){
                    ((TextView)holder.getView(R.id.more_attention_attention_user)).setTextColor(getResources().getColor(R.color.grey2));
                    ((TextView)holder.getView(R.id.more_attention_attention_user)).setText("取消关注");
                }
                if (mListData.get(position).getNickname()==null){
                    holder.setText(R.id.more_attention_user_name,mListData.get(position).getAccount());
                }else {
                    holder.setText(R.id.more_attention_user_name,mListData.get(position).getNickname());
                }
                holder.setText(R.id.more_attention_user_rent,String.valueOf("租"+mListData.get(position).getRent_book_num())+"本");
                holder.setText(R.id.more_attention_user_donate,String.valueOf("捐"+mListData.get(position).getDonate_book_num())+"本");
                holder.setText(R.id.more_attention_user_live_num,String.valueOf("住过"+mListData.get(position).getCheck_in_hotel_num())+"间民宿");
                holder.getView(R.id.more_attention_attention_user).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doCollect(holder,position);
                    }
                });
            }
        };
        mList.setAdapter(mCommonAdapter);
    }

    private void doCollect(final ViewHolder holder, final int postion) {
        customProgress.show();
        HttpUtil.okHttpGet(true, Api.ATTENTION +mListData.get(postion).getYunsu_id(), new HttpCallback() {
            @Override
            public void onFail() {
                customProgress.dismiss();

            }

            @Override
            public void onSuccess(String Data) {
                customProgress.dismiss();
                if (mListData.get(postion).getIs_concern()==0){
                    ((TextView)holder.getView(R.id.more_attention_attention_user)).setTextColor(getResources().getColor(R.color.grey2));
                    ((TextView)holder.getView(R.id.more_attention_attention_user)).setText("取消关注");
                     mListData.get(postion).setIs_concern(1);
                     mCommonAdapter.notifyDataSetChanged();
                }else {
                    ((TextView)holder.getView(R.id.more_attention_attention_user)).setTextColor(getResources().getColor(R.color.green1));
                    ((TextView)holder.getView(R.id.more_attention_attention_user)).setText("关注");
                    mListData.get(postion).setIs_concern(0);
                    mCommonAdapter.notifyDataSetChanged();
                }

            }
        });
    }


    private void initData() {
        customProgress.show();
        OkHttpUtils.get()
                .url(Api.USER_LIST)
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
                                    mListData.addAll((JSON.parseObject(response,UserListData.class)).getData().getData());
                                    mCommonAdapter.notifyDataSetChanged();
                                }
                                LogUtils.i("用户参数"+(JSON.parseObject(response,UserListData.class)).getData().getData());
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
        return R.layout.list_layout_with_top;
    }


}

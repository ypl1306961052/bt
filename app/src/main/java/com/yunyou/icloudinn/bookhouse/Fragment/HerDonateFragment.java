package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Activity.BookDetailActivity;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.DonateBookData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Util.MyUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class HerDonateFragment extends Fragment {
    private RecyclerView mList;
    private CommonAdapter<DonateBookData.DataBeanX.DataBean> mCommonAdapter;
    private List<DonateBookData.DataBeanX.DataBean> mListData=new ArrayList<>();
    private CustomProgress customProgress;
    private int mUserId;
    public static HerDonateFragment getInstance(int id){
        Bundle bundle=new Bundle();
        bundle.putInt("userid",id);
        HerDonateFragment herDonateFragment=new HerDonateFragment();
        herDonateFragment.setArguments(bundle);
        return herDonateFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mUserId=getArguments().getInt("userid");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.list_layout,container,false);
        initMyview(view);
        return view;
    }

    private void initMyview(View view) {
        customProgress=new CustomProgress(getActivity(),R.style.Custom_Progress,"加载中",false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList= (RecyclerView) view.findViewById(R.id.list);
        mList.setLayoutManager(linearLayoutManager);
        mCommonAdapter=new CommonAdapter<DonateBookData.DataBeanX.DataBean>(getActivity(),R.layout.list_item_donate_book,mListData) {
            @Override
            protected void convert(ViewHolder holder, final DonateBookData.DataBeanX.DataBean dataBean, int position) {
                Glide.with(getActivity())
                        .load(Api.BASE_URL+dataBean.getCover())
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.item_donate_picture));
                holder.getView(R.id.item_donate_picture).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Intent intent= new Intent(getActivity(), BookDetailActivity.class);
                        intent.putExtra("id",dataBean.getId());
                        startActivity(intent);
                    }
                });
                holder.setText(R.id.item_donate_name,dataBean.getBook_name());
                if (dataBean.getStatus()==1){
                    holder.setText(R.id.item_donate_status,"状态：已审核");
                }else {
                    holder.setText(R.id.item_donate_status,"状态：正在审核");
                }
                try {
                    holder.setText(R.id.item_donate_number_borrow,dataBean.getBook().getRent_num()+"人租过");
                } catch (Exception e) {
                    holder.setText(R.id.item_donate_number_borrow,"0人租过");
                    e.printStackTrace();
                }
                holder.setText(R.id.item_donate_tiem,"租书时间："+ MyUtils.TimeStamp2Date(String.valueOf(dataBean.getDonate_time()),""));
            }
        };
        mList.setAdapter(mCommonAdapter);
    }

    private void initData() {
        customProgress.show();
        OkHttpUtils.get()
                .url(Api.DONATE_BOOK+"?user_id="+mUserId)
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
                                mListData.addAll((JSON.parseObject(response,DonateBookData.class)).getData().getData());
                                mCommonAdapter.notifyDataSetChanged();
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
    public void onResume() {
        if (mListData.size()==0){
            initData();
        }
        super.onResume();
    }
}

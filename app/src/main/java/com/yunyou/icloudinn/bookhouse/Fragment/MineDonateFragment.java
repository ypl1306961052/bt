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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Activity.BookDonateActivity;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.DonateBookData;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.MyUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MineDonateFragment extends Fragment {

    private RecyclerView mList;
    private CommonAdapter<DonateBookData.DataBeanX.DataBean> mCommonAdapter;
    private List<DonateBookData.DataBeanX.DataBean> mListData = new ArrayList<>();
    private CustomProgress customProgress;
    private TextView mEmpty;

    public static MineDonateFragment getInstance() {
        MineDonateFragment mineDonateFragment = new MineDonateFragment();
        return mineDonateFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);
        initMyview(view);
        return view;
    }

    private void initMyview(View view) {
        customProgress = new CustomProgress(getActivity(), R.style.Custom_Progress, "加载中", false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList = (RecyclerView) view.findViewById(R.id.list);
        mList.setLayoutManager(linearLayoutManager);
        mEmpty = (TextView) view.findViewById(R.id.empty);
        mEmpty.setText("你没有捐过书，快去捐书吧");//我的租书
        mEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BookDonateActivity.class));
            }
        });
        mCommonAdapter = new CommonAdapter<DonateBookData.DataBeanX.DataBean>(getActivity(), R.layout.list_item_donate_book, mListData) {
            @Override
            protected void convert(ViewHolder holder, DonateBookData.DataBeanX.DataBean dataBean, final int position) {
                Glide.with(getActivity())
                        .load( dataBean.getCover())
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.item_donate_picture));
                holder.setText(R.id.item_donate_name, dataBean.getBook_name());
                if (dataBean.getBook() == null) {
                    holder.setText(R.id.item_donate_number_borrow, "0人租过");
                } else {
                    holder.setText(R.id.item_donate_number_borrow, dataBean.getBook().getRent_num() + "人租过");
                }

                if (dataBean.getStatus() == 0) {
                    holder.setText(R.id.item_donate_status, "状态：等待审核");
                } else {
                    holder.setText(R.id.item_donate_status, "状态：已审核");
                }
                holder.setText(R.id.item_donate_tiem, "捐书时间" + MyUtils.TimeStamp2Date(String.valueOf(dataBean.getDonate_time()), ""));

                //                holder.setText(R.id.item_rent_status,dataBean.getBook().getModel().getName());
                //                holder.setText(R.id.item_rent_number_borrow,dataBean.getBook().getModel().getName());
            }
        };
        mList.setAdapter(mCommonAdapter);
    }

    private void initData() {
        UserData user = MyApplication.getInstance().getUser();
        if (user == null)
            return;
        customProgress.show();
        OkHttpUtils.get()
                .addParams("user_id", user.getYunsu_id() + "")
                .url(Api.DONATE_BOOK)
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
                                LogUtils.i("捐书" + response);
                                DonateBookData donateBookData = JSON.parseObject(response, DonateBookData.class);
                                if (donateBookData.getData().getData().size() == 0) {
                                    mEmpty.setVisibility(View.VISIBLE);
                                } else {
                                    mListData.addAll((JSON.parseObject(response, DonateBookData.class)).getData().getData());
                                    mCommonAdapter.notifyDataSetChanged();
                                }

                            } else {
                                ToastUtils.showLongToast(json.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtils.showLongToast("网络错误" + e);

                        }

                    }
                });
    }

    @Override
    public void onResume() {
        LogUtils.i("执行");
        if (mListData.size() == 0) {
            initData();
        }
        super.onResume();
    }




}

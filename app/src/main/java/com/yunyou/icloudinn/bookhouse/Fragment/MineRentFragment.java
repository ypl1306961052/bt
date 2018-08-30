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
import com.yunyou.icloudinn.bookhouse.Activity.QRActivity;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.RentBookData;
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

public class MineRentFragment extends Fragment {
    private CommonAdapter<RentBookData.DataBeanX.DataBean> mCommonAdapter;
    private List<RentBookData.DataBeanX.DataBean>mListData=new ArrayList<>();
    private CustomProgress customProgress;
    private RecyclerView mList;
    private TextView mEmpty;
    public static MineRentFragment getInstacnce(){
        MineRentFragment mineRentFragment=new MineRentFragment();
        return mineRentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.list_layout,container,false);
        initMyView(view);
        return view;
    }

    private void initMyView(View view) {
        customProgress=new CustomProgress(getActivity(),R.style.Custom_Progress,"加载中",false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList= (RecyclerView) view.findViewById(R.id.list);
        mList.setLayoutManager(linearLayoutManager);
        mEmpty= (TextView) view.findViewById(R.id.empty);
        mEmpty.setText("你没有租过书，快去租书吧");
        mEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), QRActivity.class));
            }
        });
        mCommonAdapter=new CommonAdapter<RentBookData.DataBeanX.DataBean>(getActivity(),R.layout.list_item_rent_book,mListData) {
            @Override
            protected void convert(ViewHolder holder, RentBookData.DataBeanX.DataBean dataBean, int position) {
                Glide.with(getActivity())
                        .load(dataBean.getBook().getModel().getCover_img())
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.item_rent_picture));
                holder.setText(R.id.item_rent_name,dataBean.getBook().getModel().getName());
//                holder.setText(R.id.item_rent_status,dataBean.getBook().getModel().getName());
                holder.setText(R.id.item_rent_can_borrow,"正在被"+dataBean.getRent_user()+"租阅");
                holder.setText(R.id.item_rent_day,"已租天数：5");
                holder.setText(R.id.item_rent_number_borrow,dataBean.getRent_num()+"人租过");
                holder.setText(R.id.item_rent_money,"已交押金"+dataBean.getGuaranty_money());
                holder.setText(R.id.item_rent_day_time,"租书时间："+ MyUtils.TimeStamp2Date(String.valueOf(dataBean.getRent_time()),""));
            }
        };
        mList.setAdapter(mCommonAdapter);
    }

    private void initData() {

        UserData user = MyApplication.getInstance().getUser();
        if(user == null)return;
        customProgress.show();
        OkHttpUtils.get()
                .addParams("user_id",user.getYunsu_id()+"")
                .url(Api.RENT_BOOK)
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
                                RentBookData rentBookData=JSON.parseObject(response,RentBookData.class);
                                if (rentBookData.getData().getData().size()==0){
                                    mEmpty.setVisibility(View.VISIBLE);
                                }else {
                                    LogUtils.i("赠书"+response);
                                    mListData.addAll((JSON.parseObject(response,RentBookData.class)).getData().getData());
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
    public void onResume() {
        super.onResume();
        if (mListData.size()==0){
            initData();
        }
    }
}

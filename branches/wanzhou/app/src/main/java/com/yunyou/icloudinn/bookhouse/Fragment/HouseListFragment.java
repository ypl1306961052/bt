package com.yunyou.icloudinn.bookhouse.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.HouseListData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.HttpUtil;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class HouseListFragment extends BaseFragment implements View.OnClickListener {
    private XRecyclerView mHouseList;
    private List<HouseListData.DataBeanX.DataBean> houseList;
    private CommonAdapter mAdapter;
    private CustomProgress customProgress;

    public static HouseListFragment getInanstance(){
        HouseListFragment houseListFragment=new HouseListFragment();
        return houseListFragment;
    }
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        StatusBarCompat.compat(getHoldingActivity(), getResources().getColor(R.color.green1));
        customProgress=new CustomProgress(getActivity(),R.style.Custom_Progress,"加载中",false);
        houseList=new ArrayList<>();
        if (MyApplication.getInstance().isLogin()){
            initDataLogin();
        }else {
            initDataNotLogin();
        }
        initMyView(view);
        mHouseList= (XRecyclerView) view.findViewById(R.id.house_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getHoldingActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mHouseList.setLayoutManager(linearLayoutManager);
        mAdapter=new CommonAdapter<HouseListData.DataBeanX.DataBean>(getHoldingActivity(),R.layout.house_list_item,houseList) {
            @Override
            protected void convert(final ViewHolder holder, final HouseListData.DataBeanX.DataBean dataBean, int position) {
                holder.setText(R.id.house_list_name,dataBean.getName());
                holder.setText(R.id.house_list_recommend,dataBean.getName());
                holder.setText(R.id.house_list_price,"￥"+dataBean.getPrice());
                Glide.with(getHoldingActivity())
                        .load(Api.BASE_URL+dataBean.getThumb())
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.house_list_picture));
                holder.getView(R.id.house_list_picture).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       addNewFragment(HouseDeatilMainFragment.getInstance(dataBean.getId()));
                    }
                });
                if (MyApplication.getInstance().isLogin()){
                    if (dataBean.isIs_collect()){
                        holder.getView(R.id.house_list_fever).setBackground(getHoldingActivity().getResources().getDrawable(R.drawable.is_collect));
                    }
                }
                holder.getView(R.id.house_list_fever).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MyApplication.getInstance().isLogin()){
                            if (dataBean.isIs_collect()){
                                doUnCollect();
                            }else {
                                doCollect();
                            }
                        }
                    }
                    private void doCollect() {
                        customProgress.show();
                        OkHttpUtils.post()
                                .url(Api.COLLECT)
                                .addParams("source_id",String.valueOf(dataBean.getId()))
                                .addParams("type","1")
                                .build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                ToastUtils.showLongToast("网络错误" + e);
                                customProgress.dismiss();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                customProgress.dismiss();
                                try {
                                    JSONObject json = JSON.parseObject(response);
                                    if (json.getInteger("code") == 100) {
                                        ToastUtils.showLongToast( "收藏成功");
                                        dataBean.setIs_collect(true);
                                        holder.getView(R.id.house_list_fever).setBackground(getHoldingActivity().getResources().getDrawable(R.drawable.collect));
                                    } else {
                                        ToastUtils.showLongToast( json.getString("msg"));
                                    }
                                } catch (Exception e) {
                                    ToastUtils.showLongToast("网络错误" + e);
                                }
                            }
                        });
                    }
                    private void doUnCollect(){
                        customProgress.show();
                        OkHttpUtils.post()
                                .url(Api.COLLECT)
                                .addParams("source_id",String.valueOf(dataBean.getId()))
                                .addParams("type","1")
                                .build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                ToastUtils.showLongToast("网络错误" + e);
                                customProgress.dismiss();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                customProgress.dismiss();
                                try {
                                    JSONObject json = JSON.parseObject(response);
                                    if (json.getInteger("code") == 100) {
                                        ToastUtils.showLongToast( "取消收藏成功");
                                        dataBean.setIs_collect(false);
                                        holder.getView(R.id.house_list_fever).setBackground(getHoldingActivity().getResources().getDrawable(R.drawable.un_collect));
                                    } else {
                                        ToastUtils.showLongToast( json.getString("msg"));
                                    }
                                } catch (Exception e) {
                                    ToastUtils.showLongToast("网络错误" + e);
                                }
                            }
                        });
                    }
                });
            }
        };
       mHouseList.setAdapter(mAdapter);
       mHouseList.setLoadingMoreEnabled(false);
       mHouseList.setLoadingListener(new XRecyclerView.LoadingListener() {
           @Override
           public void onRefresh() {
               if (MyApplication.getInstance().isLogin()){
                   loadDataLogin();
               }else {
                   loadData();
               }

           }

           @Override
           public void onLoadMore() {

           }
       });
    }


    private void initMyView(View view) {
        View mReturnMap=view.findViewById(R.id.return_map);
        mReturnMap.setOnClickListener(this);
        View back=view.findViewById(R.id.back);
        back.setOnClickListener(this);
        TextView title= (TextView) view.findViewById(R.id.title);
        title.setText("民宿列表");
    }

    private void initDataLogin() {
        customProgress.show();
        OkHttpUtils.get()
                .url(Api.HOUSE_LIST+"0")
                .build()
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showLongToast("网络错误" + e);
                customProgress.dismiss();
            }
            @Override
            public void onResponse(String response, int id) {
                customProgress.dismiss();
                mHouseList.refreshComplete();
                try {
                    HouseListData houseListData = JSON.parseObject(response,HouseListData.class);
                    if (houseListData.getCode()==100){
                        houseList.addAll(houseListData.getData().getData());
                    }else {
                        ToastUtils.showLongToast(houseListData.getMsg());
                    }
                }catch (Exception e){
                    ToastUtils.showLongToast("网络错误" + e);
                }
            }
        });
    }

    private void initDataNotLogin() {
        customProgress.show();
        OkHttpUtils.get().url(Api.HOUSE_LIST+"0").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showLongToast("网络错误" + e);
                customProgress.dismiss();
            }
            @Override
            public void onResponse(String response, int id) {
                customProgress.dismiss();
                mHouseList.refreshComplete();
                try {
                    HouseListData houseListData = JSON.parseObject(response,HouseListData.class);
                    if (houseListData.getCode()==100){
                        houseList.addAll(houseListData.getData().getData());
                    }else {
                        ToastUtils.showLongToast(houseListData.getMsg());
                    }
                }catch (Exception e){
                    ToastUtils.showLongToast("网络错误" + e);
                }
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_house_list;
    }

    private void loadData() {
        OkHttpUtils.get().url(Api.HOUSE_LIST+"0").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showLongToast("网络错误" + e);
                mHouseList.refreshComplete();
            }
            @Override
            public void onResponse(String response, int id) {
                mHouseList.refreshComplete();
                try {
                    HouseListData houseListData = JSON.parseObject(response,HouseListData.class);
                    if (houseListData.getCode()==100){
                        houseList.addAll(houseListData.getData().getData());
                        mAdapter.notifyDataSetChanged();
                    }else {
                        ToastUtils.showLongToast(houseListData.getMsg());
                    }
                }catch (Exception e){
                    ToastUtils.showLongToast("网络错误" + e);
                }
            }
        });
    }

    private void loadDataLogin(){
        OkHttpUtils.get()
                .url(Api.HOUSE_LIST+"0")
                .build()
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showLongToast("网络错误" + e);
                mHouseList.refreshComplete();
            }
            @Override
            public void onResponse(String response, int id) {
                mHouseList.refreshComplete();
                try {
                    HouseListData houseListData = JSON.parseObject(response,HouseListData.class);
                    if (houseListData.getCode()==100){
                        houseList.addAll(houseListData.getData().getData());
                        mAdapter.notifyDataSetChanged();
                    }else {
                        ToastUtils.showLongToast(houseListData.getMsg());
                    }
                }catch (Exception e){
                    ToastUtils.showLongToast("网络错误" + e);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                removeFragment();
                break;
            case R.id.return_map:
                removeFragment();
        }
    }
}

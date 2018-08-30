package com.yunyou.icloudinn.bookhouse.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunyou.icloudinn.bookhouse.Adapter.BookCollectAdapter;
import com.yunyou.icloudinn.bookhouse.Adapter.HouseCollectAdapter;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.BookCollectData;
import com.yunyou.icloudinn.bookhouse.JavaBean.HouseCollectData;
import com.yunyou.icloudinn.bookhouse.JavaBean.RentBookData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class HouseCollectListFragment extends Fragment implements BookCollectAdapter.IonSlidingViewClickListener {

    private List<HouseCollectData.DataBeanX.DataBean> mListData=new ArrayList<>();
    private CustomProgress customProgress;
    private RecyclerView mList;
    private HouseCollectAdapter houseCollectAdapter;
    private TextView mEmpty;

    public static HouseCollectListFragment getInstance(){
        return new HouseCollectListFragment();
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.list_layout,container,false);
        initMyView(view);
        initList();
        return view;
    }


    private void initMyView(View view) {
        customProgress=new CustomProgress(getActivity(),R.style.Custom_Progress,"加载中",false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList= (RecyclerView) view.findViewById(R.id.list);
        mList.setLayoutManager(linearLayoutManager);
        mEmpty= (TextView) view.findViewById(R.id.empty);
    }

    private void initList() {
         houseCollectAdapter=new HouseCollectAdapter(getActivity(),this,mListData);
        mList.setAdapter(houseCollectAdapter);
    }

    private void initData() {
        customProgress.show();
        OkHttpUtils.get()
                .url(Api.COLLECT_HOUSE)
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
                                if ((JSON.parseObject(response,HouseCollectData.class)).getData().getData().size()==0){
                                    mEmpty.setVisibility(View.VISIBLE);
                                }else {
                                    mListData.addAll((JSON.parseObject(response,HouseCollectData.class)).getData().getData());
                                    houseCollectAdapter.notifyDataSetChanged();
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

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onDeleteBtnCilck(View view, int position) {
        doDelete(position);
    }
    private void doDelete(final int position) {
        customProgress.show();
        LogUtils.i("删除的id"+mListData.get(position).getHouse().getId());
        OkHttpUtils.post()
                .url(Api.COLLECT)
                .addParams("source_id",String.valueOf(mListData.get(position).getHouse().getId()))
                .addParams("type","2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        customProgress.dismiss();
                        ToastUtils.showLongToast("网络错误" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("删除的回应"+response);
                        customProgress.dismiss();
                        try {
                            JSONObject json = JSON.parseObject(response);
                            if (json.getInteger("code") == 100) {
                                houseCollectAdapter.removeData(position);
                                houseCollectAdapter.notifyDataSetChanged();
                            } else {
                                ToastUtils.showLongToast( json.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtils.showLongToast("网络错误" + e);

                        }
                    }
                });
    }
}

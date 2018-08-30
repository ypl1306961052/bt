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
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Adapter.BookCollectAdapter;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.BookCollectData;
import com.yunyou.icloudinn.bookhouse.JavaBean.HouseCollectData;
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

public class BookCollectListFragment extends Fragment implements BookCollectAdapter.IonSlidingViewClickListener {
    private CommonAdapter<BookCollectData.DataBeanX.DataBean> mCommonAdapter;
    private List<BookCollectData.DataBeanX.DataBean> mListData=new ArrayList<>();
    private CustomProgress customProgress;
    private RecyclerView mList;
    private BookCollectAdapter bookCollectAdapter;
    private TextView mEmpty;

    public static BookCollectListFragment getInstance(){
        return new BookCollectListFragment();
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
        bookCollectAdapter = new BookCollectAdapter(getActivity(),this,mListData);
        mList.setAdapter(bookCollectAdapter);
    }

    private void initData() {
        customProgress.show();
        OkHttpUtils.get()
                .url(Api.COLLECT_BOOK)
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
                                if ((JSON.parseObject(response,BookCollectData.class)).getData().getData().size()==0){
                                    mEmpty.setVisibility(View.VISIBLE);
                                }else {
                                    mListData.addAll((JSON.parseObject(response,BookCollectData.class)).getData().getData());
                                    bookCollectAdapter.notifyDataSetChanged();
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
        LogUtils.i("删除的id"+mListData.get(position).getBook().getModel().getId());
        OkHttpUtils.post()
                .url(Api.COLLECT)
                .addParams("source_id",String.valueOf(mListData.get(position).getBook().getId()))
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
                                bookCollectAdapter.removeData(position);
                                bookCollectAdapter.notifyDataSetChanged();
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

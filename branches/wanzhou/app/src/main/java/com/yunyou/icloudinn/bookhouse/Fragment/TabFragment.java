package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.yunyou.icloudinn.bookhouse.JavaBean.BookCategoryData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class TabFragment extends Fragment {

    private int type;
    public static final String TABLAYOUT_FRAGMENT = "tab_fragment";
    private RecyclerView mList;
    private CommonAdapter<BookCategoryData.DataBean> mCommonAdapter;
    private List<BookCategoryData.DataBean> mListData=new ArrayList<>();
    private View mView;

    public static TabFragment newInstance(int type) {
        TabFragment fragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLAYOUT_FRAGMENT, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = (int) getArguments().getSerializable(TABLAYOUT_FRAGMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(mView == null) {
            mView = inflater.inflate(R.layout.list_layout, container, false);
            initView(mView);
            initData();
        }
        //判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup)mView.getParent();
        if(parent != null) {
            parent.removeView(mView);
        }
        return mView;
    }

    private void initView(View view) {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),3);
        mList = (RecyclerView) view.findViewById(R.id.list);
        mList.setLayoutManager(gridLayoutManager);
        mCommonAdapter=new CommonAdapter<BookCategoryData.DataBean>(getActivity(),R.layout.list_item_book_category,mListData) {
            @Override
            protected void convert(ViewHolder holder, final BookCategoryData.DataBean dataBean, int position) {
                Glide.with(getActivity())
                        .load(Api.BASE_URL+dataBean.getCover_img())
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.item_book_category_picture));
                holder.setText(R.id.item_book_category_name,dataBean.getName());
                holder.getView(R.id.item_book_category_picture).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i =new Intent(getActivity(), BookDetailActivity.class);
                        i.putExtra("id",dataBean.getId());
                        startActivity(i);
                    }
                });
            }
        };
        mList.setAdapter(mCommonAdapter);
    }
    private void initData() {
        OkHttpUtils.post()
                .url(Api.BOOK_CATEGORY)
                .addParams("category_id",String.valueOf(type))
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
                                BookCategoryData bookCategoryData=JSON.parseObject(response,BookCategoryData.class);
                                mListData.addAll(bookCategoryData.getData());
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
}

package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Activity.BookDetailActivity;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.BookCategoryData;
import com.yunyou.icloudinn.bookhouse.JavaBean.ItemEvent;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class TabFragment extends Fragment {

    private int type;
    public static final String TABLAYOUT_FRAGMENT = "tab_fragment";
    public RecyclerView mList;
    private CommonAdapter<BookCategoryData.DataBean> mCommonAdapter;
    private List<BookCategoryData.DataBean> mListData=new ArrayList<>();
    private View mView;
    private View itemView;
    private TextView emptyView;
    private int itemCount,itemHeight;
    private ItemEvent itemEvent = new ItemEvent();
    private Handler handler;

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
        emptyView = (TextView) view.findViewById(R.id.empty);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),3);//表格
        mList = (RecyclerView) view.findViewById(R.id.list);
        mList.setLayoutManager(gridLayoutManager);
        mList.setNestedScrollingEnabled(false);//设置禁止滑动
        mCommonAdapter=new CommonAdapter<BookCategoryData.DataBean>(getActivity(),R.layout.list_item_book_category,mListData) {
            @Override
            protected void convert(ViewHolder holder, final BookCategoryData.DataBean dataBean, int position) {
                itemView = holder.getConvertView();
                itemView.measure(0,0);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
                itemHeight = itemView.getMeasuredHeight()+params.topMargin+params.bottomMargin + 80;
                //itemEvent.setItemHeight(itemHeight);
                Message message = new Message();
                message.what = 1;
                message.arg1 = itemHeight;
                handler.sendMessage(message);

                Glide.with(getActivity())
                        .load(dataBean.getCover_img())//一本书中的全部书籍
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
                                if (bookCategoryData.getData().size()==0){
                                    emptyView.setVisibility(View.VISIBLE);
                                    emptyView.setText("目前没有该类书籍");
                                    itemCount=6;
                                }else {
                                    itemCount = bookCategoryData.getData().size();
                                }
                                mListData.addAll(bookCategoryData.getData());
                                mCommonAdapter.notifyDataSetChanged();

                                handler = new Handler(){
                                    @Override
                                    public void handleMessage(Message msg) {
                                        super.handleMessage(msg);
                                        if (msg.what==1){
                                            int Height = msg.arg1;
                                            itemEvent.setItemHeight(Height);
                                            itemEvent.setCount(itemCount);
                                            EventBus.getDefault().post(itemEvent);
                                            LogUtils.d("getItemHeight",itemEvent.getItemHeight());
                                            LogUtils.d("getItemCount",itemEvent.getCount());
                                        }

                                    }
                                };

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

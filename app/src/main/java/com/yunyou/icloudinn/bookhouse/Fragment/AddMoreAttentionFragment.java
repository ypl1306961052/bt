package com.yunyou.icloudinn.bookhouse.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yunyou.icloudinn.bookhouse.Adapter.UserListAdapter;
import com.yunyou.icloudinn.bookhouse.Base.BaseRecyclerViewHolder;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.PageData;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

public class AddMoreAttentionFragment extends BaseFragment{
    private RecyclerView mList;
    private List<UserData> allUserList = new ArrayList<>();
    private UserListAdapter adapter;

    public static AddMoreAttentionFragment getInstance(){
        return new AddMoreAttentionFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initMyView(view);
//        initUserList();
        initData();
    }

    private void initMyView(View view) {
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

    private void initUserList(){
        adapter = new UserListAdapter(getContext(),allUserList,new int[]{R.layout.list_item_more_attention,R.layout.list_item_more_attention_cancel});
        mList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                addNewFragment(UserDeatilFragment.getInstance(allUserList.get(position)));
            }
        });
    }

    //获取所有用户列表
    private void initData() {
        OkHttpUtils.get()
                .url(Api.USER_LIST)
                .build()
                .execute(new JsonCallback<PageData<UserData>>(getActivity(),true) {
                             @Override
                             public void onResponse(PageData<UserData> page, int id) {
                                 if (allUserList.size()==0){
                                     allUserList.addAll(page.getData());
                                     initUserList();
                                 }else {
                                     adapter.upData(page.getData());
                                 }
                                 initUserList();
                             }
                         });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.list_layout_with_top;
    }


}

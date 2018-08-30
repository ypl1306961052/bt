package com.yunyou.icloudinn.bookhouse.Base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by function on 2017/10/9.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    public List<T> list = new ArrayList<>();
    private Context mContext;
    private int[] layoutItemId;

    public BaseRecyclerAdapter(Context context, List list, int [] layoutItemId){
        this.mContext = context;
        this.list =list;
        this.layoutItemId = layoutItemId;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(layoutItemId[viewType],parent,false);
        BaseRecyclerViewHolder holder = new BaseRecyclerViewHolder(itemView,mContext);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {

        setViewHolderData(holder,list.get(position),getItemViewType(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
    /*
    * 设置ViewHolder数据抽象成方法暴露给外部使用
    * @holder: ViewHolder
    * @data: 网络获取的泛型数据
    * @type： RecyclerView中条目listItem 的类型
    * */
    public abstract void setViewHolderData(BaseRecyclerViewHolder holder, T data , int type);
}

package com.yunyou.icloudinn.bookhouse.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMTextElem;
import com.yunyou.icloudinn.bookhouse.Base.BaseRecyclerAdapter;
import com.yunyou.icloudinn.bookhouse.Base.BaseRecyclerViewHolder;
import com.yunyou.icloudinn.bookhouse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by function on 2017/10/9.
 */

public class ConversationAdapter extends BaseRecyclerAdapter<TIMMessage> {
    public List conversation = new ArrayList();
    public Context mContext;
    public int [] layoutId;
    private int itemType;

    public ConversationAdapter(Context context, List list, int[] layoutItemId) {
        super(context, list, layoutItemId);
        this.mContext = context;
        this.layoutId = layoutItemId;
        this.conversation = list;
    }


    @Override
    public void setViewHolderData(BaseRecyclerViewHolder holder, TIMMessage data, int type) {

        switch (type){
            case 0:
                for (int i=0;i<data.getElementCount();i++) {
                    TIMTextElem timTextElem = (TIMTextElem) data.getElement(i);
                    holder.setTextView(R.id.textView_left, timTextElem.getText());
                }
                break;
            case 1:
                for (int i=0;i<data.getElementCount();i++){
                    TIMTextElem timTextElem = (TIMTextElem) data.getElement(i);
                    holder.setTextView(R.id.textView_right,timTextElem.getText());
                }

                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
         TIMMessage message =  list.get(position);
        if (message.isSelf()){
            itemType = 0;
        }else {
            itemType = 1;
        }
        return itemType;
    }

    public void upData(List data){
        this.list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }
    public void addDataAll(List data){
        list.addAll(data);
        notifyDataSetChanged();
    }
    public void addData(TIMMessage data){
        list.add(data);
        notifyDataSetChanged();
    }
}

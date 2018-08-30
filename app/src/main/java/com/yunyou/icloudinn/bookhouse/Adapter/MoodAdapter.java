package com.yunyou.icloudinn.bookhouse.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.yunyou.icloudinn.bookhouse.R;

public class MoodAdapter extends RecyclerView.Adapter {
    private int MOOD_TYPR=3;
    private int BOOK_TYPE=2;
    private Context context;
    private JSONArray DataObject;

    public MoodAdapter(Context context, JSONArray jsonObject) {
        this.context = context;
        DataObject = jsonObject;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==MOOD_TYPR){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment, parent,false);
            return new MoodAdapter.MoodViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_shopping_mood, parent,false);
            return new MoodAdapter.ItemViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MoodAdapter.MoodViewHolder){

        }else {

        }
    }

    @Override
    public int getItemCount() {
        return DataObject.size();
    }


    @Override
    public int getItemViewType(int position) {
        return DataObject.getJSONObject(position).getInteger("type");
    }


    class MoodViewHolder extends RecyclerView.ViewHolder {

       public MoodViewHolder(View itemView) {
           super(itemView);
       }
   }

   class ItemViewHolder extends RecyclerView.ViewHolder {

       public ItemViewHolder(View itemView) {
           super(itemView);
       }
   }
}

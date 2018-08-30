package com.yunyou.icloudinn.bookhouse.Adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yunyou.icloudinn.bookhouse.JavaBean.CommentReply;
import com.yunyou.icloudinn.bookhouse.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.MyViewHolder> {
    private Activity context;
    private List<CommentReply> mData;

    public ReplyAdapter(Activity context, List<CommentReply> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mine_reply_item,parent,false);
        return new ReplyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.replyText.setText(mData.get(position).getUser_name());
        holder.mContext.setText(":"+mData.get(position).getContent());
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            holder.mRepyle.setLayoutManager(linearLayoutManager);
            holder.mRepyle.setAdapter(new CommonAdapter<CommentReply.ReplyBean>(context,R.layout.reply_list_tiem,mData.get(position).getReply()) {
                @Override
                protected void convert(ViewHolder holder, CommentReply.ReplyBean replyBean, int position) {
                    holder.setText(R.id.reply_item_name1,replyBean.getUser_name());
                    holder.setText(R.id.reply_item_name2,replyBean.getTo_user_name());
                    holder.setText(R.id.reply_item_context,":"+replyBean.getReply());
                }
            });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView replyText;
        private RecyclerView mRepyle;
        private TextView mContext;
        public MyViewHolder(View itemView) {
            super(itemView);
            replyText= (TextView) itemView.findViewById(R.id.reply_item_name);
            mRepyle= (RecyclerView) itemView.findViewById(R.id.comment_reply_list);
            mContext= (TextView) itemView.findViewById(R.id.reply_item_context);
        }
    }
}

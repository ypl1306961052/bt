package com.yunyou.icloudinn.bookhouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyou.icloudinn.bookhouse.Activity.ChapterDetailActivity;
import com.yunyou.icloudinn.bookhouse.JavaBean.BookWriteBean;
import com.yunyou.icloudinn.bookhouse.JavaBean.BookWriteChapterBean;
import com.yunyou.icloudinn.bookhouse.R;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.MyViewHolder> {
    private Context context;
    private List<BookWriteChapterBean> chapterList;

    public ChapterAdapter(Context context, BookWriteBean bookWrite) {
        this.context = context;
        this.chapterList = bookWrite.getChapter();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chapter,parent,false);
        return new ChapterAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvChapter.setText("第"+chapterList.get(position).getSequence()+"章");
        holder.tvTitle.setText(chapterList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ChapterDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("chapter",chapterList.get(position));
                intent.putExtra("max_sequence",chapterList.get(0).getSequence());// 最大章节
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvChapter;
        private TextView tvTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvChapter= (TextView) itemView.findViewById(R.id.text_chapter);
            tvTitle= (TextView) itemView.findViewById(R.id.text_chapter_title);
        }
    }
}

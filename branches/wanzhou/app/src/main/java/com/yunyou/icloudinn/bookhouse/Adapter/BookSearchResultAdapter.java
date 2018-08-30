package com.yunyou.icloudinn.bookhouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Activity.BookDetailActivity;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.BookData;
import com.yunyou.icloudinn.bookhouse.R;

import java.util.List;

public class BookSearchResultAdapter extends RecyclerView.Adapter<BookSearchResultAdapter.MyViewHolder> {
    private Context context;
    private List<BookData> bookList;

    public BookSearchResultAdapter(Context context, List<BookData> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book_search,parent,false);
        return new BookSearchResultAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvBookName.setText(bookList.get(position).getName());
        holder.tvRentNun.setText("租借："+bookList.get(position).getRentNum()+"次");
        holder.tvReadNum.setText("浏览："+bookList.get(position).getReadNum()+"次");
        Glide.with(context)
                .load(Api.BASE_URL+bookList.get(position).getCoverImg())
                .centerCrop()
                .into(holder.ivBookImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, BookDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",bookList.get(position).getBookId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBookName,tvRentNun,tvReadNum;
        private ImageView ivBookImg;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvBookName= (TextView) itemView.findViewById(R.id.text_book_title);
            tvRentNun= (TextView) itemView.findViewById(R.id.text_rent_time);
            tvReadNum= (TextView) itemView.findViewById(R.id.text_read_time);
            ivBookImg= (ImageView) itemView.findViewById(R.id.iv_book);
        }
    }
}

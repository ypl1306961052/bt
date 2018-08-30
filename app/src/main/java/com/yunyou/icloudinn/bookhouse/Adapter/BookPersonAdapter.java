package com.yunyou.icloudinn.bookhouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Activity.BookDetailActivity;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;

public class BookPersonAdapter extends RecyclerView.Adapter<BookPersonAdapter.Holder> {
    private Context context;
    private JSONArray listData = new JSONArray();

    public BookPersonAdapter(Context context, JSONArray listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book_information, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        JSONObject book = null;
        int type = listData.getJSONObject(position).getInteger("type");
        String bookName = listData.getJSONObject(position).getString("action");
        String coverImg = "/uploads/20170725/743304088b9e3d04fd8cf60e80ab76bd.jpg";
        // 捐书
        if (type == 4) {
            JSONObject bookDonate = listData.getJSONObject(position).getJSONObject("book_donate");
            if(bookDonate!=null) book = bookDonate.getJSONObject("book");
            if(book==null){
                bookName = bookDonate.getString("book_name");
                coverImg = bookDonate.getString("cover");
            }
        }
        // 租书
        if(type == 5){
            holder.mStatus.setBackground(context.getResources().getDrawable(R.color.green1));
            holder.mStatusText.setText("租");
            JSONObject bookRent = listData.getJSONObject(position).getJSONObject("book_rent");
            if(bookRent!=null)book = bookRent.getJSONObject("book");
        }

        if(book!=null){
            JSONObject bookModel = book.getJSONObject("model");
            holder.mCanRent.setText(book.getString("rent_num") + "人租过");
            holder.mComment.setText(book.getString("comment_num") + "条书评");
            if(bookModel!=null){
                bookName = bookModel.getString("name");
                coverImg = bookModel.getString("cover_img");
            }
        }

        holder.mBookName.setText(bookName);
        Glide.with(context).load(Api.BASE_URL + coverImg).centerCrop().into(holder.mBookPicture);

        final JSONObject bookData = book;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bookData!=null){
                    Intent intent = new Intent();
                    intent.setClass(context, BookDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id",bookData.getInteger("id"));
                    context.startActivity(intent);
                }else {
                    ToastUtils.showLongToast("该书已经下架！");
                }
            }
        });
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private ImageView mBookPicture;
        private View mStatus;
        private TextView mStatusText, mBookName, mRent, mComment, mCanRent;

        public Holder(View itemView) {
            super(itemView);
            mBookPicture = (ImageView) itemView.findViewById(R.id.item_book_information_picture);
            mStatus = itemView.findViewById(R.id.item_book_information_picture2);
            mBookName = (TextView) itemView.findViewById(R.id.item_book_information_name);
            mStatusText = (TextView) itemView.findViewById(R.id.item_book_information_status);
            mRent = (TextView) itemView.findViewById(R.id.item_book_information_rent);
            mComment = (TextView) itemView.findViewById(R.id.item_book_information_comment);
            mCanRent = (TextView) itemView.findViewById(R.id.item_book_information_isRent);
        }
    }

}

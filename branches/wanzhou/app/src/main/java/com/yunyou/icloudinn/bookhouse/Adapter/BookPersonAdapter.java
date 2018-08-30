package com.yunyou.icloudinn.bookhouse.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;

public class BookPersonAdapter extends RecyclerView.Adapter<BookPersonAdapter.Holder> {
    private Context context;
    private JSONArray listData = new JSONArray();

    public BookPersonAdapter(Context context, JSONArray listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book_information, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        boolean type = listData.getJSONObject(position).getInteger("type") == 5;
        if (type) {
            doRent(holder, position);
        } else {
            try {
                doDonate(holder, position);
            } catch (Exception e) {
                LogUtils.e("" + e);
            }

        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
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

    private void doRent(Holder holder, int position) {
        holder.mStatus.setBackground(context.getResources().getDrawable(R.color.green1));
        holder.mStatusText.setText("租");
        String name = listData.getJSONObject(position).getJSONObject("book_rent").getJSONObject("book").getJSONObject("model").getString("name");
        String pictureurl = listData.getJSONObject(position).getJSONObject("book_rent").getJSONObject("book").getJSONObject("model").getString("cover_img");
        String rent_num = listData.getJSONObject(position).getJSONObject("book_rent").getJSONObject("book").getString("rent_num");
        String Comment = listData.getJSONObject(position).getJSONObject("book_rent").getJSONObject("book").getString("comment_num");
        String rentUser = listData.getJSONObject(position).getJSONObject("book_rent").getJSONObject("book").getString("renting_user");
        LogUtils.i("显示图片" + pictureurl);
        Glide.with(context)
                .load(Api.BASE_URL + pictureurl)
                .centerCrop()
                .into(holder.mBookPicture);

        holder.mBookName.setText(name);

        if (rentUser == null || rentUser.isEmpty()) {
            holder.mCanRent.setText("可租");
        } else {
            holder.mCanRent.setText(listData.getJSONObject(position).getJSONObject("book_rent").getJSONObject("book").getString("renting_user") + "在租");
        }

        holder.mComment.setText(Comment + "条书评");
    }

    private void doDonate(Holder holder, int position) throws Exception {
        if (listData.getJSONObject(position).getJSONObject("book_donate") != null) {
            String name = listData.getJSONObject(position).getJSONObject("book_donate").getJSONObject("book").getJSONObject("model").getString("name");
            String pictureurl = listData.getJSONObject(position).getJSONObject("book_donate").getJSONObject("book").getJSONObject("model").getString("cover_img");
            String rent_num = listData.getJSONObject(position).getJSONObject("book_donate").getJSONObject("book").getString("rent_num");
            String Comment = listData.getJSONObject(position).getJSONObject("book_donate").getJSONObject("book").getString("comment_num");
            Glide.with(context)
                    .load(Api.BASE_URL + pictureurl)
                    .centerCrop()
                    .into(holder.mBookPicture);
            holder.mBookName.setText(name);
            holder.mCanRent.setText(rent_num + "人租过");
            holder.mComment.setText(Comment + "条书评");
        } else {
            holder.mBookName.setText(listData.getJSONObject(position).getString("action").substring(6));
        }
    }
}

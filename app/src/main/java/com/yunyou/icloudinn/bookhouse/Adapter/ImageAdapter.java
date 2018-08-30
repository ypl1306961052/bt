package com.yunyou.icloudinn.bookhouse.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.MulitPointTouchImageView;

/**
 * Created by chen on 2017/10/16.
 */

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private JSONArray urlArr;

    public ImageAdapter(Context context, JSONArray urlArr) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.urlArr = urlArr;
    }

    @Override
    public int getCount() {
        return urlArr.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MulitPointTouchImageView imgView = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.gallery_item, null);
            // imgView = new MulitPointTouchImageView(mContext);
            // imgView.setLayoutParams(new Gallery.LayoutParams(
            // LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        }
        // ImageView view = new ImageView(context);

        // Logger.d(TAG, "getView():" + position);
        // ImageView imgView = (ImageView) convertView
        // .findViewById(R.id.imgImageView);
        imgView = (MulitPointTouchImageView) convertView
                .findViewById(R.id.imgImageView);
        Glide.with(mContext)
                .load(Api.BASE_URL+urlArr.get(position)).into(imgView);
        return convertView;
    }

}
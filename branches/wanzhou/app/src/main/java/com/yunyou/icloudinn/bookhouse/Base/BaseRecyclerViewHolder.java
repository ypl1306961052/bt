package com.yunyou.icloudinn.bookhouse.Base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by function on 2017/10/9.
 */

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    public View itemView;
    private Context mContext;
    private TextView textView;
    private ImageView imageView;

    public BaseRecyclerViewHolder(View itemView,Context context){
        super(itemView);
        this.itemView = itemView;
        this.mContext = context;
    }
    /*
    * 返回每个条目itemView中的控件，泛型textView,imageView等等都继承自view
    * @itemId: list_item 的ID
    * */
    public <T extends View> T getItemViewId(@IdRes int itemId){

        return (T) itemView.findViewById(itemId);
    }

    /*
    * 给TextView设置文字
    * @viewId: textView控件的Id
    * @text: 设置的文字
    * */
    public void setTextView(@IdRes int textViewId, CharSequence text){
        textView = (TextView) itemView.findViewById(textViewId);
        textView.setText(text);
    }

    /*
    * 给ImageView设置网络资源
    * @imageViewId: imageView控件Id
    * @url: 图片地址
    * */
    public void setImageView(@IdRes int imageViewId,String url){

    }
}

package com.yunyou.icloudinn.bookhouse.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.HouseCollectData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.SlidingButtonView;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.MyUtils;

import java.util.ArrayList;
import java.util.List;

public class HouseCollectAdapter extends RecyclerView.Adapter<HouseCollectAdapter.MyViewHolder> implements SlidingButtonView.IonSlidingButtonListener {
    private Context mContext;

    private BookCollectAdapter.IonSlidingViewClickListener mIDeleteBtnClickListener;

    private List<HouseCollectData.DataBeanX.DataBean> mListData=new ArrayList<>();

    private SlidingButtonView mMenu = null;

    public HouseCollectAdapter(Context context, Fragment fragment, List<HouseCollectData.DataBeanX.DataBean> data) {
        mContext = context;
        mIDeleteBtnClickListener = (BookCollectAdapter.IonSlidingViewClickListener) fragment;
        LogUtils.i("收藏列表长度"+data.size());
        mListData=data;

    }
    @Override
    public int getItemCount() {
        return mListData.size();
    }

    @Override
    public void onBindViewHolder(final HouseCollectAdapter.MyViewHolder holder, final int position) {

//        holder.textView.setText(mDatas.get(position));
        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = MyUtils.getScreenWidth(mContext);
        if (mListData.get(position).getHouse()!=null){
            Glide.with(mContext)
                    .load(mListData.get(position).getHouse().getThumb())
                    .centerCrop()
                    .into(holder.mPicture);
            holder.mName.setText(String.valueOf(mListData.get(position).getHouse().getName()));
            holder.layout_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //判断是否有删除菜单打开
                    if (menuIsOpen()) {
                        closeMenu();//关闭菜单
                    } else {
                        int n = holder.getLayoutPosition();
                        mIDeleteBtnClickListener.onItemClick(v, n);
                    }
                }
            });
            holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int n = holder.getLayoutPosition();
                    mIDeleteBtnClickListener.onDeleteBtnCilck(v, n);

                }
            });
        }

    }

    public void addData(int position) {
        notifyItemInserted(position);
    }

    public void removeData(int position){
        LogUtils.i("删除"+position);
        mListData.remove(position);
        notifyItemRemoved(position);
        closeMenu();
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    /**
     * 滑动或者点击了Item监听
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if(menuIsOpen()){
            if(mMenu != slidingButtonView){
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }
    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if(mMenu != null){
            return true;
        }
        Log.i("asd","mMenu为null");
        return false;
    }



    public interface IonSlidingViewClickListener {
        void onItemClick(View view,int position);
        void onDeleteBtnCilck(View view,int position);
    }

    @Override
    public HouseCollectAdapter.MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_house_collect, arg0,false);
        HouseCollectAdapter.MyViewHolder holder = new HouseCollectAdapter.MyViewHolder(view);
        return holder;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView btn_Delete,mName,mPrice;
        private ImageView mPicture;
        private ViewGroup layout_content;
        public MyViewHolder(View itemView) {
            super(itemView);
            btn_Delete = (TextView) itemView.findViewById(R.id.book_delete);
            mName = (TextView) itemView.findViewById(R.id.item_house_collect_name);
            mPrice = (TextView) itemView.findViewById(R.id.item_house_collect_price);

            layout_content = (ViewGroup) itemView.findViewById(R.id.layout_content);
            mPicture= (ImageView) itemView.findViewById(R.id.item_house_collect_picture);
            ((SlidingButtonView) itemView).setSlidingButtonListener(HouseCollectAdapter.this);
        }
    }
}

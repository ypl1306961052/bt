package com.yunyou.icloudinn.bookhouse.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Base.BaseRecyclerAdapter;
import com.yunyou.icloudinn.bookhouse.Base.BaseRecyclerViewHolder;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by function on 2017/10/16.
 */

public class UserListAdapter extends BaseRecyclerAdapter<UserData> {
    private List<UserData> allUserList = new ArrayList();
    private Context context;
    private int [] layoutItemId;
    private TextView attention_user;
    private ImageView user_picutre;
    private BaseRecyclerViewHolder.OnItemClickListener onItemClickListener;


    public UserListAdapter(Context context, List list, int[] layoutItemId) {
        super(context, list, layoutItemId);
        this.allUserList = list;
        this.context = context;
        this.layoutItemId = layoutItemId;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutItemId[viewType],parent,false);
        int[] itemId = new int[]{R.id.more_attention_attention_user};
        BaseRecyclerViewHolder holder = new BaseRecyclerViewHolder(itemView, context, onItemClickListener);
        return holder;
    }

    @Override
    public void setViewHolderData(BaseRecyclerViewHolder holder, UserData data,int position, int type) {
        attention_user = (TextView) holder.getItemView(R.id.more_attention_attention_user);
        user_picutre = (ImageView) holder.getItemView(R.id.more_attention_user_picutre);
        //加载头像
        Glide.with(context).load(data.getHead_img_url())
                .placeholder(R.drawable.head_img)
                .centerCrop()
                .into(user_picutre);
        //加载用户名称
        if (data.getNickname()==null){
            holder.setTextView(R.id.more_attention_user_name,data.getAccount());
        }else {
            holder.setTextView(R.id.more_attention_user_name,data.getNickname());
        }
        holder.setTextView(R.id.more_attention_user_rent,String.valueOf("租"+data.getRent_book_num())+"本");
        holder.setTextView(R.id.more_attention_user_donate,String.valueOf("捐"+data.getDonate_book_num())+"本");
        holder.setTextView(R.id.more_attention_user_live_num,String.valueOf("住过"+data.getCheck_in_hotel_num())+"间民宿");
    }

    @Override
    public int getItemViewType(int position) {
        if (allUserList.get(position).getIs_concern()==1){
            return 1;
        }else {
            return 0;
        }
    }
    //加载关注/取消关注
    private void doCollect(BaseRecyclerViewHolder holder, final int position, final int type) {
        OkHttpUtils.get().url(Api.ATTENTION +allUserList.get(position).getYunsu_id())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {}

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = JSON.parseObject(response);
                        if (jsonObject.getInteger("code")==100){
                            if (jsonObject.get("msg").equals("关注成功！")){

                            }else {

                            }
                        }
                    }
                });
    }
    /*
    * 更新数据列表
    * */
    public void upData(List data){
        this.allUserList.clear();
        allUserList.addAll(data);
        notifyDataSetChanged();
    }

    /*
   * 设置监听事件
   * */
    public void setOnItemClickListener(BaseRecyclerViewHolder.OnItemClickListener listener){
        this.onItemClickListener = listener;
    }
}

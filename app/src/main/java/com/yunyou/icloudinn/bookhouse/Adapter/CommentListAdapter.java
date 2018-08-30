package com.yunyou.icloudinn.bookhouse.Adapter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hedgehog.ratingbar.RatingBar;
import com.yunyou.icloudinn.bookhouse.Activity.BaseFragmentActivity;
import com.yunyou.icloudinn.bookhouse.Activity.LoginActivity;
import com.yunyou.icloudinn.bookhouse.Callback.CommentCallBack;
import com.yunyou.icloudinn.bookhouse.Callback.HttpCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.HouseDetailData;
import com.yunyou.icloudinn.bookhouse.JavaBean.IsFever;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Ui.MultiImageView;
import com.yunyou.icloudinn.bookhouse.Util.HttpUtil;
import com.yunyou.icloudinn.bookhouse.Util.MyUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.MyViewHolder> {
    private List<HouseDetailData.DataBean.CommentBean> commentBean ;
    private BaseFragmentActivity context;
    private CommentCallBack myCommentCallBack;
    private CustomProgress customProgress;
    private ArrayList<IsFever> FeverList;

    public CommentListAdapter(List<HouseDetailData.DataBean.CommentBean> commentBean, BaseFragmentActivity context, CustomProgress customProgress, CommentCallBack commentCallBack) {
        this.commentBean = commentBean;
        this.context=context;
        myCommentCallBack=commentCallBack;
        this.customProgress=customProgress;
        FeverList=new ArrayList<>();

        for (int i = 0; i <commentBean.size() ; i++) {
            IsFever fever=new IsFever();
            fever.setFever(false);
            FeverList.add(fever);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mFormShoping.setVisibility(View.GONE);
        holder.mCommentData.setText(MyUtils.TimeStamp2Date(String.valueOf(commentBean.get(position).getCreate_time()),""));
        if (commentBean.get(position).getImgs()!=null){
            holder.multiImageView.setList(commentBean.get(position).getImgs());
        }
        holder.mCommentDescribe.setText(commentBean.get(position).getContent());
        holder.mCommentName.setText(commentBean.get(position).getUser_name());
        holder.mRatingBar.setStar(commentBean.get(position).getRecommend_exponent());
        holder.mRatingBar.setStarCount(5-commentBean.get(position).getRecommend_exponent());
        holder.mFeverNum.setText(String.valueOf(commentBean.get(position).getPraise_num()));
        Glide.with(context)
                .load(commentBean.get(position).getHead_img())
                .centerCrop()
                .into(holder.mCommentPicture);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.mCommendList.setLayoutManager(linearLayoutManager);
        if (commentBean.get(position).getReply().size()!=0){
            holder.mCommendList.setAdapter(new CommonAdapter<HouseDetailData.DataBean.CommentBean.ReplyBean>(context,R.layout.list_item_reply_comment,commentBean.get(position).getReply()) {
                @Override
                protected void convert(ViewHolder holder, HouseDetailData.DataBean.CommentBean.ReplyBean replyBean, int position) {
                    if (replyBean.getTo_user_name().isEmpty()){
                        holder.getView(R.id.reply_item_reply1).setVisibility(View.GONE);
                        holder.getView(R.id.reply_item_name2).setVisibility(View.GONE);
                        holder.setText(R.id.reply_item_name1,replyBean.getUser_name());
                        holder.setText(R.id.reply_item_context,"："+replyBean.getReply());
                    }else {
                        holder.setText(R.id.reply_item_name2,replyBean.getTo_user_name());
                        holder.setText(R.id.reply_item_name1,replyBean.getUser_name());
                        holder.setText(R.id.reply_item_context,"："+replyBean.getReply());
                    }


                }
            });
        }

        //点赞
        holder.mCommentFever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().isLogin()){
                   doFever(holder,position);
                }else {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
        holder.mCommentReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().isLogin()){

                    myCommentCallBack.OnClikComment(commentBean.get(position).getId(),commentBean.get(position).getUser_name(),position);

                }else {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }

            }
        });
    }

    private void doFever(final MyViewHolder holder, final int positiion) {
        if (FeverList.get(positiion).isFever()){
            HashMap hashmap=new HashMap();
            hashmap.put("comment_id",String.valueOf(commentBean.get(positiion).getId()));
            hashmap.put("action","cancel");
            customProgress.show();
            HttpUtil.okHttpPost(true, hashmap, Api.PRAISE, new HttpCallback() {
                @Override
                public void onFail() {
                    customProgress.dismiss();
                }

                @Override
                public void onSuccess(String Data) {
                    FeverList.get(positiion).setFever(false);
                    ToastUtils.showLongToast("取消点赞成功");
                    int num= commentBean.get(positiion).getPraise_num()-1;
                    commentBean.get(positiion).setPraise_num(num);
                    holder.mFeverNum.setText(String.valueOf(num));
                    customProgress.dismiss();
                }
            });
        }else {
            HashMap hashmap=new HashMap();
            hashmap.put("comment_id",String.valueOf(commentBean.get(positiion).getId()));
            hashmap.put("action","praise");
            customProgress.show();
            HttpUtil.okHttpPost(true, hashmap, Api.PRAISE, new HttpCallback() {
                @Override
                public void onFail() {
                    customProgress.dismiss();
                }

                @Override
                public void onSuccess(String Data) {
                    FeverList.get(positiion).setFever(true);
                    ToastUtils.showLongToast("点赞成功");
                    int num= commentBean.get(positiion).getPraise_num()+1;
                    commentBean.get(positiion).setPraise_num(num);
                    holder.mFeverNum.setText(String.valueOf(num));
                    customProgress.dismiss();
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return commentBean.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder {

        private MultiImageView multiImageView;
        private  TextView mCommentName,mCommentDescribe,mCommentData,mFeverNum,mFormShoping;
        private ImageView mCommentPicture,mCommentFever,mCommentReply;
        private RecyclerView mCommendList;
        private RatingBar mRatingBar;

        public MyViewHolder(View view) {
            super(view);
            mCommentName=(TextView) view.findViewById(R.id.comment_nickname);
            mCommentDescribe=(TextView) view.findViewById(R.id.comment_describe);
            mCommentData=(TextView) view.findViewById(R.id.comment_data);
            mFeverNum=(TextView) view.findViewById(R.id.comment_people_num);
            mCommentPicture= (ImageView) view.findViewById(R.id.comment_picture);
            mCommendList= (RecyclerView) view.findViewById(R.id.reply_list);
            mRatingBar= (RatingBar) view.findViewById(R.id.ratingbar);
            mCommentFever= (ImageView) view.findViewById(R.id.comment_fever);
            mCommentReply= (ImageView) view.findViewById(R.id.comment_follow_up);
            mFormShoping= (TextView) view.findViewById(R.id.comment_from_shopping);
            multiImageView= (MultiImageView) view.findViewById(R.id.user_picture);
        }
    }
}

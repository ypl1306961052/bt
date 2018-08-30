package com.yunyou.icloudinn.bookhouse.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.hedgehog.ratingbar.RatingBar;
import com.yunyou.icloudinn.bookhouse.Activity.BookDetailActivity;
import com.yunyou.icloudinn.bookhouse.Activity.HouseDetailActivity;
import com.yunyou.icloudinn.bookhouse.Activity.ImagePagerActivity;
import com.yunyou.icloudinn.bookhouse.Activity.LoginActivity;
import com.yunyou.icloudinn.bookhouse.Activity.UserDetailActivity;
import com.yunyou.icloudinn.bookhouse.Callback.CommentCallBack;
import com.yunyou.icloudinn.bookhouse.Callback.HttpCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.CommentReply;
import com.yunyou.icloudinn.bookhouse.JavaBean.IsFever;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomDialog;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Ui.MultiImageView;
import com.yunyou.icloudinn.bookhouse.Util.HttpUtil;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class CircleCommentAdapter extends RecyclerView.Adapter<CircleCommentAdapter.MyViewHolder> {

    private Activity context;
    private CommentCallBack myCommentCallBack;
    private ArrayList<Integer> FeverList=new ArrayList<>();;
    private JSONArray listData;
    private LinearLayout edittextbody;
    private EditText editText;
    private ImageView sendIv;
    private ReplyAdapter  mReplyAdapter;
    List<CommentReply>listComment=new ArrayList<>();

    CustomDialog.Builder builder;// 图片浏览对话框
    private JSONObject hotel;
    private JSONObject book;
    private JSONObject heartfeeling;
    private SimpleDateFormat dateFormat;
    private String currentDate;

    //初始化
    public CircleCommentAdapter(JSONArray jsonArray, Activity context, CustomProgress customProgress, LinearLayout edittextbody, EditText editText, ImageView sendIv) {
        listData = jsonArray;
        this.context = context;
        this.edittextbody=edittextbody;
        this.editText=editText;
        this.sendIv=sendIv;
        //全局控制点赞的总数
        if (FeverList.size()==0){
            for (int i = 0; i <listData.size() ; i++) {
                IsFever fever=new IsFever();
                fever.setFever(false);
                FeverList.add(0);
            }
        }else {
            FeverList.clear();
            for (int i = 0; i <listData.size() ; i++) {
                IsFever fever=new IsFever();
                fever.setFever(false);
                FeverList.add(0);
            }
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment,parent,false);
        return new CircleCommentAdapter.MyViewHolder(view);
    }

    //布局变化不大，使用同一个view，用type进行微调
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)  {
        //强制关闭复用
        holder.setIsRecyclable(false);
        //加载头像
        Glide.with(context)
                .load(listData.getJSONObject(position).getString("user_head_img"))
                .centerCrop()
                .placeholder(R.drawable.user_head)
                .into(holder.mCommentPicture);
        //头像点击
        holder.mCommentPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(UserDetailActivity.getInstance(context,listData.getJSONObject(position).getInteger("user_id")));
            }
        });
        //发布的日期
        String createTime = listData.getJSONObject(position).getString("create_time");
        dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        currentDate =dateFormat.format(Long.parseLong(createTime) * 1000);
        holder.mCommentData.setText(currentDate);

        //根据type调节item类型
        try {
            switch (listData.getJSONObject(position).getInteger("type")){
                case 1:
                    holder.mFromShopping.setText("来自民宿的评论");
                    hotel = listData.getJSONObject(position).getJSONObject("hotel");
                    int star= hotel.getJSONArray("comment").getJSONObject(0).getInteger("recommend_exponent");
                    holder.mRatingBar.setStar(star);
                    holder.mCommentName.setText(listData.getJSONObject(position).getString("user_name"));
                    holder.mCommentDescribe.setText(listData.getJSONObject(position).getJSONObject("hotel").getJSONArray("comment").getJSONObject(0).getString("content"));
                    holder.mCommentName.setText(listData.getJSONObject(position).getJSONObject("hotel").getJSONArray("comment").getJSONObject(0).getString("user_name"));
                    boolean HousenotNull=listData.getJSONObject(position).getJSONObject("hotel").getJSONArray("comment").getJSONObject(0).getJSONArray("imgs")!=null&&listData.getJSONObject(position).getJSONObject("hotel").getJSONArray("comment").getJSONObject(0).getJSONArray("imgs").size()!=0;
                    //加载图片集
                    List<String> pictureList=new ArrayList<>();
                    if (HousenotNull){
                        for (int i = 0; i <listData.getJSONObject(position).getJSONObject("hotel").getJSONArray("comment").getJSONObject(0).getJSONArray("imgs").size() ; i++) {
                            pictureList.add((String)listData.getJSONObject(position).getJSONObject("hotel").getJSONArray("comment").getJSONObject(0).getJSONArray("imgs").get(i));
                        }
                        holder.multiImageView.setList(pictureList);
                    }
                    //去民宿
                    holder.mFromShopping.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(context, HouseDetailActivity.class);
                            intent.putExtra("id",listData.getJSONObject(position).getJSONObject("hotel").getIntValue("id"));
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 2:
                    holder.mFromShopping.setText("来自书屋的评论");
                    book = listData.getJSONObject(position).getJSONObject("book");
                    int star2= book.getJSONArray("comment").getJSONObject(0).getInteger("recommend_exponent");
                    holder.mRatingBar.setStar(star2);
                    holder.mCommentDescribe.setText(listData.getJSONObject(position).getJSONObject("book").getJSONArray("comment").getJSONObject(0).getString("content"));
                    if (holder.mCommentName.getTag() != book){
                        holder.mCommentName.setText(listData.getJSONObject(position).getString("user_name"));
                    }else {
                        holder.mCommentName = null;
                        JSONObject json= (JSONObject) holder.mCommentName.getTag();
                        holder.mCommentName .setText(json.getString("user_name"));;
                    }
                    holder.mCommentName.setTag(book);
                    holder.mCommentName.setText(listData.getJSONObject(position).getJSONObject("book").getJSONArray("comment").getJSONObject(0).getString("user_name"));
                    List<String> pictureList2=new ArrayList<>();
                    //加载图片集合
                    boolean BooknotNull=listData.getJSONObject(position).getJSONObject("book").getJSONArray("comment").getJSONObject(0).getJSONArray("imgs")!=null&&listData.getJSONObject(position).getJSONObject("book").getJSONArray("comment").getJSONObject(0).getJSONArray("imgs").size()!=0;
                    if (BooknotNull){
                        for (int i = 0; i <listData.getJSONObject(position).getJSONObject("book").getJSONArray("comment").getJSONObject(0).getJSONArray("imgs").size() ; i++) {
                            pictureList2.add((String)listData.getJSONObject(position).getJSONObject("book").getJSONArray("comment").getJSONObject(0).getJSONArray("imgs").get(i));
                        }
                        holder.multiImageView.setList(pictureList2);
                    }
                   //去书屋
                    holder.mFromShopping.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(context,BookDetailActivity.class);
                            intent.putExtra("id",listData.getJSONObject(position).getJSONObject("book").getIntValue("id"));
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 3:
                    heartfeeling = listData.getJSONObject(position).getJSONObject("heartfeeling");
                    holder.mCommentDescribe.setText(heartfeeling.getString("title"));
                    if (holder.mCommentName.getTag() != heartfeeling){
                        holder.mCommentName.setText(listData.getJSONObject(position).getString("user_name"));
                    }else {
                        holder.mCommentName = null;
                        JSONObject json= (JSONObject) holder.mCommentName.getTag();
                        holder.mCommentName .setText(json.getString("user_name"));
                    }
                    holder.mCommentName.setTag(heartfeeling);
                    holder.mCommentName.setText(listData.getJSONObject(position).getString("user_name"));
                    holder.mRatingBar.setVisibility(View.GONE);
                    holder.mFromShopping.setVisibility(View.GONE);
                    holder.mFeverNum.setText(listData.getJSONObject(position).getString("praise_num"));
                    holder.mCommentRecommend.setVisibility(View.GONE);
                    Glide.with(context)
                            .load(listData.getJSONObject(position).getString("user_head_img"))
                            .centerCrop()
                            .placeholder(R.drawable.user_head)
                            .into(holder.mCommentPicture);

                    ArrayList<String> pictureList3 = new ArrayList<>();
                    //设置心情图片列表
                    final JSONArray imgArr = listData.getJSONObject(position).getJSONObject("heartfeeling").getJSONArray("imgs");
                    if (imgArr.size()>0){
                        for (int i = 0; i <imgArr.size() ; i++) {
                            pictureList3.add(imgArr.get(i)+"");
                        }
                        final ArrayList imgList = pictureList3;
                        holder.multiImageView.setList(pictureList3);
                        holder.multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() { // 浏览相册
                            @Override
                            public void onItemClick(View view, int position) {
                                //点击图片相应的逻辑处理
    //                            if(builder==null)builder = new CustomDialog.Builder(context);
    //                            builder.create(imgArr).show();
                                Intent intent  =new Intent();
                                intent.setClass(context, ImagePagerActivity.class);
                                intent.putExtra("image_index",position);
                                intent.putExtra("image_urls",imgList);
                                context.startActivity(intent);
                            }
                        });
                    }

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        //设置回复列表
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.mCommendList.setLayoutManager(linearLayoutManager);
        boolean replyNotNull=listData.getJSONObject(position).getJSONArray("dynamic_comment")!=null&&listData.getJSONObject(position).getJSONArray("dynamic_comment").size()!=0;
        if (replyNotNull){
            if (listComment.size()!=0){
                listComment.clear();
            }
            for (int i = 0; i <listData.getJSONObject(position).getJSONArray("dynamic_comment").size() ; i++) {
                listComment.add(JSON.parseObject((listData.getJSONObject(position).getJSONArray("dynamic_comment").get(i)).toString(), CommentReply.class));
            }
            mReplyAdapter= new ReplyAdapter(context,listComment);
            holder.mCommendList.setAdapter(mReplyAdapter);
        }

        //点赞
        holder.mCommentFever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().isLogin()){
                    doFever(holder,position,listData.getJSONObject(position).getString("id"));
                }else {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
        /*
        * 回复
        * */
        holder.mCommentReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().isLogin()){
                    doReply(position);
                }else {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }

            }
        });
    }

    /*
    * 更新Adapter数据
    * */
    public void updateData(JSONArray list){
        listData.clear();
        listData.addAll(list);
        notifyDataSetChanged();


    }

    private void doReply(final int positiion) {
        edittextbody.setVisibility(View.VISIBLE);
        editText.requestFocus();
        editText.setFocusable(true);
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        sendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReply(positiion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    private void doFever(final CircleCommentAdapter.MyViewHolder holder, final int positiion,String id) {
            HashMap hashmap=new HashMap();
            hashmap.put("id",id);
            HttpUtil.okHttpPost(true, hashmap, Api.COMMENT_PRAISE, new HttpCallback() {
                @Override
                public void onFail() {
                }

                @Override
                public void onSuccess(String Data) {
                    int num;
                    if ( FeverList.get(positiion)!=0){
                        num=FeverList.get(positiion);
                        num++;
                        FeverList.set(positiion,num);
                    }else {
                        num= listData.getJSONObject(positiion).getInteger("praise_num")+1;
                        FeverList.set(positiion,num);
                    }
                    ToastUtils.showLongToast("点赞成功");
                    holder.mFeverNum.setText(String.valueOf(num));
                }
            });

    }
    //发送回复
    private void sendReply(final int position) {
        String mReplyComment=editText.getText().toString();
        HashMap<String,String> hashMap=new HashMap<>();
        int id;
        id=listData.getJSONObject(position).getInteger("id");
        hashMap.put("dynamic_id",String.valueOf(id));
        hashMap.put("content",mReplyComment);
        hashMap.put("type","3");
        OkHttpUtils.post()
                .url(Api.COMMENT)
                .params(hashMap)
                .build()
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showLongToast("网络错误" + e);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject json = JSON.parseObject(response);
                    LogUtils.i("你好"+json.getString("data"));
                    if (json.getInteger("code") == 100) {
                        ToastUtils.showLongToast("回复成功");
                        edittextbody.setVisibility(View.GONE);
                        ((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        CommentReply commentReply= new CommentReply();
                        JSONObject jsonObject=JSON.parseObject(response);
                        LogUtils.i("回复测试"+ jsonObject.getJSONObject("data").getInteger("source_id"));
                        LogUtils.i("回复测试"+ jsonObject.getJSONObject("data").getString("content"));
                        LogUtils.i("回复测试"+ jsonObject.getJSONObject("data").getString("user_name"));
                        commentReply.setId(jsonObject.getJSONObject("data").getString("source_id"));
                        commentReply.setContent(jsonObject.getJSONObject("data").getString("content"));
                        commentReply.setUser_name(jsonObject.getJSONObject("data").getString("user_name"));
                        List<CommentReply.ReplyBean>replyBean=new ArrayList<>();
                        commentReply.setReply(replyBean);
                        listComment.add(commentReply);
                        mReplyAdapter.notifyDataSetChanged();

                    } else {
                        ToastUtils.showLongToast(json.getString("msg"));
                    }
                } catch (Exception e) {
                    LogUtils.i("错误测试2"+e);
                    ToastUtils.showLongToast("网络错误" + e);

                }

            }
        });

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mCommentName,mCommentDescribe,mCommentData,mFeverNum,mAction,mCommentRecommend;
        private ImageView mCommentPicture,mCommentFever,mCommentReply;
        private RecyclerView mCommendList;
        private RatingBar mRatingBar;
        private TextView mFromShopping;
        private MultiImageView multiImageView;
        public MyViewHolder(View view) {
            super(view);
            mCommentName=(TextView) view.findViewById(R.id.comment_nickname);
            mCommentDescribe=(TextView) view.findViewById(R.id.comment_describe);
            mCommentData=(TextView) view.findViewById(R.id.comment_data);
            mFeverNum=(TextView) view.findViewById(R.id.comment_people_num);
            mCommentPicture= (ImageView) view.findViewById(R.id.comment_picture);
            mCommendList= (RecyclerView) view.findViewById(R.id.reply_list);
//            mCommentPictureList= (RecyclerView) view.findViewById(R.id.user_picture);
            multiImageView= (MultiImageView) view.findViewById(R.id.user_picture);
            mAction=(TextView) view.findViewById(R.id.comment_action);
            mRatingBar= (RatingBar) view.findViewById(R.id.ratingbar);
            mCommentFever= (ImageView) view.findViewById(R.id.comment_fever);
            mCommentReply= (ImageView) view.findViewById(R.id.comment_follow_up);
            mFromShopping= (TextView) view.findViewById(R.id.comment_from_shopping);
            mCommentRecommend= (TextView) view.findViewById(R.id.comment_recommend);
        }
    }
}

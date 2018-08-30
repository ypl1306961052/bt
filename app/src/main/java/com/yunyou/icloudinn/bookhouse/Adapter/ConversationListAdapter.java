package com.yunyou.icloudinn.bookhouse.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.message.TIMConversationExt;
import com.yunyou.icloudinn.bookhouse.Base.BaseRecyclerAdapter;
import com.yunyou.icloudinn.bookhouse.Base.BaseRecyclerViewHolder;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeTextView;


/**
 * Created by function on 2017/10/13.
 */

public class ConversationListAdapter extends BaseRecyclerAdapter<TIMConversation>{
    private List allConversation = new ArrayList();
    private Activity context;
    private int [] layoutItemId;
    private TIMMessage timMessage;
    private TIMConversationExt timConversationExt;
    private SimpleDateFormat dateFormat,timeFormat;
    private String systemDate,currentDate,currentTime;
    private BGABadgeTextView messageNum;
    private BaseRecyclerViewHolder.OnItemClickListener mOnItemClickListener;
    private boolean isFirst;


    public ConversationListAdapter(Activity context, List list, int[] layoutItemId) {
        super(context, list, layoutItemId);
        this.allConversation = list;
        this.context = context;
        this.layoutItemId = layoutItemId;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutItemId[viewType],parent,false);
        BaseRecyclerViewHolder holder = new BaseRecyclerViewHolder(itemView, context,mOnItemClickListener);

        return holder;
    }

    @Override
    public void setViewHolderData(final BaseRecyclerViewHolder holder, TIMConversation data,int position, int type) {
        //获取聊天用户资料
        //getUserProfile(holder,data);
        getConversationUser(holder,data);
        //获取新消息通知
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                messageNum = (BGABadgeTextView) holder.getItemView(R.id.conversation_list_message_num);
                messageNum.showTextBadge(list.size()+"");
                return false;
            }
        });
        //获取会话
        timConversationExt = new TIMConversationExt(data);
        timConversationExt.getLocalMessage(10, //获取此会话最近的10条消息
                null, //不指定从哪条消息开始获取 - 等同于从最新的消息开始往前
                new TIMValueCallBack<List<TIMMessage>>() {//回调接口
                    @Override
                    public void onError(int code, String desc) {//获取消息失败
                        //接口返回了错误码code和错误描述desc，可用于定位请求失败原因
                        //错误码code含义请参见错误码表
                        LogUtils.d("getLocalMessage", "获取消息失败. code: " + code + " errmsg: " + desc);
                    }

                    @Override
                    public void onSuccess(final List<TIMMessage> msgs) {//获取消息成功
                        //解析会话中的本地消息
                        for (int i=msgs.size()-1;i>=0;i--){
                            timMessage = msgs.get(i);
                            DateTimeFormat(holder);
                            for (int j=0;j<timMessage.getElementCount();j++) {
                                TIMElem elem = timMessage.getElement(j);
                                if (elem.getType()== TIMElemType.Text){
                                    TIMTextElem timTextElem = (TIMTextElem) elem;
                                    holder.setTextView(R.id.conversation_list_message_text,timTextElem.getText());
                                }
                            }
                        }
                        LogUtils.d("getLocalMessage", "获取消息成功 ");
                    }
                });

        }

    //访问接口获取所有聊天用户资料
    private void getConversationUser(final BaseRecyclerViewHolder holder, final TIMConversation data) {

        OkHttpUtils.get().tag(this)
                .url(Api.USER_DETAIL+data.getPeer())
                .build()
                .execute(new JsonCallback<UserData>(context,false) {
                    @Override
                    public void onResponse(UserData userData, int id) {
                        if (userData!=null){
                            if (userData.getNickname()==null||userData.getNickname().isEmpty()){
                                userData.setNickname(userData.getYunsu_id()+"");
                            }
                            holder.setTextView(R.id.conversation_list_use_name,userData.getNickname());
                            holder.setImageView(R.id.conversation_list_head_image,userData.getHead_img_url(),context);
                        }

                        holder.setTextView(R.id.conversation_list_use_name, userData.getNickname());
                        holder.setImageView(R.id.conversation_list_head_image,userData.getHead_img_url(),context);
                    }
                });

    }

    //IMSDK回调获取聊天用户资料
    private void getUserProfile(final BaseRecyclerViewHolder holder, final TIMConversation data) {
        List users = new ArrayList();
        users.add(data.getPeer());
        final String tag = "getUsersProfile";
        TIMFriendshipManager.getInstance().getUsersProfile(users, new TIMValueCallBack<List<TIMUserProfile>>(){
            @Override
            public void onError(int code, String desc){
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                LogUtils.d(tag, "getUsersProfile failed: " + code + " desc"+desc);
            }

            @Override
            public void onSuccess(List<TIMUserProfile> result){
                LogUtils.d(tag, "getUsersProfile succ");
                for(TIMUserProfile res : result){
                    LogUtils.d(tag, "identifier: " + res.getIdentifier() + " nickName: " + res.getNickName()
                            + " remark: " + res.getRemark());
                    holder.setTextView(R.id.conversation_list_use_name,res.getNickName());
                    holder.setImageView(R.id.conversation_list_head_image,res.getFaceUrl(),context);
                }
            }
        });
    }

    //格式化消息时间日期格式
    private void DateTimeFormat(BaseRecyclerViewHolder holder) {
        dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        timeFormat = new SimpleDateFormat("HH:mm" );
        Date curDate = new Date(System.currentTimeMillis());
        //系统日期
        systemDate = dateFormat.format(curDate);
        //当前消息的日期
        currentDate = dateFormat.format(timMessage.timestamp()*1000);
        currentTime = timeFormat.format(timMessage.timestamp()*1000);
        //设置消息时间
        if (currentDate.equals(systemDate)){
            holder.setTextView(R.id.conversation_list_message_time,currentTime);
        }else {
            holder.setTextView(R.id.conversation_list_message_time,currentDate);
        }

    }


    /*
    * 更新数据列表
    * */
    public void upData(List data){
        this.allConversation.clear();
        allConversation.addAll(data);
        notifyDataSetChanged();
    }
    /*
    * 添加数据列表
    * */
    public void addDataAll(List data){
        allConversation.addAll(data);
        notifyDataSetChanged();
    }
    /*
    * 设置监听事件
    * */
    public void setOnItemClickListener(BaseRecyclerViewHolder.OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

}

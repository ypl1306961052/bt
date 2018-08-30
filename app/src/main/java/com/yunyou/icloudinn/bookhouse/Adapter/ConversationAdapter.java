package com.yunyou.icloudinn.bookhouse.Adapter;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMTextElem;
import com.yunyou.icloudinn.bookhouse.Base.BaseRecyclerAdapter;
import com.yunyou.icloudinn.bookhouse.Base.BaseRecyclerViewHolder;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by function on 2017/10/9.
 */

public class ConversationAdapter extends BaseRecyclerAdapter<TIMMessage> {
    public List<TIMMessage> messageList= new ArrayList();
    public Context mContext;
    private UserData loginUser,attentionUser;
    public int [] layoutId;
    private int itemType;
    private TextView textView_left;
    private TextView textView_right;
    private SimpleDateFormat dateFormat,timeFormat;
    private String currentDate,currentTime,systemDate;
    private TIMTextElem timTextElem;


    public ConversationAdapter(Context context, List messageList,UserData user, int[] layoutItemId) {
        super(context, messageList, layoutItemId);
        this.mContext = context;
        this.layoutId = layoutItemId;
        this.messageList = messageList;
        this.attentionUser = user;
        loginUser = MyApplication.getInstance().getUser();
    }


    @Override
    public void setViewHolderData(BaseRecyclerViewHolder holder, TIMMessage data,int position, int type) {
        //获取屏幕宽度（像素 ）
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        textView_left = (TextView) holder.getItemView(R.id.textView_left);
        textView_right = (TextView) holder.getItemView(R.id.textView_right);
        //格式化化时间日起格式
        dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        timeFormat = new SimpleDateFormat("HH:mm" );
        Date curDate = new Date(System.currentTimeMillis());
        //系统日期
        systemDate = dateFormat.format(curDate);
        //当前消息的日期
        currentDate =dateFormat.format(data.timestamp()*1000);
        currentTime = timeFormat.format(data.timestamp()*1000);

        //显示发送失败
        if (data.status().getStatus()==3){
            TextView message_status = (TextView) holder.getItemView(R.id.message_status);
            message_status.setVisibility(View.VISIBLE);
            message_status.setText("发送失败");
            holder.setTextView(R.id.conversation_message_time,"");
        }else {
            //设置消息时间
            if (currentDate.equals(systemDate)){
                holder.setTextView(R.id.conversation_message_time,""+currentTime);
            }else {
                holder.setTextView(R.id.conversation_message_time,""+currentDate);
            }
        }
        //获取消息
        for (int i=0;i<data.getElementCount();i++) {
            timTextElem = (TIMTextElem) data.getElement(i);
        }

        switch (type){
            case 0: // 自己
                String myHeadImage = loginUser.getHead_img_url();
                holder.setImageView(R.id.conversation_head_img_my,myHeadImage,mContext);
                //设置最大宽度
                textView_left.setMaxWidth(screenWidth/2);
                //加载消息
                holder.setTextView(R.id.textView_left, timTextElem.getText());
                break;
            case 1: // 对方
                String othersHeadImage =  attentionUser.getHead_img_url();
                holder.setImageView(R.id.conversation_head_img_others,othersHeadImage,mContext);
                textView_right.setMaxWidth(screenWidth/2);
                holder.setTextView(R.id.textView_right,timTextElem.getText());
                break;
        }
    }

    /*
    * 返回item的类型
    * @position：item位置
    * */
    @Override
    public int getItemViewType(int position) {
         TIMMessage message =  messageList.get(position);
        if (message.isSelf()){
            itemType = 0;
        }else {
            itemType = 1;
        }
        return itemType;
    }
    /*
    * 更新数据列表
    * */
    public void upData(List data){
        this.messageList.clear();
        messageList.addAll(data);
        notifyDataSetChanged();
    }
    /*
    * 添加数据列表
    * */
    public void addDataAll(List data){
        messageList.addAll(data);
        notifyDataSetChanged();
    }
    /*
    * 添加消息对象
    * */
    public void addData(TIMMessage data){
        messageList.add(data);
        notifyDataSetChanged();
    }
}

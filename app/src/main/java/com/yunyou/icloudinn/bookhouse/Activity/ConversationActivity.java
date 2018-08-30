package com.yunyou.icloudinn.bookhouse.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.message.TIMConversationExt;
import com.tencent.imsdk.ext.message.TIMManagerExt;
import com.umeng.socialize.utils.Log;
import com.yunyou.icloudinn.bookhouse.Adapter.ConversationAdapter;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;


public class ConversationActivity extends BaseActivity {

    private UserData loginUser,attentionUser;
    private String showText;
    private TIMConversation timConversation;
    private TIMConversationExt timConversationExt;


    private EditText edit_message;
    private Button button_send;
    private RecyclerView recyclerView;
    private ConversationAdapter adapter;
    private List conversationMessage = new ArrayList();
    private List conversationUser = new ArrayList();
    private int layoutId[] = new int[]{R.layout.list_item_conversation_left,R.layout.list_item_conversation_right};
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        getConversationUser();
        initConversation();
        initView();
    }

    //聊天用户
    private void getConversationUser() {
        loginUser = MyApplication.getInstance().getUser();
        attentionUser = (UserData) getIntent().getSerializableExtra("user");

        OkHttpUtils.get().tag(this)
                .url(Api.USER_LIST+"/"+attentionUser.getYunsu_id())
                .build()
                .execute(new JsonCallback<UserData>(this, true) {
                    @Override
                    public void onResponse(final UserData user, int id) {
                        attentionUser = user;
                        conversationUser.add(user);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                title.setText(attentionUser.getNickname());
                            }
                        });
                    }
                });
    }

    private void initView() {
        edit_message = (EditText) findViewById(R.id.edit_message);
        button_send = (Button) findViewById(R.id.button_send);
        recyclerView = (RecyclerView) findViewById(R.id.listView_conversation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new ConversationAdapter(this,conversationMessage,attentionUser,layoutId);
        View mBack=findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        title = (TextView) findViewById(R.id.title);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取发送消息
                showText = edit_message.getText().toString();
                if (showText.length()!=0){
                    //执行发送消息
                    sendMessage();
                    //发送完消息设置为空
                    edit_message.setText("");
                }
            }
        });

    }

    //初始化聊天界面
    private void initConversation() {
        //初始化无网络时加载本地聊天消息
        if(loginUser==null||attentionUser==null){
            ToastUtils.showLongToast("聊天用户不能为空！");
            return;
        }
        TIMManagerExt.getInstance().initStorage(loginUser.getYunsu_id()+"", new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.e("getLocalMessage", "初始化本地存储失败, code: " + code + "|descr: " + desc);
            }

            @Override
            public void onSuccess() {
                Log.i("getLocalMessage", "初始化本地存储成功");
            }
        });

        timConversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C,attentionUser.getYunsu_id()+"");
        //获取此会话的消息
        timConversationExt = new TIMConversationExt(timConversation);
        timConversationExt.getLocalMessage(20, //获取此会话最近的10条消息
                null, //不指定从哪条消息开始获取 - 等同于从最新的消息开始往前
                new TIMValueCallBack<List<TIMMessage>>() {//回调接口
                    @Override
                    public void onError(int code, String desc) {//获取消息失败
                        //接口返回了错误码code和错误描述desc，可用于定位请求失败原因
                        //错误码code含义请参见错误码表
                        LogUtils.d("getLocalMessage", "获取消息失败. code: " + code + " errmsg: " + desc);
                    }

                    @Override
                    public void onSuccess(List<TIMMessage> msgs) {//获取消息成功

                        for (int i=msgs.size()-1;i>=0;i--){
                            TIMMessage message = msgs.get(i);
                            adapter.addData(message);
                        }
                        recyclerView.setAdapter(adapter);
                        recyclerView.scrollToPosition(conversationMessage.size()-1);
                        LogUtils.d("getLocalMessage", "获取消息成功 ");
                    }
                });


        //获取新消息通知
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                adapter.addDataAll(list);
                recyclerView.scrollToPosition(conversationMessage.size()-1);

                return false;
            }
        });


    }

    //聊天发送消息
    private void sendMessage() {
        final TIMMessage mMessage = new TIMMessage();
        final TIMTextElem timTextElem = new TIMTextElem();
        showText = edit_message.getText().toString();
        timTextElem.setText(showText);
        mMessage.addElement(timTextElem);
        //获取和某个用户的对话，peer对方用户名
        timConversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C,attentionUser.getYunsu_id()+"");
        //发送消息
        timConversation.sendMessage(mMessage, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int code, String s) {
                LogUtils.d("sendMessage","发送消息失败 "+s+",code="+code);
                if(code==6011){
                    loadUserToIm(attentionUser.getYunsu_id()+"",mMessage);
                }else{
                    //adapter.addData(mMessage);
                    recyclerView.setAdapter(adapter);
                    recyclerView.scrollToPosition(conversationMessage.size()-1);
                }

            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                LogUtils.d("sendMessage","发送消息成功 ");
                adapter.addData(timMessage);
                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(conversationMessage.size()-1);
            }
        });
    }

    /**
     * 导入并重新发送信息
     * @param userId
     * @param mMessage
     */
    private void loadUserToIm(String userId,final TIMMessage mMessage){
        OkHttpUtils.get().url(Api.USER_LOAD_TO_IM+userId).build().execute(new JsonCallback<String>(this,true) {
            @Override
            public void onResponse(String data, int id) {
                if(data=="true")
                timConversation.sendMessage(mMessage, new TIMValueCallBack<TIMMessage>() {

                    @Override
                    public void onError(int i, String s) {
                        adapter.addData(mMessage);
                        recyclerView.setAdapter(adapter);
                        recyclerView.scrollToPosition(conversationMessage.size()-1);
                    }

                    @Override
                    public void onSuccess(TIMMessage timMessage) {
                        LogUtils.d("sendMessage","发送消息成功 ");
                        adapter.addData(timMessage);
                        recyclerView.setAdapter(adapter);
                        recyclerView.scrollToPosition(conversationMessage.size()-1);
                    }
                });
            }
        });
    }

}

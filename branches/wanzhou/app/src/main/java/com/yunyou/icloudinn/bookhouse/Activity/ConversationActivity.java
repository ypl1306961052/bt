package com.yunyou.icloudinn.bookhouse.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.message.TIMConversationExt;
import com.tencent.imsdk.ext.message.TIMManagerExt;
import com.tencent.imsdk.protocol.im_common;
import com.umeng.socialize.utils.Log;
import com.yunyou.icloudinn.bookhouse.Adapter.ConversationAdapter;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.IMsigData;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSHelper;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSPwdLoginListener;
import tencent.tls.platform.TLSUserInfo;

public class ConversationActivity extends AppCompatActivity {
    private MyApplication instance = MyApplication.getInstance();
    private UserData user;
    private String userSig ,userName ="admin";
    private String showText;
    private String peer = "admin"; //聊天对方用户名
    private TIMConversation timConversation;
    private TIMConversationExt timConversationExt;
    private TLSLoginHelper tlsLoginHelper = TLSLoginHelper.getInstance();

    private EditText edit_message;
    private Button button_send;
    private TextView showMessage;
    private RecyclerView recyclerView;
    private ConversationAdapter adapter;
    private List conversationList = new ArrayList();
    private int layoutId[] = new int[]{R.layout.list_item_conversation_left,R.layout.list_item_conversation_right};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        //初始化IM的SDK
        TIMManager.getInstance().init(this,new TIMSdkConfig(1400011424));
        initData();
        initView();
        initConversation();

    }
    private void initView() {
        edit_message = (EditText) findViewById(R.id.edit_message);
        button_send = (Button) findViewById(R.id.button_send);
        showMessage = (TextView) findViewById(R.id.showMessage);
        recyclerView = (RecyclerView) findViewById(R.id.listView_conversation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new ConversationAdapter(this,conversationList,layoutId);
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
        //初始化地聊天消息
        TIMManagerExt.getInstance().initStorage(userName, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.e("getLocalMessage", "initStorage failed, code: " + code + "|descr: " + desc);
            }

            @Override
            public void onSuccess() {
                Log.i("getLocalMessage", "initStorage succ");
            }
        });
        timConversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C,peer);
        //获取此会话的消息
        timConversationExt = new TIMConversationExt(timConversation);
        timConversationExt.getLocalMessage(10, //获取此会话最近的10条消息
                null, //不指定从哪条消息开始获取 - 等同于从最新的消息开始往前
                new TIMValueCallBack<List<TIMMessage>>() {//回调接口
                    @Override
                    public void onError(int code, String desc) {//获取消息失败
                        //接口返回了错误码code和错误描述desc，可用于定位请求失败原因
                        //错误码code含义请参见错误码表
                        LogUtils.d("getLocalMessage", "get message failed. code: " + code + " errmsg: " + desc);
                    }

                    @Override
                    public void onSuccess(final List<TIMMessage> msgs) {//获取消息成功
                        for (int i=msgs.size()-1;i>=0;i--){
                            TIMMessage message = msgs.get(i);
                            adapter.addData(message);
                        }
                        recyclerView.setAdapter(adapter);
                        recyclerView.scrollToPosition(conversationList.size()-1);
                    }
                });


        //获取新消息通知
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                adapter.addDataAll(list);
                recyclerView.scrollToPosition(conversationList.size()-1);

                return false;
            }
        });


    }

    private void initTIMLogin() {
     TIMManager.getInstance().login(userName, userSig, new TIMCallBack() {
         @Override
         public void onError(int i, String s) {
             LogUtils.d("TIMLogin","聊天未登录 "+ i + s);
         }

         @Override
         public void onSuccess() {
             LogUtils.d("TIMLogin","聊天登录成功 ");
             userName = TIMManager.getInstance().getLoginUser();

         }
     });
    }

    //聊天发送消息
    private void sendMessage() {
        TIMMessage mMessage = new TIMMessage();
        final TIMTextElem timTextElem = new TIMTextElem();
        showText = edit_message.getText().toString();
        timTextElem.setText(showText);
        mMessage.addElement(timTextElem);
        //获取和某个用户的对话，peer对方用户名
        timConversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C,peer);
        //发送消息
        timConversation.sendMessage(mMessage, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int i, String s) {
                LogUtils.d("sendMessage","发送消息失败 "+s);
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                LogUtils.d("sendMessage","发送消息成功 ");
                for (int i=0; i< timMessage.getElementCount();i++){
                    TIMTextElem elem = (TIMTextElem) timMessage.getElement(i);
                    //showMessage.setText(timTextElem.getText());
                    adapter.addData(timMessage);
                    recyclerView.setAdapter(adapter);
                    recyclerView.scrollToPosition(conversationList.size()-1);
                }



            }
        });
    }

    //初始化数据，获取签名
    private void initData() {
        //获取用户签名
        OkHttpUtils.get() .url(Api.IM_USERSIG)
                .addParams("account","admin")
                .build()
                .execute(new JsonCallback<JSONObject>(this,true) {
                    @Override
                    public void onResponse(JSONObject data, int id) {

                            userSig = data.getString("imsig");
                            LogUtils.d("TIMLogin",userSig);
                            initTIMLogin();
                    }
                });
    }
}

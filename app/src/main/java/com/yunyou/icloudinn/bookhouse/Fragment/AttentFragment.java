package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.ext.message.TIMManagerExt;
import com.yunyou.icloudinn.bookhouse.Activity.ConversationActivity;
import com.yunyou.icloudinn.bookhouse.Adapter.ConversationListAdapter;
import com.yunyou.icloudinn.bookhouse.Base.BaseRecyclerViewHolder;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.PageData;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.IMLoginUtils;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

public class AttentFragment extends BaseFragment implements View.OnClickListener {
    private MyApplication instance;
    private String userName,userSig,headImage;
    private TextView mAttentionTo,mToAttention;
    private RecyclerView conversationListView;
    private List<TIMConversation> allConversation = new ArrayList<>();
    private int[] layoutItem = new int[]{R.layout.list_item_conversation_list};
    private static final int  ERR_IMSDK_KICKED_BY_OTHERS=6028;//IM聊天SDK在其他设备登录返回码
    private Handler handlerUser,handlerMessage;


    public static AttentFragment getInstance(){
        return new AttentFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        instance = MyApplication.getInstance();
        initMyView(view);
        initData();
    }
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        ToastUtils.showLongToast("登录返回");
    }
    // TODO: 2017/10/12 所有会话列表待实现
    private void initConversationList() {
        //获取所有会话列表
        allConversation = TIMManagerExt.getInstance().getConversationList();
        ConversationListAdapter adapter = new ConversationListAdapter(getActivity(),allConversation,layoutItem);
        conversationListView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final String peer = allConversation.get(position).getPeer();
                if(peer==null||peer==""){
                    ToastUtils.showLongToast("用户无效，用户ID="+peer);
                    return;
                }else {
                   // ToastUtils.showLongToast("用户ID="+peer);
                }
                getUserData(peer);
                handlerUser = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what==1){
                            Intent intent = new Intent(getActivity(), ConversationActivity.class);
                            UserData userData= new UserData();
                            userData.setYunsu_id(Integer.parseInt(peer));
                            userData.setHead_img_url(headImage);
                            intent.putExtra("user",userData);
                            startActivity(intent);
                        }
                    }
                };
            }
        });


        //获取新消息通知
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                conversationListView.scrollToPosition(0);
                return false;
            }
        });
    }

    private void initData() {
        initAttentionMeData();
        initMeAttentionData();
        if (instance.isLogin()==true){
            //initConversation();
            IMLoginUtils.initConversation(getHoldingActivity(),MyApplication.getInstance().getUser(),handlerMessage);
        }
    }
    /*
    * 获取用户签名，登录聊天
    * */
    private void initConversation() {
        UserData userData = new UserData();
        userData.setYunsu_id(instance.getUser().getYunsu_id());
        int yunsu_id = userData.getYunsu_id();
        userName =String.valueOf(yunsu_id);
        OkHttpUtils.get() .url(Api.IM_USERSIG)
                .addParams("account", userName)
                .build()
                .execute(new JsonCallback<JSONObject>(getActivity(),true) {
                    @Override
                    public void onResponse(JSONObject data, int id) {
                        userSig = data.getString("imsig");
                        if (userSig==null){
                            return;
                        }
                        LogUtils.d("TIMLogin",userSig.toString());
                        //聊天登录
                        TIMManager.getInstance().login(userName, userSig, new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                LogUtils.d("TIMLogin","聊天未登录 "+ i + s);
                                if (i==ERR_IMSDK_KICKED_BY_OTHERS){
                                    ToastUtils.showShortToast("用户在其他设备登录");
                                }
                            }

                            @Override
                            public void onSuccess() {
                                LogUtils.d("TIMLogin","聊天登录成功 ");
                                Message message =  new Message();
                                message.what=2;
                                handlerMessage.sendMessage(message);
                            }
                        });
                    }
                });
    }
    //获取用户
    public void getUserData(String peer) {
        OkHttpUtils.get().tag(getActivity())
                .url(Api.USER_DETAIL+"/"+peer)
                .build()
                .execute(new JsonCallback<UserData>(getActivity(), false) {
                    @Override
                    public void onResponse(UserData user, int id) {
                        headImage = user.getHead_img_url();
                        Message msg = new Message();
                        msg.what = 1;
                        handlerUser.sendMessage(msg);
                    }
                });
    }

    private void initAttentionMeData() {
        OkHttpUtils.get().tag(getActivity())
                .url(Api.ATTENTION)
                .addParams("type","0")
                .build()
                .execute(new JsonCallback<PageData<String>>(getActivity(), true) {
                    @Override
                    public void onResponse(PageData<String> page, int id) {
                        if(page!=null) {
                            mAttentionTo.setText("共关注" + page.getData().size() + "位友人");
                        }
                    }
                });

    }

    private void initMeAttentionData(){
        OkHttpUtils.get().tag(getActivity())
                .url(Api.ATTENTION)
                .addParams("type","1")
                .build()
                .execute(new JsonCallback<PageData<String>>(getActivity(), true) {
                    @Override
                    public void onResponse(PageData<String> page, int id) {
                        if(page!=null){
                            mToAttention.setText("共" + page.getData().size() + "人关注我");
                        }
                    }
                });

    }

    private void initMyView(View view) {
        StatusBarCompat.compat(getActivity(), getResources().getColor(R.color.green1));
        View mBack=view.findViewById(R.id.back);
        TextView title= (TextView) view.findViewById(R.id.title);
        mAttentionTo= (TextView) view.findViewById(R.id.attention_to_num);
        mToAttention= (TextView) view.findViewById(R.id.to_attention_num);
        View mAttentionTo=view.findViewById(R.id.attention_to_layout);
        View mToAttionTo=view.findViewById(R.id.to_attention_layout);
        conversationListView = (RecyclerView) view.findViewById(R.id.conversation_list);
        conversationListView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mBack.setOnClickListener(this);
        mAttentionTo.setOnClickListener(this);
        mToAttionTo.setOnClickListener(this);
        title.setText("我的消息");
        handlerMessage = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==2){
                    initConversationList();
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        if(MyApplication.getInstance().isLogin()){
            initConversationList();
        }else {
            getActivity().finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attention;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                removeFragment();
                break;
            case R.id.attention_to_layout:
                addNewFragment(MineAttentionFragment.getInstance(true));
                break;
            case R.id.to_attention_layout:
                addNewFragment(MineAttentionFragment.getInstance(false));
                break;
        }
    }

}

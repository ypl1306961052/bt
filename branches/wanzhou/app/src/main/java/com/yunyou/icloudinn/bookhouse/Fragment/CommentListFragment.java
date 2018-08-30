package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunyou.icloudinn.bookhouse.Activity.LoginActivity;
import com.yunyou.icloudinn.bookhouse.Adapter.CommentListAdapter;
import com.yunyou.icloudinn.bookhouse.Callback.CommentCallBack;
import com.yunyou.icloudinn.bookhouse.Callback.HttpCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.HouseDetailData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Ui.MultiImageView;
import com.yunyou.icloudinn.bookhouse.Util.HttpUtil;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class CommentListFragment extends BaseFragment implements View.OnClickListener {

    private List<HouseDetailData.DataBean.CommentBean>commentBean=new ArrayList<>();
    private LinearLayout edittextbody;
    private EditText editText;
    private ImageView sendIv;
    private CustomProgress customProgress;
    private String mReplyComment,mReplynName;
    private int mReplyId,replyPostion;
    private CommentListAdapter commentListAdapter;
    private int houseId;
    private HouseDetailData houseDetailData;
    private MultiImageView multiImageView;
    public static CommentListFragment getInstance(HouseDetailData.DataBean commentBean){
        Bundle bundle=new Bundle();
        bundle.putSerializable("CommentBean",commentBean);
        CommentListFragment calendarSelectorFragment =new CommentListFragment();
        calendarSelectorFragment.setArguments(bundle);
        return calendarSelectorFragment;
    }
    public static CommentListFragment getInstance(int houseId){
        Bundle bundle=new Bundle();
        bundle.putInt("houseId",houseId);
        CommentListFragment calendarSelectorFragment =new CommentListFragment();
        calendarSelectorFragment.setArguments(bundle);
        return calendarSelectorFragment;
    }
    private RecyclerView mCommentList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            houseId=getArguments().getInt("houseId");
            if (houseId==0){
                HouseDetailData.DataBean data = (HouseDetailData.DataBean) getArguments().getSerializable("CommentBean");
                data.getThumb();
                commentBean.addAll(data.getComment());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i("回复列表测试----"+houseId);
        if (houseId!=0){
            initData();
        }
    }

    private void initData() {
        customProgress.show();
        LogUtils.i("网络测试"+Api.HOUSE_DETAIL+houseId);
        OkHttpUtils.get().url(Api.HOUSE_DETAIL+houseId).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showLongToast("网络错误" + e);
                customProgress.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                customProgress.dismiss();
                try {
                    JSONObject json = JSON.parseObject(response);
                    if (json.getInteger("code") == 100){
                        houseDetailData=JSON.parseObject(response,HouseDetailData.class);
                        commentBean=houseDetailData.getData().getComment();
                        initAdapter();
                    }else {
                        ToastUtils.showLongToast(json.getString("msg"));
                    }
                }catch (Exception e){
                    ToastUtils.showLongToast("网络错误" + e);
                }
            }
        });
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        customProgress=new CustomProgress(getHoldingActivity(),R.style.Custom_Progress,"加载中",false);

        View mBack=view.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        TextView title= (TextView) view.findViewById(R.id.title);
        title.setText("回复列表");
        mCommentList= (RecyclerView) view.findViewById(R.id.house_comment_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getHoldingActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        edittextbody = (LinearLayout)view.findViewById(R.id.editTextBodyLl);
        editText = (EditText)view.findViewById(R.id.circleEt);
        sendIv = (ImageView)view.findViewById(R.id.sendIv);
        sendIv.setOnClickListener(this);
        mCommentList.setLayoutManager(linearLayoutManager);
        mCommentList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (edittextbody.getVisibility() == View.VISIBLE) {
                    edittextbody.setVisibility(View.GONE);
                    ((InputMethodManager)getHoldingActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getHoldingActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
        if (houseId==0){
            initAdapter();
        }
    }

    private void initAdapter() {
        //数据来源民宿详情
        commentListAdapter=new CommentListAdapter(commentBean, getHoldingActivity(), customProgress, new CommentCallBack() {
            @Override
            public void OnClikComment(int id,String username,int position) {
                mReplyId=id;
                replyPostion=position;
                mReplynName=username;
                edittextbody.setVisibility(View.VISIBLE);
                editText.requestFocus();
                editText.setFocusable(true);
                InputMethodManager imm = (InputMethodManager)getHoldingActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void OnClikReply() {

            }
        });
        mCommentList.setAdapter(commentListAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment_list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                removeFragment();
                break;
            case R.id.sendIv:
                if (MyApplication.getInstance().isLogin()){
                    sendReply();
                }else {
                    startActivity(new Intent(getHoldingActivity(), LoginActivity.class));
                }
                break;
        }
    }

    private void sendReply() {
        mReplyComment=editText.getText().toString();
        HashMap<String,String>hashMap=new HashMap<>();
        hashMap.put("comment_id",String.valueOf(mReplyId));
        hashMap.put("to_user_name",String.valueOf(mReplynName));
        hashMap.put("reply",String.valueOf(mReplyComment));
        customProgress.dismiss();
        HttpUtil.okHttpPost(true, hashMap, Api.REPLY, new HttpCallback() {
            @Override
            public void onFail() {
                customProgress.show();
            }

            @Override
            public void onSuccess(String Data) {
                ToastUtils.showLongToast("回复成功");
                edittextbody.setVisibility(View.GONE);
                ((InputMethodManager)getHoldingActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getHoldingActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                customProgress.dismiss();
                showContent();
            }
        });
    }

    private void showContent() {
        HouseDetailData.DataBean.CommentBean.ReplyBean replyBean=new HouseDetailData.DataBean.CommentBean.ReplyBean();
        replyBean.setReply(mReplyComment);
        replyBean.setUser_name(MyApplication.getInstance().getUser().getData().getNickname());
        replyBean.setTo_user_name("");
        LogUtils.i("回复测试-----"+mReplyComment+"----------"+MyApplication.getInstance().getUser().getData().getNickname());
        commentBean.get(replyPostion).getReply().add(replyBean);
        commentListAdapter.notifyDataSetChanged();
    }
}

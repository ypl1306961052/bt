package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yunyou.icloudinn.bookhouse.Activity.LoginActivity;
import com.yunyou.icloudinn.bookhouse.Adapter.BookCommentAdapter;
import com.yunyou.icloudinn.bookhouse.Callback.CommentCallBack;
import com.yunyou.icloudinn.bookhouse.Callback.HttpCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.BookDetailData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Util.HttpUtil;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;

import java.util.HashMap;

public class BookDetailCommentFramgnet extends Fragment implements View.OnClickListener {
    private BookDetailData bookDetailData;
    private LinearLayout edittextbody;
    private EditText editText;
    private ImageView sendIv;
    private CustomProgress customProgress;
    private String mReplyComment,mReplynName;
    private int mReplyId,replyPostion;
    private BookCommentAdapter bookCommentAdapter;
    private RecyclerView mCommentList;

    public static BookDetailCommentFramgnet getInstance(BookDetailData bookDetailData){
        BookDetailCommentFramgnet bookDetailCommentFramgnet=new BookDetailCommentFramgnet();
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",bookDetailData);
        bookDetailCommentFramgnet.setArguments(bundle);
        return bookDetailCommentFramgnet;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookDetailData= (BookDetailData) getArguments().getSerializable("data");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_comment_list,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        customProgress=new CustomProgress(getActivity(),R.style.Custom_Progress,"加载中",false);
        mCommentList= (RecyclerView) view.findViewById(R.id.house_comment_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        edittextbody = (LinearLayout)view.findViewById(R.id.editTextBodyLl);
        editText = (EditText)view.findViewById(R.id.circleEt);
        sendIv = (ImageView)view.findViewById(R.id.sendIv);
        View top=view.findViewById(R.id.commment_top);
        top.setVisibility(View.GONE);
        sendIv.setOnClickListener(this);
        mCommentList.setLayoutManager(linearLayoutManager);
        mCommentList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (edittextbody.getVisibility() == View.VISIBLE) {
                    edittextbody.setVisibility(View.GONE);
                    ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
        bookCommentAdapter=new BookCommentAdapter(bookDetailData.getData().getComment(),getActivity(), customProgress, new CommentCallBack() {
            @Override
            public void OnClikComment(int id,String username,int position) {
                mReplyId=id;
                replyPostion=position;
                mReplynName=username;
                edittextbody.setVisibility(View.VISIBLE);
                editText.requestFocus();
                editText.setFocusable(true);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void OnClikReply() {

            }
        });
        mCommentList.setAdapter(bookCommentAdapter);
    }

    private void sendReply() {
        mReplyComment=editText.getText().toString();
        HashMap<String,String> hashMap=new HashMap<>();
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
                ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                customProgress.dismiss();
                showContent();
            }
        });
    }

    private void showContent() {
        BookDetailData.DataBean.CommentBean.ReplyBean replyBean=new BookDetailData.DataBean.CommentBean.ReplyBean();
        replyBean.setReply(mReplyComment);
        replyBean.setUser_name(MyApplication.getInstance().getUser().getNickname());
        replyBean.setTo_user_name("");
        LogUtils.i("回复测试-----"+mReplyComment+"----------"+MyApplication.getInstance().getUser().getNickname());
        bookDetailData.getData().getComment().get(replyPostion).getReply().add(replyBean);
        bookCommentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendIv:
                if (MyApplication.getInstance().isLogin()){
                    sendReply();
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
        }
    }
}

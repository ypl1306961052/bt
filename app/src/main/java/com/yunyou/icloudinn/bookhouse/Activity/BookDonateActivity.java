package com.yunyou.icloudinn.bookhouse.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocationClientOption;
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.DonateBookItem;
import com.yunyou.icloudinn.bookhouse.JavaBean.DonateJson;
import com.yunyou.icloudinn.bookhouse.LoginUtil.LoginUtil;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;

public class BookDonateActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView list;
    private List<DonateBookItem>listData;
    private CommonAdapter<DonateBookItem>mAdapter;
    private EditText userPhone;
    private int REQUEST_IMAGE=10000;
    private int currentPostion=0;
    private List<String>pictureId=new ArrayList<>();
    private CustomProgress customProgress;
    public AMapLocationClientOption mLocationOption = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_donate);
        initView();
    }

    private void initView() {
        customProgress=new CustomProgress(this,R.style.Custom_Progress,"加载中",false);
        StatusBarCompat.compat(this, getResources().getColor(R.color.green1));
        list= (RecyclerView) findViewById(R.id.book_donate_list);
        View back=findViewById(R.id.back);
        TextView title= (TextView) findViewById(R.id.title);
        View add=findViewById(R.id.book_donate_add);
        View compete=findViewById(R.id.donate_book_compete);
        userPhone= (EditText) findViewById(R.id.donate_book_phone);
        title.setText("捐书");
        add.setOnClickListener(this);
        compete.setOnClickListener(this);
        back.setOnClickListener(this);
        title.setOnClickListener(this);
        initList();
    }

    private void initList() {
        listData=new ArrayList<>();
        listData.add(new DonateBookItem());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);
        mAdapter=new CommonAdapter<DonateBookItem>(this,R.layout.list_item_book_donate,listData) {
            @Override
            protected void convert(ViewHolder holder, final DonateBookItem donateBookItem, final int position) {
                //图片选择
                holder.getView(R.id.donate_book_select_picture).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentPostion=position;
                        initSelect();
                    }
                });
                if (donateBookItem.getCover()!=null&&(!donateBookItem.getCover().isEmpty())){
                    ( holder.getView(R.id.donate_book_book_picture)).setVisibility(View.VISIBLE);
                }
                //图片加载
                Glide.with(BookDonateActivity.this)
                        .load(donateBookItem.getCover())
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.donate_book_book_picture));
                ((EditText)holder.getView(R.id.book_donate_title)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        donateBookItem.setBook_name(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }

        };
        list.setAdapter(mAdapter);
    }

    private void initSelect() {
        MultiImageSelector.create(this).showCamera(true).single().start(this,REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){
            if(resultCode == RESULT_OK){
                // 获取返回的图片列表
                List<String> path=new ArrayList<>();
                path.addAll(data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT)) ;
                // 处理你自己的逻辑 ....
                upLoadPicture(path.get(0));
                listData.get(currentPostion).setCover(path.get(0));
                mAdapter.notifyDataSetChanged();
                for ( String a:path) {
                    LogUtils.i("图片选择"+a);
                }
            }
        }
    }

    //上传图片
    private void upLoadPicture(String picture) {
        customProgress.show();
        File pictureFile=new File(picture);
        OkHttpUtils.post()
                .addFile("file",picture,pictureFile)
                .url(Api.UPLOAD_PICTURE)
                .build()
                .execute(new StringCallback() {
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
                            if (json.getInteger("code") == 100) {
                                pictureId.add(json.getString("data"));
                                ToastUtils.showLongToast("上传完成");
                            } else {
                                ToastUtils.showLongToast(json.getString("msg"));

                            }
                        } catch (Exception e) {
                            ToastUtils.showLongToast("网络错误" + e);

                        }
                    }
                });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.book_donate_add :
                listData.add(new DonateBookItem());
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.donate_book_compete:
                checkBook();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void checkBook() {

        switch (LoginUtil.isInputLegal(userPhone.getText().toString())){
            case 0:
                DonateJson donateJson=new DonateJson();
                donateJson.setPhone(userPhone.getText().toString());
                //替换图片
                for (int i = 0; i <pictureId.size() ; i++) {
                    if (listData.get(i).getCover().isEmpty()||listData.get(i).getBook_name().isEmpty()){
                        ToastUtils.showLongToast("请输入完整的数据");
                        return;
                    }else {
                        listData.get(i).setCover(pictureId.get(i));
                    }

                }
                donateJson.setList(listData);
                LogUtils.i("捐书数据"+JSON.toJSONString(donateJson));
                customProgress.show();
                OkHttpUtils.postString()
                        .url(Api.DONATE_BOOK)
                        .content(JSON.toJSONString(donateJson))
                        .build()
                        .execute(new StringCallback() {
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
                                    if (json.getInteger("code") == 100) {
                                        ToastUtils.showLongToast("捐赠成功");
                                        LogUtils.i("捐书成功----"+response);
                                        finish();
                                    } else {
                                        ToastUtils.showLongToast(json.getString("msg"));
                                    }
                                } catch (Exception e) {

                                    ToastUtils.showLongToast("网络错误" + e);

                                }
                            }
                        });
                break;
        }

    }
}

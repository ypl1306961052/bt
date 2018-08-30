package com.yunyou.icloudinn.bookhouse.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.hedgehog.ratingbar.RatingBar;
import com.yunyou.icloudinn.bookhouse.Config.Api;
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

public class BookCommentActivity extends AppCompatActivity implements View.OnClickListener,AMapLocationListener {
    private ImageView mChoosePicture;
    private EditText inputText;
    private int REQUEST_IMAGE=10000;
    private RecyclerView mPictureList;
    private List<String> path=new ArrayList<>();
    private CommonAdapter<String> mAdapter;
    private RatingBar ratingBar;
    private List<String>pictureId=new ArrayList<>();
    private CustomProgress customProgress;
    public AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient;
    private TextView mLocation;
    private int id,type,feverNum,orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent()!=null){
            id=getIntent().getIntExtra("id",0);
            type=getIntent().getIntExtra("type",0);
            orderId=getIntent().getIntExtra("order",0);
        }
        setContentView(R.layout.activity_book_comment);
        initView();
        initList();
    }

    private void initView() {
        customProgress=new CustomProgress(this,R.style.Custom_Progress,"加载中",false);
        StatusBarCompat.compat(this, getResources().getColor(R.color.green1));
        inputText=(EditText)findViewById(R.id.send_mood_input);
        View mBack=findViewById(R.id.back);
        View selectPicture=findViewById(R.id.send_mood_choose_picture);
        View sendMood=findViewById(R.id.send_comment_compete);
        mLocation= (TextView) findViewById(R.id.send_mood_location);
        ratingBar= (RatingBar) findViewById(R.id.ratingbar);
        mPictureList= (RecyclerView) findViewById(R.id.send_mood_picture_list);
        mLocation.setOnClickListener(this);
        mBack.setOnClickListener(this);
        sendMood.setOnClickListener(this);
        selectPicture.setOnClickListener(this);
        TextView title= (TextView)findViewById(R.id.title);
        if (type==1){
            title.setText("民宿评价");
        }else {
            title.setText("书评");
        }

        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                feverNum= (int) RatingCount;
                LogUtils.i("点评数量"+feverNum);
            }
        });
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mPictureList.setLayoutManager(linearLayoutManager);
        mAdapter=new CommonAdapter<String>(this,R.layout.list_item_send_mood,path) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                Glide.with(BookCommentActivity.this)
                        .load(s)
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.item_select_picture));
            }
        };
        mPictureList.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_mood_choose_picture:
                pickPicture();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.send_comment_compete:
                upLoad();
                break;
            case R.id.send_mood_location:
                getLocation();
                break;
        }
    }
    private void pickPicture() {
        MultiImageSelector.create(this).showCamera(true).multi().count(9).start(this,REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){
            if(resultCode == RESULT_OK){
                // 获取返回的图片列表
                path.addAll(data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT)) ;
                // 处理你自己的逻辑 ....
                upLoadPicture();
                mAdapter.notifyDataSetChanged();
                for ( String a:path) {
                    LogUtils.i("图片选择"+a);
                }
            }
        }
    }
    private void upLoadPicture() {
        for (int i = 0; i <path.size() ; i++) {
            upLoadOnPicture(path.get(i),path.size()-1,i);
        }
    }
    //上传图片
    private void upLoadOnPicture(String picture, final int size, final int i) {
        if (i==0){
            customProgress.show();
        }
        File pictureFile=new File(picture);
        OkHttpUtils.post()
                .addFile("img",picture,pictureFile)
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
                        if (i==size){
                            customProgress.dismiss();
                        }
                        try {
                            JSONObject json = JSON.parseObject(response);
                            if (json.getInteger("code") == 100) {
                                pictureId.add(json.getString("data"));

                                if (i==size){
                                    ToastUtils.showLongToast("上传完成");
                                }
                            } else {
                                ToastUtils.showLongToast(json.getString("msg"));
                                customProgress.dismiss();
                            }
                        } catch (Exception e) {
                            ToastUtils.showLongToast("网络错误" + e);
                            customProgress.dismiss();
                        }
                    }
                });
    }
    public void getLocation() {
        //声明mLocationOption对象
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(BookCommentActivity.this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                mLocation.setText(!(aMapLocation.getAddress().isEmpty())?aMapLocation.getAddress():aMapLocation.getProvince()+aMapLocation.getCity()+aMapLocation.getDistrict());
                mlocationClient.stopLocation();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                LogUtils.e("AmapError","location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                ToastUtils.showLongToast("定位错误");
                mlocationClient.stopLocation();
            }
        }
    }

    private void upLoad() {
        LogUtils.i("图书上传图片"+ JSON.toJSONString(pictureId));
        if (orderId==0){
            OkHttpUtils.post()
                    .url(Api.COMMENT)
                    .addParams("source_id",String.valueOf(id))
                    .addParams("type","2")
                    .addParams("content",inputText.getText().toString())
                    .addParams("location",mLocation.getText().toString().equals("当前位置")?mLocation.getText().toString():"")
                    .addParams("recommend_exponent",String.valueOf(feverNum))
                    .addParams("imgs", JSON.toJSONString(pictureId))
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
                                    ToastUtils.showLongToast("发布成功" );
                                    LogUtils.i("发布成功"+response);
                                    finish();
                                } else {
                                    ToastUtils.showLongToast(json.getString("msg"));
                                }
                            } catch (Exception e) {
                                ToastUtils.showLongToast("网络错误" + e);
                            }
                        }
                    });
        }else {
            OkHttpUtils.post()
                    .url(Api.COMMENT)
                    .addParams("source_id",String.valueOf(id))
                    .addParams("type",String.valueOf(type))
                    .addParams("content",inputText.getText().toString())
                    .addParams("location",mLocation.getText().toString().equals("当前位置")?mLocation.getText().toString():"")
                    .addParams("recommend_exponent",String.valueOf(feverNum))
                    .addParams("imgs",pictureId.toString())
                    .addParams("order_id",String.valueOf(orderId))
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
                                    ToastUtils.showLongToast("发布成功" );
                                    finish();
                                } else {
                                    ToastUtils.showLongToast(json.getString("msg"));
                                }
                            } catch (Exception e) {

                                ToastUtils.showLongToast("网络错误" + e);

                            }
                        }
                    });
        }

    }
}

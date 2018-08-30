package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.hedgehog.ratingbar.RatingBar;
import com.tencent.smtt.sdk.WebView;
import com.yunyou.icloudinn.bookhouse.Activity.LoginActivity;
import com.yunyou.icloudinn.bookhouse.Callback.HttpCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.Config.UserConfig;
import com.yunyou.icloudinn.bookhouse.JavaBean.BannerItem;
import com.yunyou.icloudinn.bookhouse.JavaBean.HouseDetailData;
import com.yunyou.icloudinn.bookhouse.JavaBean.HouseListData;
import com.yunyou.icloudinn.bookhouse.JavaBean.ToOrderData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CalendarFragmentDialog;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Ui.FacilityDialog;
import com.yunyou.icloudinn.bookhouse.Ui.HouseDeailMoerDialog;
import com.yunyou.icloudinn.bookhouse.Ui.MultiImageView;
import com.yunyou.icloudinn.bookhouse.Ui.SimpleImageBanner;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.HttpUtil;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.MyUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;

public class HouseDeatilMainFragment extends BaseFragment implements View.OnClickListener {
    private SimpleImageBanner mySimpleImageBanner;
    private int mInformationId;
    private CustomProgress customProgress;
    private ImageView mCommentPicture,mClose,mAssortPicture1,mAssortPicture2,
            mAssortPicture3,mAssortPicture4;
    private HouseDetailData houseDetailData;
    private HouseListData houseListData;
    private ArrayList<BannerItem> mBannerData=new ArrayList<>();
    private TextView mName,mPrice,mType,mRoomNum,mPeopleNum,
            mCheckIn,mCheckOut,mCanCheck,mCommentName,
            mCommentDescribe,mCommentData,mCommentNumber,mAssortNmae1,mAssortNmae2,mAssortNmae3,mAssortNmae4;
    private WebView mWebView;
    private String mContent;
    private RecyclerView mRecommendList,mCommendList,mOneRoomList;
    private TextureMapView textureMapView;
    private AMap aMap;
    private UiSettings mUiSettings;
    private ToOrderData mOrderDate;
    private View mOneRoomLayout,mCommentLayout,mCommentMore,mWebViewMore,moreFacility;
    private RatingBar mRatingBar;
    private TextView mFeverNum;
    private boolean isFever=false;
    private TextView mFormShoping;
    private MultiImageView multiImageView;
    public static HouseDeatilMainFragment getInstance(int id){
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        HouseDeatilMainFragment houseDeatilMainFragment=new HouseDeatilMainFragment();
        houseDeatilMainFragment.setArguments(bundle);
        return houseDeatilMainFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        mInformationId=bundle.getInt("id");
        customProgress=new CustomProgress(getActivity(),R.style.Custom_Progress,"加载中",false);
        StatusBarCompat.compat(getActivity(), getResources().getColor(R.color.green1));
        initMyView(view);
        textureMapView.onCreate(savedInstanceState);
        initData();
        initCommendData();
    }

    private void initCommendData() {
        OkHttpUtils.get().url(Api.HOUSE_LIST+"2").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showLongToast("网络错误" + e);
                customProgress.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {

                try {
                JSONObject json = JSON.parseObject(response);
                if (json.getInteger("code") == 100){
                    houseListData=JSON.parseObject(response,HouseListData.class);
                    loadCommendList();
                }else {
                    ToastUtils.showLongToast(json.getString("msg"));
                }
                }catch (Exception e){
                    ToastUtils.showLongToast("网络错误" + e);
                }
            }
        });
    }

    private void initMyView(View view) {
        mOrderDate=new ToOrderData();
        mySimpleImageBanner = (SimpleImageBanner) view.findViewById(R.id.sib_the_most_comlex_usage);
        View mBookOneHouse=view.findViewById(R.id.book_one_house);
        View mBookWholeHouse=view.findViewById(R.id.book_whole_house);
        mName= (TextView) view.findViewById(R.id.house_detail_name);
        mPrice= (TextView) view.findViewById(R.id.house_detail_money);
        mCheckIn= (TextView) view.findViewById(R.id.house_live_in1);
        mCheckOut= (TextView) view.findViewById(R.id.house_live_out1);
        mType= (TextView) view.findViewById(R.id.house_describe_type1);
        mRoomNum= (TextView) view.findViewById(R.id.house_number1);
        mPeopleNum= (TextView) view.findViewById(R.id.house_canbook1);
        mCanCheck= (TextView) view.findViewById(R.id.house_canbook_date1);
        mWebView= (WebView) view.findViewById(R.id.house_detail_webview);
        mRecommendList= (RecyclerView) view.findViewById(R.id.recommend_list);
        mCommendList= (RecyclerView) view.findViewById(R.id.reply_list);
        textureMapView= (TextureMapView) view.findViewById(R.id.house_detail_map);
        mCommentName=(TextView) view.findViewById(R.id.comment_nickname);
        mCommentDescribe=(TextView) view.findViewById(R.id.comment_describe);

        mCommentData=(TextView) view.findViewById(R.id.comment_data);
        mCommentPicture= (ImageView) view.findViewById(R.id.comment_picture);
        mCommentNumber=(TextView)view.findViewById(R.id.comment_number);
        mClose= (ImageView) view.findViewById(R.id.close_book_one_room);
        mOneRoomLayout=view.findViewById(R.id.one_room_layout);
        mOneRoomList=(RecyclerView) view.findViewById(R.id.one_room_list);
        mCommentLayout=view.findViewById(R.id.comment_layout);
        mCommentMore=view.findViewById(R.id.commecnt_more);
        moreFacility=view.findViewById(R.id.more_facility);
        mWebViewMore=view.findViewById(R.id.webview_more);
        mAssortPicture1= (ImageView) view.findViewById(R.id.assort_image1);
        mAssortPicture2= (ImageView) view.findViewById(R.id.assort_image2);
        mAssortPicture3= (ImageView) view.findViewById(R.id.assort_image3);
        mAssortPicture4= (ImageView) view.findViewById(R.id.assort_image4);
        mAssortNmae1= (TextView) view.findViewById(R.id.assort_text1);
        mAssortNmae2= (TextView) view.findViewById(R.id.assort_text2);
        mAssortNmae3= (TextView) view.findViewById(R.id.assort_text3);
        mAssortNmae4= (TextView) view.findViewById(R.id.assort_text4);
        mRatingBar = (RatingBar)view.findViewById(R.id.ratingbar);
        mFeverNum=(TextView) view.findViewById(R.id.comment_people_num);
        mFormShoping=(TextView)view.findViewById(R.id.comment_from_shopping);
        multiImageView = (MultiImageView) view.findViewById(R.id.user_picture);
        ImageView mCommentReply = (ImageView)view.findViewById(R.id.comment_follow_up);
        mCommentReply.setVisibility(View.VISIBLE);
        ImageView mCommentFever = (ImageView)view.findViewById(R.id.comment_fever);
        View mapLayout=view.findViewById(R.id.detail_map_layout);
        View mBack=view.findViewById(R.id.back);
        View mServePhone=view.findViewById(R.id.service_ohone);
        mBack.setOnClickListener(this);
        TextView title= (TextView) view.findViewById(R.id.title);
        title.setText("民宿详情");
        mySimpleImageBanner.setOnItemClickL(new BaseBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {

            }
        });
        mCanCheck.setOnClickListener(this);
        mBookOneHouse.setOnClickListener(this);
        mBookWholeHouse.setOnClickListener(this);
        mClose.setOnClickListener(this);
        mCommentMore.setOnClickListener(this);
        mWebViewMore.setOnClickListener(this);
        moreFacility.setOnClickListener(this);
        mServePhone.setOnClickListener(this);
        mapLayout.setOnClickListener(this);
        mCommentReply.setOnClickListener(this);
        mCommentFever.setOnClickListener(this);
    }

    private void initData() {
        customProgress.show();
        LogUtils.i("网络测试"+Api.HOUSE_DETAIL+mInformationId);
        OkHttpUtils.get().url(Api.HOUSE_DETAIL+mInformationId).build().execute(new StringCallback() {
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
                         loadDataInToView();
                    }else {
                        ToastUtils.showLongToast(json.getString("msg"));
                    }
                }catch (Exception e){
                    ToastUtils.showLongToast("网络错误" + e);
                }
            }
        });
    }

    private void loadDataInToView() {
        loadBanner();
        loadMap();
        loadInToView();
        loadFaccility();
        loadWebView();
        loadList();
        loadReply();
    }

    private void loadFaccility() {
        if (houseDetailData.getData().getSupport().size()>4){
            Glide.with(getHoldingActivity())
                    .load(Api.BASE_URL+houseDetailData.getData().getSupport().get(0).getImg())
                    .centerCrop()
                    .into(mAssortPicture1);
            mAssortNmae1.setText(houseDetailData.getData().getSupport().get(0).getName());

            Glide.with(getHoldingActivity())
                    .load(Api.BASE_URL+houseDetailData.getData().getSupport().get(1).getImg())
                    .centerCrop()
                    .into(mAssortPicture2);
            mAssortNmae2.setText(houseDetailData.getData().getSupport().get(1).getName());

            Glide.with(getHoldingActivity())
                    .load(Api.BASE_URL+houseDetailData.getData().getSupport().get(2).getImg())
                    .centerCrop()
                    .into(mAssortPicture3);
            mAssortNmae3.setText(houseDetailData.getData().getSupport().get(2).getName());

            Glide.with(getHoldingActivity())
                    .load(Api.BASE_URL+houseDetailData.getData().getSupport().get(3).getImg())
                    .centerCrop()
                    .into(mAssortPicture4);
            mAssortNmae4.setText(houseDetailData.getData().getSupport().get(3).getName());
        }else {
            moreFacility.setVisibility(View.GONE);
            mAssortPicture1.setBackground(getHoldingActivity().getResources().getDrawable(R.drawable.air));
            mAssortPicture2.setBackground(getHoldingActivity().getResources().getDrawable(R.drawable.air));
            mAssortPicture3.setBackground(getHoldingActivity().getResources().getDrawable(R.drawable.air));
            mAssortPicture4.setBackground(getHoldingActivity().getResources().getDrawable(R.drawable.air));
        }
    }

    private void loadReply() {
        mCommentNumber.setText(houseDetailData.getData().getComment().size()+"条评论");
        if (houseDetailData.getData().getComment().size()!=0){
            mFormShoping.setVisibility(View.GONE);
            mRatingBar.setStar(houseDetailData.getData().getComment().get(0).getRecommend_exponent());
            mRatingBar.setStarCount(5-houseDetailData.getData().getComment().get(0).getRecommend_exponent());
            mCommentData.setText(MyUtils.TimeStamp2Date(String.valueOf(houseDetailData.getData().getComment().get(0).getCreate_time()),""));
            mCommentDescribe.setText(houseDetailData.getData().getComment().get(0).getContent());
            mCommentName.setText(houseDetailData.getData().getComment().get(0).getUser_name());
            if (houseDetailData.getData().getComment().get(0).getImgs()!=null){
                multiImageView.setList(houseDetailData.getData().getComment().get(0).getImgs());
            }
            Glide.with(getHoldingActivity())
                    .load(houseDetailData.getData().getComment().get(0).getHead_img())
                    .centerCrop()
                    .into(mCommentPicture);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getHoldingActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mCommendList.setLayoutManager(linearLayoutManager);
            mCommendList.setAdapter(new CommonAdapter<HouseDetailData.DataBean.CommentBean.ReplyBean>(getHoldingActivity(),R.layout.list_item_reply_comment,houseDetailData.getData().getComment().get(0).getReply()) {
                @Override
                protected void convert(ViewHolder holder, HouseDetailData.DataBean.CommentBean.ReplyBean replyBean, int position) {
                    if (replyBean.getTo_user_name().isEmpty()){
                        holder.getView(R.id.reply_item_reply1).setVisibility(View.GONE);
                        holder.getView(R.id.reply_item_name2).setVisibility(View.GONE);
                        holder.setText(R.id.reply_item_name1,replyBean.getUser_name());
                        holder.setText(R.id.reply_item_context,"："+replyBean.getReply());
                    }else {
                        holder.setText(R.id.reply_item_name2,replyBean.getTo_user_name());
                        holder.setText(R.id.reply_item_name1,replyBean.getUser_name());
                        holder.setText(R.id.reply_item_context,"："+replyBean.getReply());
                    }

                }
            });
        }else {
            mCommentLayout.setVisibility(View.GONE);
            mCommentMore.setVisibility(View.GONE);
        }
    }

    private void loadList() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getHoldingActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mOneRoomList.setLayoutManager(linearLayoutManager);
        mOneRoomList.setAdapter(new CommonAdapter<HouseDetailData.DataBean.RoomBean>(getHoldingActivity(),R.layout.one_room_list_item,houseDetailData.getData().getRoom()) {
            @Override
            protected void convert(ViewHolder holder, final HouseDetailData.DataBean.RoomBean roomBean, int position) {
                holder.setText(R.id.one_room_name,roomBean.getName());
                holder.setText(R.id.one_room_detail,roomBean.getBed().isEmpty()?"4":roomBean.getBed()+"张床 可住"+roomBean.getAccept_num()+"人");
                if (!(roomBean.getImg().size()==0)){
                    Glide.with(getHoldingActivity())
                            .load(Api.BASE_URL+roomBean.getImg().get(0).getUrl())
                            .centerCrop()
                    ;
                }
                holder.getView(R.id.one_room_item_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MyApplication.getInstance().isLogin()){
                            mOrderDate.setId(houseDetailData.getData().getId());
                            mOrderDate.setName(houseDetailData.getData().getName());
                            mOrderDate.setPicture(Api.BASE_URL+houseDetailData.getData().getThumb());
                            mOrderDate.setType("单间");
                            mOrderDate.setReComment(houseDetailData.getData().getRecommend_reason());
                            mOrderDate.setRoomId(roomBean.getId());
                            mOrderDate.setPrice(roomBean.getPrice());
                            addNewFragment(OrderFragment.getInstance(mOrderDate));
                        }else {
                            getHoldingActivity().startActivity(new Intent(getHoldingActivity(), LoginActivity.class));
                        }
                    }
                });
            }
        });
    }

    private void loadMap() {
        if (aMap == null) {
            aMap = textureMapView.getMap();
        }
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        LatLng centerTXpoint =new LatLng(houseDetailData.getData().getLat(),houseDetailData.getData().getLng());
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(centerTXpoint, 10f, 0, 0)));
        //添加一个mark
        LatLng housePoint=new LatLng(houseDetailData.getData().getLat(),houseDetailData.getData().getLng());
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(housePoint).title("").snippet("DefaultMarker");
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.mark);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(chageBitmap(bitmap));
        markerOptions.icon(bitmapDescriptor);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        aMap.addMarker(markerOptions);
    }

    private Bitmap chageBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 设置想要的大小
        int newWidth = 50;
        int newHeight = 50;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    private void loadCommendList() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getHoldingActivity());
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getHoldingActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecommendList.setLayoutManager(linearLayoutManager);
        mRecommendList.setAdapter(new CommonAdapter<HouseListData.DataBeanX.DataBean>(getHoldingActivity(),R.layout.house_detail_recomment_item,houseListData.getData().getData()) {
            @Override
            protected void convert(ViewHolder holder, final HouseListData.DataBeanX.DataBean dataBean, int position) {
                holder.setText(R.id.recommend_item_price,"￥"+dataBean.getPrice());
                holder.setText(R.id.recommend_item_name,"￥"+dataBean.getName());
                Glide.with(getHoldingActivity())
                        .load(Api.BASE_URL+dataBean.getThumb())
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.recommend_item_picture));
                holder.getView(R.id.recommend_item_picture).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addNewFragment(HouseDeatilMainFragment.getInstance(dataBean.getId()));
                    }
                });
            }
        });
    }

    private void loadInToView() {
        mName.setText(houseDetailData.getData().getName());
        mPrice.setText("￥"+houseDetailData.getData().getPrice());
        mType.setText(houseDetailData.getData().getHouse_type().isEmpty()?"整套":houseDetailData.getData().getHouse_type());
        mCheckIn.setText(houseDetailData.getData().getCheck_in_time().isEmpty()?"14:00":houseDetailData.getData().getCheck_in_time());
        mCheckOut.setText(houseDetailData.getData().getCheck_out_time().isEmpty()?"12:00":houseDetailData.getData().getCheck_out_time());
        mRoomNum.setText(String.valueOf(houseDetailData.getData().getRoom_num()));
        mPeopleNum.setText(String.valueOf(houseDetailData.getData().getDwell_num()));
        mContent=houseDetailData.getData().getDescribe();
    }

    private void loadWebView() {
        mContent="<style>img{max-width:100%;height:auto}" +
                "video{max-width:100%;height:auto}</style>"+mContent;
        mWebView.loadDataWithBaseURL(null, mContent, "text/html", "utf-8", null);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
        //mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setJavaScriptEnabled(true);//允许执行js
    }

    private void loadBanner() {
        for (int i = 0; i <houseDetailData.getData().getImg().size(); i++) {
            BannerItem b=new BannerItem();
            b.imgUrl=Api.BASE_URL+houseDetailData.getData().getImg().get(i).getUrl();
            mBannerData.add(b);
        }
        mySimpleImageBanner.setSource(mBannerData).startScroll();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_house_detail_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.book_one_house:
                mOneRoomLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.book_whole_house:
                if (MyApplication.getInstance().isLogin()){
                    mOrderDate.setId(houseDetailData.getData().getId());
                    mOrderDate.setName(houseDetailData.getData().getName());
                    mOrderDate.setPicture(Api.BASE_URL+houseDetailData.getData().getThumb());
                    mOrderDate.setType("整套");
                    mOrderDate.setReComment(houseDetailData.getData().getRecommend_reason());
                    mOrderDate.setPrice(houseDetailData.getData().getPrice());
                    addNewFragment(OrderFragment.getInstance(mOrderDate));
                }else {
                    getHoldingActivity().startActivity(new Intent(getHoldingActivity(), LoginActivity.class));
                }
                break;
            case R.id.back:
                removeFragment();
                break;
            case R.id.house_canbook_date1:
                LogUtils.i("当前价格1"+mOrderDate.getPrice());
                CalendarFragmentDialog.getInstance(houseDetailData.getData().getId(),houseDetailData.getData().getPrice()).show(getFragmentManager(),"calendarDialog");
                break;
            case R.id.close_book_one_room:
                mOneRoomLayout.setVisibility(View.GONE);
                break;
            case R.id.commecnt_more:
                addNewFragment(CommentListFragment.getInstance(houseDetailData.getData()));
                break;
            case R.id.detail_map_layout:
                addNewFragment(MapFragment.getInstance(houseDetailData.getData().getLat(),houseDetailData.getData().getLng()));
                break;
            case R.id.webview_more:
                HouseDeailMoerDialog.getInstance(mContent=houseDetailData.getData().getDescribe()).show(getFragmentManager(),"webview");
                break;
            case R.id.more_facility:
                FacilityDialog.getInstance(houseDetailData.getData()).show(getFragmentManager(),"facility");
                break;
            case R.id.service_ohone:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ UserConfig.SERVICE_PHONE));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.comment_fever:
                if (MyApplication.getInstance().isLogin()){
                    doFever();
                }else {
                    getHoldingActivity().startActivity(new Intent(getHoldingActivity(), LoginActivity.class));
                }
                break;
        }
    }

    private void doFever() {
        if (isFever){
            HashMap hashmap=new HashMap();
            hashmap.put("comment_id",String.valueOf(houseDetailData.getData().getComment().get(0).getId()));
            hashmap.put("action","cancel");
            customProgress.show();
            HttpUtil.okHttpPost(true, hashmap, Api.PRAISE, new HttpCallback() {
                @Override
                public void onFail() {
                    customProgress.dismiss();
                }

                @Override
                public void onSuccess(String Data) {
                    isFever=false;
                    ToastUtils.showLongToast("取消点赞成功");
                    int num= houseDetailData.getData().getComment().get(0).getPraise_num()-1;
                    mFeverNum.setText(String.valueOf(num));
                    customProgress.dismiss();
                }
            });
        }else {
            HashMap hashmap=new HashMap();
            hashmap.put("comment_id",String.valueOf(houseDetailData.getData().getComment().get(0).getId()));
            hashmap.put("action","praise");
            customProgress.show();
            HttpUtil.okHttpPost(true, hashmap, Api.PRAISE, new HttpCallback() {
                @Override
                public void onFail() {
                    customProgress.dismiss();
                }

                @Override
                public void onSuccess(String Data) {
                    isFever=true;
                    ToastUtils.showLongToast("点赞成功");
                    int num= houseDetailData.getData().getComment().get(0).getPraise_num()+1;
                    mFeverNum.setText(String.valueOf(num));
                    customProgress.dismiss();
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        textureMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        textureMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        textureMapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        textureMapView.onSaveInstanceState(outState);
    }


}

package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Activity.HouseDetailActivity;
import com.yunyou.icloudinn.bookhouse.Activity.HouseListActivity;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.HouseListData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class HotelTapFragment extends Fragment implements View.OnClickListener,LocationSource,AMapLocationListener {
    private MapView textureMapView;
    private AMap aMap;
private ImageView mylocation_hotel;
    private LatLng centerTXpoint = new LatLng(19.032262,109.835815);// 测试经纬度 this.latitude = var1;
            //his.longitude = var3;
    private LatLng housePoint;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    private CustomProgress customProgress;
    private List<HouseListData.DataBeanX.DataBean> houseList;
    private View mHouseInformationLayout;
    private ImageView mHouseInformationPicture,mHouseInformationClose;
    private TextView mHouseInformationTitle,mHouseInformationPrice,mHouseInformationRecommend,mTurnToList;
    private int id;
    private AMapLocationClient mLocationClient = null; //定位发起端
    private AMapLocationClientOption mLocationOption = null; //定位参数
    private LocationSource.OnLocationChangedListener mListener = null; //定位监听器
    private boolean isFirstLoc = true;  //标识，用于判断是否只显示一次定位信息和用户重新定位

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel_main, container, false);
        StatusBarCompat.compat(getActivity(), getResources().getColor(R.color.green1));
        customProgress=new CustomProgress(getActivity(),R.style.Custom_Progress,"加载中",false);
        textureMapView= (MapView)view.findViewById(R.id.map);
        mHouseInformationLayout= view.findViewById(R.id.hotel_infromation_layout);
        mHouseInformationPicture= (ImageView) view.findViewById(R.id.hotel_main_picture);
        mHouseInformationTitle= (TextView) view.findViewById(R.id.hotel_main_text);
        mHouseInformationPrice= (TextView) view.findViewById(R.id.hotel_main_pirce);
        mHouseInformationRecommend= (TextView) view.findViewById(R.id.hotel_main_reason);
        mylocation_hotel=(ImageView) view.findViewById(R.id.myLocation_hotel);
        mylocation_hotel.setOnClickListener(this);
        //mHouseInformationClose= (ImageView) view.findViewById(R.id.close_house_detail);
        mTurnToList= (TextView) view.findViewById(R.id.turn_house_list);
        View back=view.findViewById(R.id.back);
        back.setVisibility(View.INVISIBLE);
        TextView title= (TextView) view.findViewById(R.id.title);
        title.setText("精品民宿");
        mTurnToList.setOnClickListener(this);
        //mHouseInformationClose.setOnClickListener(this);
        mHouseInformationLayout.setOnClickListener(this);
        textureMapView.onCreate(savedInstanceState);
        initLocation();
        initData();
        return view;
    }

    private void initData() {
        customProgress.show();
        houseList =new ArrayList<>();
        OkHttpUtils.get().url(Api.HOUSE_LIST+"0").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showLongToast("网络错误" + e);
                customProgress.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                customProgress.dismiss();
              try {
                  HouseListData houseListData =JSON.parseObject(response,HouseListData.class);
                  if (houseListData.getCode()==100){
                      houseList.addAll(houseListData.getData().getData());
                      showMark();
                  }else {
                      ToastUtils.showLongToast(houseListData.getMsg());
                  }
              }catch (Exception e){
                  ToastUtils.showLongToast("网络错误" + e);
              }
            }
        });
    }

    private void showMark() {
        for (int i = 0; i <houseList.size() ; i++) {
            housePoint=new LatLng(houseList.get(i).getLat(),houseList.get(i).getLng());
            MarkerOptions markerOptions=new MarkerOptions();
            markerOptions.position(housePoint).title(""+i).snippet("DefaultMarker");
            Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.mark);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(chageBitmap(bitmap));
            markerOptions.icon(bitmapDescriptor);
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            aMap.addMarker(markerOptions);
        }
    }
    //初始化Marker
    private Bitmap chageBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 设置想要的大小
        int newWidth = 100;
        int newHeight = 100;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
       return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    private void initLocation() {
        if (aMap == null) {
            aMap = textureMapView.getMap();
        }
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        //设置定位监听
        aMap.setLocationSource(this);
        // 是否显示定位按钮
        mUiSettings.setMyLocationButtonEnabled(false);

        // 是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(true);
        //是否缩放
        mUiSettings.setZoomControlsEnabled(true);
        //默认地图移动到Marker位置

//        aMap.moveCamera(CameraUpdateFactory.newLatLng(centerTXpoint));

        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(centerTXpoint, 10f, 0, 0)));
        mLocationClient.setLocationOption(mLocationOption);

        //启动定位
//        mLocationClient.startLocation();

        //Marker监听事件
        AMap.OnMarkerClickListener markerClickListener=new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                int position=Integer.parseInt(marker.getTitle());
                id=houseList.get(position).getId();
                if ( mHouseInformationLayout.getVisibility()==View.INVISIBLE){
                    mHouseInformationLayout.setVisibility(View.VISIBLE);
                }
                Glide.with(getActivity())
                        .load(houseList.get(position).getThumb())//列表页的图片
                        .centerCrop()
                        .into(mHouseInformationPicture);
                mHouseInformationTitle.setText(houseList.get(position).getName());
                mHouseInformationPrice.setText(houseList.get(position).getPrice()+"");
                mHouseInformationRecommend.setText(houseList.get(position).getName());
                return true;
            }
        };
        aMap.setOnMarkerClickListener(markerClickListener);
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mHouseInformationLayout.setVisibility(View.INVISIBLE);

                        break;
                }
            }
        });
        }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        textureMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        textureMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        textureMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
//        setCameraPosition(aMap.getCameraPosition());//保存地图状态
        super.onDestroy();
        textureMapView.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hotel_infromation_layout://列表页
                Intent intent =new Intent(getActivity(), HouseDetailActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
            case R.id.turn_house_list:
                Intent intent1 =new Intent(getActivity(), HouseListActivity.class);
                startActivity(intent1);
                break;
            case R.id.myLocation_hotel:
                mLocationClient.startLocation();
                break;

        }
    }

    /*
    * 定位函数
    * */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {

                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(amapLocation);
                    //添加图钉
                   // aMap.addMarker(getMarkerOptions(amapLocation));
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
                   // Toast.makeText(getActivity().getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;
                }


            } else if (amapLocation.getErrorCode() == 2){
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());

                Toast.makeText(getActivity().getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
            }
        }
    }

    /*
    * 激活定位
    * */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;


    }

    /*
    * 停止定位
    * */
    @Override
    public void deactivate() {
        mListener = null;
    }
}

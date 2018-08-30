package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
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
import com.amap.api.maps.MapView;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;

public class HotelTapFragment extends Fragment implements View.OnClickListener {
    private MapView textureMapView;
    private AMap aMap;
    private LatLng centerTXpoint = new LatLng(19.032262,109.835815);// 测试经纬度
    private LatLng housePoint;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    private CustomProgress customProgress;
    private List<HouseListData.DataBeanX.DataBean> houseList;
    private View mHouseInformationLayout;
    private ImageView mHouseInformationPicture,mHouseInformationClose;
    private TextView mHouseInformationTitle,mHouseInformationPrice,mHouseInformationRecommend,mTurnToList;
    private int id;
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
        init();
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

    private void init() {
        if (aMap == null) {
            aMap = textureMapView.getMap();
          
            mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
            mUiSettings.setZoomControlsEnabled(false);
            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(centerTXpoint, 10f, 0, 0)));
            AMap.OnMarkerClickListener markerClickListener=new AMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    int position=Integer.parseInt(marker.getTitle());
                    id=houseList.get(position).getId();
                    if ( mHouseInformationLayout.getVisibility()==View.INVISIBLE){
                        mHouseInformationLayout.setVisibility(View.VISIBLE);
                    }
                    Glide.with(getActivity())
                            .load(Api.BASE_URL+houseList.get(position).getThumb())
                            .centerCrop()
                            .into(mHouseInformationPicture);
                    mHouseInformationTitle.setText(houseList.get(position).getName());
                    mHouseInformationPrice.setText(houseList.get(position).getPrice());
                    mHouseInformationRecommend.setText(houseList.get(position).getName());
                    return true;
                }
            };
            aMap.setOnMarkerClickListener(markerClickListener);
        }

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
            case R.id.hotel_infromation_layout:
                Intent intent =new Intent(getActivity(), HouseDetailActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
/*            case R.id.close_house_detail:
                mHouseInformationLayout.setVisibility(View.INVISIBLE);
                id=-1;
                break;*/
            case R.id.turn_house_list:
                Intent intent1 =new Intent(getActivity(), HouseListActivity.class);
                startActivity(intent1);
                break;

        }
    }
}

package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;

import java.io.File;
import java.net.URISyntaxException;

public class MapFragment extends BaseFragment {
    private MapView mapView;
    private Double mLat,mLan;
    private AMap aMap;
    private UiSettings mUiSettings;

    public static MapFragment getInstance(double lat,double lan){
        Bundle bundle=new Bundle();
        bundle.putDouble("lat",lat);
        bundle.putDouble("lan",lan);
        MapFragment mapFragment=new MapFragment();
        mapFragment.setArguments(bundle);
        return mapFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        mLat=bundle.getDouble("lat");
        mLan=bundle.getDouble("lan");
        View mBack=view.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        });
        TextView title= (TextView) view.findViewById(R.id.title);
        title.setText("民宿详情");
        mapView= (MapView) view.findViewById(R.id.house_detail_information_map);
        mapView.onCreate(savedInstanceState);
        loadMap();
    }

    private void loadMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        LogUtils.i("经纬度测试"+mLat+"-------"+mLan);
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        LatLng centerTXpoint =new LatLng(mLat,mLan);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(centerTXpoint, 15f, 0, 0)));
        //添加一个mark
        LatLng housePoint=new LatLng(mLat,mLan);
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(housePoint).title("").snippet("DefaultMarker");
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.mark);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(chageBitmap(bitmap));
        markerOptions.icon(bitmapDescriptor);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        aMap.addMarker(markerOptions);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

}

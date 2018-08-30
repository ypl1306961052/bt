package com.yunyou.icloudinn.bookhouse.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;

import java.util.regex.Pattern;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class QRActivity extends BaseActivity implements QRCodeView.Delegate {

    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private String[] permissions = new String[]{android.Manifest.permission.CAMERA};

    private QRCodeView mQRCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        StatusBarCompat.compat(this, getResources().getColor(R.color.green1));
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("扫描二维码");
        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mQRCodeView = (ZBarView) findViewById(R.id.zbarview);
        mQRCodeView.setDelegate(this);
        initPermission();
    }

    private void initPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 001);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.startSpot();
        //        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mQRCodeView.showScanRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {

//        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        String id=getResult(result,0);
        switch (getResult(result, 1)) {

            case "wmj"://门卡
                Intent intent=new Intent(QRActivity.this,DoorOpenActivity.class);
                intent.putExtra("wmjID",id);
                startActivity(intent);


//                startActivityForResult();
                break;
            case "book"://数
                break;
            default:
                ToastUtils.showLongToast("类别不存在");

        }
        vibrate();
        mQRCodeView.startSpot();

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    public String getResult(String result, int i) {
        //    String reg ="^((http|https)://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$ ";
        // Pattern   pattern= Pattern.compile(reg);
        //    Matcher matcher=pattern.matcher(result);
        String reg_2 = "[=&]";
        Pattern pattern1 = Pattern.compile(reg_2);
        String[] strings = pattern1.split(result);
        String s=null;
        if (i == 1) {//1:返回type
            s= strings[1];
        } else if (i == 0) {//0:返回id
           s= strings[3];
        }
return s;

    }


}

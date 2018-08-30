package com.yunyou.icloudinn.bookhouse.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.yunyou.icloudinn.bookhouse.Fragment.BookTapFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.HotelTapFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.MineTapFragment;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.FragmentTabHost;

public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener  {
    FragmentTabHost mainTapHost;
    private String[] tabText = {"一张床", "一本书", "一个人"};
    private int[] imageRes = new int[]{R.drawable.tap_bed, R.drawable.tap_book, R.drawable.tap_mine};
    private Class[] fragments = new Class[]{HotelTapFragment.class, BookTapFragment.class, MineTapFragment.class};
    private OnHideKeyboardListener onHideKeyboardListener;
    private long exitTime = 0; // 再按一次退出时间
    private String userId ;
    //定位权限
    private String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //申请定位权限
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,permissions,001);
        }
        setContentView(R.layout.activity_main);
        // 设置状态栏颜色
        //StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.green1));
        mainTapHost= (FragmentTabHost) findViewById(R.id.nomal_tab_host);
        mainTapHost.setup(this, getSupportFragmentManager(), R.id.nomal_content);
        mainTapHost.getTabWidget().setDividerDrawable(null);
        mainTapHost.setOnTabChangedListener(this);
        initTab();
    }


    /**
     * 书籍查询
     * @param v
     */
    public void doSearch(View v){
        Intent intent = new Intent();
        intent.setClass(this,BookSearchActivity.class);
        startActivity(intent);
    }

    private void initTab() {
        for (int i = 0; i < tabText.length; i++) {

            View view = LayoutInflater.from(this).inflate(R.layout.list_item_main_tap, null);
            ((TextView) view.findViewById(R.id.tap_item_text)).setText(tabText[i]);
            ((ImageView) view.findViewById(R.id.tap_item_picture)).setImageResource(imageRes[i]);

            TabHost.TabSpec tabSpec = mainTapHost.newTabSpec(tabText[i]).setIndicator(view);
            mainTapHost.addTab(tabSpec, fragments[i], null);
            mainTapHost.setTag(i);
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        //((TextView)mainTapHost.getTabWidget().getChildTabViewAt(0).findViewById(R.id.tap_item_text)).setText("fdf");
        for (int i = 0; i < mainTapHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) mainTapHost.getTabWidget().getChildAt(i).findViewById(R.id.tap_item_text);
            if (mainTapHost.getCurrentTab() == i) {//选中变色
                tv.setTextColor(this.getResources().getColor(R.color.green1));
            } else {//不选中
                tv.setTextColor(this.getResources().getColor(R.color.grey));
            }
        }

    }
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//
//    }

    public void setOnHideKeyboardListener(OnHideKeyboardListener onHideKeyboardListener){
        this.onHideKeyboardListener = onHideKeyboardListener;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if(onHideKeyboardListener != null){
            if(ev.getAction() == MotionEvent.ACTION_DOWN){
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    hideKeyboard(v.getWindowToken());
                    v.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public interface OnHideKeyboardListener{
        public boolean hideKeyboard();
    }
    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                v.clearFocus();
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

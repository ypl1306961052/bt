package com.yunyou.icloudinn.bookhouse.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Callback.HttpCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.PostCalendarSelectorData;
import com.yunyou.icloudinn.bookhouse.JavaBean.ToOrderData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Util.HttpUtil;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.TimeUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class OrderFragment extends BaseFragment implements View.OnClickListener {
    private ToOrderData mOrderData;
    private TextView mName,mType,mRecomment,mCheckInTime,mCheckOutTime,mPeopleNumberText,mTotalPrice;
    private ImageView mPicture,show;
    private String startTime="";
    private String endTime="";
    private View myView;
    private int peopleCount=1;
    private int price=-1;
    private CustomProgress customProgress;
    private long arriveTime,leaveTime;
    private TextView mHotelPrice;
    private boolean isDetailShow,canShow=false;
    private int deduction;

    public static OrderFragment getInstance(ToOrderData orderData){
        Bundle bundle=new Bundle();
        bundle.putSerializable("Data",orderData);
        OrderFragment orderFragment=new OrderFragment();
        orderFragment.setArguments(bundle);
        return orderFragment;
    }
    public static OrderFragment getInstance(){
        OrderFragment orderFragment=new OrderFragment();
        return orderFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mOrderData = (ToOrderData) getArguments().getSerializable("Data");
        initMyView(view);
        loadData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        customProgress=new CustomProgress(getActivity(),R.style.Custom_Progress,"加载中",false);
        isDetailShow=false;
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//订阅
    }

    private void loadData() {
        mName.setText(mOrderData.getName());
        mType.setText(mOrderData.getType());
        mRecomment.setText(mOrderData.getReComment());
        Glide.with(getHoldingActivity())
                .load(mOrderData.getPicture())
                .centerCrop()
                .into(mPicture);
    }

    private void initMyView(View view) {
        View mChooseCalendar=view.findViewById(R.id.choose_calendar);
        View mBack=view.findViewById(R.id.back);
        TextView title= (TextView) view.findViewById(R.id.title);
        mName=(TextView)view.findViewById(R.id.order_name);
        mRecomment=(TextView)view.findViewById(R.id.order_recommend);
        mType=(TextView)view.findViewById(R.id.order_type);
        mPicture= (ImageView) view.findViewById(R.id.order_picture);
        mCheckInTime=(TextView)view.findViewById(R.id.check_in_tiem);
        mCheckOutTime=(TextView)view.findViewById(R.id.check_out_tiem);
        mPeopleNumberText=((TextView)view.findViewById(R.id.people_num));
        mTotalPrice=((TextView)view.findViewById(R.id.total_price));
        Button mCheckButton= (Button) view.findViewById(R.id.check_order);
        peopleCount=Integer.parseInt(mPeopleNumberText.getText().toString());
        mHotelPrice= (TextView) view.findViewById(R.id.house_price);
        View subText=view.findViewById(R.id.order_number_sub);
        View addText=view.findViewById(R.id.order_number_add);
        mChooseCalendar.setOnClickListener(this);
        mBack.setOnClickListener(this);
        subText.setOnClickListener(this);
        addText.setOnClickListener(this);
        mCheckButton.setOnClickListener(this);
        title.setText("下订单");
        if (!(startTime.isEmpty())&&!(endTime.isEmpty()&&price!=-1)){
            LogUtils.i("时间测试"+startTime);
            LogUtils.i("时间测试"+endTime);
            canShow=true;
            mCheckInTime.setText(startTime);
            mCheckOutTime.setText(endTime);
            mHotelPrice.setText("￥"+String.valueOf(price));
            deduction=MyApplication.getInstance().getUser().getData().getPoint()/50;
            if (MyApplication.getInstance().getUser().getData().getPoint()!=0){
                if (deduction>price+200){
                    deduction=price+200;
                    mTotalPrice.setText("￥0");
                }else {
                    mTotalPrice.setText("￥"+String.valueOf(price-deduction+200));
                }

            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choose_calendar:
//              getHoldingActivity().addHideFragment(this,CalendarSelectorFragment.getInstance(mOrderData.getId()));
                if (mOrderData.getType().equals("单间")){
                    addNewFragment(CalendarSelectorFragment.getInstance(mOrderData.getId(),mOrderData.getRoomId(),mOrderData.getPrice()));
                }else {
                    addNewFragment(CalendarSelectorFragment.getInstance(mOrderData.getId(),mOrderData.getPrice()));
                }

                break;
            case R.id.back:
                removeFragment();
            case R.id.order_number_add:
            if (peopleCount>0){
                peopleCount++;
                mPeopleNumberText.setText(String.valueOf(peopleCount));
            }
                break;
            case R.id.order_number_sub:
            if (peopleCount!=1){
                peopleCount--;
                mPeopleNumberText.setText(String.valueOf(peopleCount));
            }
                break;
            case R.id.check_order:
                checkOrder();
                break;
        }
    }

    private void checkOrder() {
        try {
             arriveTime = (new SimpleDateFormat("yyyy-MM-dd").parse(startTime).getTime())/1000;
             leaveTime = (new SimpleDateFormat("yyyy-MM-dd").parse(endTime).getTime())/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        customProgress.show();
        HashMap<String,String> myMap=new HashMap<>();
        myMap.put("hotel_id",String.valueOf(mOrderData.getId()));
        myMap.put("type","1");
        myMap.put("num",mPeopleNumberText.getText().toString());
        myMap.put("deduction_money",String.valueOf(deduction));
        myMap.put("arrive_time",String.valueOf(arriveTime));
        myMap.put("leave_time",String.valueOf(leaveTime));
        myMap.put("pay_money",String.valueOf(price));
        if (mOrderData.getType().equals("单间")){
            myMap.put("room_id",String.valueOf(mOrderData.getRoomId()));
        }
        HttpUtil.okHttpPost(true, myMap, Api.ORDER, new HttpCallback() {
            @Override
            public void onFail() {
                customProgress.dismiss();
            }

            @Override
            public void onSuccess(String Data) {
                customProgress.dismiss();
                ToastUtils.showLongToast("预定成功");
                addNewFragment(OderResultFragment.getInstance());
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(PostCalendarSelectorData event) {
        startTime=event.getStartTime();
        endTime=event.getEndTime();
        price=event.getPrice();
        LogUtils.i("测试执行"+event.getStartTime()+event.getStartTime()+String.valueOf(event.getPrice()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}

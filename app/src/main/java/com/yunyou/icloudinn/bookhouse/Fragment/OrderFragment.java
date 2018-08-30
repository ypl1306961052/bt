package com.yunyou.icloudinn.bookhouse.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cuieney.rxpay_annotation.WX;
import com.cuieney.sdk.rxpay.RxPay;
import com.yunyou.icloudinn.bookhouse.Callback.HttpCallback;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.HotelOrderData;
import com.yunyou.icloudinn.bookhouse.JavaBean.PostCalendarSelectorData;
import com.yunyou.icloudinn.bookhouse.JavaBean.ToOrderData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.HttpUtil;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import io.reactivex.functions.Consumer;

@WX(packageName = "com.yunyou.icloudinn.bookhouse")
public class OrderFragment extends BaseFragment implements View.OnClickListener {
    private ToOrderData mOrderData; //订单数据
    private TextView mName,mType,mRecomment,mCheckInTime,mCheckOutTime,mPeopleNumberText,mTotalPrice;
    private ImageView mPicture,show;
    private EditText desPointNum; //扣除积分个数
    private TextView desPointMoney; //积分折扣的价格
    private TextView pointTxv; //用户剩余积分
    private String endTime="",startTime=""; //入住时间和退房时间
    private int totalTime; //入住总时间
    private int peopleCount=1; //入住房客人数
    private int price=-1; //房间单价
    private int payTotalMoney; //订单支付总价
    private long arriveTime,leaveTime;
    private TextView mHotelPrice,unitPriceTextView,hotelPriceTextView;
    private boolean isDetailShow,canShow=false;
    private int deduction;
    private CheckBox checkBox;

    private RxPay rxPay;

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
        rxPay = new RxPay(getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        mTotalPrice=((TextView)view.findViewById(R.id.total_price));  //入住订单支付总价
        pointTxv = (TextView)view.findViewById(R.id.lable_allPoint);
        Button mCheckButton= (Button) view.findViewById(R.id.check_order);
        peopleCount=Integer.parseInt(mPeopleNumberText.getText().toString());
        mHotelPrice= (TextView) view.findViewById(R.id.house_price); //房费
        checkBox = (CheckBox) view.findViewById(R.id.ckb_point); //选择是否使用积分
        desPointNum = (EditText) view.findViewById(R.id.edt_point);
        desPointMoney = (TextView) view.findViewById(R.id.txv_money);
        unitPriceTextView = (TextView) view.findViewById(R.id.order_unitPrice_textView); //显示房间单价×天数
        hotelPriceTextView = (TextView) view.findViewById(R.id.hotelPrice_textView); //显示房间单价×天数
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
            unitPriceTextView.setText("￥"+price+" × "+totalTime);
            hotelPriceTextView.setText("￥"+price+" × "+totalTime+"晚");
            mHotelPrice.setText("￥"+price*totalTime);
            deduction=MyApplication.getInstance().getUser().getPoint();
            pointTxv.setText("剩余积分："+deduction);
            //监听editText
            desPointNum.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length()>0){
                        int desPoint =  Integer.valueOf(desPointNum.getText().toString());
                        desPointMoney.setText(""+(float)desPoint/50); //显示积分折扣价格
                    }


                }
            });
            //是否选择积分折扣
            if (checkBox.isChecked()){
                ToastUtils.showShortToast("选中了");
                desMoney();
            }else {
                mTotalPrice.setText("￥"+payTotalMoney);
            }
        }
    }
    //计算积分折扣的价格
    private void desMoney() {
        if (MyApplication.getInstance().getUser().getPoint()!=0){
            int desPoint =  Integer.valueOf(desPointNum.getText().toString());
            desPointMoney.setText(desPoint); //显示积分折扣价格
            mTotalPrice.setText("￥"+(payTotalMoney-desPoint));
        }else {
            ToastUtils.showShortToast("您的积分不够");
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
        OkHttpUtils.post().url(Api.ORDER)
                .params(myMap)
                .build().execute(new JsonCallback<HotelOrderData>(getActivity(),true) {
            @Override
            public void onResponse(HotelOrderData order, int id) {
                wxPay(order.getHotel_order_id());
//                ToastUtils.showLongToast("预定成功");
//                addNewFragment(OderResultFragment.getInstance());
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(PostCalendarSelectorData event) {
        startTime=event.getStartTime();
        endTime=event.getEndTime();
        price=event.getPrice();
        totalTime = event.getTotalTime();
        payTotalMoney = (price*totalTime+200); //计算支付总价
        LogUtils.i("测试执行"+event.getStartTime()+event.getStartTime()+String.valueOf(event.getPrice()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 微信支付
     */
    private void wxPay(String orderId){
        OkHttpUtils.post().url(Api.WX_PAY_HOTEL_ORDER)
                .addParams("hotel_order_id",orderId)
                .addParams("pay_way","APP")
                .build()
                .execute(new JsonCallback<String>(getActivity(),true) {
                    @Override
                    public void onResponse(String data, int id) {
                        ToastUtils.showLongToast("调启微信支付....");
                        try {
                            rxPay.requestWXpay(new JSONObject(data))
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean aBoolean) throws Exception {
                                            ToastUtils.showLongToast("微信支付状态："+aBoolean);
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            ToastUtils.showLongToast("微信支付状态："+throwable.getMessage());
                                        }
                                    });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}

package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.tubb.calendarselector.custom.DayViewInflater;
import com.tubb.calendarselector.library.CalendarSelector;
import com.tubb.calendarselector.library.FullDay;
import com.tubb.calendarselector.library.MonthView;
import com.tubb.calendarselector.library.SCDateUtils;
import com.tubb.calendarselector.library.SCMonth;
import com.tubb.calendarselector.library.SegmentSelectListener;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.CalendarSelectorData;
import com.yunyou.icloudinn.bookhouse.JavaBean.PostCalendarSelectorData;
import com.yunyou.icloudinn.bookhouse.JavaBean.PriceCalendarData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.AnimDayViewInflater;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;

public class CalendarSelectorFragment extends BaseFragment implements View.OnClickListener {

    CalendarSelector selector;
    RecyclerView rvCalendar;
    List<SCMonth> data;
    private int hotelId,roomId,price,totalTime;
    private CustomProgress customProgress;
    private List<CalendarSelectorData> mouthList=new ArrayList<>();
    private TextView mCheckIn,mCheckOut;
    private ImageView mCheckInPicture,mCheckOutPicture;
    private boolean isCheck=true;
    private int mTotalPrice=0;
    private PriceCalendarData priceCalendarData;
    private FullDay mySelectingDay;
    private int mToday=-1;
    private List<String>alreadyBook=new ArrayList<>();
    public static CalendarSelectorFragment getInstance(int hotelId,int price){
        Bundle bundle=new Bundle();
        bundle.putInt("hotelid",hotelId);
        bundle.putInt("price",price);
        CalendarSelectorFragment calendarSelectorFragment=new CalendarSelectorFragment();
        calendarSelectorFragment.setArguments(bundle);
        return calendarSelectorFragment;
   }
    public static CalendarSelectorFragment getInstance(int hotelId,int roomid,int price){
        Bundle bundle=new Bundle();
        bundle.putInt("hotelid",hotelId);
        bundle.putInt("roomid",roomid);
        bundle.putInt("price",price);
        CalendarSelectorFragment calendarSelectorFragment=new CalendarSelectorFragment();
        calendarSelectorFragment.setArguments(bundle);
        return calendarSelectorFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        customProgress=new CustomProgress(getActivity(),R.style.Custom_Progress,"加载中",false);
        initMyView(view,savedInstanceState);
        initData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initMyView(View view, Bundle savedInstanceState) {
        if(savedInstanceState != null)
            selector = savedInstanceState.getParcelable("selector");
            rvCalendar = (RecyclerView) view.findViewById(R.id.rvCalendar);
            rvCalendar.setLayoutManager(new LinearLayoutManager(getHoldingActivity()));
            mCheckIn= (TextView) view.findViewById(R.id.check_in_name);
            mCheckOut= (TextView) view.findViewById(R.id.check_out_name);
            mCheckInPicture= (ImageView) view.findViewById(R.id.check_in_picture);
            mCheckOutPicture= (ImageView) view.findViewById(R.id.check_out_picture);
            View mBack=view.findViewById(R.id.back);
            TextView title= (TextView) view.findViewById(R.id.title);
            title.setText("选择日期");
            View mCheckInLayout=view.findViewById(R.id.check_in_layout);
            View mCheckOutLayout=view.findViewById(R.id.check_out_layout);
            mCheckInLayout.setOnClickListener(this);
            mCheckOutLayout.setOnClickListener(this);
            mBack.setOnClickListener(this);
            ((SimpleItemAnimator) rvCalendar.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void initData() {
        Bundle bundle = getArguments();
        hotelId = bundle.getInt("hotelid");
        roomId=bundle.getInt("roomid");
        price=bundle.getInt("price");
        LogUtils.i("传递的三个参数----套间id"+hotelId+"-----单间id----价格---"+price);
        customProgress.show();
        if (roomId==0){
            OkHttpUtils.get().url(Api.PRICE_CALENDAR+ hotelId).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.showLongToast("网络错误" + e);
                    customProgress.dismiss();
                }

                @Override
                public void onResponse(String response, int id) {
                    LogUtils.i("价格----"+response);
                    customProgress.dismiss();
                    try {
                        priceCalendarData = JSON.parseObject(response, PriceCalendarData.class);
                        if (priceCalendarData.getCode() == 100) {
                                alreadyBook.addAll(priceCalendarData.getData().getAlready_reservation());
                                initSelectorData(priceCalendarData);
//                                data = getData();
//                                segmentMode();
                        } else {
                            ToastUtils.showLongToast(priceCalendarData.getMsg());
                            data = getData();
                            //segmentMode();

                        }
                    } catch (Exception e) {
                        ToastUtils.showLongToast("网络错误" + e);
                        data = getData();
                        //segmentMode();
                        customProgress.dismiss();
                    }
                }
            });
        }else {
            OkHttpUtils.get().url(Api.PRICE_CALENDAR+ hotelId).addParams("room_id",String.valueOf(roomId)).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.showLongToast("网络错误" + e);
                    customProgress.dismiss();
                }

                @Override
                public void onResponse(String response, int id) {
                    customProgress.dismiss();
                    try {
                        priceCalendarData = JSON.parseObject(response, PriceCalendarData.class);
                        if (priceCalendarData.getCode() == 100) {
                                alreadyBook.addAll(priceCalendarData.getData().getAlready_reservation());
                                initSelectorData(priceCalendarData);
//                                data = getData();
//                                segmentMode();

                        } else {
                            ToastUtils.showLongToast(priceCalendarData.getMsg());
                            data = getData();
                            //segmentMode();

                        }
                    } catch (Exception e) {
                        ToastUtils.showLongToast("网络错误" + e);
                        data = getData();
                       // segmentMode();
                        customProgress.dismiss();
                    }
                }
            });
        }
    }
    //数据整理
    private void initSelectorData(PriceCalendarData priceCalendarData) {
        List<CalendarSelectorData.DataBean>days=new ArrayList<>();
        String month="-1";
        for (int i = 0; i <priceCalendarData.getData().getPrice_calendar().size() ; i++) {
            CalendarSelectorData.DataBean day=new CalendarSelectorData.DataBean();

            String[] strs=priceCalendarData.getData().getPrice_calendar().get(i).getDay().split("-");
           if (i==0){
               month= strs[1];
               LogUtils.i("等于0执行一次");
           }
           if (i==priceCalendarData.getData().getPrice_calendar().size()-1){
               if (month.equals(strs[1]) ){
                   LogUtils.i("最后一个相等执行一次");
                   day.setDay(strs[2]);
                   day.setPrice(priceCalendarData.getData().getPrice_calendar().get(i).getPrice());
                   day.setMonth(strs[1]);
                   day.setmCommentPrice(price);
                   days.add(day);
                   CalendarSelectorData calendarSelectorData=new CalendarSelectorData();
                   calendarSelectorData.setMonthSelector(days);
                   mouthList.add(calendarSelectorData);
                   break;
               }else {
                   LogUtils.i("最后一个不相等执行一次");
                   CalendarSelectorData calendarSelectorData=new CalendarSelectorData();
                   calendarSelectorData.setMonthSelector(days);
                   mouthList.add(calendarSelectorData);
                   LogUtils.i("数据测试2"+mouthList.get(0).getMonthSelector().get(0).getMonth()+"数据长度"+mouthList.size());
                   LogUtils.i("数据测试2"+mouthList.get(0).getMonthSelector().get(1).getMonth()+"数据长度"+mouthList.get(0).getMonthSelector().size());
                   //最后一个不相同的月份
                   days=new ArrayList<>();
                   day.setDay(strs[2]);
                   day.setPrice(priceCalendarData.getData().getPrice_calendar().get(i).getPrice());
                   day.setMonth(strs[1]);
                   day.setmCommentPrice(price);
                   days.add(day);
                   CalendarSelectorData calendarSelectorData1=new CalendarSelectorData();
                   calendarSelectorData1.setMonthSelector(days);
                   mouthList.add(calendarSelectorData1);
                   LogUtils.i("数据测试3"+mouthList.get(0).getMonthSelector().get(0).getMonth()+"数据长度"+mouthList.get(0).getMonthSelector().size());
                   break;
               }
           }
           if (month.equals(strs[1])){
               LogUtils.i("相同执行一次");
               day.setDay(strs[2]);
               day.setPrice(priceCalendarData.getData().getPrice_calendar().get(i).getPrice());
               day.setMonth(strs[1]);
               day.setmCommentPrice(price);
               days.add(day);
           }else {
               LogUtils.i("不相同执行一次");
               month= strs[1];
               CalendarSelectorData calendarSelectorData=new CalendarSelectorData();
               calendarSelectorData.setMonthSelector(days);
               mouthList.add(calendarSelectorData);
               days=new ArrayList<>();
               day.setDay(strs[2]);
               day.setPrice(priceCalendarData.getData().getPrice_calendar().get(i).getPrice());
               day.setMonth(strs[1]);
               day.setmCommentPrice(price);
               days.add(day);
           }

        }
        customProgress.dismiss();
        data = getData();
        segmentMode();
    }

    //日历监听事件

    private void segmentMode() {
        selector = new CalendarSelector(data, CalendarSelector.SEGMENT);
        selector.setSegmentSelectListener(new SegmentSelectListener() {
            @Override
            public void onSegmentSelect(FullDay startDay, FullDay endDay) {

            }

            @Override
            public boolean onInterceptSelect(FullDay selectingDay) { // one day intercept
                String year = String.valueOf(selectingDay.getYear());
                String month = String.valueOf(selectingDay.getMonth());
                String day = String.valueOf(selectingDay.getDay());
                String selectDay = year+"-"+month+"-"+day;
                if (selectingDay!=null ){
                    chooseCheckin(selectDay);
                }else {
                    selectingDay=null;
                    chooseCheckin("正在选择");
                }
                for (String a:alreadyBook) {
                    LogUtils.i("已购-日期"+a);
                    String[] strs=a.split("-");
                    LogUtils.i("分割的日期"+strs[0]+strs[1]+strs[2]);
                    if (strs[1].equals(String.valueOf(selectingDay.getMonth()))){
                        if (strs[2].equals(String.valueOf(selectingDay.getDay()))){
                         ToastUtils.showLongToast("不能选择别人已经选择的哟");
                         return true;
                        }else {
                            continue;
                        }
                    }else {
                        continue;
                    }
                }

                if (beforToday(selectingDay)){
                    //已购不能选在这里限制
                    if(mySelectingDay!=null&&mySelectingDay==selectingDay){
                        return true;
                    }
                    mySelectingDay=selectingDay;
                    return super.onInterceptSelect(selectingDay);
                }else {
                    ToastUtils.showShortToast("时间已经过去了就过去吧");
                    return true;
                }
            }

            @Override
            public boolean onInterceptSelect(FullDay startDay, FullDay endDay) { // segment days intercept
                int differDays = SCDateUtils.countDays(startDay.getYear(), startDay.getMonth(), startDay.getDay(),
                        endDay.getYear(), endDay.getMonth(), endDay.getDay());

                int currentMonth=startDay.getMonth();
                int currentDay=startDay.getDay();
                String mCheckInDay1=startDay.getYear()+"-"+ currentMonth+"-"+String.valueOf(currentDay);
                String  mCheckInDay=startDay.getYear()+"-"+ startDay.getMonth()+"-"+startDay.getDay();
                String  mCheckOutDay=endDay.getYear()+"-"+ endDay.getMonth()+"-"+endDay.getDay();
                for (int j = 0; j <differDays ; j++) {
                    for (int i = 0; i <alreadyBook.size() ; i++) {
                        if (alreadyBook.get(i).equals(mCheckInDay1)){
                           ToastUtils.showLongToast("不能选择别人租过的房子哟");
                            LogUtils.d("days",">>>>>>>>>>>>拦截成功");
                            return true;
                        }

                    }
                    //循环出所选的每一天
                    if (currentMonth==4||currentMonth==6||currentMonth==9||currentMonth==11){
                        if (currentDay==30){
                            currentMonth++;
                            currentDay=1;
                            mCheckInDay1=startDay.getYear()+"-"+ currentMonth+"-"+String.valueOf(currentDay);
                        }else {
                            mCheckInDay1=startDay.getYear()+"-"+ currentMonth+"-"+String.valueOf(++currentDay);
                        }
                    }else if (currentMonth==2){
                        if (currentDay==29){
                            currentMonth++;
                            currentDay=1;
                            mCheckInDay1=startDay.getYear()+"-"+ currentMonth+"-"+String.valueOf(currentDay);
                        }else {
                            mCheckInDay1=startDay.getYear()+"-"+ currentMonth+"-"+String.valueOf(++currentDay);
                        }
                    }else {
                        if (currentDay==31){
                            currentMonth++;
                            currentDay=1;
                            mCheckInDay1=startDay.getYear()+"-"+ currentMonth+"-"+String.valueOf(currentDay);
                        }else {
                            mCheckInDay1=startDay.getYear()+"-"+ currentMonth+"-"+String.valueOf(++currentDay);
                        }
                    }
                }
                    LogUtils.i("开始-时间"+mCheckInDay);
                    LogUtils.i("结束-时间"+mCheckOutDay);
                    LogUtils.i("差距-时间"+differDays);
                    PostCalendarSelectorData postCalendarSelectorData=new PostCalendarSelectorData();
                    postCalendarSelectorData.setStartTime(mCheckInDay);
                    postCalendarSelectorData.setEndTime(mCheckOutDay);
                    CountPrice(differDays,startDay);
                    postCalendarSelectorData.setPrice(mTotalPrice);
                    postCalendarSelectorData.setPrice(price);
                    postCalendarSelectorData.setTotalTime(totalTime);
                    LogUtils.i("总价"+mTotalPrice);
                    EventBus.getDefault().post(postCalendarSelectorData);
                    removeFragment();
                    return super.onInterceptSelect(startDay, endDay);

            }

            @Override
            public void selectedSameDay(FullDay sameDay) { // selected the same day
                super.selectedSameDay(sameDay);
            }
        });
        rvCalendar.setAdapter(new CalendarAdapter(data));
    }

    private boolean beforToday(FullDay selectingDay) {
        if (mToday==-1){
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            month++;
            int day = c.get(Calendar.DAY_OF_MONTH);
            mToday=year*10000+month*100+day;//年后面应该乘以10000，这样可以保证月份占两位，天数占两位
        }
        int year1 = selectingDay.getYear();
        int month1 = selectingDay.getMonth();
        int day1 = selectingDay.getDay();
        int mToday1=year1*10000+month1*100+day1;
       if (mToday1>mToday){
           return true;
       }else {
           return false;
       }


    }
    //计算价格
    private void CountPrice(int differDays,FullDay startDay) {
        //入住总天数
        totalTime = differDays-1;
        if (price!=0){
            mTotalPrice=differDays*price;
        }
        int currentMonth=startDay.getMonth();
        int currentDay=startDay.getDay();
        String mCheckInDay=startDay.getYear()+"-"+ currentMonth+"-"+String.valueOf(currentDay);;
        for (int j = 0; j <differDays ; j++) {

            LogUtils.i("循环出所选的天数"+mCheckInDay);
            for (int i = 0; i <priceCalendarData.getData().getPrice_calendar().size() ; i++) {
                if (priceCalendarData.getData().getPrice_calendar().get(i).getDay().equals(mCheckInDay)){
                    LogUtils.i("之前的-价格"+mTotalPrice);
                    mTotalPrice=mTotalPrice-price+priceCalendarData.getData().getPrice_calendar().get(i).getPrice();
                    LogUtils.i("单选的-价格"+priceCalendarData.getData().getPrice_calendar().get(i).getPrice());
                    LogUtils.i("之后的-价格"+mTotalPrice);
                }
            }
            //循环出所选的每一天
            if (currentMonth==4||currentMonth==6||currentMonth==9||currentMonth==11){
                if (currentDay==30){
                    currentMonth++;
                    currentDay=1;
                    mCheckInDay=startDay.getYear()+"-"+ currentMonth+"-"+String.valueOf(currentDay);
                }else {
                    mCheckInDay=startDay.getYear()+"-"+ currentMonth+"-"+String.valueOf(++currentDay);
                }
            }else if (currentMonth==2){
                if (currentDay==29){
                    currentMonth++;
                    currentDay=1;
                    mCheckInDay=startDay.getYear()+"-"+ currentMonth+"-"+String.valueOf(currentDay);
                }else {
                    mCheckInDay=startDay.getYear()+"-"+ currentMonth+"-"+String.valueOf(++currentDay);
                }
            }else {
                if (currentDay==31){
                    currentMonth++;
                    currentDay=1;
                    mCheckInDay=startDay.getYear()+"-"+ currentMonth+"-"+String.valueOf(currentDay);
                }else {
                    mCheckInDay=startDay.getYear()+"-"+ currentMonth+"-"+String.valueOf(++currentDay);
                }
            }
        }
    }

    private void sendResult(int resultOk,String startTime,String endTime) {
        if(getTargetFragment() == null){
            return;
        }else{
            Intent i = new Intent();
            i.putExtra("startTime",startTime);
            i.putExtra("endTime",endTime);
            getHoldingActivity().setResult(resultOk, i);
//            getTargetFragment().onActivityResult(getTargetRequestCode(),resultOk,i);
        }
    }

    public List<SCMonth> getData() {
        List<SCMonth> month_date = null;
        //获取当前的时间
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        month++;
        int day = c.get(Calendar.DAY_OF_MONTH);
        LogUtils.i("当前的时间"+year+"---"+month+"---"+day);
        if (month + 2 > 12) {
            int nextYear = year + 1;

            int nextMonth = 2;
            month_date = SCDateUtils.generateMonths(year, month, nextYear, nextMonth, SCMonth.SUNDAY_OF_WEEK);
        } else {
            month_date = SCDateUtils.generateMonths(year, month, year, month+2, SCMonth.SUNDAY_OF_WEEK);
        }
        return month_date;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_calendar_selector;
    }


        //适配器
       class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>{

        List<SCMonth> months;
        DayViewInflater animDayViewInflater;

        public CalendarAdapter(List<SCMonth> months){
            this.months = months;

        }
        @Override
        public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CalendarViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_calendar, parent, false));
        }

        @Override
        public void onBindViewHolder(CalendarViewHolder holder, int position) {
            SCMonth scMonth = months.get(position);
            CalendarSelectorData data=new CalendarSelectorData();

            boolean initData=false;
            if (mouthList.size()!=0){

                for (int i = 0; i <mouthList.size() ; i++) {
                    LogUtils.i("整体时间"+mouthList.get(i).getMonthSelector().toString());
                    if (String.valueOf(scMonth.getMonth()).equals(mouthList.get(i).getMonthSelector().get(0).getMonth())){
                        initData=true;
                        data=mouthList.get(i);
                        break;
                    }
            }

            }
            holder.tvMonthTitle.setText(String.format("%d-%d", scMonth.getYear(), scMonth.getMonth()));
            LogUtils.i("传入参数"+alreadyBook.toString());
            if (initData){
                animDayViewInflater = new AnimDayViewInflater(getHoldingActivity(),data,price,alreadyBook);
            }else{
                animDayViewInflater = new AnimDayViewInflater(getHoldingActivity(),price,alreadyBook);
            }
            holder.monthView.setSCMonth(scMonth, animDayViewInflater);
            selector.bind(rvCalendar, holder.monthView, position);
        }

        @Override
        public int getItemCount() {
            return months.size();
        }
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder{

        TextView tvMonthTitle;
        MonthView monthView;

        public CalendarViewHolder(View itemView) {
            super(itemView);
            tvMonthTitle = (TextView) itemView.findViewById(R.id.tvMonthTitle);
            monthView = (MonthView) itemView.findViewById(R.id.ssMv);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_in_layout:
                if (isCheck){

                }else {
                    isCheck=true;
                    chooseCheckin("未选择");
                }
                //ToastUtils.showShortToast("请选择入住时间");
                break;
            case R.id.check_out_layout:
                if (isCheck){
                    isCheck=false;
                    chooseCheckOut("未选择");
                }else {

                }
                //ToastUtils.showShortToast("请选择退房时间");
                break;
            case R.id.back:
                removeFragment();
                break;
        }
    }

    private void chooseCheckin(String selectDay) {
        mCheckOut.setText("未选择");
        mCheckOut.setTextColor(getHoldingActivity().getResources().getColor(R.color.black));
        mCheckOutPicture.setBackground(getHoldingActivity().getResources().getDrawable(R.color.grey2));
        mCheckIn.setText(selectDay);
        mCheckIn.setTextColor(getHoldingActivity().getResources().getColor(R.color.green1));
        mCheckInPicture.setBackground(getHoldingActivity().getResources().getDrawable(R.color.green1));
    }
    private void chooseCheckOut(String selectDay){
        mCheckOut.setText(selectDay);
        mCheckOut.setTextColor(getHoldingActivity().getResources().getColor(R.color.green1));
        mCheckOutPicture.setBackground(getHoldingActivity().getResources().getDrawable(R.color.green1));
        mCheckIn.setText("未选择");
        mCheckIn.setTextColor(getHoldingActivity().getResources().getColor(R.color.black));
        mCheckInPicture.setBackground(getHoldingActivity().getResources().getDrawable(R.color.grey2));
    }


}

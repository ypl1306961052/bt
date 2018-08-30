package com.yunyou.icloudinn.bookhouse.Ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.yunyou.icloudinn.bookhouse.JavaBean.PriceCalendarData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;

public class CalendarFragmentDialog extends DialogFragment {
    CalendarSelector selector;
    RecyclerView rvCalendar;
    List<SCMonth> data;
    private int roomid;
    private CustomProgress customProgress;
    private List<CalendarSelectorData> mouthList=new ArrayList<>();
    private PriceCalendarData priceCalendarData;
    private View view;
    private List<String>alreadyBook=new ArrayList<>();
    private int hotelId,price;

    public static CalendarFragmentDialog getInstance(int hotelId,int price){
        Bundle bundle=new Bundle();
        bundle.putInt("hotelid",hotelId);
        bundle.putInt("price",price);
        CalendarFragmentDialog calendarFragmentDialog =new CalendarFragmentDialog();
        calendarFragmentDialog.setArguments(bundle);
        return calendarFragmentDialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        customProgress=new CustomProgress(getActivity(),R.style.Custom_Progress,"加载中",false);
        view=inflater.inflate(R.layout.dialog_of_calendar, container);
        Bundle bundle = getArguments();
        hotelId = bundle.getInt("hotelid");
        price=bundle.getInt("price");
        LogUtils.i("当前价格2"+price);
        initMyView(view,savedInstanceState);
        initData();
        return view;
    }

    private void initMyView(View view, Bundle savedInstanceState) {
        if(savedInstanceState != null)
            selector = savedInstanceState.getParcelable("selector");
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        rvCalendar = (RecyclerView) view.findViewById(R.id.dialog_calendar_list);
        rvCalendar.setLayoutManager(new LinearLayoutManager(getActivity()));
        View mClose=view.findViewById(R.id.close_calendar_dialog);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ((SimpleItemAnimator) rvCalendar.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void initData() {

        LogUtils.i("传递的三个参数----套间id"+hotelId+"-----单间id----价格---"+price);
        customProgress.show();
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
                            segmentMode();

                        }
                    } catch (Exception e) {
                        ToastUtils.showLongToast("网络错误" + e);
                        data = getData();
                        segmentMode();
                        customProgress.dismiss();
                    }
                }
            });


    }

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
                    days.add(day);
                    CalendarSelectorData calendarSelectorData=new CalendarSelectorData();
                    calendarSelectorData.setMonthSelector(days);
                    mouthList.add(calendarSelectorData);
                    days=new ArrayList<>();
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
                days.add(day);
            }

        }
        customProgress.dismiss();
        data = getData();
        segmentMode();
    }


    private void segmentMode() {
        selector = new CalendarSelector(data, CalendarSelector.SEGMENT);
        selector.setSegmentSelectListener(new SegmentSelectListener() {
            @Override
            public void onSegmentSelect(FullDay startDay, FullDay endDay) {

            }

            @Override
            public boolean onInterceptSelect(FullDay selectingDay) { // one day intercept


                return super.onInterceptSelect(selectingDay);
            }

            @Override
            public boolean onInterceptSelect(FullDay startDay, FullDay endDay) {

                return super.onInterceptSelect(startDay, endDay);

            }


            @Override
            public void selectedSameDay(FullDay sameDay) { // selected the same day
                super.selectedSameDay(sameDay);
            }
        });
        rvCalendar.setAdapter(new CalendarFragmentDialog.CalendarAdapter(data));
    }


    public List<SCMonth> getData() {
        //获取当前的时间
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        LogUtils.i("当前的时间"+year+"---"+month+"---"+day);
        return SCDateUtils.generateMonths(year,month,year,month+2 , SCMonth.SUNDAY_OF_WEEK);
    }


    class CalendarAdapter extends RecyclerView.Adapter<CalendarFragmentDialog.CalendarViewHolder>{

        List<SCMonth> months;
        DayViewInflater DialogAnimDayViewInflater;

        public CalendarAdapter(List<SCMonth> months){
            this.months = months;

        }
        @Override
        public CalendarFragmentDialog.CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CalendarFragmentDialog.CalendarViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_calendar, parent, false));
        }

        @Override
        public void onBindViewHolder(CalendarFragmentDialog.CalendarViewHolder holder, int position) {
            SCMonth scMonth = months.get(position);
            CalendarSelectorData data=new CalendarSelectorData();
            boolean initData=false;
            if (mouthList.size()!=0){
                for (int i = 0; i <mouthList.size() ; i++) {
                    if (String.valueOf(scMonth.getMonth()).equals(mouthList.get(i).getMonthSelector().get(0).getMonth())){
                        initData=true;
                        data=mouthList.get(i);
                        break;
                    }
                }

            }
            holder.tvMonthTitle.setText(String.format("%d-%d", scMonth.getYear(), scMonth.getMonth()));

            if (initData){
                DialogAnimDayViewInflater = new DialogAnimDayViewInflater(getActivity(),data,price,alreadyBook);
            }else{
                DialogAnimDayViewInflater = new DialogAnimDayViewInflater(getActivity(),price,alreadyBook);
            }
            holder.monthView.setSCMonth(scMonth, DialogAnimDayViewInflater);
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



}

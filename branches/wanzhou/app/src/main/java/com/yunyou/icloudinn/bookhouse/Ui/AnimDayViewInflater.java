package com.yunyou.icloudinn.bookhouse.Ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.tubb.calendarselector.custom.DayViewHolder;
import com.tubb.calendarselector.custom.DayViewInflater;
import com.tubb.calendarselector.library.FullDay;
import com.yunyou.icloudinn.bookhouse.JavaBean.CalendarSelectorData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AnimDayViewInflater extends DayViewInflater {

    private CalendarSelectorData month;
    private  Context context;
    private int commonPrice;
    private List<String> alreadyBook=new ArrayList<>();
    //初始化
    public AnimDayViewInflater(Context context,CalendarSelectorData month,int commonPrice,List<String> alreadyBook) {
        super(context);
        this.month= month;
        this.context=context;
        this.commonPrice=commonPrice;
        this.alreadyBook=alreadyBook;
    }
    public AnimDayViewInflater(Context context,int commonPrice,List<String> alreadyBook) {
        super(context);
        this.commonPrice=commonPrice;
        this.context=context;
        this.alreadyBook.addAll(alreadyBook);
    }
    @Override
    public DayViewHolder inflateDayView(ViewGroup container) {
        View dayView = mLayoutInflater.inflate(R.layout.layout_dayview_custom, container, false);
        return new CustomDayViewHolder(dayView,month,commonPrice,alreadyBook,context);
    }

    public static class CustomDayViewHolder extends DayViewHolder{

        protected TextView tvDay,price;
        private int mPrevMonthDayTextColor;
        private int mNextMonthDayTextColor;
        private CalendarSelectorData month;
        private View mDayLayout;
        private int mToday=-1;
        private int commonPrice;
        private String mCheckInDay;
        private Context context;

        List<String> alreadyBook=new ArrayList<>();
        public CustomDayViewHolder(View dayView,CalendarSelectorData month,int commonPrice,List<String> alreadyBook,Context context) {
            super(dayView);
            this.month=month;
            this.commonPrice=commonPrice;
            this.alreadyBook.addAll(alreadyBook);
            this.context=context;
            tvDay = (TextView) dayView.findViewById(R.id.myDay);
            price = (TextView) dayView.findViewById(R.id.price);
            mDayLayout=dayView.findViewById(R.id.day_layout);
            LogUtils.i("传送的数据"+alreadyBook.toString());
            mPrevMonthDayTextColor = ContextCompat.getColor(mContext, com.tubb.calendarselector.library.R.color.c_999999);
            mNextMonthDayTextColor = ContextCompat.getColor(mContext, com.tubb.calendarselector.library.R.color.c_dddddd);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void setCurrentMonthDayText(FullDay day, boolean isSelected) {
//            boolean oldSelected = tvDay.isSelected();
            boolean oldSelected = mDayLayout.isSelected();
            boolean check=false;
//            LogUtils.i("日历月-----"+day.getMonth()+"-----日历天"+day.getDay());
            mCheckInDay= day.getYear()+"-"+ day+"-"+String.valueOf(day);
            for (String a:alreadyBook) {
                String[] strs=a.split("-");
                if (strs[1].equals(String.valueOf(day.getMonth()))){
                    if (strs[2].equals(String.valueOf(day.getDay()))){
                        tvDay.setText("已租");
                        mDayLayout.setBackgroundResource(R.color.green);
                        tvDay.setTextSize(10);
                        price.setVisibility(View.GONE);
                        check=true;
                    }else {
                        continue;
                    }
                }else {
                    continue;
                }
            }
            if (!check){
                tvDay.setText(String.valueOf(day.getDay()));
                if (commonPrice!=0){
                    price.setText("￥"+String.valueOf(commonPrice));
                }
                //设置价格
                if (month!=null){
                    for (int i = 0; i <month.getMonthSelector().size() ; i++) {
                        if (month.getMonthSelector().get(i).getDay().equals(String.valueOf(day.getDay()))){
                            price.setText(String.valueOf("￥"+month.getMonthSelector().get(i).getPrice()));
                            break;
                        }
                    }
                }
            }
//            tvDay.setSelected(isSelected);
            mDayLayout.setSelected(isSelected);
            // selected animation
            if(!oldSelected && isSelected){
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setInterpolator(AnimationUtils.loadInterpolator(mContext, android.R.anim.bounce_interpolator));
                animatorSet.play(ObjectAnimator.ofFloat(tvDay, "scaleX", 0.5f, 1.0f))
                        .with(ObjectAnimator.ofFloat(tvDay, "scaleY", 0.5f, 1.0f));
                animatorSet.play(ObjectAnimator.ofFloat(mDayLayout, "scaleX", 0.5f, 1.0f))
                        .with(ObjectAnimator.ofFloat(mDayLayout, "scaleY", 0.5f, 1.0f));
                animatorSet.setDuration(500)
                        .start();
            }
        }

        @Override
        public void setPrevMonthDayText(FullDay day) {
            boolean check=false;
            tvDay.setTextColor(mPrevMonthDayTextColor);
            for (String a:alreadyBook) {
                String[] strs=a.split("-");
                if (strs[1].equals(String.valueOf(day.getMonth()))){
                    if (strs[2].equals(String.valueOf(day.getDay()))){
                        tvDay.setText("已租");
                        check=true;
                    }else {
                        continue;
                    }
                }else {
                    continue;
                }
            }
            if (!check){
                tvDay.setText(String.valueOf(day.getDay()));
                if (commonPrice!=0){
                    price.setTextColor(mPrevMonthDayTextColor);
                    price.setText("￥"+String.valueOf(commonPrice));
                }
                if (month!=null){
                    for (int i = 0; i <month.getMonthSelector().size() ; i++) {
                        if (month.getMonthSelector().get(i).getDay().equals(String.valueOf(day.getDay()))){
                            price.setText(month.getMonthSelector().get(i).getPrice());
                            price.setTextColor(mPrevMonthDayTextColor);
                            break;
                        }
                    }
                }
            }
        }

        @Override
        public void setNextMonthDayText(FullDay day) {
            tvDay.setTextColor(mNextMonthDayTextColor);
            boolean check=false;
            for (String a:alreadyBook) {
                String[] strs=a.split("-");
                if (strs[1].equals(String.valueOf(day.getMonth()))){
                    if (strs[2].equals(String.valueOf(day.getDay()))){
                        tvDay.setText("已租");
                        check=true;
                    }else {
                        continue;
                    }
                }else {
                    continue;
                }
            }
            if (!check){
                tvDay.setText(String.valueOf(day.getDay()));
                if (commonPrice!=0){
                    price.setTextColor(mPrevMonthDayTextColor);
                    price.setText("￥"+String.valueOf(commonPrice));
                }
                if (month!=null){
                    for (int i = 0; i <month.getMonthSelector().size() ; i++) {
                        if (month.getMonthSelector().get(i).getDay().equals(String.valueOf(day.getDay()))){
                            price.setText(month.getMonthSelector().get(i).getPrice());
                            break;
                        }
                    }
                }
            }

        }
        private boolean beforToday(FullDay selectingDay) {
            if (mToday==-1){
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                month++;
                int day = c.get(Calendar.DAY_OF_MONTH);
                mToday=year*1000+month*100+day;
            }
            int year1 = selectingDay.getYear();
            int month1 = selectingDay.getMonth();
            int day1 = selectingDay.getDay();
            int mToday1=year1*1000+month1*100+day1;
            if (mToday1>mToday){
                return true;
            }else {
                return false;
            }
    }
}
}
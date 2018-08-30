package com.yunyou.icloudinn.bookhouse.Ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
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
import java.util.List;

public class DialogAnimDayViewInflater extends DayViewInflater {
    private CalendarSelectorData month;
    private  Context context;
    private int commonPrice;
    private List<String> alreadyBook=new ArrayList<>();
    public DialogAnimDayViewInflater(Context context,CalendarSelectorData month,int commonPrice,List<String> alreadyBook) {
        super(context);
        this.month= month;
        this.context=context;
        this.commonPrice=commonPrice;
        this.alreadyBook=alreadyBook;

    }
    public DialogAnimDayViewInflater(Context context,int commonPrice,List<String> alreadyBook) {
        super(context);
        this.context=context;
        this.commonPrice=commonPrice;
        this.alreadyBook.addAll(alreadyBook);
    }
    @Override
    public DayViewHolder inflateDayView(ViewGroup container) {
        View dayView = mLayoutInflater.inflate(R.layout.dialog_day_item, container, false);
        return new AnimDayViewInflater.CustomDayViewHolder(dayView,month,commonPrice,alreadyBook,context);
    }


}

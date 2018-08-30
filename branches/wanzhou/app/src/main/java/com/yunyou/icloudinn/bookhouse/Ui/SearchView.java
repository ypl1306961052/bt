package com.yunyou.icloudinn.bookhouse.Ui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;


public class SearchView extends EditText implements View.OnFocusChangeListener,View.OnKeyListener {
    /*
    * 是否是默认状态（图标在左边）
    * */
    private boolean isLeft=false;
    /*
    * 是否点击软键盘搜索
    * */
    private boolean pressSearch=false;
    /*
    * 软键盘搜索监听
    * */
    private OnSearchClickListener listener;

    public void setOnSearchClickListener(OnSearchClickListener onSearchClickListener){
        listener=onSearchClickListener;
    }

    public SearchView(Context context) {
        this(context, null);
        initView();
    }


    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
        initView();
    }


    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setOnFocusChangeListener(this);
        setOnKeyListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (isLeft){
            super.onDraw(canvas);
        }else {
//            Drawable[] drawables=getCompoundDrawables();
//            Drawable drawableLeft=drawables[0];
            Drawable[] drawables = getCompoundDrawables();
            Drawable drawableLeft = drawables[0];
//            Drawable drawableRight=drawables[2];
            translate(drawableLeft,canvas);
            super.onDraw(canvas);

        }
    }

    public void translate(Drawable drawable,Canvas canvas){
        if (drawable!=null){
            float textWidth=getPaint().measureText(getHint().toString());//字数的长度
            int drawablePadding =getCompoundDrawablePadding();//padding的长度
            int drawableWidth=drawable.getIntrinsicWidth();
            float bodyWidth =textWidth+drawablePadding+drawableWidth;
            canvas.translate((getWidth()-bodyWidth-getPaddingLeft()-getPaddingRight())/2,0);
        }

    }



    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        LogUtils.i("焦点变化"+hasFocus);
        if (!pressSearch&& TextUtils.isEmpty(getText().toString())){
            isLeft=hasFocus;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        pressSearch=(keyCode==KeyEvent.KEYCODE_ENTER);

        if (pressSearch && listener != null) {
            /*隐藏软键盘*/
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            }
            if (event.getAction() == KeyEvent.ACTION_UP) {
                listener.onSearchClick(v);
            }
        }
        return false;
    }
    public interface OnSearchClickListener{
        void onSearchClick(View view);
    }
}

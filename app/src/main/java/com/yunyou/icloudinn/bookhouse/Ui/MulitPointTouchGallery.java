package com.yunyou.icloudinn.bookhouse.Ui;

/**
 * Created by chen on 2017/10/17.
 */

/**
 * MyGallery.java
 * @version 1.0
 * @author Haven
 * @createTime 2011-12-9 下午03:42:53
 * android.widget.Gallery的子函数。此类很重要。建议仔细看
 */


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;

import com.yunyou.icloudinn.bookhouse.R;

public class MulitPointTouchGallery extends Gallery {
    private static final String TAG = "MulitPointTouchGallery";
    private GestureDetector gestureScanner;
    private MulitPointTouchImageView mImageView;
    private float mMaxScale = 4f;
    private float mMinScale = 0.5f;
    private float baseValue;
    float originalScale;
    private int width;
    private int height;

    public MulitPointTouchGallery(Context context) {
        super(context);
        init();
        Log.d(TAG, "MulitPointTouchGallery(Context context)");
        gestureScanner = new GestureDetector(new MySimpleGesture());
    }

    public MulitPointTouchGallery(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
        init();
        Log.d(TAG,
                "MulitPointTouchGallery(Context context, AttributeSet attrs,int defStyle)");
        gestureScanner = new GestureDetector(new MySimpleGesture());
    }

    public MulitPointTouchGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        Log.d(TAG,
                "MulitPointTouchGallery(Context context, AttributeSet attrs)");
        gestureScanner = new GestureDetector(new MySimpleGesture());
    }

    private void init() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        width = dm.widthPixels;
        height = dm.heightPixels;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        // Log.d(TAG, "onScroll()");
        View view = getGallerySelectedView();
        if (view instanceof MulitPointTouchImageView) {
            mImageView = (MulitPointTouchImageView) view;

            float v[] = new float[9];
            Matrix m = mImageView.getImageMatrix();
            m.getValues(v);
            // 图片实时的上下左右坐标
            float left, right;
            // 图片的实时宽，高
            float width, height;
            width = mImageView.getScale() * mImageView.getBitmapOriginalWidth();
            height = mImageView.getScale()
                    * mImageView.getBitmapOriginalHeight();
            // 一下逻辑为移动图片和滑动gallery换屏的逻辑。如果没对整个框架了解的非常清晰，改动以下的代码前请三思！！！！！！
            // 如果图片当前大小<屏幕大小，直接处理滑屏事件
            if ((int) width <= width
                    && (int) height <= height) {
                super.onScroll(e1, e2, distanceX, distanceY);
            } else {
                left = v[Matrix.MTRANS_X];
                right = left + width;
                Rect r = new Rect();
                mImageView.getGlobalVisibleRect(r);

                if (distanceX > 0)// 向左滑动
                {
                    if (r.left > 0) {// 判断当前ImageView是否显示完全

                        super.onScroll(e1, e2, distanceX, distanceY);

                    } else if (right < width) {

                        super.onScroll(e1, e2, distanceX, distanceY);

                    } else {
                        mImageView.postTranslate(-distanceX, -distanceY);
                    }
                } else if (distanceX < 0)// 向右滑动
                {
                    if (r.right < width) {

                        super.onScroll(e1, e2, distanceX, distanceY);

                    } else if (left > 0) {

                        super.onScroll(e1, e2, distanceX, distanceY);

                    } else {
                        mImageView.postTranslate(-distanceX, -distanceY);
                    }
                }

            }

        } else {
            super.onScroll(e1, e2, distanceX, distanceY);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 双击事件和单击事件有冲突,当双击事件触发之后,单击ACTION_UP也同时触发,因为双击之后有一个"弹回"的效果,而弹回效果有200毫秒的间隔,
        // 这时单击事件已执行完毕(执行的时间又系统决定,所以就会出现误差时大时小),而双击之后的缩放没有完全,单击事件就认为此时已经缩放完毕,之后取现在的值进行计算,导致误差
        // float mtrans_x = values[Matrix.MTRANS_X];//左上顶点X坐标
        // float mtrans_y = values[Matrix.MTRANS_Y];//左上顶点Y坐标
        // float mscale_x = values[Matrix.MSCALE_X] ;//宽度缩放倍数
        // float mscale_y = values[Matrix.MSCALE_Y] ;//高度缩放位数

        gestureScanner.onTouchEvent(event);
        View view = getGallerySelectedView();
        if (view instanceof MulitPointTouchImageView) {
            mImageView = (MulitPointTouchImageView) view;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    baseValue = 0;
                    originalScale = mImageView.getScale();
                case MotionEvent.ACTION_MOVE:
                    if (event.getPointerCount() == 2) {
                        float x = event.getX(0) - event.getX(1);
                        float y = event.getY(0) - event.getY(1);
                        float value = (float) Math.sqrt(x * x + y * y);//
                        // 计算两点的距离
                        // System.out.println("value:" + value);
                        if (baseValue == 0) {
                            baseValue = value;
                        } else {

                            // 当前两点间的距离除以手指落下时两点间的距离就是需要缩放的比例。
                            float scale = value / baseValue;

                            // scale the image
                            float currentScale = originalScale * scale;
                            Log.d(TAG, "currentScale:" + currentScale);

                            // 只有在允许的缩放范围内,才可以缩放
                            if (currentScale >= mMinScale
                                    && currentScale <= mMaxScale) {

                                mImageView.zoomTo(currentScale, x + event.getX(1),
                                        y + event.getY(1), 200f);

                            }

                        }
                    }
                    // case MotionEvent.ACTION_UP:
                    // Log.d(TAG, "ACTION_UP");
                    // if (event.getPointerCount() == 1) {
                    //
                    // // 判断上下边界是否越界
                    // View view = getGallerySelectedView();
                    // if (view instanceof MulitPointTouchImageView) {
                    // mImageView = (MulitPointTouchImageView) view;
                    //
                    // // 如果此时没有缩放完毕,则不进行图片的操作
                    // if (!mImageView.mIsScaleFinish) {
                    // break;
                    // }
                    //
                    // float width = mImageView.getScale()
                    // * mImageView.getBitmapOriginalWidth();
                    // float height = mImageView.getScale()
                    // * mImageView.getBitmapOriginalHeight();
                    // Log.d(TAG, "imageView.getBitmapOriginalWidth():"
                    // + mImageView.getBitmapOriginalWidth());
                    // Log.d(TAG, "imageView.getBitmapOriginalHeight():"
                    // + mImageView.getBitmapOriginalHeight());
                    // Log.d(TAG, "imageView.getScale():" + mImageView.getScale());
                    // Log.d(TAG, "width:" + width);
                    // Log.d(TAG, "height:" + height);
                    // if ((int) width <= width
                    // && (int) height <= height)//
                    // // 如果图片当前大小<屏幕大小，判断边界
                    // {
                    // break;
                    // }
                    // float v[] = new float[9];
                    // Matrix m = mImageView.getImageMatrix();
                    // m.getValues(v);
                    // float top = v[Matrix.MTRANS_Y];//
                    // // 此时屏幕左上角原点在当前图片坐标中的Y坐标,也就是屏幕上边缘和原始图片上边缘的距离
                    // Log.d(TAG, "top:" + top);
                    // float bottom = top + height;// 得到屏幕上方边缘至下边缘的距离
                    // Log.d(TAG, "bottom:" + bottom);
                    //
                    // // 判断图片的上边缘是否显示在屏幕内,如果显示了,则让图片的上边缘与屏幕的上边缘对齐,也就是图片的"弹回"的效果
                    // if (top > 0) {
                    // Log.d(TAG, "top postTranslateDur");
                    // mImageView.postTranslateDur(-top, 200f);
                    // }
                    // // 下边缘的"弹回"效果,原理与上边缘类似
                    // if (bottom < height) {
                    // Log.d(TAG, "bottom postTranslateDur");
                    // mImageView.postTranslateDur(
                    // height - bottom, 200f);
                    // }
                    //
                    // }
                    // }
                    // break;
            }
        }
        return super.onTouchEvent(event);
    }

    private class MySimpleGesture extends SimpleOnGestureListener {

        // 按两下的第二下Touch down时触发
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(TAG, "onDoubleTap()");
            View view = getGallerySelectedView();
            if (view instanceof MulitPointTouchImageView) {
                mImageView = (MulitPointTouchImageView) view;
                Log.d(TAG,
                        "imageView.getScale:" + mImageView.getScale()
                                + " imageView.getFitScreenScale:"
                                + mImageView.getFitScreenScale());
                if (mImageView.getBitmapOriginalWidth() > width
                        || mImageView.getBitmapOriginalHeight() > height) {
                    // 当屏幕装不下当前图片
                    // imageView.getScale():图片当前的缩放比例;imageView.getScaleRate():图片在屏幕中正好显示的比例
                    if (mImageView.getScale() > mImageView.getFitScreenScale()) {
                        // 缩小
                        mImageView.zoomTo(mImageView.getFitScreenScale(),
                                width / 2, height / 2,
                                200f);
                    } else {
                        // 放大
                        mImageView.zoomTo(1.0f, width / 2,
                                height / 2, 200f);
                    }
                } else {
                    // 当屏幕正好能装下当前图片
                    // imageView.getScale():图片当前的缩放比例;imageView.getScaleRate():图片在屏幕中正好显示的比例
                    if (mImageView.getScale() < mMaxScale) {
                        // 放大
                        mImageView.zoomTo(mMaxScale, width / 2,
                                height / 2, 200f);
                        // Log.d(TAG, "放大");
                    } else {
                        // 缩小
                        mImageView.zoomTo(mImageView.getFitScreenScale(),
                                width / 2, height / 2,
                                200f);
                        // Log.d(TAG, "缩小");
                    }
                }

            }
            // 此处将return true改为让父类去处理相关方法,而不是自己去处理
            return super.onDoubleTap(e);
        }
    }

    /**
     * 获得当前Gallery显示的视图
     *
     * @return
     */
    private View getGallerySelectedView() {
        View view = MulitPointTouchGallery.this.getSelectedView();
        ImageView imageView = null;
        if (view != null) {
            imageView = (ImageView) view.findViewById(R.id.imgImageView);
        }
        // View view = MulitPointTouchGallery.this.getSelectedView();
        return imageView;
    };

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        int keyCode;
        if (isScrollingLeft(e1, e2)) {
            keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
        } else {
            keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(keyCode, null);
        return true;
        // return false;
    }

    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
        // 调整左右滑动的灵敏度
        return e2.getX() > e1.getX();
    }

    /**
     * 把放大的图片恢复到原来的状态
     *
     * @param view
     */
    public void recoveryImageMatrix() {
        View viewTemp = getGallerySelectedView();
        if (viewTemp == null) {
            return;
        }
        if (viewTemp instanceof MulitPointTouchImageView) {
            MulitPointTouchImageView mulitPointImageView = (MulitPointTouchImageView) viewTemp;
            // 缩小
            mulitPointImageView.zoomTo(mulitPointImageView.getFitScreenScale(),
                    width / 2, height / 2, 200f);
        }

    }
}

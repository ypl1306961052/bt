package com.yunyou.icloudinn.bookhouse.Ui;

/**
 * Created by chen on 2017/10/13.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSONArray;
import com.yunyou.icloudinn.bookhouse.Adapter.ImageAdapter;
import com.yunyou.icloudinn.bookhouse.R;


public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private CustomDialog dialog;
        private View layout;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // instantiate the dialog with the custom Theme
            dialog = new CustomDialog(context,R.style.Dialog);
            layout = inflater.inflate(R.layout.dialog_normal_layout, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            // 全屏
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(lp);
            dialog.getWindow().setBackgroundDrawable(null);
            dialog.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }


        public CustomDialog create(JSONArray urlArr) {

            // 设置图片
            MulitPointTouchGallery gallery = (MulitPointTouchGallery)layout.findViewById(R.id.gallery);

            gallery.setVerticalFadingEdgeEnabled(false);// 取消竖直渐变边框
            gallery.setHorizontalFadingEdgeEnabled(false);// 取消水平渐变边框
            gallery.setAdapter(new ImageAdapter(context,urlArr));

            gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    // 选中Gallery中某个图像时，放大显示该图像
//                    WindowManager wm = (WindowManager) context
//                            .getSystemService(Context.WINDOW_SERVICE);
//
//                    int width = wm.getDefaultDisplay().getWidth();
//                    int height = wm.getDefaultDisplay().getHeight();
//                    MulitPointTouchImageView imageview = (MulitPointTouchImageView)view.findViewById(R.id.imgImageView);
//                             view.setLayoutParams(new Gallery.LayoutParams(width, height));
//                            for(int i=0; i<parent.getChildCount();i++){
//                                     //缩小选中图片旁边的图片
//                                MulitPointTouchImageView local_imageview = (MulitPointTouchImageView)parent.findViewById(R.id.imgImageView);
//                                     if(local_imageview!=imageview){
//                                             local_imageview.setLayoutParams(new Gallery.LayoutParams(width-100, height-100));
//                                            local_imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                                         }
//                                }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

//
//            // set the confirm button
//            if (positiveButtonText != null) {
//                ((Button) layout.findViewById(R.id.positiveButton))
//                        .setText(positiveButtonText);
//                if (positiveButtonClickListener != null) {
//                    ((Button) layout.findViewById(R.id.positiveButton))
//                            .setOnClickListener(new View.OnClickListener() {
//                                public void onClick(View v) {
//                                    positiveButtonClickListener.onClick(dialog,
//                                            DialogInterface.BUTTON_POSITIVE);
//                                }
//                            });
//                }
//            } else {
//                // if no confirm button just set the visibility to GONE
//                layout.findViewById(R.id.positiveButton).setVisibility(
//                        View.GONE);
//            }
            // set the cancel button
//            if (negativeButtonText != null) {
//                ((Button) layout.findViewById(R.id.negativeButton))
//                        .setText(negativeButtonText);
//                if (negativeButtonClickListener != null) {
//                    ((Button) layout.findViewById(R.id.negativeButton))
//                            .setOnClickListener(new View.OnClickListener() {
//                                public void onClick(View v) {
//                                    negativeButtonClickListener.onClick(dialog,
//                                            DialogInterface.BUTTON_NEGATIVE);
//                                }
//                            });
//                }
//            } else {
//                // if no confirm button just set the visibility to GONE
//                layout.findViewById(R.id.negativeButton).setVisibility(
//                        View.GONE);
//            }
            // set the content message
//            if (message != null) {
//                ((TextView) layout.findViewById(R.id.message)).setText(message);
//            } else if (contentView != null) {
//                // if no message set
//                // add the contentView to the dialog body
//                ((LinearLayout) layout.findViewById(R.id.content))
//                        .removeAllViews();
//                ((LinearLayout) layout.findViewById(R.id.content))
//                        .addView(contentView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
//            }
            dialog.setContentView(layout);
            return dialog;
        }
    }

}
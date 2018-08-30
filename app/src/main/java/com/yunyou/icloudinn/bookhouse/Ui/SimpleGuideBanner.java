package com.yunyou.icloudinn.bookhouse.Ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;
import com.yunyou.icloudinn.bookhouse.R;

public class SimpleGuideBanner extends BaseIndicatorBanner<Integer, SimpleGuideBanner> {
    public SimpleGuideBanner(Context context) {
        this(context, null, 0);
    }

    public SimpleGuideBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleGuideBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBarShowWhenLast(false);
    }

    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(mContext, R.layout.adapter_simple_guide, null);
        ImageView iv = ViewFindUtils.find(inflate, R.id.iv);
        View tv_jumpMen = ViewFindUtils.find(inflate, R.id.men_layout);
        View tv_jumpFemale = ViewFindUtils.find(inflate, R.id.female_layout);

        final Integer resId = mDatas.get(position);
        tv_jumpMen.setVisibility(position == mDatas.size() - 1 ? VISIBLE : GONE);
        tv_jumpFemale.setVisibility(position == mDatas.size() - 1 ? VISIBLE : GONE);
        Glide.with(mContext)
                .load(resId)
                .into(iv);

        tv_jumpMen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onJumpClickLMen != null)
                    onJumpClickLMen.onJumpClickMen();
            }
        });
        tv_jumpFemale.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onJumpClickLFemale != null)
                    onJumpClickLFemale.onJumpClickFemale();
            }
        });

        return inflate;
    }

    private OnJumpClickLMen onJumpClickLMen;
    private OnJumpClickLFemale onJumpClickLFemale;

    public interface OnJumpClickLMen {
        void onJumpClickMen();
    }
    public interface OnJumpClickLFemale {
        void onJumpClickFemale();
    }

    public void setOnJumpClickLMen(OnJumpClickLMen onJumpClickLMen) {
        this.onJumpClickLMen = onJumpClickLMen;
    }
    public void setOnJumpClickLFemale(OnJumpClickLFemale onJumpClickLFemale) {
        this.onJumpClickLFemale = onJumpClickLFemale;
    }
}

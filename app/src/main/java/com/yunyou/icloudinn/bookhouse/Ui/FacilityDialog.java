package com.yunyou.icloudinn.bookhouse.Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.JavaBean.HouseDetailData;
import com.yunyou.icloudinn.bookhouse.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

public class FacilityDialog extends DialogFragment {//房源描述
    private HouseDetailData.DataBean commentBean;
    private RecyclerView mFaciltty;
    public  static FacilityDialog getInstance(HouseDetailData.DataBean commentBean){
        Bundle bundel=new Bundle();
        bundel.putSerializable("data",commentBean);
        FacilityDialog facilityDialog=new FacilityDialog();
        facilityDialog.setArguments(bundel);
        return facilityDialog;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        commentBean= (HouseDetailData.DataBean) getArguments().getSerializable("data");

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_facility, container);//房源描述
        Button mClose= (Button) view.findViewById(R.id.close_webview_dialog);//关闭
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mFaciltty= (RecyclerView) view.findViewById(R.id.facility_list);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),4,GridLayoutManager.VERTICAL,false);
        mFaciltty.setLayoutManager(gridLayoutManager);
        mFaciltty.setAdapter(new CommonAdapter<HouseDetailData.DataBean.SupportBean>(getActivity(),R.layout.facility_list_item,commentBean.getSupport()) {

            @Override
            protected void convert(ViewHolder holder, HouseDetailData.DataBean.SupportBean supportBean, int position) {
                holder.setText(R.id.assort_item_text,supportBean.getName());
                Glide.with(getActivity())
                        .load(supportBean.getImg())//房源描述的
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.assort_item_image));
            }
        });

        return view;
    }
}

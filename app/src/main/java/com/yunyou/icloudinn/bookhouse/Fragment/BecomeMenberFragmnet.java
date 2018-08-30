package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cuieney.rxpay_annotation.WX;
import com.cuieney.sdk.rxpay.RxPay;
import com.yunyou.icloudinn.bookhouse.Activity.HouseListActivity;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.BookRentData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import io.reactivex.functions.Consumer;
import okhttp3.Call;

@WX(packageName = "com.yunyou.icloudinn.bookhouse")
public class BecomeMenberFragmnet  extends BaseFragment implements View.OnClickListener {
    private boolean isAffirm;
    private TextView bookName;
    private String mName;
    private int id,bookPrice;
    public static BecomeMenberFragmnet getInstance(boolean affirm){
        BecomeMenberFragmnet becomeMenberFragmnet=new BecomeMenberFragmnet();
        Bundle bundle=new Bundle();
        bundle.putBoolean("affirm",affirm);
        becomeMenberFragmnet.setArguments(bundle);
        return becomeMenberFragmnet;
    }

    public static BecomeMenberFragmnet getInstance(boolean affirm,String bookName,int id,int price){
        BecomeMenberFragmnet becomeMenberFragmnet=new BecomeMenberFragmnet();
        Bundle bundle=new Bundle();
        bundle.putBoolean("affirm",affirm);
        bundle.putString("bookname",bookName);
        bundle.putInt("id",id);
        bundle.putInt("price",price);
        becomeMenberFragmnet.setArguments(bundle);
        return becomeMenberFragmnet;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

            TextView tiele= (TextView) view.findViewById(R.id.title);
            View back=view.findViewById(R.id.back);
            back.setOnClickListener(this);
        if (isAffirm){
            View check=view.findViewById(R.id.rent_check);
            View cancel=view.findViewById(R.id.rent_cancel);
            bookName= (TextView) view.findViewById(R.id.rent_check_book_name);
            TextView price = (TextView) view.findViewById(R.id.rent_book_price);
            tiele.setText("确认租书");
            bookName.setText("《"+mName+"》");
            price.setText("押金"+bookPrice+"元");
            check.setOnClickListener(this);
            cancel.setOnClickListener(this);
        }else {
            View houseBecomeMember=view.findViewById(R.id.house_become_member);
            View bookBecomeMember=view.findViewById(R.id.book_become_member);
            tiele.setText("确认租书");
            houseBecomeMember.setOnClickListener(this);
            bookBecomeMember.setOnClickListener(this);
        }
    }

    @Override
    protected int getLayoutId() {
        if (getArguments()!=null){
            isAffirm=getArguments().getBoolean("affirm");
            LogUtils.i("是否是租书"+isAffirm);
            if (isAffirm){
                mName=getArguments().getString("bookname");
                id=getArguments().getInt("id");
                bookPrice=getArguments().getInt("price");
            }
        }
        if (isAffirm){
            return R.layout.fragment_affirm_rent;
        }else {
            return R.layout.fragment_become_member;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.house_become_member:
                startActivity(new Intent(getActivity(),HouseListActivity.class));
                getHoldingActivity().finish();
                break;
            case R.id.book_become_member:
                getHoldingActivity().finish();
                break;
            case R.id.rent_check:
                rentBook();
                break;
            case R.id.rent_cancel:
                getHoldingActivity().finish();
                break;
            case R.id.back:
                removeFragment();
                break;
        }
    }

    private void rentBook() {
        OkHttpUtils.post()
                .url(Api.RENT_BOOK)
                .addParams("book_id",String.valueOf(id))
                .build()
                .execute(new JsonCallback<BookRentData>(getActivity(),true) {
                    @Override
                    public void onResponse(BookRentData rentData, int id) {
                        wxPay(rentData.getId());
                    }
                });
    }

    /**
     * 微信支付
     */
    private void wxPay(String rentId){
        OkHttpUtils.post().url(Api.WX_PAY_RENT_BOOK)
                .addParams("rent_id",rentId)
                .addParams("pay_way","APP")
                .build()
                .execute(new JsonCallback<String>(getActivity(),true) {
                    @Override
                    public void onResponse(String data, int id) {
                        ToastUtils.showLongToast("调启微信支付....");
                        try {
                            RxPay rxPay = new RxPay(getActivity());
                            rxPay.requestWXpay(new org.json.JSONObject(data))
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
                        removeFragment();
                    }
                });
    }
}

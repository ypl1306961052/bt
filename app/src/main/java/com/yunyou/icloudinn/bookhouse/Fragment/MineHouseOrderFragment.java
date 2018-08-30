package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cuieney.rxpay_annotation.WX;
import com.cuieney.sdk.rxpay.RxPay;
import com.yunyou.icloudinn.bookhouse.Activity.BookCommentActivity;
import com.yunyou.icloudinn.bookhouse.Activity.HouseDetailActivity;
import com.yunyou.icloudinn.bookhouse.Activity.HouseListActivity;
import com.yunyou.icloudinn.bookhouse.Activity.QRActivity;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.HotelOrderData;
import com.yunyou.icloudinn.bookhouse.JavaBean.PageData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.MyUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

@WX(packageName = "com.yunyou.icloudinn.bookhouse")
public class MineHouseOrderFragment extends BaseFragment {
    private RecyclerView mList;
    private RxPay rxPay;// 微信支付
    private List<HotelOrderData> mListData = new ArrayList<>();
    private CommonAdapter<HotelOrderData> mCommentAdapter;
    private TextView mEmpty;
    public static MineHouseOrderFragment getInstance(){
        return new MineHouseOrderFragment();
    }

    @Override
    protected void   initView(View view, Bundle savedInstanceState) {
        StatusBarCompat.compat(getActivity(), getResources().getColor(R.color.green1));
        initMyView(view);
        rxPay = new RxPay(getActivity());// 初始化微信支付
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.getInstance().isLogin()){
            initData();
        }
    }

    private void initData() {
        OkHttpUtils.get().url(Api.ORDER).build()
                .execute(new JsonCallback<PageData<HotelOrderData>>(getActivity(),true) {
                    @Override
                    public void onResponse(PageData<HotelOrderData> page, int id) {
                            List<HotelOrderData> orderList = page.getData();
                            if (orderList.size()==0){
                                mEmpty.setVisibility(View.VISIBLE);
                            }else {
                                mListData.clear();
                                mListData.addAll(orderList);
                                mCommentAdapter.notifyDataSetChanged();
                            }
                    }
                });
    }

    private void initMyView(View view) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getHoldingActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        View mBack=view.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        });
        TextView title= (TextView) view.findViewById(R.id.title);
        title.setText("民宿订单");
        mList= (RecyclerView) view.findViewById(R.id.mine_house_order_list);
        mList.setLayoutManager(linearLayoutManager);
        mEmpty= (TextView) view.findViewById(R.id.empty);
        mEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getActivity(), HouseListActivity.class));
            }
        });
        mCommentAdapter=new CommonAdapter<HotelOrderData>(getHoldingActivity(),R.layout.list_item_house_order,mListData) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void convert(final ViewHolder holder, final HotelOrderData order, final int position) {

                Glide.with(getHoldingActivity())
                        .load(order.getHotel_img())
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.item_order_picture));
                holder.setText(R.id.item_order_name,order.getHotel_name());
                holder.setText(R.id.item_order_total_money,"总价：￥"+order.getTotal_money());
                final String data1 = MyUtils.TimeStamp2Date( String.valueOf(order.getCreate_time()) ,"");
                String data2 =MyUtils.TimeStamp2Date( String.valueOf(order.getArrive_time()) ,"");
                String data3 =MyUtils.TimeStamp2Date( String.valueOf(order.getLeave_time()) ,"");
                holder.setText(R.id.item_order_time,"下单时间："+data1);
                holder.setText(R.id.item_order_live_time,"入住时间："+data2);
                holder.setText(R.id.item_order_left_time,"离店时间："+ data3);
                switch (order.getStatus()){
                    case -1:
                        holder.getView(R.id.item_order_pay).setVisibility(View.INVISIBLE);
                        holder.getView(R.id.wait_for_pay).setVisibility(View.INVISIBLE);
                        holder.setText(R.id.order_status_text,"已取消");
                        holder.getView(R.id.item_order_status).setVisibility(View.INVISIBLE);
                        break;
                    case 0:
                        holder.getView(R.id.item_order_status).setVisibility(View.INVISIBLE);
                        holder.setText(R.id.wait_for_pay,"待支付");
                        holder.getView(R.id.door_house_order).setVisibility(View.INVISIBLE);
                        holder.getView(R.id.item_order_cancel_order).setVisibility(View.VISIBLE);
                        holder.getView(R.id.item_order_pay).setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        holder.setText(R.id.wait_for_pay,"已支付");
                        holder.getView(R.id. item_order_pay).setVisibility(View.INVISIBLE);
                        holder.getView(R.id. item_order_cancel_order).setVisibility(View.VISIBLE);
                        holder.getView(R.id.door_house_order).setVisibility(View.VISIBLE);
                        holder.setText(R.id.item_order_status,"状态：待消费");
                        break;
                    case 2:
                        holder.setText(R.id.wait_for_pay,"已支付");
                        holder.setText(R.id.item_order_status,"状态：正消费");
                        holder.getView(R.id. item_order_pay).setVisibility(View.INVISIBLE);
                        holder.getView(R.id. item_order_cancel_order).setVisibility(View.INVISIBLE);
                        holder.getView(R.id.door_house_order).setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        holder.setText(R.id.wait_for_pay,"已支付");
                        holder.setText(R.id.item_order_status,"状态：待审核");
                        holder.getView(R.id.item_order_pay).setVisibility(View.INVISIBLE);
                        holder.getView(R.id. item_order_cancel_order).setVisibility(View.INVISIBLE);
                        holder.getView(R.id.door_house_order).setVisibility(View.INVISIBLE);
                        holder.setText(R.id.order_status_text,"写评价");
                        break;
                    case 4:
                        holder.setText(R.id.wait_for_pay,"已支付");
                        holder.setText(R.id.item_order_status,"状态：已退房");
                        holder.getView(R.id.item_order_pay).setVisibility(View.INVISIBLE);
                        holder.getView(R.id. item_order_cancel_order).setVisibility(View.VISIBLE);
                        holder.getView(R.id.door_house_order).setVisibility(View.INVISIBLE);
                        holder.setText(R.id.order_status_text,"写评价");
                        break;
                    case 5:
                        holder.setText(R.id.wait_for_pay,"已支付");
                        holder.setText(R.id.item_order_status,"状态：已退房");
                        holder.getView(R.id.item_order_pay).setVisibility(View.INVISIBLE);
                        holder.getView(R.id. item_order_cancel_order).setVisibility(View.VISIBLE);
                        holder.getView(R.id.door_house_order).setVisibility(View.INVISIBLE);
                        holder.setText(R.id.order_status_text,"查看评价");
                        break;
                    default:
                        break;
                }
                //开房,通过二维码开门
                holder.getView(R.id.door_house_order).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), QRActivity.class));
                    }
                });
                //付款
                holder.getView(R.id.item_order_pay).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wxPay(order.getHotel_order_id());
                    }
                });
                //根据状态判断操作
                holder.getView(R.id.item_order_cancel_order).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int orderId = Integer.parseInt(order.getHotel_order_id());
                        switch (order.getStatus()){
                            case 0:
                                startCancel(holder, orderId,position);
                                break;
                            case 1:
                                startCancel(holder, orderId,position);
                                break;
                            case 4:
                                writeComment(order.getHotel_id(),orderId+"");
                                break;
                            case 5:
                                addNewFragment(CommentListFragment.getInstance(Integer.parseInt(order.getHotel_id())));
                                break;
                            default:
                                break;
                        }
                    }
                });
                //点击图片跳转
                holder.getView(R.id.item_order_picture).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getHoldingActivity(), HouseDetailActivity.class);
                        intent.putExtra("id",Integer.parseInt(order.getHotel_id()));
                        startActivity(intent);
                    }
                });
            }
        };
        mList.setAdapter(mCommentAdapter);
    }

    private void writeComment(String houseId, String orderid) {
        Intent intent=new Intent(getHoldingActivity(),BookCommentActivity.class);
        intent.putExtra("id",houseId);
        intent.putExtra("order",orderid);
        intent.putExtra("type",1);
        startActivity(intent);
    }

    private void startCancel(ViewHolder holder,int houseId, final int position) {
        OkHttpUtils.get()
                .url(Api.CANCEL_ORDER+houseId)
                .build()
                .execute(new JsonCallback<String>(getActivity(),true) {
                    @Override
                    public void onResponse(String response, int id) {
                            ToastUtils.showLongToast("取消成功");
                            mListData.get(position).setStatus(-1);
                            mCommentAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void startCheckOut(ViewHolder holder, final int houseId, final int position) {
        OkHttpUtils.get()
                .url(Api.CHECK_OUT+houseId)
                .build()
                .execute(new JsonCallback<String>(getActivity(),true) {
                    @Override
                    public void onResponse(String response, int id) {
                        mListData.get(position).setStatus(3);
                        mCommentAdapter.notifyDataSetChanged();
                        ToastUtils.showLongToast("退房成功");
                    }
                });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_book;
    }

    /**
     * 微信支付
     */
    private void wxPay(String orderId){
        OkHttpUtils.post().url(Api.WX_PAY_HOTEL_ORDER)
                .addParams("hotel_order_id",orderId)
                .addParams("pay_way","APP")
                .build()
                .execute(new JsonCallback<String>(getActivity(),true) {
                    @Override
                    public void onResponse(String data, int id) {
                        ToastUtils.showLongToast("调启微信支付....");
                        try {
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
                    }
                });
    }

}

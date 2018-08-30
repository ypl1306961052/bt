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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Activity.BookCommentActivity;
import com.yunyou.icloudinn.bookhouse.Activity.HouseDetailActivity;
import com.yunyou.icloudinn.bookhouse.Activity.HouseListActivity;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.OrderListData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.MyUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MineHouseOrderFragment extends BaseFragment {
    private RecyclerView mList;
    private List<OrderListData.DataBeanX.DataBean>mListData=new ArrayList<>();
    private CommonAdapter<OrderListData.DataBeanX.DataBean>mCommentAdapter;
    private CustomProgress customProgress;
    private TextView mEmpty;
    public static MineHouseOrderFragment getInstance(){
        return new MineHouseOrderFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        StatusBarCompat.compat(getActivity(), getResources().getColor(R.color.green1));
        initMyView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.getInstance().isLogin()){
            initData();
        }
    }

    private void initData() {
        customProgress.show();
        OkHttpUtils
                .get()
                .url(Api.ORDER)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        customProgress.dismiss();
                        ToastUtils.showLongToast("网络错误"+e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        customProgress.dismiss();
                        try {
                            JSONObject json = JSON.parseObject(response);
                            if (json.getInteger("code") == 100) {
                                OrderListData orderListData=JSON.parseObject(response,OrderListData.class);
                                if (orderListData.getData().getData().size()==0){
                                    mEmpty.setVisibility(View.VISIBLE);
                                }else {
                                    LogUtils.i("代码执行"+orderListData.getData().getData().size());
                                    if (mListData.size()!=0){
                                        mListData.clear();
                                        mListData.addAll(orderListData.getData().getData());
                                        mCommentAdapter.notifyDataSetChanged();
                                    }else {
                                        mListData.addAll(orderListData.getData().getData());
                                        mCommentAdapter.notifyDataSetChanged();
                                    }

                                }

                            } else {
                                ToastUtils.showLongToast( json.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtils.showLongToast("网络错误" + e);
                            LogUtils.i("token---"+MyApplication.getInstance().getUser().getData().getAccess_token()+"-----错误-----"+e);
                        }

                    }
                });
    }

    private void initMyView(View view) {
        customProgress=new CustomProgress(getActivity(),R.style.Custom_Progress,"加载中",false);
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
        mCommentAdapter=new CommonAdapter<OrderListData.DataBeanX.DataBean>(getHoldingActivity(),R.layout.list_item_house_order,mListData) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void convert(final ViewHolder holder, final OrderListData.DataBeanX.DataBean dataBean, final int position) {
                LogUtils.i("民宿订单类型"+dataBean.getStatus());
                Glide.with(getHoldingActivity())
                        .load(Api.BASE_URL+dataBean.getHotel_img())
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.item_order_picture));
                holder.setText(R.id.item_order_name,dataBean.getHotel_name());
                holder.setText(R.id.item_order_total_money,"总价：￥"+dataBean.getTotal_money());
                final String data1 = MyUtils.TimeStamp2Date( String.valueOf(dataBean.getCreate_time()) ,"");
                String data2 =MyUtils.TimeStamp2Date( String.valueOf(dataBean.getArrive_time()) ,"");
                String data3 =MyUtils.TimeStamp2Date( String.valueOf(dataBean.getLeave_time()) ,"");
                holder.setText(R.id.item_order_time,"下单时间："+data1);
                holder.setText(R.id.item_order_live_time,"入住时间："+data2);
                holder.setText(R.id.item_order_left_time,"离店时间："+ data3);
                switch (dataBean.getStatus()){
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
                //退房
                holder.getView(R.id.door_house_order).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startCheckOut(holder,dataBean.getHotel_order_id(),position);
                    }
                });
                //付款
                holder.getView(R.id.item_order_pay).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                //根据状态判断操作
                holder.getView(R.id.item_order_cancel_order).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (dataBean.getStatus()){
                            case 0:
                                startCancel(holder,dataBean.getHotel_order_id(),position);
                                break;
                            case 1:
                                startCancel(holder,dataBean.getHotel_order_id(),position);
                                break;
                            case 4:
                                writeComment(dataBean.getHotel_id(),dataBean.getHotel_order_id());
                                break;
                            case 5:
                                addNewFragment(CommentListFragment.getInstance(dataBean.getHotel_id()));
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
                        intent.putExtra("id",dataBean.getHotel_id());
                        startActivity(intent);
                    }
                });
            }
        };
        mList.setAdapter(mCommentAdapter);
    }

    private void writeComment(int houseId,int orderid) {
        Intent intent=new Intent(getHoldingActivity(),BookCommentActivity.class);
        intent.putExtra("id",houseId);
        intent.putExtra("order",orderid);
        intent.putExtra("type",1);
        startActivity(intent);
    }

    private void startCancel(ViewHolder holder,int houseId, final int position) {
        customProgress.show();
        OkHttpUtils.get()
                .url(Api.CANCEL_ORDER+houseId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        customProgress.dismiss();
                        ToastUtils.showLongToast("网络错误" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        customProgress.dismiss();
                        try {
                            JSONObject json = JSON.parseObject(response);
                            if (json.getInteger("code") == 100) {
                                ToastUtils.showLongToast("取消成功");
                                mListData.get(position).setStatus(-1);
                                mCommentAdapter.notifyDataSetChanged();
                            } else {
                                ToastUtils.showLongToast( json.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtils.showLongToast("网络错误" + e);
                        }
                    }
                });
    }

    private void startCheckOut(ViewHolder holder, final int houseId, final int position) {
        customProgress.show();
        OkHttpUtils.get()
                .url(Api.CHECK_OUT+houseId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        customProgress.dismiss();

                        ToastUtils.showLongToast("网络错误" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        customProgress.dismiss();
                        try {
                            JSONObject json = JSON.parseObject(response);
                            if (json.getInteger("code") == 100) {
                                mListData.get(position).setStatus(3);
                                mCommentAdapter.notifyDataSetChanged();
                                ToastUtils.showLongToast("退房成功");
                            } else {
                                ToastUtils.showLongToast( json.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtils.showLongToast("网络错误" + e);
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_book;
    }


}

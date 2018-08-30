package com.yunyou.icloudinn.bookhouse.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.ConcernData;
import com.yunyou.icloudinn.bookhouse.JavaBean.PageData;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;


public class MineAttentionFragment extends BaseFragment implements View.OnClickListener {//
    //个人关注
    private boolean isAttentionMe = true;
    private RecyclerView mList;
    private List<ConcernData> concernList = new ArrayList<>();
    private CommonAdapter<ConcernData> commonAdapter;
    private boolean isFrist = true;

    public static MineAttentionFragment getInstance(boolean attentionMe) {
        MineAttentionFragment mineAttentionFragment = new MineAttentionFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("attention", attentionMe);
        mineAttentionFragment.setArguments(bundle);
        return mineAttentionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        isAttentionMe = bundle.getBoolean("attention");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initMyView(view);
        initList();
        if (isAttentionMe) {
            initAttentionData("0");
        } else {
            initAttentionData("1");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    private void initMyView(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getHoldingActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList = (RecyclerView) view.findViewById(R.id.mine_attion_user_list);
        View mAddAttention = view.findViewById(R.id.mine_add_attion_layout);
        View mAddMore = view.findViewById(R.id.mine_add_more_layout);
        View mBack = view.findViewById(R.id.back);
        TextView title = (TextView) view.findViewById(R.id.title);
        mAddAttention.setOnClickListener(this);
        mAddMore.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mList.setLayoutManager(linearLayoutManager);
        title.setText("个人关注");
    }

    private void initList() {
        commonAdapter = new CommonAdapter<ConcernData>(getHoldingActivity(), R.layout.list_item_mine_attention_user, concernList) {
            @Override
            protected void convert(ViewHolder holder, final ConcernData concernData, final int position) {
                final UserData user = concernData.getUser();
                if (isAttentionMe){
                    user.setIs_concern(1);
                }
                Glide.with(getHoldingActivity())
                        .load(concernData.getUser().getHead_img_url())
                        .centerCrop()
                        .placeholder(R.drawable.head_img)
                        .into((ImageView) holder.getView(R.id.item_attention_user_picutre));
                holder.getView(R.id.attention_user_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addNewFragment(UserDeatilFragment.getInstance(user));//查看关心用户
                    }
                });
                if (user!=null){
                    if (user.getNickname()==null||user.getNickname().isEmpty()){
                        user.setNickname(user.getYunsu_id()+"");
                    }
                }
                holder.setText(R.id.item_attention_user_name, user.getNickname());
                holder.setText(R.id.item_attention_user_rent, "租" + user.getRent_book_num() + "本");
                holder.setText(R.id.item_attention_user_donate, "捐" + user.getDonate_book_num() + "本");
                holder.setText(R.id.item_attention_user_live_num, "住进" + user.getCheck_in_hotel_num() + "家民宿");
            }
        };
        mList.setAdapter(commonAdapter);
    }

    //加载关注或取消关注列表
    private void initAttentionData(String type) {
        OkHttpUtils.get().url(Api.ATTENTION)
                .addParams("type",type)
                .build()
                .execute(new JsonCallback<PageData<ConcernData>>(getActivity(), true) {
                    @Override
                    public void onResponse(PageData<ConcernData> page, int id) {
                        concernList.clear();
                        concernList.addAll((List) page.getData());
                        commonAdapter.notifyDataSetChanged();
                    }
                });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_attention_list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_add_attion_layout:
                addNewFragment(AddMoreAttentionFragment.getInstance());
                break;
            case R.id.mine_add_more_layout:
                addNewFragment(AddMoreAttentionFragment.getInstance());
                break;
            case R.id.back:
                removeFragment();
                break;
        }
    }
}

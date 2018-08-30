package com.yunyou.icloudinn.bookhouse.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yunyou.icloudinn.bookhouse.Activity.BookChapterActivity;
import com.yunyou.icloudinn.bookhouse.Activity.BookDetailActivity;
import com.yunyou.icloudinn.bookhouse.Activity.BookDonateActivity;
import com.yunyou.icloudinn.bookhouse.Activity.LoginActivity;
import com.yunyou.icloudinn.bookhouse.Activity.MainActivity;
import com.yunyou.icloudinn.bookhouse.Activity.QRActivity;
import com.yunyou.icloudinn.bookhouse.Activity.UserDetailActivity;
import com.yunyou.icloudinn.bookhouse.Adapter.BookPersonAdapter;
import com.yunyou.icloudinn.bookhouse.Adapter.TabAdapter;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.BannerItem;
import com.yunyou.icloudinn.bookhouse.JavaBean.BookListData;
import com.yunyou.icloudinn.bookhouse.JavaBean.BookTypeData;
import com.yunyou.icloudinn.bookhouse.JavaBean.BookWormData;
import com.yunyou.icloudinn.bookhouse.JavaBean.ItemEvent;
import com.yunyou.icloudinn.bookhouse.JavaBean.PageData;
import com.yunyou.icloudinn.bookhouse.JavaBean.WriteBookData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Ui.SimpleImageBanner;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class BookTapFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mHotList, mWriteList, mPersonList, mActivePersonList;
    private CustomProgress customProgress;
    private List<BookListData.DataBeanX.DataBean> mBookHotList;
    private List<BookWormData.DataBean> mBookWormList;
    private List<WriteBookData> mWriteBookList;
    private List<BookTypeData.DataBeanX.DataBean> myTypeData = new ArrayList<>();
    private JSONArray dataX = new JSONArray();
    private CommonAdapter<BookListData.DataBeanX.DataBean> mBookHotAdapter;
    private CommonAdapter<BookWormData.DataBean> mBookWormAdapter;
    private CommonAdapter<WriteBookData> mWriteBookAdapter;
    private ScrollView mScrollView;
    private ImageView mBook;
    private TextView book_button, mRent, mDonate, mClose;
    private boolean mChoose = false;
    private TabLayout tab;
    private LinearLayout noRentDonateView, linearLayout_bottom;
    private ViewPager viewpager;
    public static List<String> tabTitle = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();
    private TabAdapter adapter;
    private BookPersonAdapter bookPersonAdapter;
    private SimpleImageBanner mySimpleImageBanner;
    private LinearLayout bottomLayout;
    private TabFragment tagframent;
    private int itemHeight;
    private int itemCount;
    private ItemEvent itemEvent;
    private boolean isFirst = true;
    private int[] countList;
    private HashMap hashMap = new HashMap();
  private ArrayList<BannerItem> bannerItems = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //注册EventBus
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_book_main, container, false);
        customProgress = new CustomProgress(getActivity(), R.style.Custom_Progress, "加载中", false);
        initData();
        initView(view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//销毁EventBus
    }

    private void initData() {
        //加载动态图片

        OkHttpUtils.
                get().
                url(Api.BOOK_HOUSE)
                .build().
                execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showLongToast("网络错误" + e);
            }

            @Override
            public void onResponse(String response, int id) {
                try {

                    JSONObject jsonObject=JSON.parseObject(response);
                    JSONObject jsonObject1=jsonObject.getJSONObject("data");
                    JSONArray data=jsonObject1.getJSONArray("data");

                    for(int i=0;i<data.size();i++){
                        JSONObject jsonObject2=(JSONObject)data.get(i);
                        BannerItem bannerItem=new BannerItem();
                        bannerItem.imgUrl=jsonObject2.getString( "img_path");
                        bannerItems.add(bannerItem);

                    }
                } catch (Exception e) {
                    ToastUtils.showLongToast("网络错误" + e);
                }
                mySimpleImageBanner.setSource(bannerItems).startScroll();
            }
        });
        //加载书虫
        OkHttpUtils.get().url(Api.BOOK_WORM).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showLongToast("网络错误" + e);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    BookWormData bookWormData = JSON.parseObject(response, BookWormData.class);
                    if (bookWormData.getCode() == 100) {
                        mBookWormList.addAll(bookWormData.getData());
                        mBookWormAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showLongToast(bookWormData.getMsg());
                    }
                } catch (Exception e) {
                    ToastUtils.showLongToast("网络错误" + e);
                }
            }
        });

        //加载著书
        OkHttpUtils.get().url(Api.WRITE_BOOK).build().execute(new JsonCallback<PageData<WriteBookData>>(getActivity(), true) {
            @Override
            public void onResponse(PageData<WriteBookData> page, int id) {
                if (page == null)
                    return;
                mWriteBookList.addAll(page.getData());
                mWriteBookAdapter.notifyDataSetChanged();
            }
        });
        //加载分类
        OkHttpUtils.get()
                .url(Api.BOOK_TYPE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showLongToast("网络错误" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject json = JSON.parseObject(response);
                            if (json.getInteger("code") == 100) {
                                BookTypeData bookTypeData = JSON.parseObject(response, BookTypeData.class);
                                myTypeData.addAll(bookTypeData.getData().getData());
                                initAllType();
                            } else {
                                ToastUtils.showLongToast(json.getString("msg"));
                            }
                        } catch (Exception e) {
                            ToastUtils.showLongToast("网络错误" + e);
                        }
                    }
                });
        //加载我的书
        if (MyApplication.getInstance().isLogin()) {
            OkHttpUtils.get()
                    .url(Api.MY_BOOK)
                    .build()
                    .execute(new JsonCallback<PageData<JSONObject>>(getActivity(), true) {

                        @Override
                        public void onResponse(PageData<JSONObject> page, int id) {
                            try {
                                dataX.addAll((List) page.getData());
                                if (dataX.size() > 0) {
                                    noRentDonateView.setVisibility(View.GONE);
                                    mPersonList.setVisibility(View.VISIBLE);
                                    bookPersonAdapter = new BookPersonAdapter(getActivity(), dataX);
                                    mPersonList.setLayoutManager(new LinearLayoutManager(getActivity())); //设置布局管理器
                                    mPersonList.setAdapter(bookPersonAdapter);
                                    BookPersonAdapter adapter = new BookPersonAdapter(getActivity(), dataX);
                                    mPersonList.setAdapter(adapter);
                                } else {
                                    mPersonList.setVisibility(View.GONE);
                                    noRentDonateView.setVisibility(View.VISIBLE);
                                }

                            } catch (Exception e) {
                                ToastUtils.showLongToast("网络错误" + e);
                            }
                        }
                    });
        }
    }

    private void initView(View view) {
        LinearLayoutManager linearlayoutManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearlayoutManager1 = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearlayoutManager3 = new LinearLayoutManager(getActivity());
        linearlayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearlayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearlayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBookHotList = new ArrayList<>();
        mBookWormList = new ArrayList<>();
        mWriteBookList = new ArrayList<>();

        noRentDonateView = (LinearLayout) view.findViewById(R.id.no_rent_donate);
        mWriteList = (RecyclerView) view.findViewById(R.id.book_write_list);
        mPersonList = (RecyclerView) view.findViewById(R.id.book_person_list);
        mHotList = (RecyclerView) view.findViewById(R.id.book_hot_list);
        mActivePersonList = (RecyclerView) view.findViewById(R.id.active_person_list);
        tab = (TabLayout) view.findViewById(R.id.tab);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);//全部
        mBook = (ImageView) view.findViewById(R.id.book_detail_picture);
        book_button = (TextView) view.findViewById(R.id.book_button);
        mScrollView = (ScrollView) view.findViewById(R.id.mScrollView);
        bottomLayout = (LinearLayout) view.findViewById(R.id.linearLayout_bottom);//显示书籍分类
        mySimpleImageBanner = (SimpleImageBanner) view.findViewById(R.id.sib_the_most_comlex_usage);





        View qrCode = view.findViewById(R.id.ar_code);
        qrCode.setOnClickListener(this);
        View back = view.findViewById(R.id.back);
        back.setVisibility(View.INVISIBLE);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText("一本书");
        book_button.setOnClickListener(this);
        mWriteList.setLayoutManager(linearlayoutManager);
        mActivePersonList.setLayoutManager(linearlayoutManager3);
        initWritre();
        // 我的书动态列表适配
        bookPersonAdapter = new BookPersonAdapter(getActivity(), dataX);
        mPersonList.setLayoutManager(linearlayoutManager1);
        mPersonList.setAdapter(bookPersonAdapter);
//        mySimpleImageBanner.setSource(bannerItems).startScroll();
        // 最热
        initHotList();
        initActivePerson();
    }



    private void initAllType() {
        if (fragments.size() == 0) {
            for (int i = 0; i < myTypeData.size(); i++) {
                fragments.add(TabFragment.newInstance(myTypeData.get(i).getId()));
                tabTitle.add(myTypeData.get(i).getName());

            }
        }
        adapter = new TabAdapter(getActivity().getSupportFragmentManager(), fragments);

        adapter.setTabTitle(tabTitle);
        //给ViewPager设置适配器
        viewpager.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来。
        tab.setupWithViewPager(viewpager);
        //设置可以滑动
        tab.setTabMode(TabLayout.MODE_FIXED);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getItemData(ItemEvent itemEvent) {
        itemHeight = itemEvent.getItemHeight();
        itemCount = itemEvent.getCount();
        //获取ScrollView中节点
        LinearLayout linearLayout = (LinearLayout) mScrollView.getChildAt(0);
        //获取ScrollView中linearLayout所包含所有VIEW的高
        final int allViewHeight = linearLayout.getMeasuredHeight();
        final int layoutHeight = (int) (itemHeight * (Math.ceil((itemCount / 3))));
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            ViewGroup.LayoutParams params = bottomLayout.getLayoutParams();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        //                            int scrollHeight = mScrollView.getScrollY();
                        //                            if (scrollHeight >= allViewHeight / 2) {
                        //                                params.height = 2440;
                        //                                bottomLayout.setLayoutParams(params);
                        //                        }
                        break;
                }

                return false;
            }
        });
        //ViewPage监听
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /*
            * 当页面开始滑动的时候，三种状态的变化顺序为1-->2-->0
            *  position :当前页面，及你点击滑动的页面
            * positionOffset:当前页面偏移的百分比
            * positionOffsetPixels:当前页面偏移的像素位置
            * */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //此方法是页面跳转完后被调用，position是你当前选中的页面的
            @Override
            public void onPageSelected(int position) {
                ViewGroup.LayoutParams params = bottomLayout.getLayoutParams();
                //                for (int i = 0; i < fragments.size()-1; i++) {
                //                    if (position == i){
                //                        params.height= 2440;
                //                        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(params);
                //                        bottomLayout.setLayoutParams(layoutParams);
                //                    }
                //                }
            }

            //state ==1的时表示正在滑动，state==2的时表示滑动完毕了，state==0的时表示什么都没
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    //书虫作品
    private void initWritre() {
        mWriteBookAdapter = new CommonAdapter<WriteBookData>(getActivity(), R.layout.write_book_item, mWriteBookList) {
            @Override
            protected void convert(ViewHolder holder, final WriteBookData dataBean, int position) {
                Glide.with(getContext())
                        .load(dataBean.getCover())
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.write_book_picture));
                holder.setText(R.id.write_book_name, dataBean.getName());
                holder.setText(R.id.write_book_chapters, "更新到第" + dataBean.getNew_chapter().getSequence() + "章");
                holder.setText(R.id.write_book_detail, dataBean.getNew_chapter().getContent());
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), BookChapterActivity.class);
                        intent.putExtra("write_book_id", dataBean.getId() + "");
                        getActivity().startActivity(intent);

                    }
                });
            }
        };
        mWriteList.setAdapter(mWriteBookAdapter);
    }

    //加载最热
    private void initHotList() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHotList.setLayoutManager(layoutManager);
        mBookHotAdapter = new CommonAdapter<BookListData.DataBeanX.DataBean>(getActivity(), R.layout.book_hot_item, mBookHotList) {
            @Override
            protected void convert(ViewHolder holder, final BookListData.DataBeanX.DataBean dataBean, int position) {
                Glide.with(getActivity())
                        .load(Api.BASE_URL + dataBean.getModel().getCover_img())
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.book_item_picture));
                holder.setText(R.id.book_item_name, dataBean.getModel().getName());

                holder.getView(R.id.book_item_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                        intent.putExtra("id", dataBean.getId());
                        getActivity().startActivity(intent);
                    }
                });
            }
        };
        mHotList.setAdapter(mBookHotAdapter);
    }

    //初始化活跃书虫列表
    private void initActivePerson() {
        mBookWormAdapter = new CommonAdapter<BookWormData.DataBean>(getActivity(), R.layout.list_item_active_person, mBookWormList) {
            @Override
            protected void convert(final ViewHolder holder, final BookWormData.DataBean dataBean, final int position) {
                Glide.with(getActivity())
                        .load(dataBean.getUser_head_img())

                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.active_item_picture));
                holder.setText(R.id.active_item_rent, "借" + dataBean.getRent_num() + "本");
                holder.setText(R.id.active_item_donate, "捐" + dataBean.getDonate_num() + "本");
                holder.getView(R.id.is_attention_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MyApplication.getInstance().isLogin()) {
                            doCollect(holder, position);
                            holder.getView(R.id.is_attention_layout).setTag(position);
                        } else {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }

                    }
                });
                holder.getView(R.id.active_item_picture).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), UserDetailActivity.class);
                        intent.putExtra("id", dataBean.getUser_id());
                        startActivity(intent);
                    }
                });
                if (dataBean.getIs_concern() == 0) {
                    holder.getView(R.id.is_attention_layout);
                    ((TextView) holder.getView(R.id.book_is_attention)).setText("关注");
                    ((TextView) holder.getView(R.id.book_is_attention)).setTextColor(getResources().getColor(R.color.green));
                } else {
                    holder.getView(R.id.is_attention_layout);
                    ((TextView) holder.getView(R.id.book_is_attention)).setText("取消关注");
                    ((TextView) holder.getView(R.id.book_is_attention)).setTextColor(getResources().getColor(R.color.c_000000));
                }
            }
        };
        mActivePersonList.setAdapter(mBookWormAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.book_button:
                startActivity(new Intent(getActivity(), BookDonateActivity.class));
                break;
            case R.id.ar_code:
                startActivity(new Intent(getActivity(), QRActivity.class));
                break;

        }
    }

    private void doCollect(final ViewHolder holder, final int postion) {
        OkHttpUtils.get()
                .url(Api.ATTENTION + mBookWormList.get(postion).getUser_id())
                .build().execute(new JsonCallback<JSONObject>(getActivity(), true) {
            @Override
            public void onResponse(JSONObject data, int id) {
                if (mBookWormList.get(postion).getIs_concern() == 0) {
                    holder.getView(R.id.is_attention_layout).setBackground(getResources().getDrawable(R.drawable.border_of_book_attention));
                    ((TextView) holder.getView(R.id.book_is_attention)).setText("取消关注");
                    ((TextView) holder.getView(R.id.book_is_attention)).setTextColor(getResources().getColor(R.color.c_000000));
                    mBookWormList.get(postion).setIs_concern(1);
                    mBookWormAdapter.notifyDataSetChanged();
                } else {
                    holder.getView(R.id.is_attention_layout).setBackground(getResources().getDrawable(R.drawable.border_of_book_attention));
                    ((TextView) holder.getView(R.id.book_is_attention)).setText("关注");
                    ((TextView) holder.getView(R.id.book_is_attention)).setTextColor(getResources().getColor(R.color.green));
                    mBookWormList.get(postion).setIs_concern(0);
                    mBookWormAdapter.notifyDataSetChanged();
                }
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        // TODO Auto-generated method stub
        MainActivity.OnHideKeyboardListener onHideKeyboardListener = new MainActivity.OnHideKeyboardListener() {
            @Override
            public boolean hideKeyboard() {
                // TODO Auto-generated method stub
                return true;
            }
        };
        ((MainActivity) getActivity()).setOnHideKeyboardListener(onHideKeyboardListener);
        super.onAttach(context);
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideMyKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}

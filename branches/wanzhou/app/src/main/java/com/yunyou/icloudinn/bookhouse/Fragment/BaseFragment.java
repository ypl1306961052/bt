package com.yunyou.icloudinn.bookhouse.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yunyou.icloudinn.bookhouse.Activity.BaseFragmentActivity;


/**
 * Created by tangyangkai on 16/5/4
 * fragment的基类
 */
public abstract class BaseFragment extends Fragment {

    protected BaseFragmentActivity mActivity;

    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取fragment布局文件ID
    protected abstract int getLayoutId();

    //获取宿主Activity
    protected BaseFragmentActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseFragmentActivity) activity;
    }

    //添加fragment
    public void addNewFragment(BaseFragment fragment) {
        if (null != fragment) {
            getHoldingActivity().replaceFragment(fragment);
        }
    }

    //移除fragment
    public void removeFragment() {
        getHoldingActivity().removeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);
        return view;
    }
}

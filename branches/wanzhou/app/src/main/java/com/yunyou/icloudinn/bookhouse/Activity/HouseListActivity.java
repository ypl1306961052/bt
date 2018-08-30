package com.yunyou.icloudinn.bookhouse.Activity;



import com.yunyou.icloudinn.bookhouse.Fragment.BaseFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.HouseListFragment;


public class HouseListActivity extends FragmentUtilActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return HouseListFragment.getInanstance();
    }

}

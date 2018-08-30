package com.yunyou.icloudinn.bookhouse.Activity;

import com.yunyou.icloudinn.bookhouse.Fragment.BaseFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.MineHouseOrderFragment;

public class OrderActivity extends FragmentUtilActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return MineHouseOrderFragment.getInstance();
    }


}

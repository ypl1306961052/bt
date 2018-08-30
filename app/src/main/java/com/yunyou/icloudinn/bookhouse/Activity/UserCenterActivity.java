package com.yunyou.icloudinn.bookhouse.Activity;

import com.yunyou.icloudinn.bookhouse.Fragment.BaseFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.UserCenterFragment;

public class UserCenterActivity extends FragmentUtilActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return UserCenterFragment.getInstance();
    }


}

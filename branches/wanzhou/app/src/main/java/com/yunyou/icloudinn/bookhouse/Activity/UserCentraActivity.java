package com.yunyou.icloudinn.bookhouse.Activity;

import com.yunyou.icloudinn.bookhouse.Fragment.BaseFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.UserCentraFragment;

public class UserCentraActivity extends FragmentUtilActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return UserCentraFragment.getInstance();
    }


}

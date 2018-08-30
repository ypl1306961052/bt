package com.yunyou.icloudinn.bookhouse.Activity;

import com.yunyou.icloudinn.bookhouse.Fragment.AttentFragment;
import com.yunyou.icloudinn.bookhouse.Fragment.BaseFragment;

public class AttentionActivity extends FragmentUtilActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return AttentFragment.getInstance();
    }


}

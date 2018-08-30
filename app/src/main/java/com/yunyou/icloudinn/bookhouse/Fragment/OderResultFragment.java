package com.yunyou.icloudinn.bookhouse.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yunyou.icloudinn.bookhouse.R;

public class OderResultFragment extends BaseFragment implements View.OnClickListener {

    public static OderResultFragment getInstance(){
        return new OderResultFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
            View mComplete=view.findViewById(R.id.order_complete);
            View mContiune=view.findViewById(R.id.order_continue);
            View mBack=view.findViewById(R.id.back);
            TextView title= (TextView) view.findViewById(R.id.title);
            title.setText("预定完成");
            mBack.setOnClickListener(this);
            mComplete.setOnClickListener(this);
            mContiune.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_oder_result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.order_complete:
                addNewFragment(MineHouseOrderFragment.getInstance());
                break;
            case R.id.order_continue:
                getHoldingActivity().finish();
                break;
            case R.id.back:
                removeFragment();
                break;
        }
    }
}

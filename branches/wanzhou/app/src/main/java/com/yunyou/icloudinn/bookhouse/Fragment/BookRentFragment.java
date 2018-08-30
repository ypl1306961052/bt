package com.yunyou.icloudinn.bookhouse.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;

public class BookRentFragment extends BaseFragment implements View.OnClickListener {
    private int id,price;
    private String bookNmae;
    private TextView mName,mPrice;
    public static BookRentFragment getInstance(int id,String bookNmae,int price){
        BookRentFragment bookRentFragment=new BookRentFragment();
        Bundle bundle=new Bundle();
        bundle.putString("bookName",bookNmae);
        bundle.putInt("id",id);
        bundle.putInt("price",price);
        bookRentFragment.setArguments(bundle);
        return bookRentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookNmae=getArguments().getString("bookName");
        id=getArguments().getInt("id");
        price=getArguments().getInt("price");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initMyView(view);
        initData();
    }

    private void initMyView(View view) {
        StatusBarCompat.compat(getActivity(), getResources().getColor(R.color.green1));
        mName= (TextView) view.findViewById(R.id.rent_book_name);
        mPrice= (TextView) view.findViewById(R.id.rent_book_price);
        View check=view.findViewById(R.id.check_rent);
        View cancel=view.findViewById(R.id.cancel_rent);
        View back=view.findViewById(R.id.back);
        TextView title= (TextView)view.findViewById(R.id.title);
        title.setText("租书");
        check.setOnClickListener(this);
        cancel.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void initData() {
        mName.setText("《"+bookNmae+"》");
        mPrice.setText("押金"+price+"元");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_rent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_rent:

                break;
            case R.id.cancel_rent:

                break;
            case R.id.back:
                removeFragment();
                break;
        }
    }
}

package com.yunyou.icloudinn.bookhouse.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.yunyou.icloudinn.bookhouse.Fragment.BaseFragment;


public abstract class BaseFragmentActivity extends BaseActivity {

    //布局文件ID
    protected abstract int getContentViewId();

    //布局中Fragment的ID
    protected abstract int getFragmentContentId();

    //添加fragment
    public void replaceFragment(BaseFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    public void addHideFragment(BaseFragment fristFragment,BaseFragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .hide(fristFragment)
//                    .add(fragment,fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    public void findFragmentReplace(String tag) {
            FragmentManager fm=getSupportFragmentManager();
            BaseFragment fragment = (BaseFragment) fm.findFragmentByTag(tag);
        if (fragment!=null){
            FragmentTransaction fragmentTransaction=fm.beginTransaction();
            fragmentTransaction
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }else {
            return;
        }


    }

    public void  addFragment(BaseFragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .setTransition(FragmentTransaction.TRANSIT_NONE)
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }
    //移除fragment
    public void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    public void removeSpecifyFragment(int num){
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            for (int i=0;i<num;i++){
                getSupportFragmentManager().popBackStack();
            }
        } else {
            finish();
        }
    }

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

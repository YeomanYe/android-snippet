package com.fwheart.androidsnippet;

import android.os.Bundle;

import com.fwheart.androidsnippet.base.BaseFragmentActivity;
import com.fwheart.androidsnippet.fragment.HomeFragment;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null != savedInstanceState) return;
        HomeFragment fragment = new HomeFragment();
        initWithFragment(fragment);
    }

    @Override
    protected int getContextViewId() {
        return R.id.mainAct;
    }

}

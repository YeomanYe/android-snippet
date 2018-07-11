package com.fwheart.androidsnippet;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fwheart.androidsnippet.base.BaseFragmentActivity;
import com.fwheart.androidsnippet.fragment.HomeFragment;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null != savedInstanceState) return;
        HomeFragment fragment = new HomeFragment();
        String frgTag = fragment.getClass().getSimpleName();
        getSupportFragmentManager()
                .beginTransaction()
                .add(0,fragment,frgTag)
                .addToBackStack(frgTag)
                .commit();
    }
}

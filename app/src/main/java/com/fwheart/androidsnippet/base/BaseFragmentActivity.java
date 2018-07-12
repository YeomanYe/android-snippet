package com.fwheart.androidsnippet.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

/**
 * Created by yeming on 2018/7/10.
 */

public abstract class BaseFragmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(getContextViewId());
        setContentView(frameLayout);
    }
    protected abstract int getContextViewId();
}

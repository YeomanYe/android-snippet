package com.fwheart.androidsnippet.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by yeming on 2018/7/10.
 */

public abstract class BaseFragmentActivity extends AppCompatActivity {
    private String TAG = "BaseFragmentActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(getContextViewId());
        setContentView(frameLayout);
    }
    public int startFragment(BaseFragment fragment) {
        Log.i(TAG, "startFragment");
        String tagName = fragment.getClass().getSimpleName();
        return getSupportFragmentManager()
                .beginTransaction()
                .replace(getContextViewId(), fragment, tagName)
                .addToBackStack(tagName)
                .commit();
    }
    public int initWithFragment(BaseFragment fragment){
        String frgTag = fragment.getClass().getSimpleName();
        return getSupportFragmentManager()
                .beginTransaction()
                .add(getContextViewId(),fragment,frgTag)
                .addToBackStack(frgTag)
                .commit();
    }
    protected abstract int getContextViewId();
}

package com.fwheart.androidsnippet.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fwheart.androidsnippet.helper.TipsHelper;

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return onCreateView();
    }
    public void showTips(String msg){
        TipsHelper.showTips(getActivity(),msg);
    }
    public BaseFragmentActivity getBaseFragmentActivity(){
        return (BaseFragmentActivity) this.getActivity();
    }
    protected abstract View onCreateView();
}

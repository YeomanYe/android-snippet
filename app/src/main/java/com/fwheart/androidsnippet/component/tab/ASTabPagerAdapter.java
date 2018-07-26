package com.fwheart.androidsnippet.component.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ASTabPagerAdapter extends FragmentPagerAdapter {

    private ASTabItem[] asTabItems;

    public ASTabPagerAdapter(FragmentManager fm){
        this(fm,null);
    }

    public ASTabPagerAdapter(FragmentManager fm,ASTabItem[] items){
        super(fm);
        this.asTabItems = items;
    }

    public ASTabItem[] getAsTabItems() {
        return asTabItems;
    }

    public void setAsTabItems(ASTabItem[] asTabItems) {
        this.asTabItems = asTabItems;
    }

    @Override
    public Fragment getItem(int position) {
        return asTabItems[position].getPage();
    }

    @Override
    public int getCount() {
        return asTabItems.length;
    }
}

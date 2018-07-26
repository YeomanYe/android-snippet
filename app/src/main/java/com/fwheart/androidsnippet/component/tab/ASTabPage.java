package com.fwheart.androidsnippet.component.tab;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.fwheart.androidsnippet.R;

public class ASTabPage extends FrameLayout {
    private FrameLayout rootLayout;
    private ViewPager viewPager;
    private ASTabBar asTabBar;
    private ASTabItem[] asTabItems;
    public ASTabPage(Context context){
        this(context,null);
    }
    public ASTabPage(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.comp_tab_page,this);
        initViewPager();
        initTabBar();
    }


    private void initViewPager(){
        viewPager = findViewById(R.id.viewpager);
    }
    private void initTabBar(){
        asTabBar = findViewById(R.id.tab_bar);
        asTabBar.setViewPager(viewPager);
    }
    public static class TextItem implements ASTabItem{
        private String text;
        private Fragment fragment;
        public TextItem(String text,Fragment fragment){
            this.text = text;
            this.fragment = fragment;
        }
        public String getText() {
            return text;
        }

        @Override
        public Fragment getPage() {
            return fragment;
        }
    }
    public static class IconItem implements ASTabItem{
        private int resId;
        private int tintResId;
        private Fragment fragment;
        public IconItem(int resId,int tintResId,Fragment fragment){
            this.resId = resId;
            this.tintResId = tintResId;
            this.fragment = fragment;
        }
        public int getResId() {
            return resId;
        }
        public int getTintResId() {
            return tintResId;
        }

        @Override
        public Fragment getPage() {
            return fragment;
        }
    }
    public static class TextIconItem implements ASTabItem{
        private String text;
        private int resId;
        private int tintResId;
        private Fragment fragment;
        public TextIconItem(String text,int resId,int tintResId,Fragment fragment){
            this.resId = resId;
            this.tintResId = tintResId;
            this.text = text;
            this.fragment = fragment;
        }
        public int getResId() {
            return resId;
        }
        public int getTintResId() {
            return tintResId;
        }
        public String getText(){
            return text;
        }

        @Override
        public Fragment getPage() {
            return fragment;
        }
    }

    public void init(FragmentActivity fragmentActivity,ASTabItem...items){
        asTabItems = items;
        viewPager.setAdapter(new ASTabPagerAdapter(fragmentActivity.getSupportFragmentManager(),asTabItems));
        asTabBar.setTabItems(items);
        asTabBar.init();
    }
}

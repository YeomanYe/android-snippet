package com.fwheart.androidsnippet.component.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.fwheart.androidsnippet.R;

public class ASTabPage extends FrameLayout {
    private FrameLayout rootLayout;
    private ViewPager viewPager;
    private ASTabBar asTabBar;
    private int tabBarBg;
    private ASTabItem[] asTabItems;
    private TabDir tabDir = TabDir.BOTTOM;
    enum TabDir{
        TOP,BOTTOM
    }

    public ASTabPage(Context context){
        this(context,null);
    }
    public ASTabPage(Context context, AttributeSet attrs){
        this(context,attrs,R.attr.tab_page_defStyle);
    }
    public ASTabPage(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.comp_tab_page,this,true);
        initViewPager();
        initTabBar();
        initAttr(attrs,defStyleAttr);
    }

    private void initAttr(AttributeSet attrs,int defStyleAttr){
        Context context = getContext();
        TypedArray tArr = context.obtainStyledAttributes(attrs,R.styleable.ASTabPage,defStyleAttr,0);
        tabBarBg = tArr.getColor(R.styleable.ASTabPage_tab_bar_background,tabBarBg);
        asTabBar.setBackgroundColor(tabBarBg);
        tabDir = TabDir.values()[tArr.getInt(R.styleable.ASTabPage_tab_bar_direction,tabDir.ordinal())];
        asTabBar.initAttr(attrs,defStyleAttr);
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
        //设置viewpager自适应间距
        asTabBar.post(new Runnable() {
            @Override
            public void run() {
                LayoutParams pageLp = (FrameLayout.LayoutParams)viewPager.getLayoutParams();
                LayoutParams tabBarLp = (FrameLayout.LayoutParams) asTabBar.getLayoutParams();
                switch (tabDir){
                    case TOP:
                        tabBarLp.gravity = Gravity.TOP;
                        pageLp.setMargins(0,asTabBar.getHeight(),0,0);
                        break;
                    case BOTTOM:
                        tabBarLp.gravity = Gravity.BOTTOM;
                        pageLp.setMargins(0,0,0,asTabBar.getHeight());
                        break;
                }
            }
        });
        viewPager.setAdapter(new ASTabPagerAdapter(fragmentActivity.getSupportFragmentManager(),asTabItems));
        asTabBar.setTabItems(items);
        asTabBar.init();
    }
}

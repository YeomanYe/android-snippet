package com.fwheart.androidsnippet.component.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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
        this(context,attrs,R.attr.tabpage_defStyle);
    }
    public ASTabPage(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.comp_tab_page,this);
        initViewPager();
        initTabBar();
        initAttr(attrs,defStyleAttr);
    }

    private void initAttr(AttributeSet attrs,int defStyleAttr){
        Context context = getContext();
        TypedArray tArr = context.obtainStyledAttributes(attrs,R.styleable.ASTabBar,defStyleAttr,0);
        asTabBar.itemPadding = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding, asTabBar.itemPadding);
        asTabBar.itemPaddingTop = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_top, asTabBar.itemPaddingTop);
        asTabBar.itemPaddingBottom = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_bottom, asTabBar.itemPaddingBottom);
        asTabBar.itemPaddingLeft = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_left, asTabBar.itemPaddingLeft);
        asTabBar.itemPaddingRight = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_right, asTabBar.itemPaddingRight);
        asTabBar.itemPaddingHorizontal = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_horizontal, asTabBar.itemPaddingHorizontal);
        asTabBar.itemPaddingVertical = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_vertical, asTabBar.itemPaddingVertical);
        asTabBar.itemTextSizeSp = tArr.getInteger(R.styleable.ASTabBar_tab_title_textSize,asTabBar.itemTextSizeSp);
        asTabBar.distributeEvenly = tArr.getBoolean(R.styleable.ASTabBar_tab_distribute_evenly,asTabBar.distributeEvenly);
        asTabBar.titleOffset = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_title_offset, asTabBar.titleOffset);
        asTabBar.setHasIndicator(tArr.getBoolean(R.styleable.ASTabBar_tab_has_indicator,asTabBar.hasIndicator));
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
                LayoutParams lp = (FrameLayout.LayoutParams)viewPager.getLayoutParams();
                lp.setMargins(0,0,0,asTabBar.getHeight());
            }
        });

        viewPager.setAdapter(new ASTabPagerAdapter(fragmentActivity.getSupportFragmentManager(),asTabItems));
        asTabBar.setTabItems(items);
        asTabBar.init();
    }
}

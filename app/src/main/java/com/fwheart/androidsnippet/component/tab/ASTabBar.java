/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fwheart.androidsnippet.component.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;
import com.fwheart.androidsnippet.helper.AssetHelper;
import com.fwheart.androidsnippet.helper.SizeHelper;

import org.w3c.dom.Attr;

import java.util.List;

/**
 * To be used with ViewPager to provide a tab indicator component which give constant feedback as to
 * the user's scroll progress.
 * <p>
 * To use the component, simply add it to your view hierarchy. Then in your
 * {@link android.app.Activity} or {@link android.support.v4.app.Fragment} call
 * {@link #setViewPager(android.support.v4.view.ViewPager)} providing it the ViewPager this layout is being used for.
 * <p>
 * The colors can be customized in two ways. The first and simplest is to provide an array of colors
 * via {@link #setSelectedIndicatorColors(int...)}. The
 * alternative is via the {@link TabColorizer} interface which provides you complete control over
 * which color is used for any individual position.
 * <p>
 * The views used as tabs can be customized by calling ,
 * providing the layout ID of your custom layout.
 */
public class ASTabBar extends HorizontalScrollView {
    /**
     * Allows complete control over the colors drawn in the tab layout. Set with
     * {@link #setCustomTabColorizer(TabColorizer)}.
     */
    public interface TabColorizer {

        /**
         * @return return the color of the indicator used when {@code position} is selected.
         */
        int getIndicatorColor(int position);

    }


    public int titleOffset = 20;
    public int itemTextSizeSp = 12;
    public int iconBottom = 0; //icon 的margin bottom
    public int textBottom = 0;//text 的margin bottom
    public boolean hasIndicator = false; //是否显示指示器
    public boolean distributeEvenly = true; //是否均分空间
    public ASTabItem[] tabItems;


    public int itemPadding = 16;
    public int itemPaddingTop = 0;
    public int itemPaddingLeft = 0;
    public int itemPaddingRight = 0;
    public int itemPaddingBottom = 0;
    public int itemPaddingHorizontal = 0;
    public int itemPaddingVertical = 0;



    private ViewPager mViewPager;
    private SparseArray<String> mContentDescriptions = new SparseArray<>();
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

    private ASTabStrip mTabStrip;

    public ASTabBar(Context context) {
        this(context, null);
    }

    public ASTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.tabbar_defStyle);
    }

    public ASTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);
        // Make sure that the Tab Strips fills this View
        setFillViewport(true);


        mTabStrip = new ASTabStrip(context);
        mTabStrip.setSelectedIndicatorColors(Color.argb(0,0,0,0));
        addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        initAttr(attrs,defStyleAttr);
    }

    private void initAttr(AttributeSet attrs,int defStyleAttr){
        Context context = getContext();
        TypedArray tArr = context.obtainStyledAttributes(attrs,R.styleable.ASTabBar,defStyleAttr,0);
        itemPadding = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding, itemPadding);
        itemPaddingTop = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_top, itemPaddingTop);
        itemPaddingBottom = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_bottom, itemPaddingBottom);
        itemPaddingLeft = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_left, itemPaddingLeft);
        itemPaddingRight = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_right, itemPaddingRight);
        itemPaddingHorizontal = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_horizontal, itemPaddingHorizontal);
        itemPaddingVertical = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_vertical, itemPaddingVertical);
        itemTextSizeSp = tArr.getInteger(R.styleable.ASTabBar_tab_title_textSize,itemTextSizeSp);
        distributeEvenly = tArr.getBoolean(R.styleable.ASTabBar_tab_distribute_evenly,distributeEvenly);
        titleOffset = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_title_offset, titleOffset);
        setHasIndicator(tArr.getBoolean(R.styleable.ASTabBar_tab_has_indicator,hasIndicator));
    }

    private void setItemPadding(int p,int pTop,int pBottom,int pLeft,int pRight,int pHorizontal,int pVertical){
        itemPadding = p;
        itemPaddingBottom = pBottom;
        itemPaddingTop = pTop;
        itemPaddingLeft = pLeft;
        itemPaddingRight = pRight;
        itemPaddingHorizontal = pHorizontal;
        itemPaddingVertical = pVertical;
    }

    public void setHasIndicator(boolean hasIndicator){
        mTabStrip.setHasIndicator(hasIndicator);
    }

    public ASTabItem[] getTabItems() {
        return tabItems;
    }

    public void setTabItems(ASTabItem[] tabItems) {
        this.tabItems = tabItems;
    }

    /**
     * Set the custom {@link ASTabBar.TabColorizer} to be used.
     *
     * If you only require simple custmisation then you can use
     * {@link #setSelectedIndicatorColors(int...)} to achieve
     * similar effects.
     */
    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        mTabStrip.setCustomTabColorizer(tabColorizer);
    }

    public void setDistributeEvenly(boolean distributeEvenly) {
        this.distributeEvenly = distributeEvenly;
    }

    /**
     * Sets the colors to be used for indicating the selected tab. These colors are treated as a
     * circular array. Providing one color will mean that all tabs are indicated with the same color.
     */
    public void setSelectedIndicatorColors(int... colors) {
        mTabStrip.setSelectedIndicatorColors(colors);
    }

    /**
     * Set the {@link android.support.v4.view.ViewPager.OnPageChangeListener}. When using {@link ASTabBar} you are
     * required to set any {@link android.support.v4.view.ViewPager.OnPageChangeListener} through this method. This is so
     * that the layout can update it's scroll position correctly.
     *
     * @see android.support.v4.view.ViewPager#setOnPageChangeListener(android.support.v4.view.ViewPager.OnPageChangeListener)
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPagerPageChangeListener = listener;
    }


    /**
     * Sets the associated view pager. Note that the assumption here is that the pager content
     * (number of tabs and tab titles) does not change after this call has been made.
     */
    public void setViewPager(ViewPager viewPager) {
        mTabStrip.removeAllViews();
        mViewPager = viewPager;
    }

    public void init(){
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();
        }
    }

    /**
     * Create a default view to be used for tabs. This is called if a custom tab view is not set via
     * .
     */
    protected LinearLayout createDefaultTabView(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        if(itemPaddingLeft == 0 && itemPaddingRight == 0){
            itemPaddingRight = itemPaddingLeft = itemPaddingHorizontal > 0 ? itemPaddingHorizontal : itemPadding;
        }
        if(itemPaddingTop == 0 && itemPaddingBottom == 0){
            itemPaddingTop = itemPaddingBottom = itemPaddingVertical > 0 ? itemPaddingVertical : itemPadding;
        }
        linearLayout.setPadding(itemPaddingLeft, itemPaddingTop, itemPaddingRight, itemPaddingBottom);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        TextView textView = null;
        ImageView imgView = null;
        ASTabItem item = tabItems[0];
        if(ASTabPage.TextItem.class.isInstance(item)){
            ASTabPage.TextItem pItem = (ASTabPage.TextItem) item;
            textView = createTextView(pItem.getText());
        }else if(ASTabPage.IconItem.class.isInstance(item)){
            ASTabPage.IconItem pItem = (ASTabPage.IconItem) item;
            imgView = createImageView(pItem.getResId());
        }else if(ASTabPage.TextIconItem.class.isInstance(item)){
            ASTabPage.TextIconItem pItem = (ASTabPage.TextIconItem) item;
            textView = createTextView(pItem.getText());
            imgView = createImageView(pItem.getResId());
        }
        if(null != imgView) linearLayout.addView(imgView);
        if(null != textView) linearLayout.addView(textView);
        return linearLayout;
    }

    protected TextView createTextView(String text){
        Context context = getContext();
        TextView textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, itemTextSizeSp);
        textView.setId(R.id.tab_item_text);
        textView.setText(text);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundResource(AssetHelper.getResId(context, R.attr.selectableItemBackground));
        textView.setBottom(SizeHelper.dp2px(context,textBottom));
        return textView;
    }

    protected ImageView createImageView(int resId){
        Context context = getContext();
        ImageView imgView = new ImageView(context);
        imgView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        imgView.setImageResource(resId);
        imgView.setId(R.id.tab_item_icon);
        imgView.setBottom(SizeHelper.dp2px(context,iconBottom));
        return imgView;
    }

    private void populateTabStrip() {
        final PagerAdapter adapter = mViewPager.getAdapter();
        final OnClickListener tabClickListener = new TabClickListener();

        for (int i = 0; i < adapter.getCount(); i++) {
            //使用默认结构初始化title
            View tabView = createDefaultTabView(getContext());

            if (distributeEvenly) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                lp.width = 0;
                lp.weight = 1;
            }

            tabView.setOnClickListener(tabClickListener);
            String desc = mContentDescriptions.get(i, null);
            if (desc != null) {
                tabView.setContentDescription(desc);
            }

            mTabStrip.addView(tabView);
            if (i == mViewPager.getCurrentItem()) {
                tabView.setSelected(true);
            }
        }
    }

    private void resetTint(){
        for(int i=0,count = mTabStrip.getChildCount();i<count;i++){
            LinearLayout linearLayout = (LinearLayout)mTabStrip.getChildAt(i);
            ImageView icon = linearLayout.findViewById(R.id.tab_item_icon);
            ASTabItem tabItem = tabItems[i];
            if(ASTabPage.IconItem.class.isInstance(tabItem)){
                icon.setImageResource(((ASTabPage.IconItem)tabItem).getResId());
            }else if(ASTabPage.TextIconItem.class.isInstance(tabItem)){
                icon.setImageResource(((ASTabPage.TextIconItem)tabItem).getResId());
            }
        }
    }

    private void setTintAtIndex(int index){
        resetTint();
        LinearLayout linearLayout = (LinearLayout) mTabStrip.getChildAt(index);
        ImageView icon = linearLayout.findViewById(R.id.tab_item_icon);
        ASTabItem tabItem = tabItems[index];
        if(ASTabPage.IconItem.class.isInstance(tabItem)){
            icon.setImageResource(((ASTabPage.IconItem)tabItem).getTintResId());
        }else if(ASTabPage.TextIconItem.class.isInstance(tabItem)){
            icon.setImageResource(((ASTabPage.TextIconItem)tabItem).getTintResId());
        }
    }

    public void setContentDescription(int i, String desc) {
        mContentDescriptions.put(i, desc);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (mViewPager != null) {
            scrollToTab(mViewPager.getCurrentItem(), 0);
        }
    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = mTabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedChild = mTabStrip.getChildAt(tabIndex);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;

            if (tabIndex > 0 || positionOffset > 0) {
                // If we're not at the first child and are mid-scroll, make sure we obey the offset
                targetScrollX -= titleOffset;
            }

            scrollTo(targetScrollX, 0);
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int tabStripChildCount = mTabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return;
            }

            mTabStrip.onViewPagerPageChanged(position, positionOffset);

            View selectedTitle = mTabStrip.getChildAt(position);
            int extraOffset = (selectedTitle != null)
                    ? (int) (positionOffset * selectedTitle.getWidth())
                    : 0;
            scrollToTab(position, extraOffset);
            setTintAtIndex(position);
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrolled(position, positionOffset,
                        positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStrip.onViewPagerPageChanged(position, 0f);
                scrollToTab(position, 0);
                setTintAtIndex(position);
            }
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                mTabStrip.getChildAt(i).setSelected(position == i);
            }
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageSelected(position);
            }
        }

    }

    private class TabClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                if (v == mTabStrip.getChildAt(i)) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    }
}

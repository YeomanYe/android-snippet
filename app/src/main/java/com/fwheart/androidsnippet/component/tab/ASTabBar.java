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
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;
import com.fwheart.androidsnippet.helper.AssetHelper;
import com.fwheart.androidsnippet.helper.SizeHelper;

/**
 * To be used with ViewPager to provide a tab indicator component which give constant feedback as to
 * the user's scroll progress.
 * <p>
 * To use the component, simply add it to your view hierarchy. Then in your
 * {@link android.app.Activity} or {@link android.support.v4.app.Fragment} call
 * {@link #setViewPager(android.support.v4.view.ViewPager)} providing it the ViewPager this layout is being used for.
 * <p>
 * The colors can be customized in two ways. The first and simplest is to provide an array of colors
 * via . The
 * alternative is via the {@link} interface which provides you complete control over
 * which color is used for any individual position.
 * <p>
 * The views used as tabs can be customized by calling ,
 * providing the layout ID of your custom layout.
 */
public class ASTabBar extends HorizontalScrollView {



    public int tabOffset = 20;
    public int itemTextSizeSp = 12;
    public int iconBottom = 0; //icon 的margin bottom
    public int textBottom = 0;//text 的margin bottom
    public ASTabItem[] tabItems;
    public int textColor = Color.WHITE;

    public int itemPadding = 16;
    public int itemPaddingTop = 0;
    public int itemPaddingLeft = 0;
    public int itemPaddingRight = 0;
    public int itemPaddingBottom = 0;
    public int itemPaddingHorizontal = 0;
    public int itemPaddingVertical = 0;



    private ViewPager viewPager;
    private ViewPager.OnPageChangeListener onPageChangeListener;

    private ASTabStrip tabStrip;

    public ASTabBar(Context context) {
        this(context, null);
    }

    public ASTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.tab_bar_defStyle);
    }

    public ASTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttr(attrs,defStyleAttr);
    }

    private void initView(Context context) {
        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);
        // Make sure that the Tab Strips fills this View
        setFillViewport(true);


        tabStrip = new ASTabStrip(context);
        addView(tabStrip, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void initAttr(AttributeSet attrs,int defStyleAttr){
        Context context = getContext();
        TypedArray tArr = context.obtainStyledAttributes(attrs,R.styleable.ASTabBar,defStyleAttr,0);
        itemPadding = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding, itemPadding);
        itemPaddingTop = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_top, itemPaddingTop);
        itemPaddingBottom = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_bottom, itemPaddingBottom);
        itemPaddingLeft = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_left, itemPaddingLeft);
        itemPaddingRight = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_right, itemPaddingRight);
        itemPaddingHorizontal = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_horizontal, itemPaddingHorizontal);
        itemPaddingVertical = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_vertical, itemPaddingVertical);
        itemTextSizeSp = tArr.getInteger(R.styleable.ASTabBar_tab_text_size,itemTextSizeSp);
        tabOffset = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_offset, tabOffset);
        textColor = tArr.getColor(R.styleable.ASTabBar_tab_text_color,textColor);
        tabStrip.initAttr(attrs,defStyleAttr);
    }



    public ASTabItem[] getTabItems() {
        return tabItems;
    }

    public void setTabItems(ASTabItem[] tabItems) {
        this.tabItems = tabItems;
    }


    /**
     * Set the {@link android.support.v4.view.ViewPager.OnPageChangeListener}. When using {@link ASTabBar} you are
     * required to set any {@link android.support.v4.view.ViewPager.OnPageChangeListener} through this method. This is so
     * that the layout can update it's scroll position correctly.
     *
     * @see android.support.v4.view.ViewPager#setOnPageChangeListener(android.support.v4.view.ViewPager.OnPageChangeListener)
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        onPageChangeListener = listener;
    }


    /**
     * Sets the associated view pager. Note that the assumption here is that the pager content
     * (number of tabs and tab titles) does not change after this call has been made.
     */
    public void setViewPager(ViewPager viewPager) {
        tabStrip.removeAllViews();
        this.viewPager = viewPager;
    }

    public void init(){
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();
        }
    }

    /**
     * Create a default view to be used for tabs. This is called if a custom tab view is not set via
     * .
     */
    protected LinearLayout createTabView(Context context) {
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
        textView.setTextColor(textColor);
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
        final PagerAdapter adapter = viewPager.getAdapter();
        final OnClickListener tabClickListener = new TabClickListener();

        for (int i = 0; i < adapter.getCount(); i++) {
            //使用默认结构初始化title
            View tabView = createTabView(getContext());

            tabView.setOnClickListener(tabClickListener);

            tabStrip.addView(tabView);
            if (i == viewPager.getCurrentItem()) {
                tabView.setSelected(true);
            }
        }
    }

    private void resetTint(){
        for(int i = 0, count = tabStrip.getChildCount(); i<count; i++){
            LinearLayout linearLayout = (LinearLayout) tabStrip.getChildAt(i);
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
        LinearLayout linearLayout = (LinearLayout) tabStrip.getChildAt(index);
        ImageView icon = linearLayout.findViewById(R.id.tab_item_icon);
        ASTabItem tabItem = tabItems[index];
        if(ASTabPage.IconItem.class.isInstance(tabItem)){
            icon.setImageResource(((ASTabPage.IconItem)tabItem).getTintResId());
        }else if(ASTabPage.TextIconItem.class.isInstance(tabItem)){
            icon.setImageResource(((ASTabPage.TextIconItem)tabItem).getTintResId());
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (viewPager != null) {
            scrollToTab(viewPager.getCurrentItem(), 0);
        }
    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = tabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedChild = tabStrip.getChildAt(tabIndex);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;

            if (tabIndex > 0 || positionOffset > 0) {
                // If we're not at the first child and are mid-scroll, make sure we obey the offset
                targetScrollX -= tabOffset;
            }

            scrollTo(targetScrollX, 0);
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int tabStripChildCount = tabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return;
            }

            tabStrip.onViewPagerPageChanged(position, positionOffset);

            View selectedTitle = tabStrip.getChildAt(position);
            int extraOffset = (selectedTitle != null)
                    ? (int) (positionOffset * selectedTitle.getWidth())
                    : 0;
            scrollToTab(position, extraOffset);
            setTintAtIndex(position);
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageScrolled(position, positionOffset,
                        positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;

            if (onPageChangeListener != null) {
                onPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                tabStrip.onViewPagerPageChanged(position, 0f);
                scrollToTab(position, 0);
                setTintAtIndex(position);
            }
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                tabStrip.getChildAt(i).setSelected(position == i);
            }
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageSelected(position);
            }
        }

    }

    private class TabClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                if (v == tabStrip.getChildAt(i)) {
                    viewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    }
}

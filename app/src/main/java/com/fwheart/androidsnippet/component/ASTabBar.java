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

package com.fwheart.androidsnippet.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
 * The views used as tabs can be customized by calling {@link #setCustomTabView(int, int)},
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

    private enum TabType {
        Text,Icon,TextIcon
    }

    private int titleOffset = 20;
    private int itemTextSizeSp = 12;
    private int iconBottom = 0; //icon 的margin bottom
    private int textBottom = 0;//text 的margin bottom
    private static boolean hasIndicator = false; //是否显示指示器

    private TabType tabMode = TabType.TextIcon;
    private TabType[] tabModes = TabType.values();

    private int itemPadding = 16;
    private int itemPaddingTop = 0;
    private int itemPaddingLeft = 0;
    private int itemPaddingRight = 0;
    private int itemPaddingBottom = 0;
    private int itemPaddingHorizontal = 0;
    private int itemPaddingVertical = 0;


    private int mTabViewLayoutId;
    private int mTabViewTextViewId;
    private boolean mDistributeEvenly = true;

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
        TypedArray tArr = context.obtainStyledAttributes(attrs,R.styleable.ASTabBar,defStyleAttr,0);
        initAttr(tArr);
        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);
        // Make sure that the Tab Strips fills this View
        setFillViewport(true);


        mTabStrip = new ASTabStrip(context);
        mTabStrip.setSelectedIndicatorColors(Color.argb(0,0,0,0));
        addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    private void initAttr(TypedArray tArr){
        itemPadding = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding, itemPadding);
        itemPaddingTop = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_top, itemPaddingTop);
        itemPaddingBottom = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_bottom, itemPaddingBottom);
        itemPaddingLeft = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_left, itemPaddingLeft);
        itemPaddingRight = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_right, itemPaddingRight);
        itemPaddingHorizontal = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_horizontal, itemPaddingHorizontal);
        itemPaddingVertical = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_item_padding_vertical, itemPaddingVertical);
        itemTextSizeSp = tArr.getInteger(R.styleable.ASTabBar_tab_title_textSize,itemTextSizeSp);
        hasIndicator = tArr.getBoolean(R.styleable.ASTabBar_tab_has_indicator,hasIndicator);
        mDistributeEvenly = tArr.getBoolean(R.styleable.ASTabBar_tab_distribute_evenly,hasIndicator);
        titleOffset = tArr.getDimensionPixelSize(R.styleable.ASTabBar_tab_title_offset, titleOffset);
        tabMode = tabModes[tArr.getInteger(R.styleable.ASTabBar_tab_type,tabMode.ordinal())];
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
        mDistributeEvenly = distributeEvenly;
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

    public void setCustomTabView(int layoutResId){
        setCustomTabView(layoutResId,0);
    }
    /**
     * Set the custom layout to be inflated for the tab views.
     *
     * @param layoutResId Layout id to be inflated
     * @param textViewId id of the {@link android.widget.TextView} in the inflated view
     */
    public void setCustomTabView(int layoutResId, int textViewId) {
        mTabViewLayoutId = layoutResId;
        mTabViewTextViewId = textViewId;
    }

    /**
     * Sets the associated view pager. Note that the assumption here is that the pager content
     * (number of tabs and tab titles) does not change after this call has been made.
     */
    public void setViewPager(ViewPager viewPager) {
        mTabStrip.removeAllViews();

        mViewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();
        }
    }

    /**
     * Create a default view to be used for tabs. This is called if a custom tab view is not set via
     * {@link #setCustomTabView(int, int)}.
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
        switch (tabMode){
            case Text:
                textView = createTextView();
                break;
            case Icon:
                imgView = createImageView();
                break;
            case TextIcon:
                textView = createTextView();
                imgView = createImageView();
                break;
        }
        if(null != imgView) linearLayout.addView(imgView);
        if(null != textView) linearLayout.addView(textView);
        return linearLayout;
    }

    protected TextView createTextView(){
        Context context = getContext();
        TextView textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, itemTextSizeSp);
        textView.setId(R.id.tab_item_text);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setBackgroundResource(AssetHelper.getResId(context, R.attr.selectableItemBackground));
        textView.setBottom(SizeHelper.dp2px(context,textBottom));
        return textView;
    }

    protected ImageView createImageView(){
        Context context = getContext();
        ImageView imgView = new ImageView(context);
        imgView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        imgView.setImageResource(R.mipmap.ic_launcher);
        imgView.setBottom(SizeHelper.dp2px(context,iconBottom));
        return imgView;
    }

    private void populateTabStrip() {
        final PagerAdapter adapter = mViewPager.getAdapter();
        final OnClickListener tabClickListener = new TabClickListener();

        for (int i = 0; i < adapter.getCount(); i++) {
            View tabView = null;
            TextView tabTitleView = null;
            //使用自定义结构初始化
            if (mTabViewLayoutId != 0) {
                // If there is a custom tab view layout id set, try and inflate it
                tabView = LayoutInflater.from(getContext()).inflate(mTabViewLayoutId, mTabStrip,
                        false);
                if(0 != mTabViewTextViewId) {
                    tabTitleView = (TextView) tabView.findViewById(mTabViewTextViewId);
                }
            }
            //使用默认结构初始化title
            if (tabView == null) {
                tabView = createDefaultTabView(getContext());
                tabTitleView = (TextView) tabView.findViewById(R.id.tab_item_text);
            }
            //设置title
            if(null != tabTitleView){
                tabTitleView.setText(adapter.getPageTitle(i));
                tabTitleView.setTextColor(Color.WHITE);
            }

            if (mDistributeEvenly) {
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

    /**
     * 指示器设置类
     */
    private static class ASTabStrip extends LinearLayout {

        private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0;
        private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
        private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 3;
        private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFF33B5E5;

        private final int mBottomBorderThickness;
        private final Paint mBottomBorderPaint;

        private final int mSelectedIndicatorThickness;
        private final Paint mSelectedIndicatorPaint;

        private final int mDefaultBottomBorderColor;

        private int mSelectedPosition;
        private float mSelectionOffset;

        private ASTabBar.TabColorizer mCustomTabColorizer;
        private final ASTabStrip.SimpleTabColorizer mDefaultTabColorizer;

        ASTabStrip(Context context) {
            this(context, null);
        }

        ASTabStrip(Context context, AttributeSet attrs) {
            super(context, attrs);
            setWillNotDraw(false);

            final float density = getResources().getDisplayMetrics().density;

            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.colorForeground, outValue, true);
            final int themeForegroundColor =  outValue.data;

            mDefaultBottomBorderColor = setColorAlpha(themeForegroundColor,
                    DEFAULT_BOTTOM_BORDER_COLOR_ALPHA);

            mDefaultTabColorizer = new ASTabStrip.SimpleTabColorizer();
            mDefaultTabColorizer.setIndicatorColors(DEFAULT_SELECTED_INDICATOR_COLOR);

            mBottomBorderThickness = (int) (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density);
            mBottomBorderPaint = new Paint();
            mBottomBorderPaint.setColor(mDefaultBottomBorderColor);

            mSelectedIndicatorThickness = (int) (SELECTED_INDICATOR_THICKNESS_DIPS * density);
            mSelectedIndicatorPaint = new Paint();
        }

        void setCustomTabColorizer(ASTabBar.TabColorizer customTabColorizer) {
            mCustomTabColorizer = customTabColorizer;
            invalidate();
        }

        void setSelectedIndicatorColors(int... colors) {
            // Make sure that the custom colorizer is removed
            mCustomTabColorizer = null;
            mDefaultTabColorizer.setIndicatorColors(colors);
            invalidate();
        }

        void onViewPagerPageChanged(int position, float positionOffset) {
            mSelectedPosition = position;
            mSelectionOffset = positionOffset;
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            final int height = getHeight();
            final int childCount = getChildCount();
            final ASTabBar.TabColorizer tabColorizer = mCustomTabColorizer != null
                    ? mCustomTabColorizer
                    : mDefaultTabColorizer;

            // Thick colored underline below the current selection
            if (childCount > 0 && hasIndicator) {
                View selectedTitle = getChildAt(mSelectedPosition);
                int left = selectedTitle.getLeft();
                int right = selectedTitle.getRight();
                int color = tabColorizer.getIndicatorColor(mSelectedPosition);

                if (mSelectionOffset > 0f && mSelectedPosition < (getChildCount() - 1)) {
                    int nextColor = tabColorizer.getIndicatorColor(mSelectedPosition + 1);
                    if (color != nextColor) {
                        color = blendColors(nextColor, color, mSelectionOffset);
                    }

                    // Draw the selection partway between the tabs
                    View nextTitle = getChildAt(mSelectedPosition + 1);
                    left = (int) (mSelectionOffset * nextTitle.getLeft() +
                            (1.0f - mSelectionOffset) * left);
                    right = (int) (mSelectionOffset * nextTitle.getRight() +
                            (1.0f - mSelectionOffset) * right);
                }

                mSelectedIndicatorPaint.setColor(color);

                canvas.drawRect(left, height - mSelectedIndicatorThickness, right, height, mSelectedIndicatorPaint);
            }

            // Thin underline along the entire bottom edge
            canvas.drawRect(0, height - mBottomBorderThickness, getWidth(), height, mBottomBorderPaint);
        }

        /**
         * Set the alpha value of the {@code color} to be the given {@code alpha} value.
         */
        private static int setColorAlpha(int color, byte alpha) {
            return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
        }

        /**
         * Blend {@code color1} and {@code color2} using the given ratio.
         *
         * @param ratio of which to blend. 1.0 will return {@code color1}, 0.5 will give an even blend,
         *              0.0 will return {@code color2}.
         */
        private static int blendColors(int color1, int color2, float ratio) {
            final float inverseRation = 1f - ratio;
            float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
            float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
            float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
            return Color.rgb((int) r, (int) g, (int) b);
        }

        private static class SimpleTabColorizer implements ASTabBar.TabColorizer {
            private int[] mIndicatorColors;

            @Override
            public final int getIndicatorColor(int position) {
                return mIndicatorColors[position % mIndicatorColors.length];
            }

            void setIndicatorColors(int... colors) {
                mIndicatorColors = colors;
            }
        }
    }
}

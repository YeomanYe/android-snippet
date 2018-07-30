package com.fwheart.androidsnippet.component.tab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 指示器设置类
 */
class ASTabStrip extends ViewGroup {

    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0;
    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 3;
    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFF33B5E5;
    private int mItemSpaceInScrollMode;

    private final int mBottomBorderThickness;
    private final Paint mBottomBorderPaint;

    private final int mSelectedIndicatorThickness;
    private final Paint mSelectedIndicatorPaint;

    private final int mDefaultBottomBorderColor;

    private int mSelectedPosition;
    private float mSelectionOffset;
    private boolean hasIndicator;

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//        heightSpecSize = 160;
        List<View> childViews = getChildren();
        int size = childViews.size();
        int i;
        int visibleChild = 0;
        for (i = 0; i < size; i++) {
            View child = childViews.get(i);
            if (child.getVisibility() == VISIBLE) {
                visibleChild++;
            }
        }
        if (size == 0 || visibleChild == 0) {
            setMeasuredDimension(widthSpecSize, heightSpecSize);
            return;
        }

        int childHeight = heightSpecSize - getPaddingTop() - getPaddingBottom();
        int childWidthMeasureSpec, childHeightMeasureSpec, resultWidthSize = 0;
        if (true) {
            resultWidthSize = widthSpecSize;
            int modeFixItemWidth = widthSpecSize / visibleChild;
            for (i = 0; i < size; i++) {
                final View child = childViews.get(i);
                if (child.getVisibility() != VISIBLE) {
                    continue;
                }
                childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(modeFixItemWidth, MeasureSpec.EXACTLY);
                childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), MeasureSpec.EXACTLY);
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        } else {
            for (i = 0; i < size; i++) {
                final View child = childViews.get(i);
                if (child.getVisibility() != VISIBLE) {
                    continue;
                }
                childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSpecSize, MeasureSpec.AT_MOST);
                childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                resultWidthSize += child.getMeasuredWidth() + mItemSpaceInScrollMode;
            }
            resultWidthSize -= mItemSpaceInScrollMode;
        }

        setMeasuredDimension(resultWidthSize, heightSpecSize);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        List<View> childViews = getChildren();
        int size = childViews.size();
        int i;
        int visibleChild = 0;
        for (i = 0; i < size; i++) {
            View child = childViews.get(i);
            if (child.getVisibility() == VISIBLE) {
                visibleChild++;
            }
        }

        if (size == 0 || visibleChild == 0) {
            return;
        }

        int usedLeft = getPaddingLeft();
        for (i = 0; i < size; i++) {
            View childView = childViews.get(i);
            if (childView.getVisibility() != VISIBLE) {
                continue;
            }
            final int childMeasureWidth = childView.getMeasuredWidth();
            childView.layout(usedLeft, getPaddingTop(), usedLeft + childMeasureWidth, b - t - getPaddingBottom());
            usedLeft = usedLeft + childMeasureWidth;
        }
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

    public void setHasIndicator(boolean hasIndicator){
        this.hasIndicator = hasIndicator;
    }

    public List<View> getChildren(){
        List<View> list = new ArrayList<>();
        for(int i=0,len=getChildCount();i<len;i++){
            list.add(getChildAt(i));
        }
        return list;
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
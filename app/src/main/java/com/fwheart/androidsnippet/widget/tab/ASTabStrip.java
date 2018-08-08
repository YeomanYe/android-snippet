package com.fwheart.androidsnippet.widget.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.fwheart.androidsnippet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 指示器设置类
 */
class ASTabStrip extends LinearLayout {

    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0;
    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 3;

    private final int mBottomBorderThickness;
    private final Paint mBottomBorderPaint;

    private final int mSelectedIndicatorThickness;
    private final Paint mSelectedIndicatorPaint;

    private final int mDefaultBottomBorderColor;

    private int mSelectedPosition;
    private float mSelectionOffset;
    private boolean hasIndicator = true;
    private int itemSpace = 0;
    private int indicatorColor = Color.WHITE;//只是器颜色

    private StripType stripType = StripType.FIXED;
    enum StripType{
        FIXED,SCROLLABLE
    }
    public void initAttr(AttributeSet attrs,int defStyleAttr){
        Context context = getContext();
        TypedArray tArr = context.obtainStyledAttributes(attrs,R.styleable.ASTabStrip,defStyleAttr,0);
        hasIndicator = tArr.getBoolean(R.styleable.ASTabStrip_tab_strip_indicator,hasIndicator);
        stripType = StripType.values()[tArr.getInt(R.styleable.ASTabStrip_tab_strip_type,stripType.ordinal())];
    }
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


        mBottomBorderThickness = (int) (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density);
        mBottomBorderPaint = new Paint();
        mBottomBorderPaint.setColor(mDefaultBottomBorderColor);

        mSelectedIndicatorThickness = (int) (SELECTED_INDICATOR_THICKNESS_DIPS * density);
        mSelectedIndicatorPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = getMeasuredHeight();
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
        if (stripType == StripType.FIXED) {
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
                resultWidthSize += child.getMeasuredWidth() + itemSpace;
            }
            resultWidthSize -= itemSpace;
        }

        setMeasuredDimension(resultWidthSize,getMeasuredHeight());
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
            usedLeft = usedLeft + childMeasureWidth + (stripType == StripType.SCROLLABLE ? itemSpace : 0);
        }
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

        // Thick colored underline below the current selection
        if (childCount > 0 && hasIndicator) {
            View selectedTitle = getChildAt(mSelectedPosition);
            int left = selectedTitle.getLeft();
            int right = selectedTitle.getRight();

            if (mSelectionOffset > 0f && mSelectedPosition < (getChildCount() - 1)) {


                // Draw the selection partway between the tabs
                View nextTitle = getChildAt(mSelectedPosition + 1);
                left = (int) (mSelectionOffset * nextTitle.getLeft() +
                        (1.0f - mSelectionOffset) * left);
                right = (int) (mSelectionOffset * nextTitle.getRight() +
                        (1.0f - mSelectionOffset) * right);
            }

            mSelectedIndicatorPaint.setColor(indicatorColor);

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

}
package com.fwheart.androidsnippet.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import com.fwheart.androidsnippet.R;

public class AssetHelper {
    public static int getResId(Context c,int resId){
        TypedValue typedValue = new TypedValue();
        c.getTheme().resolveAttribute(resId,typedValue,true);
        return typedValue.resourceId;
    }
    public static Drawable getDrawable(Context c,int attrId){
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = c.getTheme().obtainStyledAttributes(getResId(c,attrId), attrs);
        return typedArray.getDrawable(0);
    }
}

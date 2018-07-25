package com.fwheart.androidsnippet.helper;

import android.content.Context;
import android.util.TypedValue;

public class AssetHelper {
    public static int getResId(Context c,int resId){
        TypedValue typedValue = new TypedValue();
        c.getTheme().resolveAttribute(resId,typedValue,true);
        return typedValue.resourceId;
    }
}

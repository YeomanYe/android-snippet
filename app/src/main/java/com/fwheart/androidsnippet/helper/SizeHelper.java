package com.fwheart.androidsnippet.helper;

import android.content.Context;

public class SizeHelper {
    private static float getDensity(Context c){
        return c.getResources().getDisplayMetrics().density;
    }
    /**
     * 单位转换:dp -> px
     *
     * @param dp
     * @return
     */
    public static int dp2px(Context c,int dp){
        return (int)(getDensity(c) * dp + 0.5);
    }

    /**
     * 单位转换: sp -> px
     *
     * @param sp
     * @return
     */
    public static int sp2px(Context context, int sp) {
        return (int) (getDensity(context) * sp + 0.5);
    }

    /**
     * 单位转换:px -> dp
     *
     * @param px
     * @return
     */
    public static int px2dp(Context context, int px) {
        return (int) (px / getDensity(context) + 0.5);
    }

}

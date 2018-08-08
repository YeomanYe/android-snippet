package com.fwheart.androidsnippet.helper;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

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
    /**
     * 获取 DisplayMetrics
     *
     * @return
     */
    private static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }
}

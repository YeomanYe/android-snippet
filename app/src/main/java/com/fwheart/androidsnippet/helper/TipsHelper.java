package com.fwheart.androidsnippet.helper;

import android.content.Context;
import android.widget.Toast;

public class TipsHelper {

    public static void showTips(Context ctx, String msg){
        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
    }
}

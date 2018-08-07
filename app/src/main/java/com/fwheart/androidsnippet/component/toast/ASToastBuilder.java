package com.fwheart.androidsnippet.component.toast;

import android.content.Context;

import com.fwheart.androidsnippet.base.BaseDialogBuilder;

public abstract class ASToastBuilder<T extends ASToastBuilder> extends BaseDialogBuilder<T,ASToast> {
    public ASToastBuilder(Context context) {
        super(context);
    }
    @Override
    public ASToast createDialog(Context context){
        return new ASToast(context);
    }
}

package com.fwheart.androidsnippet.widget.dialog;

import android.content.Context;

import com.fwheart.androidsnippet.base.BaseDialogBuilder;

public abstract class ASDialogBuilder<T extends ASDialogBuilder> extends BaseDialogBuilder<T,ASDialog> {

    public ASDialogBuilder(Context context) {
        super(context);
        this.context = context;
    }
    @Override
    public ASDialog createDialog(Context context){
        return new ASDialog(context);
    }

}

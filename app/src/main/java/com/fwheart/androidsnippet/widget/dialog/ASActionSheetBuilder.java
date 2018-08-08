package com.fwheart.androidsnippet.widget.dialog;

import android.content.Context;

import com.fwheart.androidsnippet.base.BaseDialogBuilder;

public abstract class ASActionSheetBuilder<T extends ASActionSheetBuilder> extends BaseDialogBuilder<T,ASActionSheet> {
    protected ASActionSheetBuilder(Context context) {
        super(context);
    }
    @Override
    public ASActionSheet createDialog(Context context){
        return new ASActionSheet(context);
    }
}

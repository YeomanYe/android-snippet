package com.fwheart.androidsnippet.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.fwheart.androidsnippet.R;
import com.fwheart.androidsnippet.helper.SizeHelper;

public class ASActionSheet extends AlertDialog {
    protected ASActionSheet(Context context) {
        this(context, R.style.AlertDialog);
    }

    protected ASActionSheet(Context context, int themeResId) {
        super(context, themeResId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //noinspection ConstantConditions
        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        // 在底部，宽度撑满
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;

        int screenWidth = SizeHelper.getScreenWidth(getContext());
        int screenHeight = SizeHelper.getScreenHeight(getContext());
        params.width = screenWidth < screenHeight ? screenWidth : screenHeight;
        getWindow().setAttributes(params);
        setCanceledOnTouchOutside(true);
    }
    public static class ListBuilder extends ASActionSheetBuilder<ListBuilder> {

        public ListBuilder(Context context) {
            super(context);
            setLayoutId(R.layout.widget_bottomsheet);
        }

        @Override
        protected void createContent(ViewGroup content, View root) {

        }
    }
}

package com.fwheart.androidsnippet.component.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;

public class ASDialog extends AlertDialog {

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = 800;
        //                params.height = 200 ;
        this.getWindow().setAttributes(params);
    }

    public ASDialog(Context context) {
        this(context,R.style.AlertDialog);
    }


    protected ASDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class ConfirmBuilder extends ASDialogBuilder{

        public ConfirmBuilder(Context context) {
            super(context);
        }

        @Override
        public void createContent(ViewGroup content, View root) {
            TextView msgText = new TextView(context);
            msgText.setText(msg);
            msgText.setTextColor(Color.GRAY);
            content.addView(msgText);
        }

    }

}

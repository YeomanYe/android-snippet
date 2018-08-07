package com.fwheart.androidsnippet.component.toast;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;

import java.util.Timer;
import java.util.TimerTask;

public class ASToast extends AlertDialog {
    public ASToast(Context context) {
        this(context, R.style.Toast);
    }

    public ASToast(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void show() {
        super.show();
        final Dialog dialog = this;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }

    public static void success(Context context,String msg){
        createToast(context,msg,R.drawable.icon_notify_done);
    }

    private static void createToast(Context context,String msg,int icon){
        new Builder(context)
                .setIcon(icon)
                .setText(msg)
                .create()
                .show();
    }

    public static class Builder extends ASToastBuilder<Builder>{

        private int iconId;
        private String text;
        private TextView textView;
        private ImageView imgView;

        public int getIcon() {
            return iconId;
        }

        public Builder setIcon(int iconId) {
            this.iconId = iconId;
            return this;
        }

        public String getText() {
            return text;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder(Context context) {
            super(context);
            setHasFooter(false);
            setHasHeader(false);
            setLayoutId(R.layout.comp_toast);
        }

        @Override
        public void createContent(ViewGroup content, View root) {
            imgView = content.findViewById(R.id.icon);
            textView = content.findViewById(R.id.msg);
            imgView.setImageResource(iconId);
            textView.setText(text);
        }
    }
}

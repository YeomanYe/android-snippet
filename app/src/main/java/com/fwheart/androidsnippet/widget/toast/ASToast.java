package com.fwheart.androidsnippet.widget.toast;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;
import com.fwheart.androidsnippet.widget.view.ASLoadingView;
import com.fwheart.androidsnippet.helper.SizeHelper;

import java.util.Timer;
import java.util.TimerTask;

public class ASToast extends AlertDialog {
    private int delay = 1500;
    public static final int SHORT_DELAY = 1500;
    public static final int LONG_DELAY = 3000;
    public ASToast(Context context) {
        this(context, R.style.Toast);
    }

    public ASToast(Context context, int themeResId) {
        super(context, themeResId);
    }

    public ASToast setDelay(int delay) {
        this.delay = delay;
        return this;
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
        timer.schedule(task, delay);
    }

    public static void success(Context context,String msg){
        createToast(context,msg,R.drawable.icon_notify_done);
    }

    public static void info(Context context,String msg){
        createToast(context,msg,R.drawable.icon_notify_info);
    }

    public static void error(Context context,String msg){
        createToast(context,msg,R.drawable.icon_notify_error);
    }

    public static void loading(Context context,String msg){
        createToast(context,msg,0);
    }

    private static void createToast(Context context,String msg,int icon,int delay){
        ASToast toast = (ASToast) new Builder(context)
                .setIcon(icon)
                .setText(msg)
                .create();
        toast.setDelay(delay).show();
    }


    private static void createToast(Context context,String msg,int icon){
        createToast(context,msg,icon,SHORT_DELAY);
    }

    public static class Builder extends ASToastBuilder<Builder>{

        private int iconId;
        private String text;
        private TextView textView;
        private View iconView = null;

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
            if(0 != iconId){
                ImageView imgView = new ImageView(context);
                imgView.setImageResource(iconId);
                iconView = imgView;
            }
            else {
                ASLoadingView loadingView = new ASLoadingView(context);
                loadingView.setColor(Color.WHITE);
                loadingView.setSize(SizeHelper.dp2px(context, 32));
                iconView = loadingView;
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            iconView.setLayoutParams(lp);
            content.addView(iconView,0);
            textView = content.findViewById(R.id.msg);
            textView.setText(text);
        }
    }
}

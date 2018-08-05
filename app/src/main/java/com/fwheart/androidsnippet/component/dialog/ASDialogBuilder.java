package com.fwheart.androidsnippet.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;

public abstract class ASDialogBuilder<T extends ASDialogBuilder> {
    Context context;
    ASDialog dialog;
    String msg = "";
    int layoutId = R.layout.comp_dialog;
    int headerId = R.id.header,
            footerId = R.id.footer,
            contentId = R.id.content,
            okBtnId = R.id.ok_btn,
            cancelBtnId = R.id.cancel_btn;
    boolean hasHeader = true,hasFooter = true;
    OnClickListener onConfirm,onCancel;
    String title = "";
    ViewGroup header,footer,content;
    public ASDialogBuilder(Context context) {
        this.context = context;
    }


    public String getMsg() {
        return msg;
    }

    public T setMsg(String msg) {
        this.msg = msg;
        return (T)this;
    }
    public String getTitle() {
        return title;
    }

    public T setTitle(String title) {
        this.title = title;
        return (T)this;
    }

    public Dialog create(){
        return create(R.style.AlertDialog);
    }

    public OnClickListener getOnConfirm() {
        return onConfirm;
    }

    public void setOnConfirm(OnClickListener onConfirm) {
        this.onConfirm = onConfirm;
    }

    public OnClickListener getOnCancel() {
        return onCancel;
    }

    public void setOnCancel(OnClickListener onCancel) {
        this.onCancel = onCancel;
    }

    OnClickListener wrapClick(final OnClickListener onClickListener, final String tag){
        return new OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("Dialog Click",tag);
                dialog.dismiss();
                if(null != onClickListener) onClickListener.onClick(v);
            }
        };
    }
    void createHeader(ViewGroup header,View root){
        if(!hasHeader) return;
        header.setVisibility(View.VISIBLE);
        TextView titleView = header.findViewById(R.id.title);
        titleView.setText(title);
    }

    void createFooter(ViewGroup footer,View root){
        if(!hasFooter) return;
        footer.setVisibility(View.VISIBLE);
        Button okBtn = footer.findViewById(R.id.ok_btn);
        Button cancelBtn = footer.findViewById(R.id.cancel_btn);
        okBtn.setOnClickListener(wrapClick(onConfirm,"onConfirm"));
        cancelBtn.setOnClickListener(wrapClick(onCancel,"onCancel"));
    }
    abstract public void createContent(ViewGroup content,View root);
    public Dialog create(@StyleRes int themeId){
        dialog = new ASDialog(context,themeId);
        View view = LayoutInflater.from(context).inflate(layoutId,null);
        header = view.findViewById(headerId);
        content = view.findViewById(contentId);
        footer = view.findViewById(footerId);
        createHeader(header,view);

        createContent(content,view);

        createFooter(footer,view);
        view.post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams)header.getLayoutParams();
                int hHeight = header.getHeight() + lp.topMargin + lp.bottomMargin;
                lp = (FrameLayout.LayoutParams) footer.getLayoutParams();
                int fHeight = footer.getHeight() + lp.topMargin + lp.bottomMargin;
                lp = (FrameLayout.LayoutParams) content.getLayoutParams();
                lp.setMargins(0,hHeight,0,fHeight);
            }
        });
        dialog.setView(view);
        return dialog;
    }
}

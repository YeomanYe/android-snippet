package com.fwheart.androidsnippet.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;

public abstract class BaseDialogBuilder<T extends BaseDialogBuilder,E extends AlertDialog> {
    protected Context context;
    protected E dialog;
    protected String msg = "";
    protected int layoutId = R.layout.widget_dialog;
    protected int headerId = R.id.header,
            footerId = R.id.footer,
            contentId = R.id.content,
            okBtnId = R.id.ok_btn,
            cancelBtnId = R.id.cancel_btn;
    protected boolean hasHeader = true,hasFooter = true;
    protected Button okBtn,cancelBtn;
    protected View.OnClickListener onConfirm,onCancel;
    protected String title = "";
    protected ViewGroup header,footer,content;
    protected BaseDialogBuilder(Context context) {
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

    public T setOnConfirm(View.OnClickListener onConfirm) {
        this.onConfirm = onConfirm;
        return (T) this;
    }


    public T setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
        return (T) this;
    }


    public T setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
        return (T)this;
    }

    public T setLayoutId(int layoutId){
        this.layoutId = layoutId;
        return (T)this;
    }

    public T setOnCancel(View.OnClickListener onCancel) {
        this.onCancel = onCancel;
        return (T)this;
    }

    abstract public E createDialog(Context context);

    protected View.OnClickListener wrapClick(final View.OnClickListener onClickListener, final String tag){
        return new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("Dialog Click",tag);
                dialog.dismiss();
                if(null != onClickListener) onClickListener.onClick(v);
            }
        };
    }
    protected void createHeader(ViewGroup header,View root){
        if(!hasHeader) return;
        header.setVisibility(View.VISIBLE);
        TextView titleView = header.findViewById(R.id.title);
        titleView.setText(title);
    }
    protected void setOnClick(Button okBtn,Button cancelBtn){
        if(null != okBtn)okBtn.setOnClickListener(wrapClick(onConfirm,"onConfirm"));
        if(null != cancelBtn)cancelBtn.setOnClickListener(wrapClick(onCancel,"onCancel"));
    }
    protected void createFooter(ViewGroup footer,View root){
        if(!hasFooter) return;
        footer.setVisibility(View.VISIBLE);
        okBtn = footer.findViewById(R.id.ok_btn);
        cancelBtn = footer.findViewById(R.id.cancel_btn);
        setOnClick(okBtn,cancelBtn);
    }
    abstract protected void createContent(ViewGroup content,View root);
    public Dialog create(){
        dialog = createDialog(context);
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
                if(null == header || null == footer) return;
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
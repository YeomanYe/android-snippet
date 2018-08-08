package com.fwheart.androidsnippet.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;

public class ASDialog extends AlertDialog {

    @Override
    public void show() {
        super.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = 800;
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
            msgText.setTextSize(15);
            msgText.setTextColor(Color.GRAY);
            content.addView(msgText);
        }

    }

    public static class AlertBuilder extends ASDialogBuilder{

        public AlertBuilder(Context context) {
            super(context);
        }

        @Override
        public void createContent(ViewGroup content, View root) {
            TextView msgText = new TextView(context);
            msgText.setText(msg);
            msgText.setTextSize(15);
            msgText.setTextColor(Color.GRAY);
            content.addView(msgText);
        }

        @Override
        protected void createFooter(ViewGroup footer, View root) {
            super.createFooter(footer, root);
            View view = footer.findViewById(cancelBtnId);
            view.setVisibility(View.GONE);
        }
    }

    public static class PromptBuilder extends ASDialogBuilder<PromptBuilder>{

        private String placeholder = "";
        private int inputType = InputType.TYPE_CLASS_TEXT;
        private EditText editText;
        private OnClick onCancel,onConfirm;

        public static interface OnClick{
            void onClick(View v,String text);
        }


        public PromptBuilder setOnCancel(OnClick onCancel) {
            this.onCancel = onCancel;
            return this;
        }


        public PromptBuilder setOnConfirm(OnClick onConfirm) {
            this.onConfirm = onConfirm;
            return this;
        }

        public PromptBuilder(Context context) {
            super(context);
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public PromptBuilder setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public int getInputType() {
            return inputType;
        }

        public PromptBuilder setInputType(int inputType) {
            this.inputType = inputType;
            return this;
        }

        @Override
        public void createContent(ViewGroup content, View root) {
            editText = new EditText(context);
            editText.setHint(placeholder);
            editText.setInputType(inputType);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            editText.setLayoutParams(lp);
            editText.setHintTextColor(Color.GRAY);
            editText.setTextSize(15);
            editText.setTextColor(Color.GRAY);
            content.addView(editText);
        }

        @Override
        protected void setOnClick(Button okBtn, Button cancelBtn) {
            okBtn.setOnClickListener(wrapClick(onConfirm,"onConfirm"));
            cancelBtn.setOnClickListener(wrapClick(onCancel,"onCancel"));
        }

        View.OnClickListener wrapClick(final OnClick onClickListener, final String tag) {

            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = editText.getText().toString();
                    dialog.dismiss();
                    Log.d("Dialog Prompt",tag+" "+text);
                    if(null != onClickListener){
                        onClickListener.onClick(v,text);
                    }
                }
            };
        }
    }
}

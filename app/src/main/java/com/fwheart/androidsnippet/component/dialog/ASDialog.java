package com.fwheart.androidsnippet.component.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;
import com.fwheart.androidsnippet.helper.AssetHelper;
import com.fwheart.androidsnippet.helper.SizeHelper;

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
        void createFooter(ViewGroup footer, View root) {
            super.createFooter(footer, root);
            View view = footer.findViewById(cancelBtnId);
            view.setVisibility(View.GONE);
        }
    }

    public static class PromptBuilder extends ASDialogBuilder<PromptBuilder>{

        private String placeholder = "";
        private int inputType = InputType.TYPE_CLASS_TEXT;

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
            EditText editText = new EditText(context);
            editText.setHint(placeholder);
            editText.setInputType(inputType);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            editText.setLayoutParams(lp);
            editText.setHintTextColor(Color.GRAY);
            editText.setTextSize(15);
            editText.setTextColor(Color.GRAY);
            content.addView(editText);
        }
    }
}

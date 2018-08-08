package com.fwheart.androidsnippet.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;
import com.fwheart.androidsnippet.helper.SizeHelper;

import java.util.ArrayList;
import java.util.List;

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
        private List<String> items = new ArrayList<>();
        private OnSheetItemClick sheetItemClick;
        private final String TAG = "ListBuilder";
        public ListBuilder(Context context) {
            super(context);
            setLayoutId(R.layout.widget_actionsheet);
        }
        public static interface OnSheetItemClick{
            void onClick(View view,int position);
        }
        @Override
        protected void createContent(ViewGroup content, View root) {
            for(int len=items.size(),i=0;i<len;i++){
                final int index = i;
                String itemName = items.get(i);
                TextView textView = new TextView(context);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.topMargin = SizeHelper.dp2px(context,20);
                lp.gravity = Gravity.CENTER;
                textView.setLayoutParams(lp);
                textView.setTextSize(17);
                textView.setText(itemName);
                textView.setTextColor(Color.GRAY);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Log.d(TAG,"sheetClick,position:"+index);
                        if(null != sheetItemClick) sheetItemClick.onClick(v,index);
                    }
                });
                content.addView(textView);
            }
        }
        public ListBuilder addItem(String itemName){
            items.add(itemName);
            return this;
        }

        public ListBuilder setOnSheetItemClick(OnSheetItemClick onSheetItemClick){
            sheetItemClick = onSheetItemClick;
            return this;
        }

        @Override
        protected void setOnClick(Button okBtn, Button cancelBtn) {
            super.setOnClick(okBtn, cancelBtn);
        }

    }
}

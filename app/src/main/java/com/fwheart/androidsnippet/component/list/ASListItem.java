package com.fwheart.androidsnippet.component.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.fwheart.androidsnippet.R;

public class ASListItem extends RelativeLayout{
    public ASListItem(Context context) {
        this(context,null);
    }

    public ASListItem(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ASListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context,attrs,defStyleAttr,0);
    }

    public ASListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.comp_list_item,this,true);
    }
}

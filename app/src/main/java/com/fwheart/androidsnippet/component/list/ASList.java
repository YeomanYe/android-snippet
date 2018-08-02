package com.fwheart.androidsnippet.component.list;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ASList extends LinearLayout{
    public ASList(Context context) {
        this(context,null);
    }

    public ASList(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ASList(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context,attrs,defStyleAttr,0);
    }

    public ASList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}

package com.fwheart.androidsnippet.component.section;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.fwheart.androidsnippet.R;

public class ASSectionListPage extends FrameLayout {
    public ASSectionListPage(@NonNull Context context) {
        this(context,null);
    }

    public ASSectionListPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs, R.attr.list_page_defStyle);
    }

    public ASSectionListPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context,attrs,defStyleAttr,0);
    }

    public ASSectionListPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }
    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.comp_section_page,this,true);
        ASSection asList = findViewById(R.id.list);
//        ASSectionItem asListItem = null;
        /*for(int i=0;i<15;i++){
            asListItem = new ASSectionItem(context);
            asList.addView(asListItem);
        }*/
    }
}

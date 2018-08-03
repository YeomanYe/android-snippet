package com.fwheart.androidsnippet.component.section;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fwheart.androidsnippet.R;

import java.util.ArrayList;
import java.util.List;

public class ASSectionListPage extends FrameLayout {
    private LinearLayout container;
    private List<ASSection> sections = new ArrayList<>();
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
        container = findViewById(R.id.section_container);
    }
    public ASSection newSection(Context context){
        ASSection section = new ASSection(context);
        sections.add(section);
        return section;
    }
    public void init(Context context){
        for(ASSection section:sections){
            container.addView(section);
            section.init(context);
        }
    }
}

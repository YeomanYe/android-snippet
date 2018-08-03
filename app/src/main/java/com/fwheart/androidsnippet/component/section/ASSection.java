package com.fwheart.androidsnippet.component.section;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ASSection extends LinearLayout{
    private TextView titleView,subTitleView;
    private LinearLayout container;
    private RecyclerView recyclerView;
    private List<ASSectionItem> sectionItems = new ArrayList<>();
    public ASSection(Context context) {
        this(context,null);
    }

    public ASSection(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs, R.attr.list_defStyle);
    }

    public ASSection(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context,attrs,defStyleAttr,0);
    }

    public ASSection(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }
    public ASSection setTitle(String text){
        titleView.setText(text);
        titleView.setVisibility(VISIBLE);
        return this;
    }

    public ASSection setSubTitle(String text){
        subTitleView.setText(text);
        subTitleView.setVisibility(VISIBLE);
        return this;
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.comp_section,this,true);
        this.setOrientation(VERTICAL);
        container = findViewById(R.id.item_container);
        titleView = findViewById(R.id.title);
        subTitleView = findViewById(R.id.sub_title);
    }
    public void initAttr(Context context,AttributeSet attrs){

    }
    public ASSectionItem newItem(Context context){
        ASSectionItem item = new ASSectionItem(context);
        sectionItems.add(item);
        container.addView(item);
        return item;
    }
}

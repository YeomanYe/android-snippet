package com.fwheart.androidsnippet.component.section;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.fwheart.androidsnippet.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ASSection extends LinearLayout{
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

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.comp_section,this,true);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        List<Map> maps = new ArrayList<>();
        for(int i=0;i<10;i++){
            Map map = new HashMap();
            map.put("accessory",ASSectionItem.AccType.ACCESSORY_TYPE_CHEVRON);
            maps.add(map);
        }
        recyclerView.setAdapter(new ASSectionItemAdaptor(maps));
    }
    public void initAttr(Context context,AttributeSet attrs){

    }
}

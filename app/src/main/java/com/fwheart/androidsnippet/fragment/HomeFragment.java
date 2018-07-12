package com.fwheart.androidsnippet.fragment;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fwheart.androidsnippet.R;
import com.fwheart.androidsnippet.adaptor.SingleTextAdaptor;
import com.fwheart.androidsnippet.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends BaseFragment {

    private List<Map> maps = new ArrayList<>();
    @Override
    protected View onCreateView() {
        FrameLayout layout = (FrameLayout)LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home,null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = layout.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        initDatas();
        SingleTextAdaptor singleTextAdaptor = new SingleTextAdaptor(maps);
        recyclerView.setAdapter(singleTextAdaptor);
        return layout;
    }
    private void initDatas(){
        String[] itemStrs = {"Toast","Dialog"};
        for(String s:itemStrs){
            Map map = new HashMap();
            map.put("desc",s);
            maps.add(map);
        }
    }
}

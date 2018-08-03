package com.fwheart.androidsnippet.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.fwheart.androidsnippet.R;
import com.fwheart.androidsnippet.adaptor.SingleTextAdaptor;
import com.fwheart.androidsnippet.base.BaseFragment;
import com.fwheart.androidsnippet.base.BaseFragmentActivity;
import com.fwheart.androidsnippet.listener.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListTemplateFragment extends BaseFragment {
    private List<Map> maps = new ArrayList<>();
    private String[] itemStrs;
    private RecyclerItemClickListener itemClickListener;
    public ListTemplateFragment(){
        super();
    }

    @SuppressLint("ValidFragment")
    public ListTemplateFragment(String[] itemStrs,RecyclerItemClickListener listener) {
        super();
        this.itemStrs = itemStrs;
        this.itemClickListener = listener;
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    protected View onCreateView() {
        FrameLayout layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home,null);
        ButterKnife.bind(this,layout);
        initDatas();
        initView();
        return layout;
    }
    private void initView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(itemClickListener);
        SingleTextAdaptor singleTextAdaptor = new SingleTextAdaptor(maps);
        recyclerView.setAdapter(singleTextAdaptor);
    }
    private void initDatas(){
        for(String s:itemStrs){
            Map map = new HashMap();
            map.put("desc",s);
            maps.add(map);
        }
    }
}

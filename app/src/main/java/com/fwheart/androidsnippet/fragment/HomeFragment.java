package com.fwheart.androidsnippet.fragment;


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

public class HomeFragment extends BaseFragment{

    private List<Map> maps = new ArrayList<>();
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    protected View onCreateView() {
        FrameLayout layout = (FrameLayout)LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home,null);
        ButterKnife.bind(this,layout);
        initDatas();
        initView();
        return layout;
    }
    private void initView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                BaseFragmentActivity fAct = getBaseFragmentActivity();
                switch (position){
                    case 0:fAct.startFragment(createDialogList());break;
                    case 1:showTips("click 1");break;
                    case 2:showTips("click 2");break;
                }
            }
        }));
        SingleTextAdaptor singleTextAdaptor = new SingleTextAdaptor(maps);
        recyclerView.setAdapter(singleTextAdaptor);
    }
    private ListTemplateFragment createDialogList(){
        return new ListTemplateFragment(new String[]{"Tab","Drawer"},new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        }));
    }
    private void initDatas(){
        String[] itemStrs = {"Layout","Toast","Dialog","Pop-up"};
        maps.clear();
        for(String s:itemStrs){
            Map map = new HashMap();
            map.put("desc",s);
            maps.add(map);
        }
    }
}

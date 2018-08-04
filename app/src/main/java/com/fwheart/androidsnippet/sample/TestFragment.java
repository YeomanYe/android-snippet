package com.fwheart.androidsnippet.sample;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fwheart.androidsnippet.R;
import com.fwheart.androidsnippet.base.BaseFragment;
import com.fwheart.androidsnippet.component.dialog.ASDialog;
import com.fwheart.androidsnippet.component.section.ASSection;
import com.fwheart.androidsnippet.component.section.ASSectionItem;
import com.fwheart.androidsnippet.component.section.ASSectionListPage;

public class TestFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return initSectionPage(inflater,container,savedInstanceState);
    }

    private View initSectionPage(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        Context context = getContext();
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        ASSectionListPage sectionPage = view.findViewById(R.id.list_page);
        ASSection section = sectionPage.newSection(context).setTitle("Section1");
        for(int i=0;i<3;i++){
            final int index = i + 1;
            section.newItem(context)
                    .setText("Item"+index)
                    .setDetailText("Item"+index+" detail")
                    .setOrientation(LinearLayout.VERTICAL)
                    .setIcon(R.mipmap.ic_launcher)
                    .setAccType(ASSectionItem.AccType.values()[i])
            .setClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTips("click item"+index);
                }
            });
        }
        ASSection section2 = sectionPage.newSection(context).setTitle("Section2").setSubTitle("sub title");
        section2.newItem(context)
                .setText("Item1")
                .setDetailText("Item1 detail")
                .setOrientation(LinearLayout.HORIZONTAL)
                .setIcon(R.mipmap.ic_launcher)
                .setAccType(ASSectionItem.AccType.CHEVRON);
        sectionPage.init(context);
        /*ASDialog dialog = new ASDialog();
        dialog.show(getFragmentManager(),"ASDialog");*/
        return view;
    }
}

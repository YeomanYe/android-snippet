package com.fwheart.androidsnippet.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fwheart.androidsnippet.R;
import com.fwheart.androidsnippet.base.BaseFragment;
import com.fwheart.androidsnippet.helper.StatusBarHelper;
import com.fwheart.androidsnippet.widget.dialog.ASActionSheet;
import com.fwheart.androidsnippet.widget.dialog.ASDialog;
import com.fwheart.androidsnippet.widget.section.ASSection;
import com.fwheart.androidsnippet.widget.section.ASSectionItem;
import com.fwheart.androidsnippet.widget.section.ASSectionListPage;
import com.fwheart.androidsnippet.widget.toast.ASToast;

public class TestFragment extends BaseFragment {
    private static final String ARG_POSITION = "index";

    public static TestFragment newInstance(int index){
        TestFragment f = new TestFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, index);
        f.setArguments(b);
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        int index = getArguments().getInt(ARG_POSITION);
        View view = null;
        switch (index){
            case 0:view = createSectionPage(inflater,container,savedInstanceState);break;
            case 1:view = createDialogPage(inflater,container,savedInstanceState);break;
            case 2:view = createOtherPage(inflater,container,savedInstanceState);break;
        }
        return view;
    }

    private View createSectionPage(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
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
        return view;
    }

    private View createDialogPage(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Context context = getContext();
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        ASSectionListPage sectionPage = view.findViewById(R.id.list_page);
        ASSection section = sectionPage.newSection(context).setTitle("Dialog");
        String[][] textArr = new String[][]{{"Alert","只有一个按钮的dialog"},{"Confirm","具有两个按钮的dialog"},{"Prompt","带输入框的dialog"}};
        for(int i=0,len=textArr.length;i<len;i++){
            final int index = i + 1;
            section.newItem(context)
                    .setText(textArr[i][0])
                    .setDetailText(textArr[i][1])
                    .setOrientation(LinearLayout.VERTICAL)
                    .setClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (index){
                                case 1:showAlert("这是alert dialog");break;
                                case 2:showConfirm("这是confirm dialog");break;
                                case 3:showPrompt("请输入数字",InputType.TYPE_CLASS_NUMBER);break;
                            }
                        }
                    });
        }
        section = sectionPage.newSection(context).setTitle("Toast");
        textArr = new String[][]{{"Success","成功图标提示"},{"Error","失败图标提示"},{"Info","信息图标提示"},{"Loading","加载提示"}};
        for(int i=0,len=textArr.length;i<len;i++){
            final int index = i + 1;
            section.newItem(context)
                    .setText(textArr[i][0])
                    .setDetailText(textArr[i][1])
                    .setOrientation(LinearLayout.VERTICAL)
                    .setClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (index){
                                case 1:showSuc("成功提示");break;
                                case 2:showErr("失败提示");break;
                                case 3:showInfo("信息提示");break;
                                case 4:showLoading("加载提示");break;
                            }
                        }
                    });
        }
        section = sectionPage.newSection(context).setTitle("ActionSheet");
        textArr = new String[][]{{"List","列表形式"}};
        for(int i=0,len=textArr.length;i<len;i++){
            final int index = i + 1;
            section.newItem(context)
                    .setText(textArr[i][0])
                    .setDetailText(textArr[i][1])
                    .setOrientation(LinearLayout.VERTICAL)
                    .setClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (index){
                                case 1:
                                    showListActionSheet();break;
                            }
                        }
                    });
        }
        sectionPage.init(context);
        return view;
    }

    private View createOtherPage(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final Context context = getContext();
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        ASSectionListPage sectionPage = view.findViewById(R.id.list_page);
        ASSection section = sectionPage.newSection(context).setTitle("状态栏").setSubTitle("支持4.4以上MIUI和Flyme，以及6.0以上其他Android");
        String[] textArr = new String[]{"设置状态栏黑色字体与图标","设置状态栏白色字体与图标","获取状态栏实际高度"};
        for(int i=0,len=textArr.length;i<len;i++){
            final int index = i;
            section.newItem(context)
                    .setText(textArr[i])
                    .setClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          switch (index){
                              case 0:
                                  StatusBarHelper.setStatusBarLightMode(getActivity());break;
                              case 1:
                                  StatusBarHelper.setStatusBarDarkMode(getActivity());break;
                              case 2:
                                  showTips("状态栏实际高度："+StatusBarHelper.getStatusbarHeight(context));
                                  break;
                          }
                        }
                    })
                    .setOrientation(LinearLayout.VERTICAL);
        }
        sectionPage.init(context);
        return view;
    }

    private void showSuc(String msg){
        ASToast.success(getContext(),msg);
    }

    private void showInfo(String msg){
        ASToast.info(getContext(),msg);
    }
    private void showErr(String msg){
        ASToast.error(getContext(),msg);
    }
    private void showLoading(String msg){
        ASToast.loading(getContext(),msg);
    }

    private void showListActionSheet(){
        new ASActionSheet.ListBuilder(getContext())
                .setTitle("请从下列选项选择一个")
                .addItem("Item 1")
                .addItem("Item 2")
                .addItem("Item 3")
                .setOnSheetItemClick(new ASActionSheet.ListBuilder.OnSheetItemClick(){

                    @Override
                    public void onClick(View view, int position) {

                    }
                })
                .create()
                .show();
    }

    private void showAlert(String msg){
        new ASDialog.AlertBuilder(getContext())
                .setTitle("标题栏")
                .setMsg(msg)
                .create()
                .show();
    }

    private void showConfirm(String msg){
        new ASDialog.ConfirmBuilder(getContext())
                .setTitle("标题栏")
                .setMsg(msg)
                .create()
                .show();
    }

    private void showPrompt(String placeholder,int type){
        new ASDialog.PromptBuilder(getContext())
                .setTitle("标题栏")
                .setInputType(type)
                .setPlaceholder(placeholder)
                .create()
                .show();
    }
}

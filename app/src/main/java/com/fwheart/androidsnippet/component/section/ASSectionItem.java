package com.fwheart.androidsnippet.component.section;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;

import java.util.Map;

public class ASSectionItem extends RelativeLayout{
    private FrameLayout accView;
    private SwitchCompat switchView;
    private TextView textView,detailTextView;
    private ImageView iconView;
    public ASSectionItem(Context context) {
        this(context,null);
    }

    public ASSectionItem(Context context, AttributeSet attrs) {
        this(context,attrs,R.attr.list_item_defStyle);
    }

    public ASSectionItem(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context,attrs,defStyleAttr,0);
    }

    public ASSectionItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
        initAttr(context,attrs);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.comp_section_item,this,true);
        accView = findViewById(R.id.section_item_accessoryView);
        textView = findViewById(R.id.section_item_textView);
        detailTextView = findViewById(R.id.section_item_detailTextView);
        iconView = findViewById(R.id.section_item_imageView);
        setAccessoryType(AccType.ACCESSORY_TYPE_SWITCH);
    }

    public void initAttr(Context context,AttributeSet attrs){

    }

    enum AccType{
        ACCESSORY_TYPE_CHEVRON,
        ACCESSORY_TYPE_SWITCH,
        ACCESSORY_TYPE_CUSTOM,
        ACCESSORY_TYPE_NONE
    }

    private ViewGroup.LayoutParams getAccessoryLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private ImageView getAccessoryImageView() {
        ImageView resultImageView = new ImageView(getContext());
        resultImageView.setLayoutParams(getAccessoryLayoutParams());
        resultImageView.setScaleType(ImageView.ScaleType.CENTER);
        return resultImageView;
    }

    public void setAttrByMap(Map map){
        String text = (String) map.get("detailText");
        if(null != text){
            detailTextView.setText(text);
            detailTextView.setVisibility(VISIBLE);
        }
        text = (String) map.get("text");
        if(null != text){
            textView.setText(text);
            textView.setVisibility(VISIBLE);
        }
        Integer resId = (Integer) map.get("leftImg");
        if(null != resId) {
            iconView.setImageResource(resId);
            iconView.setVisibility(VISIBLE);
        }
        String detailText = (String) map.get("detailText");
        if(detailText != null){
            detailTextView.setText(detailText);
            detailTextView.setVisibility(VISIBLE);
        }
    }

    public void setAccessoryType(AccType type) {
        accView.removeAllViews();

        switch (type) {
            // 向右的箭头
            case ACCESSORY_TYPE_CHEVRON: {
                ImageView tempImageView = getAccessoryImageView();
                tempImageView.setImageResource(R.drawable.icon_chevron);
                accView.addView(tempImageView);
                accView.setVisibility(VISIBLE);
            }
            break;
            // switch开关
            case ACCESSORY_TYPE_SWITCH: {
                if (switchView == null) {
                    switchView = new SwitchCompat(getContext());
                    switchView.setLayoutParams(getAccessoryLayoutParams());
                    // disable掉且不可点击，然后通过整个item的点击事件来toggle开关的状态
//                    switchView.setClickable(false);
//                    switchView.setEnabled(false);
                }
                accView.addView(switchView);
                accView.setVisibility(VISIBLE);
            }
            break;
            // 自定义View
            case ACCESSORY_TYPE_CUSTOM:
                accView.setVisibility(VISIBLE);
                break;
            // 清空所有accessoryView
            case ACCESSORY_TYPE_NONE:
                accView.setVisibility(GONE);
                break;
        }
    }
}

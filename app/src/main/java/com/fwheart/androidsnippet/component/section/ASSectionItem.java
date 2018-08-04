package com.fwheart.androidsnippet.component.section;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;
import com.fwheart.androidsnippet.helper.AssetHelper;

import java.util.Map;

public class ASSectionItem extends RelativeLayout{
    private FrameLayout accView;
    private SwitchCompat switchView;
    private TextView textView,detailTextView;
    private LinearLayout textContainer;
    private ImageView iconView;
    private AccType accType;
    private OnClickListener onClickListener;

    public static boolean isLastTouch = false;
    int rippleSize = 3;
    // ### RIPPLE EFFECT ###
    float x = -1, y = -1;
    float radius = -1;

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
        textContainer = findViewById(R.id.section_item_textContainer);
    }

    public void initAttr(Context context,AttributeSet attrs){
        //设置波纹效果
        setClickable(true);
        setBackground(AssetHelper.getDrawable(context,R.attr.selectableItemBackground));

    }

    public static enum AccType{
        CHEVRON, SWITCH, CUSTOM, NONE
    }

    public ASSectionItem setText(String text){
        textView.setText(text);
        textView.setVisibility(VISIBLE);
        return this;
    }

    public ASSectionItem setDetailText(String text){
        detailTextView.setText(text);
        detailTextView.setVisibility(VISIBLE);
        return this;
    }

    public ASSectionItem setIcon(int resId){
        iconView.setImageResource(resId);
        iconView.setVisibility(VISIBLE);
        return this;
    }

    public ASSectionItem setOrientation(@LinearLayoutCompat.OrientationMode int orientation){
        textContainer.setOrientation(orientation);
        return this;
    }

    public ImageView getIconView() {
        return iconView;
    }

    public TextView getDetailTextView() {
        return detailTextView;
    }

    public SwitchCompat getSwitchView() {
        return switchView;
    }

    public TextView getTextView() {
        return textView;
    }

    public boolean isCheckSwitch(){
        return switchView.isChecked();
    }

    public ASSectionItem setClick(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
        this.setOnClickListener(wrapClickListener(onClickListener));
        return this;
    }

    private boolean toggleSwitch(){
        boolean checked = !switchView.isChecked();
        switchView.setChecked(checked);
        return checked;
    }

    private OnClickListener wrapClickListener(final OnClickListener onClickListener){
        return new OnClickListener(){

            @Override
            public void onClick(View v) {
                ASSectionItem item = (ASSectionItem) v;
                AccType accType = item.getAccType();
                switch (accType){
                    case SWITCH:
                        boolean checked = toggleSwitch();
                        Log.d("ClickListener",checked+"");
                        break;
                }
                if(null != onClickListener)onClickListener.onClick(v);
            }
        };
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

    public AccType getAccType() {
        return accType;
    }

    private SwitchCompat createSwitch(){
        SwitchCompat switchView = new SwitchCompat(getContext());
        switchView.setLayoutParams(getAccessoryLayoutParams());
        // disable掉且不可点击，然后通过整个item的点击事件来toggle开关的状态
        switchView.setClickable(false);
        switchView.setEnabled(false);
        switchView.setThumbResource(R.drawable.switch_custom_thumb_selector);
        switchView.setTrackResource(R.drawable.switch_custom_track_selector);
        return switchView;
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        isLastTouch = true;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            radius = getHeight() / rippleSize;
            x = event.getX();
            y = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            radius = getHeight() / rippleSize;
            x = event.getX();
            y = event.getY();
            if (!((event.getX() <= getWidth() && event.getX() >= 0) &&
                    (event.getY() <= getHeight() && event.getY() >= 0))) {
                isLastTouch = false;
                x = -1;
                y = -1;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if ((event.getX() <= getWidth() && event.getX() >= 0) &&
                    (event.getY() <= getHeight() && event.getY() >= 0)) {
                radius++;
            } else {
                isLastTouch = false;
                x = -1;
                y = -1;
            }
        }
        return true;
    }*/

    public ASSectionItem setAccType(AccType type) {
        accView.removeAllViews();
        accType = type;
        switch (type) {
            // 向右的箭头
            case CHEVRON: {
                ImageView tempImageView = getAccessoryImageView();
                tempImageView.setImageResource(R.drawable.icon_chevron);
                accView.addView(tempImageView);
                accView.setVisibility(VISIBLE);
            }
            break;
            // switch开关
            case SWITCH: {
                if (switchView == null) {
                    switchView = createSwitch();
                }
                accView.addView(switchView);
                accView.setVisibility(VISIBLE);
            }
            break;
            // 自定义View
            case CUSTOM:
                accView.setVisibility(VISIBLE);
                break;
            // 清空所有accessoryView
            case NONE:
                accView.setVisibility(GONE);
                break;
        }
        return this;
    }
}

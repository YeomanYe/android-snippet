package com.fwheart.androidsnippet.component.section;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;

import java.util.List;
import java.util.Map;

public class ASSectionItemAdaptor extends RecyclerView.Adapter<ASSectionItemAdaptor.ViewHolder> {
    private List<Map> maps;

    public ASSectionItemAdaptor(List<Map> maps) {
        this.maps = maps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new ASSectionItem(parent.getContext());
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Map map = maps.get(position);
        holder.asSectionItem.setAttrByMap(map);
    }

    @Override
    public int getItemCount() {
        return maps.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
       ASSectionItem asSectionItem;
        public ViewHolder(View view) {
            super(view);
            asSectionItem = (ASSectionItem) view;
        }
    }

}

package com.fwheart.androidsnippet.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fwheart.androidsnippet.R;

import java.util.List;
import java.util.Map;

public class SingleTextAdaptor extends RecyclerView.Adapter<SingleTextAdaptor.ViewHolder> {
    private List<Map> maps;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView comdStr;

        public ViewHolder(View view) {
            super(view);
            comdStr = (TextView) view.findViewById(R.id.text);
        }
    }

    public SingleTextAdaptor(List<Map> mapList) {
        maps = mapList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_single_text, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Map map = maps.get(position);
        holder.comdStr.setText((String)map.get("desc"));
    }

    @Override
    public int getItemCount() {
        return maps.size();
    }
}

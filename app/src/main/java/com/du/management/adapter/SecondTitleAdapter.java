package com.du.management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.du.management.R;

import java.util.List;

public class SecondTitleAdapter extends BaseAdapter {

    private Context context;

    private List<String> list;

    public SecondTitleAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null || convertView.getTag() == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_secondtitle, null);
            viewHolder.nameTV = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameTV.setText(list.get(position));
        return convertView;
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        private TextView nameTV;
    }
}

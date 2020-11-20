package com.du.management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.du.management.R;
import com.du.management.view.RemarkDialog;

import java.util.List;

public class RemarkAdapter extends BaseAdapter {

    public Context context;

    public List<RemarkDialog.Data> dataList;

    public RemarkAdapter(Context context, List<RemarkDialog.Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null || convertView.getTag() == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_text, null);
            viewHolder.textView = convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RemarkDialog.Data data = dataList.get(i);
        viewHolder.textView.setText(data.getPreResult());
        return convertView;
    }

    public class ViewHolder {
        public TextView textView;
    }

}

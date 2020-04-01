package com.du.management.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.du.management.R;
import com.du.management.bean.TaskThemeBasis;

import java.util.ArrayList;
import java.util.List;

public class LawAdapter extends BaseAdapter {

    private Context context;

    private List<TaskThemeBasis> list = new ArrayList<>();

    public LawAdapter(Context context, List<TaskThemeBasis> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null || convertView.getTag() == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_raw, null);
            viewHolder.titleTV = convertView.findViewById(R.id.title);
            viewHolder.contentTV = convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final TaskThemeBasis basis = list.get(position);
        StringBuffer nameBuffer = new StringBuffer();
        if (!TextUtils.isEmpty(basis.getRuleName())) {
            nameBuffer.append("《").append(basis.getRuleName()).append("》");
        }
        if (!TextUtils.isEmpty(basis.getName())) {
            nameBuffer.append(" ").append(basis.getName());
        }
        if (!TextUtils.isEmpty(nameBuffer.toString())) {
            viewHolder.titleTV.setText(nameBuffer.toString());
        }
        if (!TextUtils.isEmpty(basis.getText())) {
            viewHolder.contentTV.setText(Html.fromHtml(basis.getText()));
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView titleTV;

        public TextView contentTV;
    }
}

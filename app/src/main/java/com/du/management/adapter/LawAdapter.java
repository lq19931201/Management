package com.du.management.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.du.management.R;
import com.du.management.bean.TaskThemeBasis;
import com.du.management.newBean.Jianchayiju;

import java.util.ArrayList;
import java.util.List;

public class LawAdapter extends BaseAdapter {

    private Context context;

    private List<Jianchayiju> list = new ArrayList<>();

    public LawAdapter(Context context, List<Jianchayiju> list) {
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
        final Jianchayiju basis = list.get(position);
        if (!TextUtils.isEmpty(basis.getTitle())) {
            viewHolder.titleTV.setText((position + 1) + "." + basis.getTitle());
        }
        if (!TextUtils.isEmpty(basis.getContent())) {
            viewHolder.contentTV.setText(Html.fromHtml(basis.getContent()));
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView titleTV;

        public TextView contentTV;
    }
}

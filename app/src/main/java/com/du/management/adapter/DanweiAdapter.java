package com.du.management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.du.management.R;
import com.du.management.newBean.JczcField;

import java.util.List;

public class DanweiAdapter extends BaseAdapter {

    private Context context;

    private List<JczcField> jczcFieldList;

    public DanweiAdapter(Context context, List<JczcField> jczcFieldList) {
        this.context = context;
        this.jczcFieldList = jczcFieldList;
    }

    @Override
    public int getCount() {
        return jczcFieldList.size();
    }

    @Override
    public Object getItem(int i) {
        return jczcFieldList.get(i);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null || convertView.getTag() == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_jczcfield, null);
            viewHolder.key = convertView.findViewById(R.id.key);
            viewHolder.value = convertView.findViewById(R.id.value);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        JczcField jczcField = jczcFieldList.get(i);
        viewHolder.key.setText(jczcField.getFieldName());
        viewHolder.value.setText(jczcField.getValue());
        return convertView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        public TextView key;
        public TextView value;
    }

}

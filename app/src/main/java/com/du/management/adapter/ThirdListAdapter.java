package com.du.management.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.du.management.R;
import com.du.management.activity.SecondActivity;
import com.du.management.bean.TaskBody;

import java.util.ArrayList;
import java.util.List;

public class ThirdListAdapter extends BaseAdapter {

    private Context context;

    private List<TaskBody> list = new ArrayList<>();

    public ThirdListAdapter(Context context, List<TaskBody> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_thirdlist, null);
            viewHolder.titleTV = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final TaskBody taskBody = list.get(position);
        if (!TextUtils.isEmpty(taskBody.getName())) {
            viewHolder.titleTV.setText(taskBody.getName());
        }
        if (position == SecondActivity.ThirdCurrentPosition) {
            viewHolder.titleTV.setBackgroundColor(context.getResources().getColor(R.color.six_e));
            viewHolder.titleTV.setTextColor(context.getResources().getColor(R.color.second));
        } else {
            viewHolder.titleTV.setBackgroundColor(context.getResources().getColor(R.color.white));
            viewHolder.titleTV.setTextColor(context.getResources().getColor(R.color.six_three));
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView titleTV;
    }
}

package com.du.management.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.du.management.R;
import com.du.management.activity.DanweiActivity;
import com.du.management.activity.NewSecondActivity;
import com.du.management.activity.NewSecondTwoActivity;
import com.du.management.newBean.NewContent;
import com.du.management.newBean.NewTask;
import com.du.management.view.NewMyListView;

import java.util.List;

public class CurrentTaskAdapter extends BaseAdapter {

    private Context context;
    private List<NewTask> list;

    public CurrentTaskAdapter(Context context, List<NewTask> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null || convertView.getTag() == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_current_task, null);
            viewHolder.taskNameTV = convertView.findViewById(R.id.taskName);
            viewHolder.myListView = convertView.findViewById(R.id.listView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final NewTask task = list.get(position);
        if (!TextUtils.isEmpty(task.getRenwuname())) {
            viewHolder.taskNameTV.setText(task.getRenwuname());
        }
        DanweiAdapter adapter = new DanweiAdapter(task.getJczcJianchashishis(), task.getRenwuId());
        viewHolder.myListView.setAdapter(adapter);
        return convertView;
    }

    public class ViewHolder {
        public TextView taskNameTV;
        public NewMyListView myListView;
    }


    public class DanweiAdapter extends BaseAdapter {

        private List<NewContent> list;

        private long renwuId;

        public DanweiAdapter(List<NewContent> list, long renwuId) {
            this.list = list;
            this.renwuId = renwuId;
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
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            DanweiHolder viewHolder;
            if (convertView == null || convertView.getTag() == null) {
                viewHolder = new DanweiHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_danwei, null);
                viewHolder.nameTV = convertView.findViewById(R.id.name);
                viewHolder.danweiTV = convertView.findViewById(R.id.danwei);
                viewHolder.jianchaTV = convertView.findViewById(R.id.jiancha);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (DanweiHolder) convertView.getTag();
            }
            final NewContent newContent = list.get(i);
            viewHolder.nameTV.setText(newContent.getDanweiName());
            viewHolder.danweiTV.setOnClickListener(v -> {
                Intent intent = new Intent(context, DanweiActivity.class);
                intent.putExtra("name", newContent.getDanweiName());
                intent.putExtra("xiangmuId", newContent.getXiangmuId());
                context.startActivity(intent);
            });
            viewHolder.jianchaTV.setOnClickListener(v -> {
                Intent intent = new Intent(context, NewSecondTwoActivity.class);
                intent.putExtra("title", list.get(i).getDanweiName());
                intent.putExtra("taskId", renwuId);
                intent.putExtra("mobanId", list.get(i).getMobanId());
                intent.putExtra("xiangmuId", list.get(i).getXiangmuId());
                context.startActivity(intent);
            });
            return convertView;
        }

        public class DanweiHolder {
            public TextView nameTV;

            public TextView danweiTV;

            public TextView jianchaTV;
        }
    }
}

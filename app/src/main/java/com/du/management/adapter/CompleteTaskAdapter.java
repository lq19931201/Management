package com.du.management.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.du.management.MainApplication;
import com.du.management.R;
import com.du.management.activity.SecondActivity;
import com.du.management.bean.Content;
import com.du.management.bean.Task;
import com.du.management.http.HttpConstant;
import com.du.management.utils.Utils;
import com.du.management.view.CommitDialog;
import com.du.management.view.MyGridView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompleteTaskAdapter extends BaseAdapter {
    private Context context;
    private List<Task> list = new ArrayList<>();
    private int status;

    public CompleteTaskAdapter(Context context, List<Task> list) {
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
            viewHolder.taskNameTV = (TextView) convertView.findViewById(R.id.task_name);
            viewHolder.commitTV = (TextView) convertView.findViewById(R.id.commit);
            viewHolder.gridView = (MyGridView) convertView.findViewById(R.id.gridview);
            viewHolder.endTimeTV = (TextView) convertView.findViewById(R.id.end_time);
            viewHolder.companyTV = (TextView) convertView.findViewById(R.id.company_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Task task = list.get(position);
        if (!TextUtils.isEmpty(task.getTaskName()))
            viewHolder.taskNameTV.setText(task.getTaskName());
        if (!TextUtils.isEmpty(task.getName()))
            viewHolder.companyTV.setText(task.getName());
        viewHolder.endTimeTV.setText(Utils.changeTime(task.getEndTime()) + "截止");
        GridAdapter adapter = new GridAdapter(task.getContentList());
        viewHolder.gridView.setAdapter(adapter);
        viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("title", list.get(position).getContentList().get(i).getLabel());
                intent.putExtra("taskId", String.valueOf(list.get(position).getTaskId()));
                intent.putExtra("contentId", list.get(position).getContentList().get(i).getContentId());
                intent.putExtra("isComplete", true);
                context.startActivity(intent);
            }
        });
        viewHolder.commitTV.setText("已提交");
        return convertView;
    }

    public class ViewHolder {
        public TextView taskNameTV;

        public TextView commitTV;

        public TextView endTimeTV;

        public MyGridView gridView;

        public TextView companyTV;

    }

    public class GridAdapter extends BaseAdapter {
        private List<Content> contentList = new ArrayList<>();

        public GridAdapter(List<Content> contentList) {
            this.contentList = contentList;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return contentList.get(position);
        }

        @Override
        public int getCount() {
            return contentList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridViewHolder viewHolder;
            if (convertView == null || convertView.getTag() == null) {
                viewHolder = new GridViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_gridview, null);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(viewHolder);
            }
            viewHolder = (GridViewHolder) convertView.getTag();
            Content content = contentList.get(position);
            viewHolder.textView.setText(new StringBuffer().append(position + 1).append(".").append(content.getLabel()));
            return convertView;
        }

    }

    public class GridViewHolder {
        public TextView textView;
    }
}

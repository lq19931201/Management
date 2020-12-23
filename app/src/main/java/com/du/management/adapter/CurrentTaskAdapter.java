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
import com.du.management.activity.NewSecondActivity;
import com.du.management.activity.SecondActivity;
import com.du.management.bean.Content;
import com.du.management.bean.Task;
import com.du.management.http.HttpConstant;
import com.du.management.newBean.NewContent;
import com.du.management.newBean.NewTask;
import com.du.management.utils.Utils;
import com.du.management.view.CommitDialog;
import com.du.management.view.MyGridView;
import com.du.management.view.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return convertView;
    }

    public class ViewHolder {
        public TextView taskNameTV;
        public MyListView myListView;
    }

}

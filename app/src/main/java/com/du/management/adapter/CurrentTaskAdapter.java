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
import com.du.management.newBean.NewContent;
import com.du.management.newBean.NewTask;
import com.du.management.utils.Utils;
import com.du.management.view.CommitDialog;
import com.du.management.view.MyGridView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentTaskAdapter extends BaseAdapter {

    private Context context;
    private List<NewTask> list = new ArrayList<>();
    private int status;

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
            viewHolder.taskNameTV = (TextView) convertView.findViewById(R.id.task_name);
            viewHolder.commitTV = (TextView) convertView.findViewById(R.id.commit);
            viewHolder.gridView = (MyGridView) convertView.findViewById(R.id.gridview);
            viewHolder.endTimeTV = (TextView) convertView.findViewById(R.id.end_time);
            viewHolder.companyTV = (TextView) convertView.findViewById(R.id.company_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final NewTask task = list.get(position);
        if (!TextUtils.isEmpty(task.getRenwuname()))
            viewHolder.taskNameTV.setText(task.getRenwuname());
//        if (!TextUtils.isEmpty(task.getName()))
//            viewHolder.companyTV.setText(task.getName());
        viewHolder.endTimeTV.setText(Utils.formatString(task.getCreatedtime()) + "截止");
        GridAdapter adapter = new GridAdapter(task.getJczcJianchashishis());
        viewHolder.gridView.setAdapter(adapter);
        viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("title", list.get(position).getJczcJianchashishis().get(i).getDanweiName());
                intent.putExtra("taskId", String.valueOf(list.get(position).getRenwuId()));
                intent.putExtra("contentId", list.get(position).getJczcJianchashishis().get(i).getXiangmuId());
                context.startActivity(intent);
            }
        });
        viewHolder.commitTV.setText("提交");
        viewHolder.commitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CommitDialog dialog = new CommitDialog(context);
                dialog.show();
                dialog.setOnConfirmClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        final RequestQueue requestQueue = Volley.newRequestQueue(context);
                        final StringBuffer stringBuffer = new StringBuffer();
                        Map<String, String> params = new HashMap<>();
                        params.put("taskId", String.valueOf(task.getRenwuId()));
                        params.put("state", "1");
                        stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("rule/netbook/update/task");
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, stringBuffer.toString(), new JSONObject(params), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getInt("code") == 200) {
                                        Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show();
                                        EventBus.getDefault().post("refresh");
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("token", MainApplication.TOKEN);
                                return headers;
                            }
                        };
                        requestQueue.add(jsonObjectRequest);

                    }
                });
                dialog.setOnCancleClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
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
        private List<NewContent> contentList = new ArrayList<>();

        public GridAdapter(List<NewContent> contentList) {
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
            NewContent content = contentList.get(position);
            if (!TextUtils.isEmpty(content.getDanweiName()))
                viewHolder.textView.setText(content.getDanweiName());
            return convertView;
        }
    }

    public class GridViewHolder {
        public TextView textView;
    }
}

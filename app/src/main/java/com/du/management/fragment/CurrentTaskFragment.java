package com.du.management.fragment;


import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.du.management.MainApplication;
import com.du.management.R;
import com.du.management.activity.SecondActivity;
import com.du.management.adapter.CurrentTaskAdapter;
import com.du.management.bean.Task;
import com.du.management.http.HeaderStringRequest;
import com.du.management.http.HttpConstant;
import com.du.management.newBean.NewTask;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CurrentTaskFragment extends BaseFragment {
    private ListView listView;

    private List<NewTask> list = new ArrayList<>();

    @Override
    protected View initView(ViewGroup container) {
        EventBus.getDefault().register(this);
        View view = View.inflate(getActivity(), R.layout.fragment_current, null);
        listView =  view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {
        String url = new StringBuffer().append(HttpConstant.REQUSET_BASE_URL).append("jianchazhicheng/getJiancharenwu/").append(MainApplication.userId).toString();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        HeaderStringRequest request = new HeaderStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.w("CurrentTaskFragment",response);
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    if (code.equals(HttpConstant.CODE_SUCCESS)) {
                        Log.w("CurrentTaskFragment",jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        Gson gson = new Gson();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            NewTask task = gson.fromJson(jsonArray.getJSONObject(i).toString(), NewTask.class);
                            list.add(task);
                        }
                        CurrentTaskAdapter adapter = new CurrentTaskAdapter(getActivity(), list);
                        listView.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(String str) {
        list.clear();
        initData();
    }

}

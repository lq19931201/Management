package com.du.management.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.du.management.R;
import com.du.management.adapter.CompleteTaskAdapter;
import com.du.management.bean.Task;
import com.du.management.http.HeaderStringRequest;
import com.du.management.http.HttpConstant;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompleteTaskFragment extends BaseFragment {

    private ListView listView;

    private List<Task> list = new ArrayList<>();

    @Override
    protected View initView(ViewGroup container) {
        EventBus.getDefault().register(this);
        View view = View.inflate(getActivity(), R.layout.fragment_complete, null);
        listView = (ListView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    protected void initData() {
        String url = new StringBuffer().append(HttpConstant.REQUSET_BASE_URL).append("rule/netbook/getHome?state=1").toString();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        HeaderStringRequest request = new HeaderStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if (code == 200) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        Gson gson = new Gson();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Task task = gson.fromJson(jsonArray.getJSONObject(i).toString(), Task.class);
                            list.add(task);
                        }
                        CompleteTaskAdapter adapter = new CompleteTaskAdapter(getActivity(), list);
                        listView.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(String str) {
        list.clear();
        initData();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}

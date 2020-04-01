package com.du.management.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
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
import com.du.management.activity.BigPhotoActivity;
import com.du.management.adapter.CurrentTaskAdapter;
import com.du.management.adapter.PhotoAdapter;
import com.du.management.bean.Task;
import com.du.management.http.HeaderStringRequest;
import com.du.management.http.HttpConstant;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadDialog extends Dialog {

    private Context context;

    private TextView contentTV;

    private RecyclerView recyclerView;

    public ReadDialog(Context context, String taskThemeId, String themeId) {
        super(context, R.style.customDialog);
        setContentView(R.layout.dialog_read);
        findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        this.context = context;
        initView();
        getStatus(taskThemeId);
        getPhoto(taskThemeId);
    }

    private void initView() {
        contentTV = (TextView) findViewById(R.id.content);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }


    private void getStatus(String taskThemeId) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringBuffer stringBuffer = new StringBuffer();
        Map<String, String> params = new HashMap<>();
        params.put("taskThemeId", taskThemeId);
        stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("rule/netbook/getTaskThemeSituation");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, stringBuffer.toString(), new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("code") == 200) {
                        try {
                            String content = response.getJSONObject("data").getString("situation");
                            if (!TextUtils.isEmpty(content))
                                contentTV.setText(content);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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

    private void getPhoto(String taskThemeId) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringBuffer stringBuffer = new StringBuffer();
        Map<String, String> params = new HashMap<>();
        params.put("taskThemeId", taskThemeId);
        stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("rule/netbook/getTaskThemeImg");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, stringBuffer.toString(), new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("code") == 200) {
                        try {
                            final List<String> list = new ArrayList<>();
                            final JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                StringBuffer icon = new StringBuffer();
                                icon.append(jsonArray.getJSONObject(i).getString("url"));
                                list.add(icon.toString());
                            }
                            final PhotoAdapter adapter = new PhotoAdapter(context, list);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemLongClickListener(new PhotoAdapter.OnItemLongClickListener() {
                                @Override
                                public void onItemLongClick(View view, final int postion) {
                                    final DeleteDialog deleteDialog = new DeleteDialog(context);
                                    deleteDialog.show();
                                    deleteDialog.setOnConfirmClick(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            try {
                                                deleteDialog.dismiss();
                                                String url = new StringBuffer().append(HttpConstant.REQUSET_BASE_URL).append("rule/netbook/deleteTaskThemeImg/").append(jsonArray.getJSONObject(postion).getString("themeFileId")).toString();
                                                RequestQueue requestQueue = Volley.newRequestQueue(context);
                                                HeaderStringRequest request = new HeaderStringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        try {
                                                            JSONObject jsonObject = new JSONObject(response);
                                                            int code = jsonObject.getInt("code");
                                                            if (code == 200) {
                                                                list.remove(postion);
                                                                adapter.notifyDataSetChanged();
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
                                                });
                                                requestQueue.add(request);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            });
                            adapter.setOnItemClickListener(new PhotoAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int postion) {
                                    BigPhotoDialog dialog = new BigPhotoDialog(context, list.get(postion));
                                    dialog.show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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

}

package com.du.management.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
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
import com.du.management.bean.TaskTheme;
import com.du.management.bean.TaskThemeBasis;
import com.du.management.http.HeaderStringRequest;
import com.du.management.http.HttpConstant;
import com.du.management.newBean.Jcxm;
import com.du.management.view.CheckDialog;
import com.du.management.view.LawDialog;
import com.du.management.view.ReadDialog;
import com.du.management.view.RemarkDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThirdDetailAdapter extends BaseAdapter {

    private List<Jcxm> list = new ArrayList<>();

    private Context context;

    private boolean isComplete;

    public ThirdDetailAdapter(Context context, List<Jcxm> list, boolean isComplete) {
        this.context = context;
        this.list = list;
        this.isComplete = isComplete;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null || convertView.getTag() == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_third_details, null);
            viewHolder.nameTV = (TextView) convertView.findViewById(R.id.name);
            viewHolder.jianIV = (ImageView) convertView.findViewById(R.id.jian);
            viewHolder.faIV = (ImageView) convertView.findViewById(R.id.fa);
            viewHolder.remarkIV = (ImageView) convertView.findViewById(R.id.remark);
            viewHolder.takePhotoIV = (ImageView) convertView.findViewById(R.id.take_photo);
            viewHolder.readTV = (TextView) convertView.findViewById(R.id.read);
            viewHolder.bufuheTV = (TextView) convertView.findViewById(R.id.bufuhe);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Jcxm taskTheme = list.get(position);
        if (!taskTheme.isAdd) {
            viewHolder.nameTV.setText(taskTheme.getJcxmName());
            viewHolder.nameTV.setVisibility(View.VISIBLE);
            viewHolder.faIV.setVisibility(View.VISIBLE);
            viewHolder.remarkIV.setVisibility(View.VISIBLE);
            viewHolder.takePhotoIV.setVisibility(View.VISIBLE);
            viewHolder.readTV.setVisibility(View.VISIBLE);
            viewHolder.jianIV.setVisibility(View.VISIBLE);
            viewHolder.bufuheTV.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setVisibility(View.VISIBLE);
        } else {
            viewHolder.nameTV.setText(taskTheme.getJcxmName());
            viewHolder.nameTV.setVisibility(View.INVISIBLE);
            viewHolder.faIV.setVisibility(View.INVISIBLE);
            viewHolder.remarkIV.setVisibility(View.INVISIBLE);
            viewHolder.takePhotoIV.setVisibility(View.INVISIBLE);
            viewHolder.readTV.setVisibility(View.INVISIBLE);
            viewHolder.jianIV.setVisibility(View.INVISIBLE);
            viewHolder.bufuheTV.setVisibility(View.INVISIBLE);
            viewHolder.checkBox.setVisibility(View.INVISIBLE);
        }
        if (isComplete) {
            viewHolder.checkBox.setEnabled(false);
            viewHolder.takePhotoIV.setVisibility(View.GONE);
            viewHolder.remarkIV.setVisibility(View.GONE);
        }
        viewHolder.checkBox.setChecked(taskTheme.isAccord());
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = viewHolder.checkBox.isChecked();
                viewHolder.checkBox.setChecked(checked);
                taskTheme.setAccord(viewHolder.checkBox.isChecked());
            }
        });
        viewHolder.takePhotoIV.setTag(position);
        viewHolder.jianIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final RequestQueue requestQueue = Volley.newRequestQueue(context);
//                final StringBuffer stringBuffer = new StringBuffer();
//                Map<String, String> params = new HashMap<>();
//                params.put("themeId", String.valueOf(taskTheme.getThemeId()));
//                stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("rule/netbook/getElement");
//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, stringBuffer.toString(), new JSONObject(params), new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            if (response.getInt("code") == 200) {
//                                StringBuffer element = new StringBuffer();
//                                try {
//                                    JSONArray jsonArray = response.getJSONArray("data");
//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        element.append(i + 1).append(".").append(jsonArray.getJSONObject(i).getString("name")).append("\n");
//                                    }
//                                    String method = "";
//                                    if (taskTheme.getMethodList() != null && taskTheme.getMethodList().size() > 0) {
//                                        for (int k = 0; k < taskTheme.getMethodList().size(); k++) {
//                                            method = method + (k + 1) + "." + taskTheme.getMethodList().get(k) + "\n";
//                                        }
//                                    }
//                                    CheckDialog checkDialog = new CheckDialog(context, method, element.toString());
//                                    checkDialog.show();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        HashMap<String, String> headers = new HashMap<String, String>();
//                        headers.put("token", MainApplication.TOKEN);
//                        return headers;
//                    }
//                };
//                requestQueue.add(jsonObjectRequest);

            }
        });
        viewHolder.faIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = new StringBuffer().append(HttpConstant.REQUSET_BASE_URL).append("rule/netbook/getrule/").append(taskTheme.getThemeId()).toString();
//                RequestQueue requestQueue = Volley.newRequestQueue(context);
//                HeaderStringRequest request = new HeaderStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            int code = jsonObject.getInt("code");
//                            if (code == 200) {
//                                JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                Gson gson = new Gson();
//                                List<TaskThemeBasis> list = new ArrayList<>();
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    list.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), TaskThemeBasis.class));
//                                }
//                                LawDialog lawDialog = new LawDialog(context, list);
//                                lawDialog.show();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                requestQueue.add(request);
            }
        });
        viewHolder.remarkIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final RemarkDialog rDialog = new RemarkDialog(context, String.valueOf(taskTheme.getTaskThemeId()));
//                rDialog.show();
//                rDialog.setOnSaveClick(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        rDialog.dismiss();
//                        final RequestQueue requestQueue = Volley.newRequestQueue(context);
//                        final StringBuffer stringBuffer = new StringBuffer();
//                        Map<String, String> params = new HashMap<>();
//                        params.put("taskThemeId", String.valueOf(taskTheme.getTaskThemeId()));
////                        params.put("imageId","");//上传图片id
////                        params.put("fileId","");//上传文件id
//                        if (TextUtils.isEmpty(rDialog.getEditText().getText().toString())) {
//                            Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        params.put("situation", rDialog.getEditText().getText().toString());
//                        stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("rule/netbook");
//                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, stringBuffer.toString(), new JSONObject(params), new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    if (response.getInt("code") == 200) {
//                                        Toast.makeText(context, "备注成功", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
//                            }
//                        }) {
//                            @Override
//                            public Map<String, String> getHeaders() throws AuthFailureError {
//                                HashMap<String, String> headers = new HashMap<String, String>();
//                                headers.put("token", MainApplication.TOKEN);
//                                return headers;
//                            }
//                        };
//                        requestQueue.add(jsonObjectRequest);
//                    }
//                });
            }
        });
        viewHolder.takePhotoIV.setOnClickListener(listener);
        viewHolder.readTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ReadDialog readDialog = new ReadDialog(context, String.valueOf(taskTheme.getTaskThemeId()), String.valueOf(taskTheme.getThemeId()));
//                readDialog.show();
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public TextView nameTV;
        public ImageView jianIV;
        public ImageView faIV;
        public ImageView remarkIV;
        public ImageView takePhotoIV;
        public TextView readTV;
        public TextView bufuheTV;
        public CheckBox checkBox;
    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (onViewClickListener != null) {
                onViewClickListener.onViewClick(v, (Integer) v.getTag());
            }

        }
    };

    private OnViewClickListener onViewClickListener;

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    public interface OnViewClickListener {
        void onViewClick(View view, int position);
    }
}

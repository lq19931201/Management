package com.du.management.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.du.management.MainApplication;
import com.du.management.R;
import com.du.management.adapter.NewThirdAdapter;
import com.du.management.adapter.NewThirdDetailAdapter;
import com.du.management.http.HeaderStringRequest;
import com.du.management.http.HttpConstant;
import com.du.management.newBean.Jcxm;
import com.du.management.view.MyListView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewSecondActivity extends BaseActivity {

    private LinearLayout backIV;

    private LinearLayout bottomLV;

    private TextView firstTV;

    private TextView secondTV;

    private boolean isComplete;

    private List<Jcxm> thirdList = new ArrayList<>();

    private int thirdFirst = 0;

    private int thirdSecond = 0;

    private int thirdThird = 0;

    public static int thirdForth = -1;

    private long mobanId;

    private long xiangmuId;

    private ListView myListView;

    private NewThirdAdapter newThirdDetailAdapter;

    @Override
    protected int initLayoutId() {
        MIUISetStatusBarLightMode(this, true);
        return R.layout.activity_newsecond;
    }

    private void requestPremission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int read = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int write = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int camera = checkSelfPermission(Manifest.permission.CAMERA);
            if (write != PackageManager.PERMISSION_GRANTED || read != PackageManager.PERMISSION_GRANTED || camera != PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                        1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean refuse = false;
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    refuse = true;
                }
            }
        }
        if (refuse) {
            Toast.makeText(NewSecondActivity.this, "拒绝申请会导致下载功能无法正常使用哦", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initView() {
        requestPremission();
        bottomLV = (LinearLayout) findViewById(R.id.bottom);
        backIV = (LinearLayout) findViewById(R.id.back);
        firstTV = (TextView) findViewById(R.id.firstTV);
        secondTV = (TextView) findViewById(R.id.secondTV);
        myListView = (ListView) findViewById(R.id.third_detail_listview);
    }

    @Override
    protected void initData() {
        isComplete = getIntent().getBooleanExtra("isComplete", false);
        mobanId = getIntent().getLongExtra("mobanId", 1);
        xiangmuId = getIntent().getLongExtra("xiangmuId", 1);
        if (isComplete) {
            bottomLV.setVisibility(View.GONE);
        }
        requestThirdList(mobanId, xiangmuId);
    }

    private void requestThirdList(long mobanId, long xiangmuId) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("jianchazhicheng/getJianchashishiMoban/").append(MainApplication.userId).append("/" + mobanId).append("/" + xiangmuId);
        HeaderStringRequest request = new HeaderStringRequest(Request.Method.GET, stringBuffer.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("code").equals(HttpConstant.CODE_SUCCESS)) {
                        Gson gson = new Gson();
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            thirdList.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), Jcxm.class));
                        }
                        firstTV.setText(getIntent().getStringExtra("title"));
                        secondTV.setText(thirdList.get(thirdFirst).getJcxmName() + "/" + thirdList.get(thirdFirst).getJcnrlist().get(thirdSecond).getJcnrName());
                        newThirdDetailAdapter = new NewThirdAdapter(NewSecondActivity.this, thirdList.get(thirdFirst).getJcnrlist().get(thirdSecond).getJcnrfjlist());
                        myListView.setAdapter(newThirdDetailAdapter);
//                        thirdListAdapter = new ThirdListAdapter(SecondActivity.this, thirdList);
//                        thirdListView.setAdapter(thirdListAdapter);
//                        if (thirdList != null && thirdList.size() > 0) {
//                            final List<Jczb> thirdDetaiList = new ArrayList<>();
//                            thirdDetaiList.addAll(thirdList.get(ThirdCurrentPosition).getJczblist());
//                            compare(thirdList, thirdDetaiList);
//                            thirdDetailAdapter = new ThirdDetailAdapter(SecondActivity.this, thirdDetaiList, isComplete);
//                            thirdDetailListView.setAdapter(thirdDetailAdapter);
//                            thirdDetailAdapter.setOnViewClickListener(new ThirdDetailAdapter.OnViewClickListener() {
//                                @Override
//                                public void onViewClick(View view, int position) {
//                                    openCamera(thirdDetaiList.get(position));
//                                }
//                            });
//                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewSecondActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    @Override
    protected void onclick() {
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

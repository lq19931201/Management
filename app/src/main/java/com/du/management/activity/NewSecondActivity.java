package com.du.management.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.TestJsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.du.management.MainApplication;
import com.du.management.R;
import com.du.management.adapter.NewThirdAdapter;
import com.du.management.bean.PushBean;
import com.du.management.http.HeaderStringRequest;
import com.du.management.http.HttpConstant;
import com.du.management.newBean.Jcnr;
import com.du.management.newBean.Jcnrfj;
import com.du.management.newBean.Jcxm;
import com.du.management.newBean.Jczb;
import com.du.management.view.MyListView;
import com.du.management.view.SecondTitleDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewSecondActivity extends BaseActivity {

    private LinearLayout bottomLV;

    private TextView firstTV;

    private TextView secondTV;

    private boolean isComplete;

    private List<Jcnr> jcnrList = new ArrayList<>();

    private int thirdSecond = 0;

    private long mobanId;

    public static long xiangmuId;

    private MyListView myListView;

    private TextView prevTV;

    private TextView nextTV;

    private NewThirdAdapter newThirdAdapter;

    private long jczbId;

    private long renwuId;

    @Override
    protected int initLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
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
        bottomLV = findViewById(R.id.bottom);
        firstTV = findViewById(R.id.firstTV);
        secondTV = findViewById(R.id.secondTV);
        myListView = findViewById(R.id.third_detail_listview);
        prevTV = findViewById(R.id.prev);
        nextTV = findViewById(R.id.next);
    }

    @Override
    protected void initData() {
        isComplete = getIntent().getBooleanExtra("isComplete", false);
        mobanId = getIntent().getLongExtra("mobanId", 1);
        renwuId = getIntent().getLongExtra("taskId", 0);
        xiangmuId = getIntent().getLongExtra("xiangmuId", 1);
        if (isComplete) {
            bottomLV.setVisibility(View.GONE);
        }
        firstTV.setText(getIntent().getStringExtra("title"));
        requestThirdList(mobanId, xiangmuId);
    }

    private void requestThirdList(long mobanId, long xiangmuId) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("jianchazhicheng/getJianchashishiMoban/").append(MainApplication.userId).append("/" + mobanId).append("/" + xiangmuId);
        HeaderStringRequest request = new HeaderStringRequest(Request.Method.GET, stringBuffer.toString(), response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("code").equals(HttpConstant.CODE_SUCCESS)) {
                    Gson gson = new Gson();
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    List<Jcxm> thirdList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        thirdList.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), Jcxm.class));
                    }
                    for (int i = 0; i < thirdList.size(); i++) {
                        Log.w("lqlqlq", response);
                        for (int j = 0; j < thirdList.get(i).getJcnrlist().size(); j++) {
                            thirdList.get(i).getJcnrlist().get(j).setXiangmuId(thirdList.get(i).getJcxmId());
                            thirdList.get(i).getJcnrlist().get(j).setJcxmName((i + 1) + "、" + thirdList.get(i).getJcxmName() + "/" + (j + 1) + "、");
                        }
                        jcnrList.addAll(thirdList.get(i).getJcnrlist());
                    }
                    secondTV.setText(jcnrList.get(0).getJcxmName() + jcnrList.get(0).getJcnrName());
                    newThirdAdapter = new NewThirdAdapter(NewSecondActivity.this, jcnrList.get(0).getJcnrfjlist());
                    myListView.setAdapter(newThirdAdapter);
                    if (thirdList.get(0).getJcnrlist().size() == 1 && thirdList.size() == 1) {
                        nextTV.setText("提交");
                    }
                    newThirdAdapter.setCameraOnClick(new NewThirdAdapter.CameraOnClick() {
                        @Override
                        public void onClick(int jcnrfjPosition, int position) {
                            jczbId = jcnrList.get(thirdSecond).getJcnrfjlist().get(jcnrfjPosition).getJczblist().get(position).getJczbId();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, error -> Toast.makeText(NewSecondActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }

    @Override
    protected void onclick() {
        findViewById(R.id.back).setOnClickListener(v -> finish());
        nextTV.setOnClickListener(v -> {
            if (jcnrList.size() == 0) {
                Toast.makeText(NewSecondActivity.this, "当前无有效列表", Toast.LENGTH_SHORT).show();
                return;
            }
            if (nextTV.getText().equals("提交")) {
                saveRequest(jcnrList.get(jcnrList.size() - 1).getJcnrfjlist());
                RequestQueue requestQueue = Volley.newRequestQueue(NewSecondActivity.this);
                StringBuffer stringBuffer = new StringBuffer();
                Map<String, String> params = new HashMap<>();
                stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("jianchazhicheng/finishedShishixiangmu/").append(xiangmuId);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, stringBuffer.toString(), new JSONObject(params), response -> {
                    try {
                        String code = response.getString("code");
                        if (code.equals(HttpConstant.CODE_SUCCESS)) {
                            Toast.makeText(NewSecondActivity.this, "任务已完成", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }, error -> Toast.makeText(NewSecondActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show());
                requestQueue.add(jsonObjectRequest);
            } else {
                saveRequest(jcnrList.get(thirdSecond).getJcnrfjlist());
                if (jcnrList.size() - 1 == thirdSecond) {
                    nextTV.setText("提交");
                } else {
                    thirdSecond++;
                }
                if (jcnrList.size() - 1 == thirdSecond) {
                    nextTV.setText("提交");
                }
                newThirdAdapter.setList(jcnrList.get(thirdSecond).getJcnrfjlist());
                newThirdAdapter.notifyDataSetChanged();
            }
            secondTV.setText(jcnrList.get(thirdSecond).getJcxmName() + jcnrList.get(thirdSecond).getJcnrName());
        });
        prevTV.setOnClickListener(v -> {
            if (jcnrList.size() == 0) {
                Toast.makeText(NewSecondActivity.this, "当前无有效列表", Toast.LENGTH_SHORT).show();
                return;
            }
            if (thirdSecond == 0) {
                Toast.makeText(NewSecondActivity.this, "已经是第一个项了", Toast.LENGTH_SHORT).show();
                return;
            } else {
                thirdSecond--;
                nextTV.setText("下一項");
            }
            newThirdAdapter.setList(jcnrList.get(thirdSecond).getJcnrfjlist());
            newThirdAdapter.notifyDataSetChanged();
            secondTV.setText(jcnrList.get(thirdSecond).getJcxmName() + jcnrList.get(thirdSecond).getJcnrName());
        });
        secondTV.setOnClickListener(v -> {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < jcnrList.size(); i++) {
                list.add(jcnrList.get(i).getJcxmName() + jcnrList.get(i).getJcnrName());
            }
            final SecondTitleDialog dialog = new SecondTitleDialog(NewSecondActivity.this, list);
            dialog.show();
            dialog.setOnItemClickListener(position -> {
                thirdSecond = position;
                if (jcnrList.size() - 1 == thirdSecond) {
                    nextTV.setText("提交");
                } else {
                    nextTV.setText("下一項");
                }
                newThirdAdapter.setList(jcnrList.get(thirdSecond).getJcnrfjlist());
                newThirdAdapter.notifyDataSetChanged();
                secondTV.setText(jcnrList.get(thirdSecond).getJcxmName() + jcnrList.get(thirdSecond).getJcnrName());
                dialog.dismiss();
            });
        });
    }

    private void saveRequest(List<Jcnrfj> list) {
        List<PushBean> saveList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Jcnrfj taskBody = list.get(i);
            for (int k = 0; k < taskBody.getJczblist().size(); k++) {
                Jczb taskTheme = taskBody.getJczblist().get(k);
                PushBean pushBean = new PushBean();
                pushBean.setZbjgId(taskTheme.getZbjgId());
                pushBean.setIsHege(0);
                pushBean.setJcjgJcxmid(xiangmuId);
                pushBean.setJcjgJczbid(taskTheme.getJczbId());
                pushBean.setJianchaqingkuang(taskTheme.getJczcJianchajieguo() == null ? "" : taskTheme.getJczcJianchajieguo().getJianchaqingkuang());
                if (!TextUtils.isEmpty(taskTheme.getJczcJianchajieguo().getOptions())) {
                    pushBean.setOption(taskTheme.getJczcJianchajieguo().getOptions());
                }
                if (taskTheme.getJczcJianchajieguo() != null && !TextUtils.isEmpty(taskTheme.getJczcJianchajieguo().getZhenggaijianyi())) {
                    pushBean.setZhenggaiyijian(taskTheme.getJczcJianchajieguo().getZhenggaijianyi());
                } else {
                    pushBean.setZhenggaiyijian(taskTheme.getZhenggaicuoshi());
                }
                saveList.add(pushBean);
            }
        }
        JSONArray jsonObject = getJson(saveList);
        if (jsonObject == null) {
            Toast.makeText(NewSecondActivity.this, "数据格式出错", Toast.LENGTH_SHORT).show();
            return;
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("jianchazhicheng/addJianchajieguolist");
        TestJsonArrayRequest jsonArrayRequest = new TestJsonArrayRequest(Request.Method.POST, stringBuffer.toString(), jsonObject, response -> Toast.makeText(NewSecondActivity.this, "保存成功", Toast.LENGTH_SHORT).show(), error -> Toast.makeText(NewSecondActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonArrayRequest);
    }

    private JSONArray getJson(List<PushBean> list) {
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject tmpObj = null;
            for (int i = 0; i < list.size(); i++) {
                tmpObj = new JSONObject();
                tmpObj.put("isHege", list.get(i).getIsHege());
                tmpObj.put("jcjgId", list.get(i).getJcjgId());
                tmpObj.put("jcjgJcxmid", list.get(i).getJcjgJcxmid());
                tmpObj.put("jcjgJczbid", list.get(i).getJcjgJczbid());
                tmpObj.put("jianchaqingkuang", list.get(i).getJianchaqingkuang());
                tmpObj.put("zhenggaijianyi", list.get(i).getZhenggaiyijian());
                tmpObj.put("zbjgId", list.get(i).getZbjgId());
                tmpObj.put("userId", MainApplication.userId);

                if (list.get(i).getOption() != null && !TextUtils.isEmpty(list.get(i).getOption())) {
                    tmpObj.put("options", list.get(i).getOption());
                }

                jsonArray.put(tmpObj);
            }
        } catch (Exception e) {
            Log.w("lqlqlq", e.getMessage());
        }
        Log.w("lqlqlq", jsonArray.toString());
        return jsonArray;
    }
}

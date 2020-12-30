package com.du.management.activity;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.du.management.R;
import com.du.management.adapter.DanweiAdapter;
import com.du.management.newBean.DanweiBean;
import com.du.management.http.HeaderStringRequest;
import com.du.management.newBean.JczcField;
import com.du.management.newBean.UnitInformations;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DanweiActivity extends BaseActivity {

    private DanweiBean danweiBean;
    private long xiangmuId;
    private TextView chakan;
    private TextView baocun;
    private EditText city;
    private EditText address;
    private EditText location;
    private EditText creditCode;
    private EditText phone;
    private EditText userName;
    private EditText type;
    private ListView listView;
    HashMap<Integer, String> typeMap = new HashMap<>();
    List<JczcField> jczcFieldList = new ArrayList<>();

    @Override
    protected int initLayoutId() {
        MIUISetStatusBarLightMode(this, true);
        return R.layout.activity_danwei;
    }

    @Override
    protected void initData() {
        xiangmuId = getIntent().getLongExtra("xiangmuId", 0);
        String url = new StringBuffer().append("http://52.130.85.90:1311/usermanagement/app-api/getJczcUnit/").append(xiangmuId).toString();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        HeaderStringRequest request = new HeaderStringRequest(Request.Method.GET, url, response -> {
            try {
                Log.w("DanweiActivity", response);
                JSONObject jsonObject = new JSONObject(response);
                Gson gson = new Gson();
                danweiBean = gson.fromJson(jsonObject.getJSONObject("data").toString(), DanweiBean.class);
                bindData(danweiBean);
                listJczcUnitType();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "网络请求失败", Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }

    private void bindData(DanweiBean danweiBean) {
        if (!TextUtils.isEmpty(danweiBean.getShishiCity())) {
            city.setText(danweiBean.getShishiCity());
        }
        if (!TextUtils.isEmpty(danweiBean.getShishiAddress())) {
            address.setText(danweiBean.getShishiAddress());
        }
        if (!TextUtils.isEmpty(danweiBean.getShishiAddress())) {
            location.setText(danweiBean.getShishiAddress());
        }
        if (!TextUtils.isEmpty(danweiBean.getLianxifangshi())) {
            phone.setText(danweiBean.getLianxifangshi());
        }
        if (!TextUtils.isEmpty(danweiBean.getLianxiren())) {
            userName.setText(danweiBean.getLianxiren());
        }
        if (!TextUtils.isEmpty(danweiBean.getCreditCode())) {
            creditCode.setText(danweiBean.getCreditCode());
        }
        if (danweiBean.getUnitTypeId() != 0) {
            listJczcField(danweiBean.getUnitTypeId());
        }
    }

    private void bindJczcField() {
        if (danweiBean.getUnitInformations() == null || danweiBean.getUnitInformations().size() == 0) {
            return;
        }
        List<UnitInformations> unitList = danweiBean.getUnitInformations();
        for (UnitInformations unitInformations : unitList) {
            for (JczcField jczcField : jczcFieldList) {
                if (unitInformations.getFieldId() == jczcField.getFieldId()) {
                    jczcField.setValue(unitInformations.getInfoValue());
                }
            }
        }
        DanweiAdapter adapter = new DanweiAdapter(this, jczcFieldList);
        listView.setAdapter(adapter);
    }

    private void listJczcField(long unitTypeId) {
        String url = new StringBuffer().append("http://52.130.85.90:1311/usermanagement/app-api/listJczcField/").append(unitTypeId).toString();
        Log.w("DanweiActivity", "listJczcField ->" + url);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        HeaderStringRequest request = new HeaderStringRequest(Request.Method.GET, url, response -> {
            try {
                Log.w("DanweiActivity", "listJczcField ->" + response);
                JSONObject jsonObject = new JSONObject(response);
                Gson gson = new Gson();
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                jczcFieldList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jczcFieldList.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), JczcField.class));
                }
                bindJczcField();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "网络请求失败", Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }

    private void listJczcUnitType() {
        String url = "http://52.130.85.90:1311/usermanagement/app-api/listJczcUnitType/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        HeaderStringRequest request = new HeaderStringRequest(Request.Method.GET, url, response -> {
            try {
                Log.w("DanweiActivity", "listJczcUnitType ->" + response);
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    typeMap.put(jsonArray.getJSONObject(i).getInt("utId"), jsonArray.getJSONObject(i).getString("utName"));
                }
                if (danweiBean.getUnitTypeId() != 0) {
                    type.setText(typeMap.get(danweiBean.getUnitTypeId()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "网络请求失败", Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.title)).setText(getIntent().getStringExtra("name"));
        chakan = findViewById(R.id.chakan);
        baocun = findViewById(R.id.baocun);
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        location = findViewById(R.id.location);
        creditCode = findViewById(R.id.creditCode);
        phone = findViewById(R.id.phone);
        userName = findViewById(R.id.userName);
        type = findViewById(R.id.type);
        listView = findViewById(R.id.listView);
    }

    @Override
    protected void onclick() {
        findViewById(R.id.back).setOnClickListener(view -> {
            finish();
        });
    }
}

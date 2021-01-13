package com.du.management.activity;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.du.management.http.HttpConstant;
import com.du.management.utils.SpUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {

    private EditText userNameET;

    private EditText passWordET;

    private TextView loginTV;

    @Override
    protected void initData() {
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(userNameET.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passWordET.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginTV.setEnabled(false);
                Map<String, String> params = new HashMap<>();
                params.put("username", userNameET.getText().toString());
                params.put("password", passWordET.getText().toString());
                JsonObjectRequest newMissRequest = new JsonObjectRequest(
                        Request.Method.GET, new StringBuffer().append(HttpConstant.REQUSET_BASE_URL).append("jianchauser/login/").append(userNameET.getText().toString()).append("/").append(passWordET.getText().toString()).toString(),
                        new JSONObject(params), response -> {
                            try {
                                Log.w("LoginActivity",response.toString());
                                String code = response.getString("code");
                                if (code.equals(HttpConstant.CODE_SUCCESS)) {
                                    MainApplication.userId = response.getJSONObject("data").getString("userId");
                                    SpUtils.saveSpValue(LoginActivity.this, "username", userNameET.getText().toString());
                                    SpUtils.saveSpValue(LoginActivity.this, "password", passWordET.getText().toString());
                                    MainApplication.userId = response.getJSONObject("data").getString("userId");
    //                                if (!TextUtils.isEmpty(response.getString("data"))) {
    //                                    MainApplication.TOKEN = response.getString("data");
    //                                }
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "请联系管理员", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this, "请联系管理员", Toast.LENGTH_SHORT).show();
                            }
                            loginTV.setEnabled(true);
                        }, error -> {
                    Log.w("LoginActivity",error.getMessage());
                    loginTV.setEnabled(true);
                    Toast.makeText(LoginActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("Content-Type", "application/json; charset=UTF-8");
                        return headers;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(newMissRequest);
            }
        });
    }

    @Override
    protected int initLayoutId() {
        MIUISetStatusBarLightMode(this, true);
        return R.layout.acty_login;
    }

    @Override
    protected void initView() {
        userNameET = findViewById(R.id.userName);
        passWordET =  findViewById(R.id.passWord);
        loginTV = findViewById(R.id.login_button);
        try {
            String userName = SpUtils.getSpValue(this, "username");
            if (!TextUtils.isEmpty(userName)) {
                userNameET.setText(userName);
            }
            String passWord = SpUtils.getSpValue(this, "password");
            if (!TextUtils.isEmpty(passWord)) {
                passWordET.setText(passWord);
            }
        } catch (Exception e) {

        }

    }

    @Override
    protected void onclick() {

    }
}
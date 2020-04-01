package com.du.management.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.du.management.http.HttpConstant;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RemarkDialog extends Dialog {

    private EditText editText;

    private ImageView backIV;

    private ImageView saveIV;

    private Context context;

    public RemarkDialog(Context context, String taskThemeId) {
        super(context, R.style.customDialog);
        this.context = context;
        setContentView(R.layout.dialog_remark);
        editText = (EditText) findViewById(R.id.edit);
        backIV = (ImageView) findViewById(R.id.cancle);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        saveIV = (ImageView) findViewById(R.id.save);
        saveIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getStatus(taskThemeId);
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
                                editText.setText(content);
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

    public EditText getEditText() {
        return editText;
    }

    public void setOnSaveClick(android.view.View.OnClickListener onClickListener) {
        saveIV.setOnClickListener(onClickListener);
    }

    @Override
    public void dismiss() {
        View view = getCurrentFocus();
        if (view instanceof TextView) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        super.dismiss();
    }
}

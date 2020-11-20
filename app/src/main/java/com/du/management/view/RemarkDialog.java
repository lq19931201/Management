package com.du.management.view;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.du.management.R;
import com.du.management.adapter.RemarkAdapter;
import com.du.management.http.HeaderStringRequest;
import com.du.management.http.HttpConstant;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RemarkDialog extends Dialog {

    private EditText editText;

    private ImageView backIV;

    private ImageView saveIV;

    private Context context;

    private ListView listView;

    private List<Data> list = new ArrayList<>();

    private RemarkAdapter remarkAdapter;

    private List<Data> originList = new ArrayList<>();

    public RemarkDialog(Context context, long jczbId) {
        super(context, R.style.customDialog);
        this.context = context;
        setContentView(R.layout.dialog_remark);
        editText = findViewById(R.id.edit);
        backIV = findViewById(R.id.cancle);
        listView = findViewById(R.id.listView);
        backIV.setOnClickListener(v -> dismiss());
        saveIV = findViewById(R.id.save);
        saveIV.setOnClickListener(view -> {
            Log.w("RemarkDialog", editText.getText().toString());
            if (!TextUtils.isEmpty(editText.getText().toString())) {
                send(jczbId);
            }
            dismiss();
            if (onSaveListener != null) {
                onSaveListener.onClick();
            }
        });
        remarkAdapter = new RemarkAdapter(context, list);
        listView.setAdapter(remarkAdapter);
        request(jczbId);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                list.clear();
                if (TextUtils.isEmpty(editText.getText())) {
                    list.addAll(originList);
                } else {
                    for (Data data : originList) {
                        if (data.getPreResult().contains(editText.getText())) {
                            list.add(data);
                        }
                    }
                }
                remarkAdapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            editText.setText(list.get(i).getPreResult());
        });
    }

    /*http://52.130.85.90:1310/jianchazhicheng/saveResultPresupposition/{jczbId}/{preResult}*/
    private void send(long jczbId) {
        String url = new StringBuffer().append(HttpConstant.REQUSET_BASE_URL).append("jianchazhicheng/saveResultPresupposition/").append(jczbId).append("/").append(editText.getText().toString()).toString();
        Log.w("RemarkDialog", url);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        HeaderStringRequest request = new HeaderStringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.w("RemarkDialog send", jsonObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.w("RemarkDialog send", "error");
        });
        requestQueue.add(request);
    }

    private void request(long jczbId) {
        String url = new StringBuffer().append(HttpConstant.REQUSET_BASE_URL).append("jianchazhicheng/getResultPresupposition/").append(jczbId).toString();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        HeaderStringRequest request = new HeaderStringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Gson gson = new Gson();
                Log.w("RemarkDialog", jsonObject.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), Data.class));
                    originList.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), Data.class));
                }
                remarkAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        requestQueue.add(request);
    }

    public EditText getEditText() {
        return editText;
    }

    public void setOnSaveListener(onSaveListener onSaveListener) {
        this.onSaveListener = onSaveListener;
    }

    public onSaveListener onSaveListener;

    public interface onSaveListener {
        public void onClick();
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

    public class Data {
        public long preId;

        public long preJczbid;

        public String preResult;

        public void setPreId(long preId) {
            this.preId = preId;
        }

        public long getPreId() {
            return preId;
        }

        public void setPreJczbid(long preJczbid) {
            this.preJczbid = preJczbid;
        }

        public long getPreJczbid() {
            return preJczbid;
        }

        public void setPreResult(String preResult) {
            this.preResult = preResult;
        }

        public String getPreResult() {
            return preResult;
        }
    }
}

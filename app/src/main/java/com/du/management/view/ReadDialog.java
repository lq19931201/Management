package com.du.management.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.du.management.R;
import com.du.management.adapter.PhotoAdapter;
import com.du.management.http.HeaderStringRequest;
import com.du.management.http.HttpConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReadDialog extends Dialog {

    private Context context;

    private TextView contentTV;

    private RecyclerView recyclerView;

    private String jianchaqingkuang;

    public ReadDialog(Context context, long jczbId, long xiangmuId, String jianchaqingkuang) {
        super(context, R.style.customDialog);
        setContentView(R.layout.dialog_read);
        findViewById(R.id.cancle).setOnClickListener(v -> dismiss());
        this.context = context;
        this.jianchaqingkuang = jianchaqingkuang;
        initView();
        getPhoto(jczbId, xiangmuId);
    }

    private void initView() {
        contentTV = findViewById(R.id.content);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        if (!TextUtils.isEmpty(jianchaqingkuang)) {
            contentTV.setText(jianchaqingkuang);
        }
    }

    private void getPhoto(long jczbId, long xiangmuId) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("jianchazhicheng/getJianchaPic/").append(jczbId).append("/" + xiangmuId);

        Log.w("ReadDialog", stringBuffer.toString());
        HeaderStringRequest request = new HeaderStringRequest(Request.Method.GET, stringBuffer.toString(), response -> {
            try {
                Log.w("ReadDialog", response);
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("code").equals(HttpConstant.CODE_SUCCESS)) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    final List<String> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String path = HttpConstant.REQUSET_BASE_URL + jsonArray.getJSONObject(i).getString("savepath");
                        list.add(path.replaceAll("\\\\", "/"));
                    }
                    final PhotoAdapter adapter = new PhotoAdapter(context, list);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener((view, postion) -> {
                        BigPhotoDialog dialog = new BigPhotoDialog(context, list.get(postion));
                        dialog.show();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.w("ReadDialog", error.getMessage());
            Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
        });
        requestQueue.add(request);
    }

}

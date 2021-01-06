package com.du.management.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.du.management.R;
import com.du.management.activity.NewSecondActivity;
import com.du.management.adapter.PhotoAdapter;
import com.du.management.http.HeaderStringRequest;
import com.du.management.http.HttpConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhotoDialog extends Dialog {

    private RecyclerView recyclerView;

    private Context context;

    public PhotoDialog(Context context, long xiangmuId) {
        super(context, R.style.customDialog);
        this.context = context;
        setContentView(R.layout.dialog_photo);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        getPhoto(xiangmuId);
    }

    private void getPhoto(long xiangmuId) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("jianchazhicheng/getDanweiPics/").append(xiangmuId);

        HeaderStringRequest request = new HeaderStringRequest(Request.Method.GET, stringBuffer.toString(), response -> {
            try {
                Log.w("PhotoDialog", response);
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
        }, error -> Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }

}

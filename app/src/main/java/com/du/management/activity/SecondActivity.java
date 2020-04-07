package com.du.management.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
import com.du.management.adapter.SecondAdapter;
import com.du.management.adapter.ThirdDetailAdapter;
import com.du.management.adapter.ThirdListAdapter;
import com.du.management.http.HeaderStringRequest;
import com.du.management.http.HttpConstant;
import com.du.management.newBean.Jcxm;
import com.du.management.newBean.NewContent;
import com.du.management.newBean.ThreeData;
import com.du.management.utils.Utils;
import com.du.management.view.CancleDialog;
import com.du.management.view.MyListView;
import com.du.management.view.SaveDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class SecondActivity extends BaseActivity {

    private TextView titleView;

    private RecyclerView recyclerView;

    private String taskId;

    private String contentId;

    private List<NewContent> secondList = new ArrayList<>();

    private List<ThreeData> thirdList = new ArrayList<>();

    private SecondAdapter secondAdapter;

    public static int SecondCurrentPosition = 0;

    private MyListView thirdListView;

    public static int ThirdCurrentPosition = 0;

    private ThirdListAdapter thirdListAdapter;

    private MyListView thirdDetailListView;

    private ThirdDetailAdapter thirdDetailAdapter;

    private LinearLayout backIV;

    private TextView cancleTV;

    private TextView saveTV;

    private boolean isComplete;

    private LinearLayout bottomLV;

    @Override
    protected int initLayoutId() {
        MIUISetStatusBarLightMode(this, true);
        return R.layout.acty_second;
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

    private boolean refuse;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    refuse = true;
                }
            }
        }
        if (refuse) {
            Toast.makeText(SecondActivity.this, "拒绝申请会导致下载功能无法正常使用哦", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initView() {
        requestPremission();
        bottomLV = (LinearLayout) findViewById(R.id.bottom);
        backIV = (LinearLayout) findViewById(R.id.back);
        titleView = (TextView) findViewById(R.id.title);
        titleView.setText(getIntent().getStringExtra("title"));
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        secondAdapter = new SecondAdapter(SecondActivity.this, secondList);
        thirdListView = (MyListView) findViewById(R.id.third_listview);
        thirdDetailListView = (MyListView) findViewById(R.id.third_detail_listview);
        cancleTV = (TextView) findViewById(R.id.cancle);
        saveTV = (TextView) findViewById(R.id.save);
    }

    @Override
    protected void initData() {
        isComplete = getIntent().getBooleanExtra("isComplete", false);
        if (isComplete) {
            bottomLV.setVisibility(View.GONE);
        }
        taskId = getIntent().getStringExtra("taskId");
        contentId = getIntent().getStringExtra("contentId");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("jianchazhicheng/getJianchashishiRenwu/").append(MainApplication.userId).append("/" + taskId);
        HeaderStringRequest request = new HeaderStringRequest(Request.Method.GET, stringBuffer.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("code").equals(HttpConstant.CODE_SUCCESS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        Gson gson = new Gson();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            secondList.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), NewContent.class));
                        }
                        recyclerView.setAdapter(secondAdapter);
                        requestThirdList(String.valueOf(secondList.get(0).getMobanId()), String.valueOf(secondList.get(0).getXiangmuId()));
                    }
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SecondActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
        secondAdapter.setOnItemClickListener(new SecondAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Log.e("SecondActivity", "postion" + postion);
                SecondCurrentPosition = postion;
                secondAdapter.notifyDataSetChanged();
                thirdList.clear();
                requestThirdList(String.valueOf(secondList.get(postion).getMobanId()), String.valueOf(secondList.get(0).getXiangmuId()));
            }
        });
    }


    private void requestThirdList(String mobanId, String xiangmuId) {
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
                            thirdList.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), ThreeData.class));
                        }
                        thirdListAdapter = new ThirdListAdapter(SecondActivity.this, thirdList);
                        thirdListView.setAdapter(thirdListAdapter);
                        if (thirdList != null && thirdList.size() > 0) {
                            final List<Jcxm> thirdDetaiList = thirdList.get(0).getJcxmlist();
                            compare(thirdList, thirdDetaiList);
                            thirdDetailAdapter = new ThirdDetailAdapter(SecondActivity.this, thirdDetaiList, isComplete);
                            thirdDetailListView.setAdapter(thirdDetailAdapter);
                            thirdDetailAdapter.setOnViewClickListener(new ThirdDetailAdapter.OnViewClickListener() {
                                @Override
                                public void onViewClick(View view, int position) {
                                    openCamera(thirdDetaiList.get(position));
                                }
                            });
                        } else {
                            thirdDetailAdapter = new ThirdDetailAdapter(SecondActivity.this, new ArrayList<Jcxm>(), isComplete);
                            thirdDetailListView.setAdapter(thirdDetailAdapter);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SecondActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    private void compare(List<ThreeData> thirdList, List<Jcxm> thirdDetailList) {
        int size = thirdList.size();
        int detailSize = thirdDetailList.size();
        while (detailSize <= size * 2) {
            Jcxm taskTheme = new Jcxm();
            taskTheme.setJcxmName("lalalallala");
            taskTheme.setAdd(true);
            thirdDetailList.add(taskTheme);
            detailSize += 1;
        }
    }

    @Override
    protected void onclick() {
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                openCamera(thirdList.get(0).getTaskThemeList().get());
            }
        });
        thirdListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ThirdCurrentPosition = position;
//                thirdListAdapter.notifyDataSetChanged();
//                final List<TaskTheme> thirdDetaiList = thirdList.get(position).getTaskThemeList();
//                compare(thirdList, thirdDetaiList);
//                thirdDetailAdapter = new ThirdDetailAdapter(SecondActivity.this, thirdList.get(position).getTaskThemeList(), isComplete);
//                thirdDetailListView.setAdapter(thirdDetailAdapter);
//                thirdDetailAdapter.setOnViewClickListener(new ThirdDetailAdapter.OnViewClickListener() {
//                    @Override
//                    public void onViewClick(View view, int position) {
//                        openCamera(thirdDetaiList.get(position));
//                    }
//                });
            }
        });
        cancleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CancleDialog dialog = new CancleDialog(SecondActivity.this);
                dialog.show();
                dialog.setOnCancleClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setOnSaveClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        saveRequest();
                    }
                });
            }
        });
        saveTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SaveDialog dialog = new SaveDialog(SecondActivity.this);
                dialog.show();
                dialog.setOnCancleClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setOnConfirmClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        saveRequest();
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        SecondCurrentPosition = 0;
        ThirdCurrentPosition = 0;
        super.onDestroy();
    }

    private File tempFile;

    private Uri imageUri;

    private static int PHOTO_REQUEST_CAREMA = 0x12;

    private long jcxmId;

    public void openCamera(Jcxm taskTheme) {
        jcxmId = taskTheme.getJcxmId();
        //获取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Utils.getLocalPath(SecondActivity.this), filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    Toast.makeText(SecondActivity.this, "请开启存储权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ContextCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SecondActivity.this, "请开启相机权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }


    /**
     * 按尺寸压缩图片
     *
     * @param srcPath  图片路径
     * @param desWidth 压缩的图片宽度
     * @return Bitmap 对象
     */

    public static Bitmap compressImageFromFile(String srcPath, float desWidth) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float desHeight = desWidth * h / w;
        int be = 1;
        if (w > h && w > desWidth) {
            be = (int) (newOpts.outWidth / desWidth);
        } else if (w < h && h > desHeight) {
            be = (int) (newOpts.outHeight / desHeight);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率
//        newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;
    }

    public static void saveBitmap(Bitmap mBitmap, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (tempFile.exists()) {
            Log.e("lqlq", "exists");
            Log.e("lqlq", "tempFile.length()" + tempFile.length());
            String filePath = tempFile.getAbsolutePath();
            Bitmap bitmap = compressImageFromFile(filePath, 1024f);
            new File(filePath).delete();
            saveBitmap(bitmap, new File(filePath));
            try {
                File realFile = new File(filePath);
                upload(HttpConstant.REQUSET_BASE_URL + "/jianchazhicheng/upLoadJianchaPic", realFile.getAbsolutePath(), realFile.getName());
            } catch (Exception e) {

            }

        }
    }

    private void upload(String url, String filePath, String fileName) throws Exception {
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("jcjgPicFile", fileName, RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)))
//                .addFormDataPart("jczbId",)
//                .addFormDataPart("xiangmuId",)
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder().header("Authorization", "Client-ID " + UUID.randomUUID()).url(url).post(requestBody).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(SecondActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                Log.e("lqlq", "e===" + e.getMessage().toString());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getInt("code") == 200) {
                        Log.e("lqlq", "jcxmId===" + jcxmId);
                        if (jcxmId != 0) {
                            final RequestQueue requestQueue = Volley.newRequestQueue(SecondActivity.this);
                            final StringBuffer stringBuffer = new StringBuffer();
                            Map<String, String> params = new HashMap<>();
                            params.put("jcxmId", String.valueOf(jcxmId));
                            String imageId = jsonObject.getJSONObject("data").getString("fileId");
                            params.put("imageId", imageId);//上传图片id
//                        params.put("fileId","");//上传文件id
                            stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("rule/netbook/save/image");
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, stringBuffer.toString(), new JSONObject(params), new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.getInt("code") == 200) {
                                            Toast.makeText(SecondActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    jcxmId = 0;
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    jcxmId = 0;
                                    Toast.makeText(SecondActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveRequest() {
//        List<PushBean> saveList = new ArrayList<>();
//        for (int i = 0; i < thirdList.size(); i++) {
//            TaskBody taskBody = thirdList.get(i);
//            for (int k = 0; k < taskBody.getTaskThemeList().size(); k++) {
//                TaskTheme taskTheme = taskBody.getTaskThemeList().get(k);
//                if (taskTheme.getTaskThemeId() == null)
//                    continue;
//                PushBean pushBean = new PushBean();
//                pushBean.setAccord(taskTheme.isAccord());
//                pushBean.setTaskThemeId(taskTheme.getTaskThemeId());
//                saveList.add(pushBean);
//            }
//        }
//        JSONObject jsonObject = getJson(saveList);
//        if (jsonObject == null) {
//            Toast.makeText(SecondActivity.this, "数据格式出错", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("rule/netbook/save/task");
//        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, stringBuffer.toString(), jsonObject,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            int code = response.getInt("code");
//                            if (code == 200) {
//                                Toast.makeText(SecondActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                        } catch (Exception e) {
//
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(SecondActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//
//            @Override
//            public Map<String, String> getHeaders() {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("token", MainApplication.TOKEN);
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//        };
//        requestQueue.add(jsonRequest);

    }

    private JSONObject getJson(List<PushBean> list) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject tmpObj = null;
            for (int i = 0; i < list.size(); i++) {
                tmpObj = new JSONObject();
                tmpObj.put("jcxmId", list.get(i).getTaskThemeId());
                tmpObj.put("accord", list.get(i).isAccord());
                jsonArray.put(tmpObj);
            }
            jsonObject.put("list", jsonArray);
        } catch (Exception e) {

        }
        return jsonObject;
    }

    public class PushBean implements Serializable {
        public long taskThemeId;

        public boolean accord;

        public long getTaskThemeId() {
            return taskThemeId;
        }

        public void setTaskThemeId(long taskThemeId) {
            this.taskThemeId = taskThemeId;
        }

        public boolean isAccord() {
            return accord;
        }

        public void setAccord(boolean accord) {
            this.accord = accord;
        }
    }

}

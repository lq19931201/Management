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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
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
import com.du.management.utils.Utils;
import com.du.management.view.MyListView;
import com.du.management.view.SecondTitleDialog;
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

public class NewSecondActivity extends BaseActivity {

    private LinearLayout bottomLV;

    private TextView firstTV;

    private TextView secondTV;

    private boolean isComplete;

    private List<Jcnr> jcnrList = new ArrayList<>();

    private List<Jcnr> jcnrNewList = new ArrayList<>();

    private int thirdSecond = 0;

    private long mobanId;

    public static long xiangmuId;

    private MyListView myListView;

    private TextView prevTV;

    private TextView nextTV;

    private NewThirdAdapter newThirdAdapter;

    public static File tempFile;

    private Uri imageUri;

    private static int PHOTO_REQUEST_CAREMA = 0x12;

    private static int NEWSECONDTWO = 0x13;

    private long jczbId;

    private boolean titleTakePhoto;

    private long renwuId;

    @Override
    protected int initLayoutId() {
        MIUISetStatusBarLightMode(this, true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
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
        firstTV = (TextView) findViewById(R.id.firstTV);
        secondTV = (TextView) findViewById(R.id.secondTV);
        myListView = (MyListView) findViewById(R.id.third_detail_listview);
        prevTV = (TextView) findViewById(R.id.prev);
        nextTV = (TextView) findViewById(R.id.next);
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
        requestNewThirdList(mobanId, xiangmuId);
    }

    private void requestNewThirdList(long mobanId, long xiangmuId) {
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
                        jcnrNewList.addAll(thirdList.get(i).getJcnrlist());
                    }
                    secondTV.setText(jcnrNewList.get(0).getJcxmName() + jcnrNewList.get(0).getJcnrName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, error -> Toast.makeText(NewSecondActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }

    private void requestThirdList(long mobanId, long xiangmuId) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("jianchazhicheng/getJianchashishiMoban/").append(MainApplication.userId).append("/" + mobanId).append("/" + xiangmuId);
        stringBuffer.append(HttpConstant.REQUSET_BASE_URL).append("jianchazhicheng/listSanjirenwu/").append(MainApplication.userId).append("/" + mobanId).append("/" + xiangmuId);
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
                    newThirdAdapter = new NewThirdAdapter(NewSecondActivity.this, jcnrList.get(0).getJcnrfjlist());
                    myListView.setAdapter(newThirdAdapter);
                    if (thirdList.get(0).getJcnrlist().size() == 1 && thirdList.size() == 1) {
                        nextTV.setText("提交");
                    }
                    newThirdAdapter.setCameraOnClick((jcnrfjPosition, position) -> {
                        titleTakePhoto = false;
                        jczbId = jcnrList.get(thirdSecond).getJcnrfjlist().get(jcnrfjPosition).getJczblist().get(position).getJczbId();
                        openCamera();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, error -> Toast.makeText(NewSecondActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }

    public void openCamera() {
        //获取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Utils.getLocalPath(NewSecondActivity.this), filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(NewSecondActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    Toast.makeText(NewSecondActivity.this, "请开启存储权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ContextCompat.checkSelfPermission(NewSecondActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(NewSecondActivity.this, "请开启相机权限", Toast.LENGTH_SHORT).show();
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
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
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
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        Log.w("lqlqlq", String.valueOf(bitmap.getAllocationByteCount()));
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
        if (requestCode == NEWSECONDTWO) {
            if (data != null) {
                Jcnr jcnr = (Jcnr) data.getSerializableExtra("data");
                if (jcnr != null) {
                    int position = data.getIntExtra("position", 0);
                    jcnrList.set(position, jcnr);
                    newThirdAdapter.setList(jcnrList.get(thirdSecond).getJcnrfjlist());
                    newThirdAdapter.notifyDataSetChanged();
                }
            }
        } else {
            if (tempFile.exists()) {
                Log.e("lqlq", "exists");
                Log.e("lqlq", "tempFile.length()" + tempFile.length());
                String filePath = tempFile.getAbsolutePath();
                Bitmap bitmap = compressImageFromFile(filePath, 1024f);
                new File(filePath).delete();
                saveBitmap(bitmap, new File(filePath));
                try {

                    File realFile = new File(filePath);
                    if (titleTakePhoto) {
                        upload(HttpConstant.REQUSET_BASE_URL + "/jianchazhicheng/upLoadDanweiPic", realFile.getAbsolutePath(), realFile.getName());
                    } else {
                        upload(HttpConstant.REQUSET_BASE_URL + "/jianchazhicheng/upLoadJianchaPic", realFile.getAbsolutePath(), realFile.getName());
                    }
                } catch (Exception e) {

                }
            }
        }

    }

    private void upload(String url, String filePath, String fileName) throws Exception {
        RequestBody requestBody;
        if (titleTakePhoto) {
            requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).
                    addFormDataPart("xiangmuId", String.valueOf(xiangmuId)).
                    addFormDataPart("jcjgPicFile", fileName, RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)))
                    .build();
        } else {
            requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).
                    addFormDataPart("xiangmuId", String.valueOf(xiangmuId)).
                    addFormDataPart("jczbId", String.valueOf(jczbId)).
                    addFormDataPart("jcjgPicFile", fileName, RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)))
                    .build();
        }
        okhttp3.Request request = new okhttp3.Request.Builder().header("Authorization", "Client-ID " + UUID.randomUUID()).url(url).post(requestBody).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.w("upload", "onfail");
                tempFile = null;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewSecondActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Log.w("upload", "onResponse");
                tempFile = null;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewSecondActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
//            secondTV.setText(jcnrList.get(thirdSecond).getJcxmName() + jcnrList.get(thirdSecond).getJcnrName());
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
//            secondTV.setText(jcnrList.get(thirdSecond).getJcxmName() + jcnrList.get(thirdSecond).getJcnrName());
        });
//        ImageView titleIV = (ImageView) findViewById(R.id.take_photo);
//        titleIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final CameraPhotoDialog dialog = new CameraPhotoDialog(NewSecondActivity.this);
//                dialog.show();
//                dialog.setCallBack(new CameraPhotoDialog.CallBack() {
//                    @Override
//                    public void onMessage(int position) {
//                        dialog.dismiss();
//                        if (position == 0) {
//                            titleTakePhoto = true;
//                            openCamera();
//                        } else {
//                            PhotoDialog photoDialog = new PhotoDialog(NewSecondActivity.this);
//                            photoDialog.show();
//                        }
//                    }
//                });
//            }
//        });

        secondTV.setOnClickListener(v -> {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < jcnrNewList.size(); i++) {
                list.add(jcnrNewList.get(i).getJcxmName() + jcnrNewList.get(i).getJcnrName());
            }
            final SecondTitleDialog dialog = new SecondTitleDialog(NewSecondActivity.this, list);
            dialog.show();
            dialog.setOnItemClickListener(position -> {
                dialog.dismiss();
                Intent intent = new Intent(NewSecondActivity.this, NewSecondTwoActivity.class);
                intent.putExtra("title", firstTV.getText().toString());
                intent.putExtra("taskId", renwuId);
                intent.putExtra("mobanId", mobanId);
                intent.putExtra("xiangmuId", xiangmuId);
                intent.putExtra("jcnr", jcnrList.get(position));
                intent.putExtra("position", position);
                startActivityForResult(intent, NEWSECONDTWO);
//                    thirdSecond = position;
//                    if (jcnrList.size() - 1 == thirdSecond) {
//                        nextTV.setText("提交");
//                    } else {
//                        nextTV.setText("下一項");
//                    }
//                    newThirdAdapter.setList(jcnrList.get(thirdSecond).getJcnrfjlist());
//                    newThirdAdapter.notifyDataSetChanged();
//                    secondTV.setText(jcnrList.get(thirdSecond).getJcxmName() + jcnrList.get(thirdSecond).getJcnrName());
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
                pushBean.setIsHege(taskTheme.getJczcJianchajieguo().getIsHege());
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

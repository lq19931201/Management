package com.du.management.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.du.management.R;
import com.du.management.adapter.DanweiAdapter;
import com.du.management.http.HeaderStringRequest;
import com.du.management.http.HttpConstant;
import com.du.management.newBean.DanweiBean;
import com.du.management.newBean.JczcField;
import com.du.management.newBean.UnitInformations;
import com.du.management.utils.GPSUtil;
import com.du.management.utils.Utils;
import com.du.management.view.CameraPhotoDialog;
import com.du.management.view.PhotoDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class DanweiActivity extends BaseActivity {

    private DanweiBean danweiBean;
    private long xiangmuId;
    private TextView baocun;
    private ImageView photo;
    private EditText city;
    private EditText address;
    private EditText location;
    private EditText creditCode;
    private EditText phone;
    private EditText userName;
    private Spinner typeSpinner;
    private ListView listView;
    HashMap<Integer, String> typeMap = new HashMap<>();
    List<JczcField> jczcFieldList = new ArrayList<>();
    private File tempFile;
    private Uri imageUri;
    private static int PHOTO_REQUEST_CAREMA = 0x12;

    @Override
    protected int initLayoutId() {
        MIUISetStatusBarLightMode(this, true);
        return R.layout.activity_danwei;
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
            Toast.makeText(this, "拒绝申请会导致下载功能无法正常使用哦", Toast.LENGTH_SHORT).show();
        }
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
                requestLocation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "网络请求失败", Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }

    private void bindJczcField() {
        List<UnitInformations> unitList = danweiBean.getUnitInformations();
        for (UnitInformations unitInformations : unitList) {
            for (JczcField jczcField : jczcFieldList) {
                if (unitInformations.getFieldId() == jczcField.getFieldId()) {
                    jczcField.setSaveValue(unitInformations.getInfoValue());
                }
            }
        }
        DanweiAdapter adapter = new DanweiAdapter(this, jczcFieldList);
        listView.setAdapter(adapter);
    }

    private void listJczcField(long unitTypeId) {
        String url = new StringBuffer().append("http://52.130.85.90:1311/usermanagement/app-api/listJczcField/").append(unitTypeId).toString();
        Log.w("DanweiActivity", "listJczcField ->" + url);
        jczcFieldList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        HeaderStringRequest request = new HeaderStringRequest(Request.Method.GET, url, response -> {
            jczcFieldList.clear();
            try {
                Log.w("DanweiActivity", "listJczcField ->" + response);
                JSONObject jsonObject = new JSONObject(response);
                Gson gson = new Gson();
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    jczcFieldList.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), JczcField.class));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            bindJczcField();
        }, error -> {
            bindJczcField();
            Toast.makeText(this, "网络请求失败", Toast.LENGTH_SHORT).show();
        });
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
                String[] mItems = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    typeMap.put(jsonArray.getJSONObject(i).getInt("utId"), jsonArray.getJSONObject(i).getString("utName"));
                    mItems[i] = jsonArray.getJSONObject(i).getString("utName");
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mItems);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typeSpinner.setAdapter(adapter);
                if (danweiBean.getUnitTypeId() != 0) {
                    typeSpinner.setSelection((int) danweiBean.getUnitTypeId() - 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "网络请求失败", Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }

    @Override
    protected void initView() {
        requestPremission();
        ((TextView) findViewById(R.id.title)).setText(getIntent().getStringExtra("name"));
        photo = findViewById(R.id.photo);
        baocun = findViewById(R.id.baocun);
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        location = findViewById(R.id.location);
        creditCode = findViewById(R.id.creditCode);
        phone = findViewById(R.id.phone);
        userName = findViewById(R.id.userName);
        typeSpinner = findViewById(R.id.type);
        listView = findViewById(R.id.listView);
    }

    @Override
    protected void onclick() {
        findViewById(R.id.back).setOnClickListener(view -> finish());
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                danweiBean.setUnitTypeId(i + 1);
                listJczcField(i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        baocun.setOnClickListener(v -> {
            danweiBean.setShishiCity(city.getText().toString());
            danweiBean.setShishiAddress(address.getText().toString());
            danweiBean.setLianxifangshi(phone.getText().toString());
            danweiBean.setLianxiren(userName.getText().toString());
            danweiBean.setCreditCode(creditCode.getText().toString());
            if (danweiBean.getUnitInformations() == null || danweiBean.getUnitInformations().size() > 0) {
                danweiBean.unitInformations = new ArrayList<>();
            }
            if (jczcFieldList.size() > 0) {
                if (danweiBean.getUnitInformations() != null && danweiBean.getUnitInformations().size() > 0) {
                    danweiBean.getUnitInformations().clear();
                }
                for (JczcField jczcField : jczcFieldList) {
                    UnitInformations unitInformations1 = new UnitInformations();
                    unitInformations1.setFieldId(jczcField.getFieldId());
                    unitInformations1.setInfoValue(jczcField.getSaveValue());
                    unitInformations1.setInfoType(jczcField.getFieldType());
                    unitInformations1.setUnitId(xiangmuId);
                    unitInformations1.setTypeId(typeSpinner.getSelectedItemId() + 1);
                    danweiBean.getUnitInformations().add(unitInformations1);
                }
            }
            Log.w("DanweiActivity", "save - > " + new Gson().toJson(danweiBean));
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://52.130.85.90:1311/usermanagement/app-api/saveUnit", new JSONObject(new Gson().toJson(danweiBean)), response -> {
                    try {
                        Log.w("DanweiActivity", "save -> response");
                        String code = response.getString("code");
                        if (code.equals(HttpConstant.CODE_SUCCESS)) {
                            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }, error -> Toast.makeText(this, "网络请求失败", Toast.LENGTH_SHORT).show());
                requestQueue.add(jsonObjectRequest);
            } catch (Exception e) {

            }
        });
        photo.setOnClickListener(v -> {
            final CameraPhotoDialog dialog = new CameraPhotoDialog(DanweiActivity.this);
            dialog.show();
            dialog.setCallBack(position -> {
                dialog.dismiss();
                if (position == 0) {
                    openCamera();
                } else {
                    PhotoDialog photoDialog = new PhotoDialog(DanweiActivity.this, xiangmuId);
                    photoDialog.show();
                }
            });
        });
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
            tempFile = new File(Utils.getLocalPath(this), filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    Toast.makeText(this, "请开启存储权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "请开启相机权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
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
                upload(HttpConstant.REQUSET_BASE_URL + "/jianchazhicheng/upLoadDanweiPic", realFile.getAbsolutePath(), realFile.getName());
            } catch (Exception e) {

            }
        }
    }

    public static Bitmap compressImageFromFile(String srcPath, float desWidth) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
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
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
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

    private void upload(String url, String filePath, String fileName) throws Exception {
        RequestBody requestBody;
        requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).
                addFormDataPart("xiangmuId", String.valueOf(xiangmuId)).
                addFormDataPart("jcjgPicFile", fileName, RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)))
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder().header("Authorization", "Client-ID " + UUID.randomUUID()).url(url).post(requestBody).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.w("DanweiActivity", "upload->" + e.getMessage());
                tempFile = null;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DanweiActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DanweiActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.w("DanweiActivity", "upload -> onResponse");
                tempFile = null;
            }
        });
    }

    private void requestLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(true);
        criteria.setCostAllowed(true);
        criteria.setSpeedRequired(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

        // 获取最佳服务对象
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.w("DanweiActivity", "requestLocation == null");
            return;
        }
        locationManager.getLastKnownLocation(provider).getProvider();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, gpsLocationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 10, networkListener);
    }

    private LocationListener networkListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double[] a = GPSUtil.gps84_To_Gcj02(location.getLatitude(), location.getLongitude());
            Log.w("DanweiActivity", "requestLocation networkListener-> " + a[0]);
            Log.w("DanweiActivity", "requestLocation networkListener-> " + a[1]);
            danweiBean.setLocationX(a[1]);
            danweiBean.setLocationY(a[0]);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private LocationListener gpsLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double[] a = GPSUtil.gps84_To_Gcj02(location.getLatitude(), location.getLongitude());
            Log.w("DanweiActivity", "requestLocation gpsLocationListener-> " + a[0]);
            Log.w("DanweiActivity", "requestLocation gpsLocationListener-> " + a[1]);
            danweiBean.setLocationX(a[1]);
            danweiBean.setLocationY(a[0]);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
}

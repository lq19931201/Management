package com.du.management.utils;

import android.content.Context;
import android.os.Environment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class Utils {

    public static String changeTime(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        Date date = new Date(time);
        String RealTime = simpleDateFormat.format(date);
        return RealTime;
    }

    public static String MapToString(Map<String, String> map) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String key : map.keySet()) {
            stringBuffer.append(key).append("=").append(map.get(key)).append("&");
        }
        return stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
    }

    public static String getLocalPath(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return context.getExternalCacheDir().getAbsolutePath();
        } else {
            return context.getFilesDir().getAbsolutePath();
        }
    }

    public static String formatString(String time) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df2.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = null;
        try {
            date = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df2.format(date);
    }
}

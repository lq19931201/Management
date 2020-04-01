package com.du.management.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {

    public static String getSpValue(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");
        return value;
    }

    public static void saveSpValue(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}

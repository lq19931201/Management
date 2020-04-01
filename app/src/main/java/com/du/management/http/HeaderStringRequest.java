package com.du.management.http;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.du.management.MainApplication;

import java.util.HashMap;
import java.util.Map;

public class HeaderStringRequest extends StringRequest {
    public HeaderStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public HeaderStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("token", MainApplication.TOKEN);
        return params;
    }

}



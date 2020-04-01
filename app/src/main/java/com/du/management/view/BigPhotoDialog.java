package com.du.management.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.du.management.R;

import java.util.logging.Handler;

public class BigPhotoDialog extends Dialog {

    private Context context;

    private ImageView view;

    private String url;

    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Glide.with(context).load(url).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            }).into(view);
        }
    };


    public BigPhotoDialog(Context context, String url) {
        super(context, R.style.customDialog);
        setContentView(R.layout.dialog_bigphoto);
        view = findViewById(R.id.view);
        this.url = url;
        this.context = context;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        handler.sendEmptyMessage(0);
    }
}

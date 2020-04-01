package com.du.management.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.du.management.R;

public class BigPhotoActivity extends BaseActivity {

    private ImageView imageView;

    @Override
    protected int initLayoutId() {
        return R.layout.dialog_bigphoto;
    }

    @Override
    protected void initView() {
        imageView = (ImageView) findViewById(R.id.view);
    }

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url))
            Glide.with(this).load(url).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            }).into(imageView);
    }

    @Override
    protected void onclick() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

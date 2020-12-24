package com.du.management.activity;

import android.view.View;
import android.widget.TextView;

import com.du.management.R;

public class DanweiActivity extends BaseActivity {
    @Override
    protected int initLayoutId() {
        MIUISetStatusBarLightMode(this, true);
        return R.layout.activity_danwei;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.title)).setText(getIntent().getStringExtra("name"));
    }

    @Override
    protected void onclick() {
        findViewById(R.id.back).setOnClickListener(view -> {
            finish();
        });
    }
}

package com.du.management.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.du.management.R;
import com.du.management.fragment.CurrentTaskFragment;
import com.du.management.fragment.MineFragment;

public class MainActivity extends BaseActivity {

    private RadioGroup radioGroup;

    private ViewPager viewPager;

    private RadioButton currentRadioButton;

    private RadioButton mineRadioButton;


    @Override
    protected int initLayoutId() {
        MIUISetStatusBarLightMode(this, true);
        return R.layout.activity_main;
    }

    private void requestPremission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int location1 = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int location2 = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (location1 != PackageManager.PERMISSION_GRANTED || location2 != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }
        }
    }

    @Override
    protected void initView() {
        radioGroup = findViewById(R.id.radioGroup);
        viewPager = findViewById(R.id.viewPager);
        currentRadioButton = findViewById(R.id.button_one);
        mineRadioButton = findViewById(R.id.button_three);
    }

    @Override
    protected void initData() {
        requestPremission();
        final CurrentTaskFragment currentTaskFragment = new CurrentTaskFragment();
        final MineFragment mineFragment = new MineFragment();

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return currentTaskFragment;
                    case 1:
                        return mineFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        viewPager.addOnPageChangeListener(new MyPageChangeListener());
        radioGroup.setOnCheckedChangeListener(new MyCheckChangeListener());
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        currentRadioButton.setChecked(true);
    }

    private class MyCheckChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.button_one:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.button_three:
                    viewPager.setCurrentItem(1);
                    break;
            }
        }
    }


    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    currentRadioButton.setChecked(true);
                    break;
                case 1:
                    mineRadioButton.setChecked(true);
                    break;
            }
        }
    }

    @Override
    protected void onclick() {

    }
}

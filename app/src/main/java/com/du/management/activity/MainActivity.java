package com.du.management.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.du.management.R;
import com.du.management.activity.BaseActivity;
import com.du.management.fragment.CompleteTaskFragment;
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

    @Override
    protected void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        currentRadioButton = (RadioButton) findViewById(R.id.button_one);
        mineRadioButton = (RadioButton) findViewById(R.id.button_three);
    }

    @Override
    protected void initData() {
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

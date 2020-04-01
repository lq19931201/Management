package com.du.management.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(container);
        initData();
        return view;
    }

    protected abstract View initView(ViewGroup container);

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected abstract void initData();
}

package com.du.management.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.du.management.R;
import com.du.management.utils.SpUtils;

public class MineFragment extends BaseFragment {

    private TextView userNameTV;

    private TextView unLogin;

    @Override
    protected View initView(ViewGroup container) {
        View view = View.inflate(getActivity(), R.layout.fragment_mine, null);
        unLogin = (TextView) view.findViewById(R.id.unlogin_button);
        userNameTV = (TextView) view.findViewById(R.id.userName);
        return view;
    }

    @Override
    protected void initData() {
        String userName = SpUtils.getSpValue(getActivity(), "username");
        if (!TextUtils.isEmpty(userName)) {
            userNameTV.setText(userName);
        }
        unLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

    }
}

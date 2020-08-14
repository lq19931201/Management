package com.du.management.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.du.management.R;
import com.du.management.newBean.Jcnrfj;
import com.du.management.newBean.Jczb;
import com.du.management.view.MyListView;

import java.util.ArrayList;
import java.util.List;

public class NewThirdAdapter extends BaseAdapter {

    private List<Jcnrfj> list;

    private Context context;

    public NewThirdAdapter(Context context, List<Jcnrfj> jcnrfjList) {
        this.list = jcnrfjList;
        this.context = context;
    }

    public void setList(List<Jcnrfj> list) {
        this.list = list;
    }

    private NewThirdDetailAdapter newThirdDetailAdapter;

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null || convertView.getTag() == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_third, null);
            viewHolder.titleTV = (TextView) convertView.findViewById(R.id.title);
            viewHolder.myListView = (MyListView) convertView.findViewById(R.id.myList);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Jcnrfj jcnrfj = list.get(position);
        viewHolder.titleTV.setText((position + 1) + "、" + jcnrfj.getJcnrfjName());
        loop(jcnrfj.getJczblist());
        newThirdDetailAdapter = new NewThirdDetailAdapter(position, context, jcnrfj.getJczblist());
        viewHolder.myListView.setAdapter(newThirdDetailAdapter);
        newThirdDetailAdapter.notifyDataSetChanged();
        newThirdDetailAdapter.setOnViewClickListener(new NewThirdDetailAdapter.OnViewClickListener() {
            @Override
            public void onViewClick(View view, int jcnrfjPosition, int position) {
                if (cameraOnClick != null) {
                    cameraOnClick.onClick(jcnrfjPosition, position);
                }
            }
        });
        return convertView;
    }

    private void loop(List<Jczb> jczbList) {
        for (Jczb jczb : jczbList) {
            if (jczb.getJczcJianchajieguo() != null && jczb.getJczcJianchajieguo().getJcjgId() > 0) {
                jczb.setAdd(true);
            } else {
                jczb.setAdd(false);
            }
            if (jczb.getJczcZhibiaojieguos() != null && jczb.getJczcZhibiaojieguos().size() > 0) {
                jczb.setZbjgId(jczb.getJczcZhibiaojieguos().get(0).getZbjgId());
            }
        }
        List<Jczb> integerList = new ArrayList<>();
        for (int i = 0; i < jczbList.size(); i++) {
            if (jczbList.get(i).isAdd()) {
                for (int k = 0; k < jczbList.size(); k++) {
                    if (jczbList.get(i).getJczbId() == jczbList.get(k).getZilianjieId()) {
                        integerList.add(jczbList.get(k));
                    }
                }
            }
        }
        jczbList.removeAll(integerList);
    }

    private CameraOnClick cameraOnClick;

    public void setCameraOnClick(CameraOnClick cameraOnClick) {
        this.cameraOnClick = cameraOnClick;
    }

    public interface CameraOnClick {
        void onClick(int jcnrfjPosition, int position);
    }

    public NewThirdDetailAdapter getNewThirdDetailAdapter() {
        return newThirdDetailAdapter;
    }

    private class ViewHolder {
        private TextView titleTV;
        private MyListView myListView;
    }
}

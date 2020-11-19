package com.du.management.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.du.management.R;
import com.du.management.newBean.Jcnrfj;
import com.du.management.newBean.Jczb;
import com.du.management.view.MyListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NewThirdAdapter extends BaseAdapter {

    private List<Jcnrfj> list;

    private Context context;

    private boolean notify = true;

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
        if (parent instanceof MyListView) {
            if (((MyListView) parent).isOnMeasure) {
                return convertView;
            }
        }
        Jcnrfj jcnrfj = list.get(position);
        viewHolder.titleTV.setText((position + 1) + "、" + jcnrfj.getJcnrfjName());
        Log.w("NewThirdAdapter", "getView");
        newThirdDetailAdapter = new NewThirdDetailAdapter(position, context, jcnrfj.getJczblist());
        viewHolder.myListView.setAdapter(newThirdDetailAdapter);
        setListViewHeightBasedOnChildren(viewHolder.myListView);
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

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        Log.w("NewThirdAdapter", "height = " + height);
        params.height = height;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
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

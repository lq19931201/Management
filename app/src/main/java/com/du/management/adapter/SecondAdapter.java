package com.du.management.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.du.management.R;
import com.du.management.activity.SecondActivity;
import com.du.management.bean.Content;
import com.du.management.bean.TaskLevel;
import com.du.management.newBean.NewContent;

import java.util.ArrayList;
import java.util.List;

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.ViewHolder> {

    private List<NewContent> list = new ArrayList<>();

    private Context context;

    private OnItemClickListener onItemClickListener;

    public SecondAdapter(Context context, List<NewContent> data) {
        this.list = data;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_second, null, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!TextUtils.isEmpty(list.get(position).getDanweiName())) {
            holder.textView.setText(list.get(position).getDanweiName());
        }
        if (position == SecondActivity.SecondCurrentPosition) {
            holder.textView.setTextColor(context.getResources().getColor(R.color.second));
        } else {
            holder.textView.setTextColor(context.getResources().getColor(R.color.six_three));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;

        private OnItemClickListener onclick;

        public ViewHolder(View view, OnItemClickListener onItemClickListener) {
            super(view);
            onclick = onItemClickListener;
            textView = view.findViewById(R.id.text);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onclick != null) {
                onclick.onItemClick(v, getPosition());
            }

        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int postion);
    }
}

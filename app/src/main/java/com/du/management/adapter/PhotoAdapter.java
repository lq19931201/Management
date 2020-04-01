package com.du.management.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.du.management.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private Context context;

    private List<String> list = new ArrayList<>();

    public PhotoAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_photo, null, false);
        return new ViewHolder(view, onItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position) + "?imageView2/1/w/64/h/64").listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(holder.imageView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        private ImageView imageView;

        private OnItemLongClickListener longClickListener;

        public ViewHolder(View view, OnItemLongClickListener longClickListener) {
            super(view);
            this.longClickListener = onItemLongClickListener;
            imageView = view.findViewById(R.id.imageView);
            imageView.setOnLongClickListener(this);
            imageView.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(v, getPosition());
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int postion);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int postion);
    }
}

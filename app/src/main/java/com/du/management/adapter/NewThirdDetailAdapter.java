package com.du.management.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.du.management.R;
import com.du.management.activity.NewSecondActivity;
import com.du.management.newBean.Jczb;
import com.du.management.newBean.JczbNew;
import com.du.management.newBean.jczcJianchajieguo;
import com.du.management.view.ReadDialog;
import com.du.management.view.RemarkDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NewThirdDetailAdapter extends BaseAdapter {
    private Context context;
    private List<Jczb> list;
    private int jcnrfjPosition;
    private boolean loop;

    public NewThirdDetailAdapter(int jcnrfjPosition, Context context, List<Jczb> list) {
        this.jcnrfjPosition = jcnrfjPosition;
        this.context = context;
        this.list = list;
        int k = 0;
        for (Jczb jczb : list) {
            jczb.setPosition(k++);
            if (jczb.getJczcZhibiaojieguos() != null && jczb.getJczcZhibiaojieguos().size() > 0) {
                jczb.setZbjgId(jczb.getJczcZhibiaojieguos().get(0).getZbjgId());
            }
            if (jczb.getJczcJianchajieguo() != null && jczb.getJczcJianchajieguo().getOptions() != null && !TextUtils.isEmpty(jczb.getJczcJianchajieguo().getOptions())) {
                String[] strings = jczb.getJczcJianchajieguo().getOptions().split(",");
                for (JczbNew jczbNew : jczb.getJczblist()) {
                    for (int i = 0; i < strings.length; i++) {
                        if (jczbNew.getJczbId() == Long.valueOf(strings[i].trim())) {
                            jczbNew.setChecked(true);
                        }
                    }
                }
            }
        }
    }

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null || convertView.getTag() == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_thirddetail, null);
            viewHolder.nameTV = convertView.findViewById(R.id.name);
            viewHolder.remarkIV = convertView.findViewById(R.id.remark);
            viewHolder.takePhotoIV = convertView.findViewById(R.id.take_photo);
            viewHolder.readTV = convertView.findViewById(R.id.read);
            viewHolder.operateLV = convertView.findViewById(R.id.operate);
            viewHolder.allLV = convertView.findViewById(R.id.all);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //关键代码
        final Jczb jczb = list.get(position);
        if (loop) {
            if (jczb.isVisible()) {
                viewHolder.allLV.setVisibility(View.VISIBLE);
            } else {
                viewHolder.allLV.setVisibility(View.GONE);
            }
        } else {
            viewHolder.allLV.setVisibility(View.VISIBLE);
        }
        viewHolder.nameTV.setText((position + 1) + "、" + jczb.getJczbName());
        if (jczb.getJczcJianchajieguo() == null) {
            jczb.setJczcJianchajieguo(new jczcJianchajieguo());
        }
        List<String> list = new ArrayList<>();
        if (jczb.getZhibiaotype() == 0) {
            for (com.du.management.newBean.jczcZhibiaojieguos jieguo : jczb.getJczcZhibiaojieguos()) {
                list.add(jieguo.getJianchaqingkuang());
            }
        } else {
            for (JczbNew jczbNew : jczb.getJczblist()) {
                list.add(jczbNew.getJczbName());
            }
        }
        viewHolder.takePhotoIV.setTag(position);
        viewHolder.takePhotoIV.setOnClickListener(listener);
        viewHolder.remarkIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RemarkDialog rDialog = new RemarkDialog(context, jczb.getJczbId());
                if (!TextUtils.isEmpty(jczb.getJczcJianchajieguo().getJianchaqingkuang())) {
                    rDialog.getEditText().setText(jczb.getJczcJianchajieguo().getJianchaqingkuang());
                }
                rDialog.show();
                rDialog.setOnSaveListener(() -> jczb.getJczcJianchajieguo().setJianchaqingkuang(rDialog.getEditText().getText().toString()));
            }
        });
        viewHolder.readTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadDialog readDialog = new ReadDialog(context, jczb.getJczbId(), NewSecondActivity.xiangmuId, jczb.getJczcJianchajieguo().getJianchaqingkuang());
                readDialog.show();
            }
        });
//        viewHolder.zhengIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final RemarkDialog rDialog = new RemarkDialog(context, 1);
//                if (jczb.getJczcJianchajieguo() != null && !TextUtils.isEmpty(jczb.getJczcJianchajieguo().getZhenggaijianyi())) {
//                    rDialog.getEditText().setText(jczb.getJczcJianchajieguo().getZhenggaijianyi());
//                } else if (!TextUtils.isEmpty(jczb.getZhenggaicuoshi())) {
//                    rDialog.getEditText().setText(jczb.getZhenggaicuoshi());
//                }
//                rDialog.show();
//                rDialog.setOnSaveListener(new RemarkDialog.onSaveListener() {
//                    @Override
//                    public void onClick() {
//                        if (jczb.getJczcJianchajieguo() != null) {
//                            jczb.getJczcJianchajieguo().setZhenggaijianyi(rDialog.getEditText().getText().toString());
//                        } else {
//                            jczb.setZhenggaicuoshi(rDialog.getEditText().getText().toString());
//                        }
//                    }
//                });
//            }
//        });
        return convertView;
    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (onViewClickListener != null) {
                onViewClickListener.onViewClick(v, jcnrfjPosition, (Integer) v.getTag());
            }

        }
    };

    private OnViewClickListener onViewClickListener;

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    public interface OnViewClickListener {
        void onViewClick(View view, int jcnrfjPosition, int position);
    }

    public class ViewHolder {
        public TextView nameTV;
        public ImageView remarkIV;
        public ImageView takePhotoIV;
        public TextView readTV;
        public LinearLayout operateLV;
        public LinearLayout allLV;
    }
}

package com.du.management.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.du.management.R;
import com.du.management.activity.NewSecondActivity;
import com.du.management.newBean.Jczb;
import com.du.management.newBean.jczcJianchajieguo;
import com.du.management.view.ReadDialog;
import com.du.management.view.RemarkDialog;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

public class NewThirdDetailAdapter extends BaseAdapter {
    private Context context;
    private List<Jczb> list;
    private int jcnrfjPosition;

    public NewThirdDetailAdapter(int jcnrfjPosition, Context context, List<Jczb> list) {
        this.jcnrfjPosition = jcnrfjPosition;
        this.context = context;
        this.list = list;
        for (Jczb jczb : list) {
            if (jczb.getJczcJianchajieguo() != null && jczb.getJczbId() > 0) {
                jczb.setAdd(true);
            } else {
                jczb.setAdd(false);
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
            viewHolder.nameTV = (TextView) convertView.findViewById(R.id.name);
            viewHolder.chooseBox = (CheckBox) convertView.findViewById(R.id.chooseCheckBox);
            viewHolder.niceSpinner = (NiceSpinner) convertView.findViewById(R.id.niceSpinner);
            viewHolder.jianIV = (ImageView) convertView.findViewById(R.id.jian);
            viewHolder.remarkIV = (ImageView) convertView.findViewById(R.id.remark);
            viewHolder.takePhotoIV = (ImageView) convertView.findViewById(R.id.take_photo);
            viewHolder.readTV = (TextView) convertView.findViewById(R.id.read);
            viewHolder.operateLV = (LinearLayout) convertView.findViewById(R.id.operate);
            viewHolder.allLV = (LinearLayout) convertView.findViewById(R.id.all);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Jczb jczb = list.get(position);
        viewHolder.nameTV.setText(jczb.getJczbName());
        if (jczb.getJczcJianchajieguo() == null) {
            jczb.setJczcJianchajieguo(new jczcJianchajieguo());
        }
        if (jczb.isAdd()) {
            viewHolder.operateLV.setVisibility(View.VISIBLE);
            viewHolder.chooseBox.setChecked(true);
        } else {
            viewHolder.chooseBox.setChecked(false);
            viewHolder.operateLV.setVisibility(View.GONE);
        }
        List<String> list = new ArrayList<>();
        for (com.du.management.newBean.jczcZhibiaojieguos jieguo : jczb.getJczcZhibiaojieguos()) {
            list.add(jieguo.getJianchaqingkuang());
        }
        if (list.size() == 0) {
            viewHolder.niceSpinner.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.niceSpinner.setVisibility(View.VISIBLE);
            viewHolder.niceSpinner.attachDataSource(list);
            viewHolder.niceSpinner.setSelectedIndex(jczb.getJcqkPosition());
            viewHolder.niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    jczb.setJcqkPosition(i);
                    jczb.getJczcJianchajieguo().setJianchaqingkuang(list.get(i));
                }
            });
        }

        viewHolder.allLV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jczb.setAdd(!jczb.isAdd());
                notifyDataSetChanged();
            }
        });
        viewHolder.takePhotoIV.setTag(position);
        viewHolder.takePhotoIV.setOnClickListener(listener);
        viewHolder.remarkIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RemarkDialog rDialog = new RemarkDialog(context, "1");
                if (!TextUtils.isEmpty(jczb.getJczcJianchajieguo().getJianchaqingkuang())) {
                    rDialog.getEditText().setText(jczb.getJczcJianchajieguo().getJianchaqingkuang());
                }
                rDialog.show();
                rDialog.setOnSaveClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rDialog.dismiss();
                        jczb.getJczcJianchajieguo().setJianchaqingkuang(rDialog.getEditText().getText().toString());
                    }
                });
            }
        });
        viewHolder.readTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadDialog readDialog = new ReadDialog(context, jczb.getJczbId(), NewSecondActivity.xiangmuId, jczb.getJczcJianchajieguo().getJianchaqingkuang());
                readDialog.show();
            }
        });
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
        public CheckBox chooseBox;
        public NiceSpinner niceSpinner;
        public ImageView jianIV;
        public ImageView remarkIV;
        public ImageView takePhotoIV;
        public TextView readTV;
        public LinearLayout operateLV;
        public LinearLayout allLV;
    }

}

package com.du.management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.du.management.R;
import com.du.management.activity.NewSecondActivity;
import com.du.management.newBean.Jczb;

import java.util.List;

public class NewThirdDetailAdapter extends BaseAdapter {
    private Context context;
    private List<Jczb> list;

    public NewThirdDetailAdapter(Context context, List<Jczb> list) {
        this.context = context;
        this.list = list;
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
            viewHolder.jianIV = (ImageView) convertView.findViewById(R.id.jian);
            viewHolder.faIV = (ImageView) convertView.findViewById(R.id.fa);
            viewHolder.remarkIV = (ImageView) convertView.findViewById(R.id.remark);
            viewHolder.takePhotoIV = (ImageView) convertView.findViewById(R.id.take_photo);
            viewHolder.readTV = (TextView) convertView.findViewById(R.id.read);
            viewHolder.bufuheTV = (TextView) convertView.findViewById(R.id.bufuhe);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            viewHolder.operateLV = (LinearLayout) convertView.findViewById(R.id.operate);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Jczb jczb = list.get(position);
        viewHolder.nameTV.setText((position + 1) + "." + jczb.getJczbName());
        viewHolder.checkBox.setChecked(jczb.isHege());
        if (NewSecondActivity.thirdForth >= 0) {
            if (NewSecondActivity.thirdForth == position) {
                viewHolder.operateLV.setVisibility(View.VISIBLE);
                viewHolder.chooseBox.setChecked(true);
            } else {
                viewHolder.operateLV.setVisibility(View.GONE);
                viewHolder.chooseBox.setChecked(false);
            }
        } else {
            viewHolder.chooseBox.setChecked(false);
            viewHolder.operateLV.setVisibility(View.GONE);
        }
        viewHolder.chooseBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.chooseBox.isChecked()) {
                    NewSecondActivity.thirdForth = position;
                } else {
                    NewSecondActivity.thirdForth = -1;
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public TextView nameTV;
        public CheckBox chooseBox;
        public ImageView jianIV;
        public ImageView faIV;
        public ImageView remarkIV;
        public ImageView takePhotoIV;
        public TextView readTV;
        public TextView bufuheTV;
        public CheckBox checkBox;
        public LinearLayout operateLV;
    }

}

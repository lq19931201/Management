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
import com.du.management.view.DetailDialog;
import com.du.management.view.ReadDialog;
import com.du.management.view.RemarkDialog;

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
            viewHolder.detailTV = convertView.findViewById(R.id.detail);
            viewHolder.nameTagTV = convertView.findViewById(R.id.nameTag);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //关键代码
        final Jczb jczb = list.get(position);
        viewHolder.nameTV.setText((position + 1) + "、" + jczb.getJczbName());
        if (jczb.getJczcJianchajieguo() == null) {
            jczb.setJczcJianchajieguo(new jczcJianchajieguo());
            jczb.getJczcJianchajieguo().setIsHege(1);
        }
        /*jczb.getJczcJianchajieguo().getIsHege()
         * 0时是异常状态,
         * 2是优秀,
         * 1是正常状态*/
        if (jczb.getJczcJianchajieguo().getIsHege() == 0) {
            viewHolder.nameTV.setTextColor(context.getResources().getColor(R.color.ishege_red));
            viewHolder.operateLV.setVisibility(View.VISIBLE);
            viewHolder.nameTagTV.setText("×");
        } else if (jczb.getJczcJianchajieguo().getIsHege() == 2) {
            viewHolder.nameTV.setTextColor(context.getResources().getColor(R.color.ishege_blue));
            viewHolder.operateLV.setVisibility(View.VISIBLE);
            viewHolder.nameTagTV.setText("√");
        } else if (jczb.getJczcJianchajieguo().getIsHege() == 1) {
            viewHolder.nameTV.setTextColor(context.getResources().getColor(R.color.black));
            viewHolder.operateLV.setVisibility(View.GONE);
            viewHolder.nameTagTV.setText("○");
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
        viewHolder.remarkIV.setOnClickListener(v -> {
            final RemarkDialog rDialog = new RemarkDialog(context, jczb.getJczbId());
            if (!TextUtils.isEmpty(jczb.getJczcJianchajieguo().getJianchaqingkuang())) {
                rDialog.getEditText().setText(jczb.getJczcJianchajieguo().getJianchaqingkuang());
            }
            rDialog.show();
            rDialog.setOnSaveListener(() -> jczb.getJczcJianchajieguo().setJianchaqingkuang(rDialog.getEditText().getText().toString()));
        });
        viewHolder.readTV.setOnClickListener(v -> {
            ReadDialog readDialog = new ReadDialog(context, jczb.getJczbId(), NewSecondActivity.xiangmuId, jczb.getJczcJianchajieguo().getJianchaqingkuang());
            readDialog.show();
        });
        viewHolder.detailTV.setOnClickListener(v -> {
            DetailDialog detailDialog = new DetailDialog(context, jczb.getEligibilityCriteria(), jczb.getJczcJianchajieguo().getZhenggaijianyi(), jczb.getInspectionmethod(), jczb.getJianchayiju(), jczb.getChufayijus());
            detailDialog.show();
        });
        viewHolder.nameTV.setOnClickListener(v -> {
            if (jczb.getJczcJianchajieguo().getIsHege() == 0) {
                jczb.getJczcJianchajieguo().setIsHege(2);
            } else if (jczb.getJczcJianchajieguo().getIsHege() == 2) {
                jczb.getJczcJianchajieguo().setIsHege(1);
            } else if (jczb.getJczcJianchajieguo().getIsHege() == 1) {
                jczb.getJczcJianchajieguo().setIsHege(0);
            }
            notifyDataSetChanged();
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
        public ImageView detailTV;
        public TextView nameTagTV;
    }
}

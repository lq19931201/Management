package com.du.management.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.du.management.newBean.JczbNew;
import com.du.management.newBean.jczcJianchajieguo;
import com.du.management.view.JCfunctionDialog;
import com.du.management.view.LawDialog;
import com.du.management.view.ReadDialog;
import com.du.management.view.RemarkDialog;

import org.angmarch.views.NiceSpinner;

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
            if (jczb.getJczcJianchajieguo() != null && jczb.getJczcJianchajieguo().getJcjgId() > 0) {
                jczb.setAdd(true);
            } else {
                jczb.setAdd(false);
            }
            if (jczb.getJczcZhibiaojieguos() != null && jczb.getJczcZhibiaojieguos().size() > 0) {
                jczb.setZbjgId(jczb.getJczcZhibiaojieguos().get(0).getZbjgId());
            }
            if (jczb.getJczcJianchajieguo() != null && jczb.getJczcJianchajieguo().getOptions() != null && !TextUtils.isEmpty(jczb.getJczcJianchajieguo().getOptions())) {
                Log.w("NewThirdDetailAdapter", jczb.getJczcJianchajieguo().getOptions());
                String[] strings = jczb.getJczcJianchajieguo().getOptions().split(",");
                for (JczbNew jczbNew : jczb.getJczblist()) {
                    Log.w("NewThirdDetailAdapter", "" + jczbNew.getJczbId());
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
            viewHolder.nameTV = (TextView) convertView.findViewById(R.id.name);
            viewHolder.chooseBox = (CheckBox) convertView.findViewById(R.id.chooseCheckBox);
            viewHolder.niceSpinner = (NiceSpinner) convertView.findViewById(R.id.niceSpinner);
            viewHolder.jianIV = (ImageView) convertView.findViewById(R.id.jian);
            viewHolder.remarkIV = (ImageView) convertView.findViewById(R.id.remark);
            viewHolder.takePhotoIV = (ImageView) convertView.findViewById(R.id.take_photo);
            viewHolder.readTV = (TextView) convertView.findViewById(R.id.read);
            viewHolder.operateLV = (LinearLayout) convertView.findViewById(R.id.operate);
            viewHolder.allLV = (LinearLayout) convertView.findViewById(R.id.all);
            viewHolder.zhengIV = (ImageView) convertView.findViewById(R.id.zheng);
            viewHolder.keyboardIV = (ImageView) convertView.findViewById(R.id.keyboard);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.w("NewThirdDetailAdapter", "getView");
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
        if (jczb.isAdd()) {
            viewHolder.operateLV.setVisibility(View.VISIBLE);
            viewHolder.chooseBox.setChecked(true);
        } else {
            viewHolder.chooseBox.setChecked(false);
            viewHolder.operateLV.setVisibility(View.GONE);
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

        if (list.size() == 0) {
            viewHolder.niceSpinner.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.niceSpinner.setVisibility(View.VISIBLE);
            viewHolder.niceSpinner.attachDataSource(list);
            if (jczb.getZhibiaotype() == 0) {
                viewHolder.niceSpinner.setSelectedIndex(jczb.getJcqkPosition());
                viewHolder.niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        jczb.setJcqkPosition(i);
                        jczb.getJczcJianchajieguo().setJianchaqingkuang(list.get(i));
                        jczb.setZbjgId(jczb.getJczcZhibiaojieguos().get(i).getZbjgId());
                    }
                });
            } else {
                viewHolder.niceSpinner.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            showMutilAlertDialog(jczb, jczb.getJczblist());
                        }
                        return true;
                    }
                });
            }
        }

        viewHolder.jianIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LawDialog lawDialog = new LawDialog(context, jczb.getJianchayiju());
                lawDialog.show();
            }
        });

        viewHolder.nameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jczb.setAdd(!jczb.isAdd());
                loop();
                if (onCheckBoxClick != null) {
                    onCheckBoxClick.onClick();
                }
//                notifyDataSetChanged();
            }
        });

        viewHolder.chooseBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jczb.setAdd(!jczb.isAdd());
                loop();
//                notifyDataSetChanged();
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
        viewHolder.zhengIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RemarkDialog rDialog = new RemarkDialog(context, "1");
                if (jczb.getJczcJianchajieguo() != null && !TextUtils.isEmpty(jczb.getJczcJianchajieguo().getZhenggaijianyi())) {
                    rDialog.getEditText().setText(jczb.getJczcJianchajieguo().getZhenggaijianyi());
                } else if (!TextUtils.isEmpty(jczb.getZhenggaicuoshi())) {
                    rDialog.getEditText().setText(jczb.getZhenggaicuoshi());
                }
                rDialog.show();
                rDialog.setOnSaveClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rDialog.dismiss();
                        if (jczb.getJczcJianchajieguo() != null) {
                            jczb.getJczcJianchajieguo().setZhenggaijianyi(rDialog.getEditText().getText().toString());
                        } else {
                            jczb.setZhenggaicuoshi(rDialog.getEditText().getText().toString());
                        }
                    }
                });
            }
        });
        viewHolder.keyboardIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JCfunctionDialog dialog = new JCfunctionDialog(context, jczb.getInspectionmethod());
                dialog.show();
            }
        });
        return convertView;
    }

    AlertDialog alertDialog = null;

    public void showMutilAlertDialog(Jczb jczb, List<JczbNew> list) {
        final String[] items = new String[list.size()];
        boolean checkItems[] = new boolean[list.size()];
        List<Long> options = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            items[i] = list.get(i).getJczbName();
            checkItems[i] = list.get(i).isChecked();
        }

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle("这是多选框");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(items, checkItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                if (options.contains(list.get(i).getJczbId())) {
                    options.remove(list.get(i).getJczbId());
                } else {
                    options.add(list.get(i).getJczbId());
                }
                list.get(i).setChecked(isChecked);
            }
        });
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
                StringBuilder stringBuilder = new StringBuilder();
                for (int k = 0; k < options.size(); k++) {
                    stringBuilder.append(options.get(k)).append(",");
                }
                jczb.getJczcJianchajieguo().setOptions(stringBuilder.toString());
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });

        alertDialog = alertBuilder.create();
        alertDialog.show();
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
        public ImageView zhengIV;
        public LinearLayout operateLV;
        public LinearLayout allLV;
        public ImageView keyboardIV;
    }

    private void loop() {
        loop = true;
        for (Jczb jczb : list) {
            jczb.setVisible(true);
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isAdd()) {
                for (int k = 0; k < list.size(); k++) {
                    if (list.get(i).getJczbId() == list.get(k).getZilianjieId()) {
                        list.get(k).setVisible(false);
                    }
                }
            }
        }
        Collections.sort(list, new Comparator<Jczb>() {
            @Override
            public int compare(Jczb jczb, Jczb t1) {
                return jczb.getPosition() < t1.getPosition() ? -1 : 1;
            }
        });
        Collections.sort(list, new Comparator<Jczb>() {
            @Override
            public int compare(Jczb jczb, Jczb t1) {
                if (jczb.isVisible() ^ t1.isVisible()) {
                    return jczb.isVisible() ? -1 : 1;
                } else {
                    return 0;
                }
            }
        });
    }

    public void setOnCheckBoxClick(NewThirdDetailAdapter.onCheckBoxClick onCheckBoxClick) {
        this.onCheckBoxClick = onCheckBoxClick;
    }

    public onCheckBoxClick onCheckBoxClick;

    public interface onCheckBoxClick {
        void onClick();
    }
}

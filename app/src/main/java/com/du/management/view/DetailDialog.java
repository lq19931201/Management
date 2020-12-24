package com.du.management.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.du.management.R;
import com.du.management.newBean.Chufayijus;
import com.du.management.newBean.Jianchayiju;

import java.util.List;

public class DetailDialog extends Dialog {
    public DetailDialog(Context context, String hegebiaozhun, String zhenggaijianyi, String jianchafangfa, List<Jianchayiju> jianchayijuList, List<Chufayijus> chufayijuses) {
        super(context, R.style.customDialog);
        setContentView(R.layout.dialog_detail);
        if (!TextUtils.isEmpty(hegebiaozhun)) {
            ((TextView) findViewById(R.id.hege)).setText(hegebiaozhun);
        } else {
            ((TextView) findViewById(R.id.hege)).setText("暂无内容");
        }
        if (!TextUtils.isEmpty(zhenggaijianyi)) {
            ((TextView) findViewById(R.id.zhenggaijianyi)).setText(zhenggaijianyi);
        } else {
            ((TextView) findViewById(R.id.zhenggaijianyi)).setText("暂无内容");
        }
        if (!TextUtils.isEmpty(jianchafangfa)) {
            ((TextView) findViewById(R.id.jianchafangfa)).setText(jianchafangfa);
        } else {
            ((TextView) findViewById(R.id.jianchafangfa)).setText("暂无内容");
        }
        if (jianchayijuList != null && jianchayijuList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < jianchayijuList.size(); i++) {
                stringBuilder.append((i + 1) + "." + jianchayijuList.get(i).getContent());
                if (i != jianchayijuList.size() - 1) {
                    stringBuilder.append("\n");
                }
            }
            ((TextView) findViewById(R.id.jianchayiju)).setText(stringBuilder.toString());
        } else {
            ((TextView) findViewById(R.id.jianchayiju)).setText("暂无内容");
        }

        if (chufayijuses != null && chufayijuses.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < chufayijuses.size(); i++) {
                stringBuilder.append((i + 1) + "." + chufayijuses.get(i).getContent());
                if (i != chufayijuses.size() - 1) {
                    stringBuilder.append("\n");
                }
            }
            ((TextView) findViewById(R.id.chufayiju)).setText(stringBuilder.toString());
        } else {
            ((TextView) findViewById(R.id.chufayiju)).setText("暂无内容");
        }
    }
}

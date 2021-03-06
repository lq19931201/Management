package com.du.management.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.du.management.R;
import com.du.management.adapter.LawAdapter;
import com.du.management.bean.TaskThemeBasis;
import com.du.management.newBean.Jianchayiju;

import java.util.List;

public class LawDialog extends Dialog {
    private MyListView listView;

    public LawDialog(Context context, List<Jianchayiju> list) {
        super(context, R.style.customDialog);
        setContentView(R.layout.dialog_law);
        listView = (MyListView) findViewById(R.id.listview);
        if (list != null) {
            LawAdapter adapter = new LawAdapter(context, list);
            listView.setAdapter(adapter);
        }
        findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}

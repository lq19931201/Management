package com.du.management.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.du.management.R;
import com.du.management.adapter.SecondTitleAdapter;

import java.util.List;

public class SecondTitleDialog extends Dialog {

    private ListView listView;

    private CallBack callBack;

    public SecondTitleDialog(Context context, List<String> list) {
        super(context, R.style.customDialog);
        setContentView(R.layout.dialog_secondtitle);
        listView = findViewById(R.id.listView);
        SecondTitleAdapter adapter = new SecondTitleAdapter(context, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (callBack != null) {
                    callBack.onItemClick(i);
                }
            }
        });
    }

    public void setOnItemClickListener(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void onItemClick(int position);
    }
}

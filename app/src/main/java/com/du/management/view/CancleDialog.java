package com.du.management.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.du.management.R;

public class CancleDialog extends Dialog {

    private TextView cancleTV;

    private TextView saveTV;

    public CancleDialog(Context context) {
        super(context, R.style.customDialog);
        setContentView(R.layout.dialog_cancle);

        cancleTV = (TextView) findViewById(R.id.cancle);
        cancleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        saveTV = (TextView) findViewById(R.id.save);
        saveTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setOnCancleClick(android.view.View.OnClickListener onClickListener) {
        cancleTV.setOnClickListener(onClickListener);
    }

    public void setOnSaveClick(android.view.View.OnClickListener onClickListener) {
        saveTV.setOnClickListener(onClickListener);
    }
}

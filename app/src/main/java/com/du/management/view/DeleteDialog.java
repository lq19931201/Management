package com.du.management.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.du.management.R;

public class DeleteDialog extends Dialog {

    private TextView cancleTV;

    private TextView confirmTV;

    public DeleteDialog(Context context) {
        super(context, R.style.customDialog);
        setContentView(R.layout.dialog_delete);
        cancleTV = (TextView) findViewById(R.id.cancle);
        confirmTV = (TextView) findViewById(R.id.confirm);
        cancleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirmTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setOnCancleClick(android.view.View.OnClickListener onClickListener) {
        cancleTV.setOnClickListener(onClickListener);
    }

    public void setOnConfirmClick(android.view.View.OnClickListener onClickListener) {
        confirmTV.setOnClickListener(onClickListener);
    }
}

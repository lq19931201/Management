package com.du.management.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.du.management.R;

public class CommitDialog extends Dialog{
    private TextView cancleTV;

    private TextView confirmTV;

    public CommitDialog(Context context) {
        super(context, R.style.customDialog);
        setContentView(R.layout.dialog_commit);
        cancleTV = (TextView) findViewById(R.id.cancle);
        cancleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        confirmTV = (TextView) findViewById(R.id.confirm);
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

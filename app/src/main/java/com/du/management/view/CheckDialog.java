package com.du.management.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.du.management.R;

public class CheckDialog extends Dialog {

    private TextView methodTV;

    private TextView elementTV;

    private TextView contentTV;

    private ImageView cancleIV;

    public CheckDialog(final Context context, final String method, final String element) {
        super(context, R.style.customDialog);
        setContentView(R.layout.dialog_check);
        methodTV = (TextView) findViewById(R.id.method);
        elementTV = (TextView) findViewById(R.id.element);
        cancleIV = (ImageView) findViewById(R.id.cancle);
        contentTV = (TextView) findViewById(R.id.content);
        contentTV.setText(method);
        methodTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodTV.setBackground(context.getResources().getDrawable(R.drawable.check_checked));
                elementTV.setBackground(context.getResources().getDrawable(R.drawable.check_unchecked));
                contentTV.setText(method);
            }
        });
        elementTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodTV.setBackground(context.getResources().getDrawable(R.drawable.check_unchecked));
                elementTV.setBackground(context.getResources().getDrawable(R.drawable.check_checked));
                contentTV.setText(element);
            }
        });
        cancleIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}

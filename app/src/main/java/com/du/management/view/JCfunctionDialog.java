package com.du.management.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.du.management.R;

public class JCfunctionDialog extends Dialog {
    public JCfunctionDialog(Context context, String content) {
        super(context, R.style.customDialog);
        setContentView(R.layout.dialog_jcff);
        ((TextView)findViewById(R.id.content)).setText(content);
        findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}

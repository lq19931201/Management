package com.du.management.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.du.management.R;

public class CameraPhotoDialog extends Dialog {

    private CallBack callBack;

    public CameraPhotoDialog(Context context) {
        super(context, R.style.customDialog);
        setContentView(R.layout.dialog_camera_photo);
        TextView camera = findViewById(R.id.camera);
        TextView photo = findViewById(R.id.photo);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null) {
                    callBack.onMessage(0);
                }
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null) {
                    callBack.onMessage(1);
                }
            }
        });
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        public void onMessage(int position);
    }
}

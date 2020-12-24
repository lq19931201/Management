package com.du.management.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class NewMyListView extends ListView {
    public NewMyListView(Context context) {
        super(context);
    }

    public NewMyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewMyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        //AT_MOST(表示子控件的高度能扩展多高就扩展多高，但要小于给出的size)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

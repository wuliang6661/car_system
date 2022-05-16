package com.hlbw.car_system.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;

public class SuperExpandableListView extends ExpandableListView {

    public SuperExpandableListView(Context context) {
        super(context);
    }

    public SuperExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

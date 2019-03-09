package com.shoyu666.module.ui.circle;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zebra.module.ui.circle.R;

/**
 * @author xining
 * @date 2019/3/1
 */
public class CycleView extends RelativeLayout {
    public InstrumentView mInstrumentView;
    public TextView centerText;

    public CycleView(Context context) {
        super(context);
        init(null);
    }

    public CycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CycleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        int layout = R.layout.speedview;
        if (attrs != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.CycleView);
            layout = obtainStyledAttributes.getResourceId(R.styleable.CycleView_Cycle_Layout, R.layout.speedview);
            obtainStyledAttributes.recycle();
        }
        View ui = LayoutInflater.from(getContext()).inflate(layout, this, true);
        mInstrumentView = ui.findViewById(R.id.InstrumentView);
        centerText = ui.findViewById(R.id.centerText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = View.MeasureSpec.getSize(widthMeasureSpec);
        int h = View.MeasureSpec.getSize(heightMeasureSpec);
        int tempSpec;
        if (w < h) {
            tempSpec = widthMeasureSpec;
        } else {
            tempSpec = heightMeasureSpec;
        }
        super.onMeasure(tempSpec, tempSpec);
    }

    public CycleView setOutProcess(float process) {
        mInstrumentView.setOutProcess(process);
        return this;
    }

    public CycleView setInnerProcess(float process) {
        mInstrumentView.setInProcess(process);
        return this;
    }

    public void commit() {
        mInstrumentView.commit();
    }

    public void setCenterText(SpannableString spannableString) {
        centerText.setText(spannableString);
    }
}

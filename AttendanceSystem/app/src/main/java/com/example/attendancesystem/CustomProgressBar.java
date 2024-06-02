package com.example.attendancesystem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class CustomProgressBar extends ProgressBar {

    private Paint mProgressPaint;

    public CustomProgressBar(Context context) {
        super(context);
        init();
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mProgressPaint = new Paint();
        mProgressPaint.setColor(getResources().getColor(R.color.blue));
        mProgressPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float progressRatio = (float) getProgress() / getMax();
        float progressBarWidth = getWidth() * progressRatio;

        canvas.drawRect(0, 0, progressBarWidth, getHeight(), mProgressPaint);
    }
}


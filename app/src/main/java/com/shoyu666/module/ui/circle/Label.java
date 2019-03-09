package com.shoyu666.module.ui.circle;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.text.SpannableString;
import android.text.TextPaint;
/**
 * @author xining
 * @date 2019/3/1
 */
public class Label {
    public RectF rect;
    public SpannableString lab;
    public int angel;
    public InstrumentView view;

    public Label(SpannableString lab, int angel, InstrumentView view) {
        this.lab = lab;
        this.angel = angel;
        this.view = view;
    }

    public void draw(RectF lableCycle, Canvas canvas, TextPaint labelPaint) {
        float textSize = labelPaint.getTextSize();
        update(lableCycle, textSize);
//        PaintHelper.debug(canvas,rect, Color.RED);
        PaintHelper.draw(canvas, lab, labelPaint, rect, -textSize, true);
    }

    private void update(RectF lableCycle, float textSize) {
        if (rect == null) {
            rect = new RectF();
            float x = CycleMath.getX(lableCycle, angel);
            float y = CycleMath.getY(lableCycle, angel);
            float offset = textSize * 4;
            rect.left = x - offset;
            rect.right = x + offset;
            rect.top = y - offset;
            rect.bottom = y + offset+20;
        }
    }
}

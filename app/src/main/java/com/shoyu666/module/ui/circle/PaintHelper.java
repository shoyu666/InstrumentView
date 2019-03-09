package com.shoyu666.module.ui.circle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.text.SpannableString;
import android.text.StaticLayout;
import android.text.TextPaint;

/**
 * @author xining
 * @date 2019/3/1
 */
public class PaintHelper {

    public static StaticLayout getStaticLayout(SpannableString text, TextPaint mPaint, int measuredWidth) {
        StaticLayout layout = new StaticLayout(text, mPaint, measuredWidth, Layout.Alignment.ALIGN_NORMAL, 1.2F, 0.0F, true);
        return layout;
    }

    public static TextPaint getTextPaintByTextSize(float textSize,int labelTextColor) {
        TextPaint mPaint = new TextPaint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(labelTextColor);
        mPaint.setTextSize(textSize);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setFakeBoldText(false);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setTextAlign(Paint.Align.CENTER);
        return mPaint;
    }

    public static StaticLayout draw(Canvas canvas, SpannableString subTitle, TextPaint paint, RectF rectF, float dy, boolean debug) {
        if (canvas == null) {
            return null;
        }
        try {
            canvas.save();
            StaticLayout layout = PaintHelper.getStaticLayout(subTitle, paint, (int) rectF.width());
            canvas.translate(rectF.centerX(), rectF.centerY() + dy);
            layout.draw(canvas);
            if (canvas.getSaveCount() > 0) {
                canvas.restore();
            }
            return layout;
        } catch (Exception e) {
        }
        return null;
    }

    public static void debug(Canvas canvas, RectF rectF,int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(MDP_PX.dip2px(2));
        canvas.drawRect(rectF, paint);
    }

    public static void debugCycle(Canvas canvas, RectF rectF,int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(MDP_PX.dip2px(2));
        canvas.drawCircle(rectF.centerX(),rectF.centerY(),rectF.width()/2, paint);
    }
}

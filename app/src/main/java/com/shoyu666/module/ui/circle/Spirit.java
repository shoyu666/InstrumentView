package com.shoyu666.module.ui.circle;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;
/**
 * @author xining
 * @date 2019/3/1
 */
public class Spirit {
    public static final String TAG = "Spirit";
    public InstrumentView stage;

    public Spirit(InstrumentView stage) {
        this.stage = stage;
    }

    public PointF OutA = new PointF();
    public PointF OutB = new PointF();
    public PointF OutCenter = new PointF();
    public PointF InA = new PointF();
    public PointF InB = new PointF();
    public PointF InCenter = new PointF();
    public float startAngle;
    public float enterAngle;
    public float endAngle;
    //
    public float startPercent;
    public float endPercent;
    public Path path = new Path();

    LinearGradient radialGradient = null;
    LinearGradient dyPecentRadialGradient = null;

    public void drawBackground(Canvas canvas, Paint paint, int i, RectF outCycle, RectF innerCycle) {
        updata(i, stage.spiritAngle, outCycle, innerCycle, false);
        draw(canvas, paint, outCycle, innerCycle, stage.spiritAngle, false);
    }


    public void drawProcess(int i, RectF outCycle, RectF innerCycle, Canvas canvas, Paint paint, float percent) {
        Log.d(TAG, "percent" + percent + "  " + startPercent + " - " + endPercent);
        if (percent <= startPercent) {
            return;
        }
        if (percent >= endPercent) {
            updata(i, stage.spiritAngle, outCycle, innerCycle, true);
            draw(canvas, paint, outCycle, innerCycle, stage.spiritAngle, true);
            return;
        }
        if (percent < endPercent) {
            float pen = endPercent - startPercent;
            float percentPen = percent - startPercent;
            float offset = stage.spiritAngle * percentPen / pen;
            updata(i, offset, outCycle, innerCycle, true);
            draw(canvas, paint, outCycle, innerCycle, offset, true);
        }
    }

    public void draw(Canvas canvas, Paint paint, RectF outCycle, RectF innerCycle, float offset, boolean process) {
        path.reset();
        path.moveTo(InA.x, InA.y);
        path.lineTo(OutA.x, OutA.y);
        path.arcTo(outCycle, startAngle, offset, false);
        path.lineTo(InB.x, InB.y);
        path.arcTo(innerCycle, endAngle, -offset, false);
        path.close();
        if (process) {
            paint.setShader(dyPecentRadialGradient);
            canvas.drawPath(path, paint);
            Log.d(TAG, "draw process");
        } else {
            paint.setShader(radialGradient);
            canvas.drawPath(path, paint);
            Log.d(TAG, "draw");
        }
    }

    private void updata(int i, float offset, RectF outCycle, RectF innerCycle, boolean process) {
        float startAnglePe = i * (stage.spiritAngle + stage.offsetAngle);
        startAngle = stage.startAngle + startAnglePe;
        //
        float endAnglePe = startAnglePe + offset;
        endAngle = stage.startAngle + endAnglePe;
        //
        enterAngle = startAngle + (offset) / 2;

        startPercent = startAnglePe / stage.leftAngle;
        endPercent = endAnglePe / stage.leftAngle;
        Log.d(TAG, startAnglePe + "-" + endAnglePe + "  /" + stage.leftAngle);
        Log.e(TAG, startPercent + "-" + endPercent);
        //
        OutA.x = CycleMath.getX(outCycle, startAngle);
        OutA.y = CycleMath.getY(outCycle, startAngle);
        //
        OutB.x = CycleMath.getX(outCycle, endAngle);
        OutB.y = CycleMath.getY(outCycle, endAngle);
        //
        OutCenter.x = CycleMath.getX(outCycle, enterAngle);
        OutCenter.y = CycleMath.getY(outCycle, enterAngle);
        //
        InA.x = CycleMath.getX(innerCycle, startAngle);
        InA.y = CycleMath.getY(innerCycle, startAngle);
        //
        InB.x = CycleMath.getX(innerCycle, endAngle);
        InB.y = CycleMath.getY(innerCycle, endAngle);
        //
        InCenter.x = CycleMath.getX(innerCycle, enterAngle);
        InCenter.y = CycleMath.getY(innerCycle, enterAngle);
        //
        if (radialGradient == null) {
            radialGradient = new LinearGradient(
                    InCenter.x, InCenter.y,
                    OutCenter.x, OutCenter.y,
                    new int[]{stage.bgStartColor, stage.bgCenterColor, stage.bgEndColor}, null,
                    Shader.TileMode.MIRROR);
        }
        if (process) {
            dyPecentRadialGradient = new LinearGradient(
                    InCenter.x, InCenter.y,
                    OutCenter.x, OutCenter.y,
                    new int[]{stage.pStartColor, stage.pCenterColor, stage.pEndColor}, null,
                    Shader.TileMode.MIRROR);
        }
    }
}

package com.shoyu666.module.ui.circle;

import android.graphics.RectF;

import java.math.BigDecimal;
/**
 * @author xining
 * @date 2019/3/1
 */
public class CycleMath {
    /**
     * 获得圆上的x点
     * @param cycle
     * @param angle
     * @return
     */
    public static float getX(RectF cycle, float angle) {
        float x0 = cycle.centerX();
        float r = cycle.width() / 2;
        double x = x0 + r * Math.cos((Math.toRadians(angle)));
        return BigDecimal.valueOf(x).floatValue();
    }

    /**
     * 获得圆上的y点
     * @param cycle
     * @param angle
     * @return
     */
    public static float getY(RectF cycle, float angle) {
        float y0 = cycle.centerY();
        float r = cycle.width() / 2;
        double y = y0 + r * Math.sin((Math.toRadians(angle)));
        return BigDecimal.valueOf(y).floatValue();
    }
}

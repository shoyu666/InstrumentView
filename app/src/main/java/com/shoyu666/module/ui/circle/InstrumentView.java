package com.shoyu666.module.ui.circle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.SpannableString;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.zebra.module.ui.circle.R;

import static com.shoyu666.module.ui.circle.InstrumentViewDefault.CenterDefaultColor;
import static com.shoyu666.module.ui.circle.InstrumentViewDefault.DefaultBoundProcessCycleOffset;
import static com.shoyu666.module.ui.circle.InstrumentViewDefault.DefaultEmptyAngle;
import static com.shoyu666.module.ui.circle.InstrumentViewDefault.DefaultLabelTextSize;
import static com.shoyu666.module.ui.circle.InstrumentViewDefault.DefaultOffsetAngle;
import static com.shoyu666.module.ui.circle.InstrumentViewDefault.DefaultOutInCycleOffset;
import static com.shoyu666.module.ui.circle.InstrumentViewDefault.DefaultOutProcessLineColor;
import static com.shoyu666.module.ui.circle.InstrumentViewDefault.DefaultOutProcessLineWidth;
import static com.shoyu666.module.ui.circle.InstrumentViewDefault.DefaultProcessCycleOutCycleOffset;
import static com.shoyu666.module.ui.circle.InstrumentViewDefault.DefaultStartAngle;
import static com.shoyu666.module.ui.circle.InstrumentViewDefault.EndDefaultColor;
import static com.shoyu666.module.ui.circle.InstrumentViewDefault.PCenterDefaultColor;
import static com.shoyu666.module.ui.circle.InstrumentViewDefault.PEndDefaultColor;
import static com.shoyu666.module.ui.circle.InstrumentViewDefault.PStartDefaultColor;
import static com.shoyu666.module.ui.circle.InstrumentViewDefault.SpiritDefaultCount;
import static com.shoyu666.module.ui.circle.InstrumentViewDefault.StartDefaultColor;

/**
 * @author xining
 * @date 2019/3/1
 */
public class InstrumentView extends View {
    public RectF BoundCycle = new RectF();
    public RectF LabelCenterCycle = new RectF();
    public RectF ProcessCycle = new RectF();
    public RectF OutCycle = new RectF();
    public RectF InnerCycle = new RectF();
    public Paint spirtPaint = new Paint();
    public Paint outLinePaint = new Paint();

    /**
     * 背景渐变
     */
    public int bgStartColor = StartDefaultColor;
    public int bgCenterColor = CenterDefaultColor;
    public int bgEndColor = EndDefaultColor;
    /**
     * 进度渐变
     */
    public int pStartColor = PStartDefaultColor;
    public int pCenterColor = PCenterDefaultColor;
    public int pEndColor = PEndDefaultColor;
    /**
     * 空角度
     */
    public float emptyAngle = DefaultEmptyAngle;
    /**
     * 块数量
     */
    public int spiritCount = SpiritDefaultCount;
    /**
     * 块间隔
     */
    public float offsetAngle = DefaultOffsetAngle;
    /**
     * 块开始角度
     */
    public float startAngle = DefaultStartAngle;
    /**
     * 外围进度线颜色
     */
    public int processLineColor = DefaultOutProcessLineColor;
    /**
     * 外围进度线宽度
     */
    public float outProcessLineWidth = DefaultOutProcessLineWidth;
    /**
     * [Bound,outCycle]空间
     */
    public float bound_Process_CycleOffset = DefaultBoundProcessCycleOffset;

    public float process_Out_CycleOffset = DefaultProcessCycleOutCycleOffset;
    /**
     * 块空间
     */
    public float out_In_CycleOffset = DefaultOutInCycleOffset;
    /**
     * 可用角度
     */
    public float leftAngle;
    /**
     * 空隙数量
     */
    public int blackSpiritCount;
    /**
     * 块角度
     */
    public float spiritAngle;
    /**
     * 块
     */
    public Spirit[] spirits;
    /**
     * 外围线进度
     */
    public float processOutLine = 0.32f;
    /**
     * 外圈进度
     */
    public float processOutCycle = 0.32f;

    /**
     * labels
     */
    public Label[] labels;
    public TextPaint labelPaint = new TextPaint();
    public float labelTextSize = DefaultLabelTextSize;
    public int labelTextColor = Color.WHITE;

    public InstrumentView(Context context) {
        super(context);
        init(null);
    }

    public InstrumentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public InstrumentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 设置外围线进度
     *
     * @param process
     * @return
     */
    public InstrumentView setOutProcess(float process) {
        if (process < 0 || process > 1) {
            throw new RuntimeException("process " + process + "  not in [0,1] ");
        }
        this.processOutLine = process;
        return this;
    }

    /***
     * 设置内圈进度
     * @param process
     * @return
     */
    public InstrumentView setInProcess(float process) {
        if (process < 0 || process > 1) {
            throw new RuntimeException("process " + process + "  not in [0,1] ");
        }
        this.processOutCycle = process;
        return this;
    }

    public void commit() {
        invalidate();
    }

    private void init(AttributeSet attrs) {
        int labelRes = R.array.defaultLabel;
        int labelAngelRes = R.array.defaultLabelAngel;
        String[] labels;
        int[] labelAngels;
        if (attrs != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.InstrumentView);
            //渐变
            bgStartColor = obtainStyledAttributes.getColor(R.styleable.InstrumentView_BgStartColor, StartDefaultColor);
            bgCenterColor = obtainStyledAttributes.getColor(R.styleable.InstrumentView_BgCenterColor, CenterDefaultColor);
            bgEndColor = obtainStyledAttributes.getColor(R.styleable.InstrumentView_BgEndColor, EndDefaultColor);
            //渐变
            pStartColor = obtainStyledAttributes.getColor(R.styleable.InstrumentView_PStartColor, PStartDefaultColor);
            pCenterColor = obtainStyledAttributes.getColor(R.styleable.InstrumentView_PCenterColor, PCenterDefaultColor);
            pEndColor = obtainStyledAttributes.getColor(R.styleable.InstrumentView_PEndColor, PEndDefaultColor);
            //大小
            outProcessLineWidth = obtainStyledAttributes.getDimension(R.styleable.InstrumentView_OutProcessLineWidth, DefaultOutProcessLineWidth);
            //
            bound_Process_CycleOffset = obtainStyledAttributes.getDimension(R.styleable.InstrumentView_BoundProcessCycleOffset, DefaultBoundProcessCycleOffset);
            process_Out_CycleOffset = obtainStyledAttributes.getDimension(R.styleable.InstrumentView_ProcessOutCycleOffset, DefaultProcessCycleOutCycleOffset);
            out_In_CycleOffset = obtainStyledAttributes.getDimension(R.styleable.InstrumentView_OutInCycleOffset, DefaultOutInCycleOffset);
            //块数量
            spiritCount = obtainStyledAttributes.getInteger(R.styleable.InstrumentView_SpiritCount, SpiritDefaultCount);
            labelRes = obtainStyledAttributes.getResourceId(R.styleable.InstrumentView_OutLabel, R.array.defaultLabel);
            labelAngelRes = obtainStyledAttributes.getResourceId(R.styleable.InstrumentView_OutLabelAngel, R.array.defaultLabelAngel);
            //标签
            labelTextSize = obtainStyledAttributes.getDimension(R.styleable.InstrumentView_LabelTextSize, DefaultLabelTextSize);
            labelTextColor = obtainStyledAttributes.getColor(R.styleable.InstrumentView_LabelTextColor, Color.WHITE);
            obtainStyledAttributes.recycle();
        }
        labels = getResources().getStringArray(labelRes);
        labelAngels = getResources().getIntArray(labelAngelRes);
        if (labels.length != 7) {
            throw new RuntimeException("label size must eq 7");
        }
        if (labelAngels.length != 7) {
            throw new RuntimeException("labelAngels size must eq 7");
        }
        initTextPaint();
        initOutLine();
        initSpirt();
        initLabels(labels, labelAngels);
    }

    private void initTextPaint() {
        labelPaint = PaintHelper.getTextPaintByTextSize(labelTextSize, labelTextColor);
    }

    private void initLabels(String[] labelsStr, int[] labelAngels) {

        int labelSize = labelsStr.length;
        labels = new Label[labelSize];
        for (int i = 0; i < labelSize; i++) {
            String lab = labelsStr[i];
            int angel = labelAngels[i];
            labels[i] = new Label(new SpannableString(lab), angel, this);
        }
    }

    private void initSpirt() {
        leftAngle = 360 - emptyAngle;
        blackSpiritCount = spiritCount - 1;
        spiritAngle = (leftAngle - (offsetAngle * blackSpiritCount)) / spiritCount;
        spirits = new Spirit[spiritCount];
        for (int i = 0; i < spiritCount; i++) {
            spirits[i] = new Spirit(this);
        }
    }

    private void initOutLine() {
        outLinePaint.setColor(processLineColor);
        outLinePaint.setStyle(Paint.Style.STROKE);
        outLinePaint.setAntiAlias(true);
        outLinePaint.setDither(true);
        outLinePaint.setStrokeWidth(outProcessLineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        PaintHelper.debugCycle(canvas,BoundCycle, Color.RED);
//        PaintHelper.debugCycle(canvas,LabelCenterCycle, Color.BLUE);
//        PaintHelper.debug(canvas,OutCycle, Color.YELLOW);
//        PaintHelper.debug(canvas,InnerCycle, Color.GREEN);
        drawBackground(canvas);
        drawProcess(canvas);
        drawProcessOutLine(canvas);
        drawLabel(canvas);
    }

    private void drawLabel(Canvas canvas) {
        for (int i = 0; i < labels.length; i++) {
            labels[i].draw(LabelCenterCycle, canvas, labelPaint);
        }
    }

    private void drawProcessOutLine(Canvas canvas) {
        float sweepAngle = leftAngle * processOutLine;
        outLinePaint.setColor(bgEndColor);
        canvas.drawArc(ProcessCycle, startAngle, 270, false, outLinePaint);
        outLinePaint.setColor(processLineColor);
        canvas.drawArc(ProcessCycle, startAngle, sweepAngle, false, outLinePaint);
    }


    private void drawBackground(Canvas canvas) {
        for (int i = 0; i < spiritCount; i++) {
            spirits[i].drawBackground(canvas, spirtPaint, i, OutCycle, InnerCycle);
        }
    }

    private void drawProcess(Canvas canvas) {
        for (int i = 0; i < spiritCount; i++) {
            spirits[i].drawProcess(i, OutCycle, InnerCycle, canvas, spirtPaint, processOutCycle);
        }
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
        initBound(tempSpec);
        super.onMeasure(tempSpec, tempSpec);
    }

    private void initBound(int tempSpec) {
        int bound = View.MeasureSpec.getSize(tempSpec);
        BoundCycle.left = 0;
        BoundCycle.top = 0;
        BoundCycle.right = bound;
        BoundCycle.bottom = bound;
        //
        LabelCenterCycle.left = bound_Process_CycleOffset / 2;
        LabelCenterCycle.top = bound_Process_CycleOffset / 2;
        LabelCenterCycle.right = bound - bound_Process_CycleOffset / 2;
        LabelCenterCycle.bottom = bound - bound_Process_CycleOffset / 2;
        //
        ProcessCycle.left = bound_Process_CycleOffset;
        ProcessCycle.top = bound_Process_CycleOffset;
        ProcessCycle.right = bound - bound_Process_CycleOffset;
        ProcessCycle.bottom = bound - bound_Process_CycleOffset;
        //
        OutCycle.left = ProcessCycle.left+process_Out_CycleOffset;
        OutCycle.top = ProcessCycle.top+process_Out_CycleOffset;
        OutCycle.right = ProcessCycle.right - process_Out_CycleOffset;
        OutCycle.bottom = ProcessCycle.bottom - process_Out_CycleOffset;
        //
        InnerCycle.left = OutCycle.left + out_In_CycleOffset;
        InnerCycle.top = OutCycle.top + out_In_CycleOffset;
        InnerCycle.right = OutCycle.right - out_In_CycleOffset;
        InnerCycle.bottom = OutCycle.bottom - out_In_CycleOffset;
    }
}

package com.example.administrator.myapplication.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/10/11.
 */

public class CircleView extends View {
    RectF rectF;

    private Paint mPaint=new Paint();
    private int width,height,edge=8,centerX,centerY;
    private float angle=0;


    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        height=h;
        centerX=width/2;
        centerY=height/2;
        rectF=new RectF(edge,edge,width-edge,height-edge);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.parseColor("#29C8F8"));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX,centerY,width/2f-edge,mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8f);
        canvas.drawCircle(centerX,centerY,width/2f-edge,mPaint);
    }

}

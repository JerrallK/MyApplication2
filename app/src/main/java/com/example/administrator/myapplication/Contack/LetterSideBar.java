package com.example.administrator.myapplication.Contack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2017/9/29.
 */

public class LetterSideBar extends View {
    String TAG="LetterSideBar";
    private OnTouchLetterListener onTouchLetterListener;

    public static String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private int choose = -1;

    private float width, height;

    private TextView mTextView;

    private Paint paint = new Paint();

    public void setTextView(TextView mTextView) {
        this.mTextView = mTextView;
    }


    public LetterSideBar(Context context) {
        super(context);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        int height = getHeight();
//        int width = getWidth();

        float singleHeight = height / (letters.length);

        for (int i = 0; i < letters.length; i++) {
            paint.setColor(Color.parseColor("#B8B8B8"));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(40);

            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            float xpos = (width - paint.measureText(letters[i])) / 2;
            float ypos = singleHeight * (i + 1);
            canvas.drawText(letters[i], xpos, ypos, paint);
            paint.reset();

        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int oldChoose = choose;
        OnTouchLetterListener listener = onTouchLetterListener;
        int c = (int) ((y / height) * letters.length);

        Log.d(TAG, "*****++++***c="+c+"  height="+height);

        switch (action) {
            case MotionEvent.ACTION_UP:
                //setBackground(new ColorDrawable(Color.parseColor("#00FFFFFF")));
                choose = -1;
                invalidate();
                if (mTextView != null) {
                    mTextView.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                setBackgroundResource(R.drawable.sidebar_background);
                if (oldChoose != c) {
                    if (c >= 0 && c < letters.length) {
                        if (listener != null) {
                            listener.onTouchLetter(letters[c]);
                        }
                        if (mTextView != null) {
                            mTextView.setText(letters[c]);
                            mTextView.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }
                break;
        }


        return true;


    }

    public void setOnTouchLetterListener(OnTouchLetterListener onTouchLetterListener) {
        this.onTouchLetterListener = onTouchLetterListener;


    }

    public interface OnTouchLetterListener {
        void onTouchLetter(String s);
    }

}

package com.lulu.autoscrolltextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 自滚动TextView
 * Created by LuLu on 2016/5/16.
 */
public class AutoScrollTextView extends TextView implements View.OnClickListener {

    /** 文本长度 */
    private float textLength = 0f;
    /** 文字的横坐标 */
    private float step = 0f;
    /** 文字的纵坐标 */
    private float y = 0f;
    /** 用于计算的临时变量 */
    private float temp_view_plus_text_length = 0.0f;
    /** 用于计算的临时变量 */
    private float temp_view_plus_two_text_length = 0.0f;
    /** 是否开始滚动 */
    public boolean isStarting = false;
    /** 绘图样式 */
    private Paint paint = null;
    /** 文本内容 */
    private String text = "";


    public AutoScrollTextView(Context context) {
        super(context);
        initView();
    }

    public AutoScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AutoScrollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        paint = getPaint();
        setOnClickListener(this);

    }

    public void init(WindowManager windowManager) {
        text = getText().toString();
        paint.setColor(Color.rgb(251, 163, 68));
        textLength = paint.measureText(text);
        /* 屏幕或空间的宽度 */
        float viewWidth = getWidth();
        if (viewWidth == 0) {
            if (windowManager != null) {
                Point point = new Point();
                windowManager.getDefaultDisplay().getSize(point);
                viewWidth = point.x;
            }
        }
        step = textLength;
        temp_view_plus_text_length = viewWidth + textLength;
        temp_view_plus_two_text_length = viewWidth + textLength * 2;
        y = getTextSize() + getPaddingTop();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);

        ss.step = step;
        ss.isStarting = isStarting;

        return ss;

    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        step = ss.step;
        isStarting = ss.isStarting;
    }

    public static class SavedState extends BaseSavedState {
        public boolean isStarting = false;
        public float step = 0.0f;

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeBooleanArray(new boolean[] { isStarting });
            out.writeFloat(step);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }
        };

        @SuppressWarnings("unused")
        private SavedState(Parcel in) {
            super(in);
            boolean[] b = null;
            in.readBooleanArray(b);
            if (b != null && b.length > 0)
                isStarting = b[0];
            step = in.readFloat();
        }
    }

    public void startScroll() {
        isStarting = true;
        invalidate();
    }

    public void stopScroll() {
        isStarting = false;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawText(text, temp_view_plus_text_length - step, y, paint);
        if (!isStarting) {
            return;
        }
        step += 1;// 0.5为文字滚动速度。
        if (step > temp_view_plus_two_text_length)
            step = textLength;
        invalidate();
    }


    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        if (isStarting)
            stopScroll();
        else
            startScroll();

    }


}
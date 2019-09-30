package com.hanwool.imagestorage.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hanwool.imagestorage.R;

public class Custom_ImageViewWithScaleAndMove extends View {
    private Drawable drawable;
    private float scaleFactor = 1.0f;
    private ScaleGestureDetector scaleGestureDetector;
    private int deltaX;
    private int deltaY;
    Context context;

    public Custom_ImageViewWithScaleAndMove(Context context) {
        super(context);
//        String pathName = "/path/to/file/xxx.jpg";
//        bitmap b = BitmapFactory.decodeFile(pathName);
        drawable = context.getResources().getDrawable(R.drawable.folder_icon);
        setFocusable(true);
        scaleGestureDetector = new ScaleGestureDetector(context,
                new ScaleListener());
    }
    public Custom_ImageViewWithScaleAndMove(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Custom_ImageViewWithScaleAndMove);
        drawable = typedArray.getDrawable(R.styleable.Custom_ImageViewWithScaleAndMove_src);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        }
        typedArray.recycle();
        setFocusable(true);
        scaleGestureDetector = new ScaleGestureDetector(context,
                new ScaleListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        scaleGestureDetector.onTouchEvent(event);
        setScaleX(scaleFactor);
        setScaleY(scaleFactor);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) getLayoutParams();
                deltaX = X - lParams.leftMargin;
                deltaY = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
                    layoutParams.leftMargin = X - deltaX;
                    layoutParams.topMargin = Y - deltaY;
                    layoutParams.rightMargin = 0;
                    layoutParams.bottomMargin = 0;
                    setLayoutParams(layoutParams);

                break;
        }
        invalidate();
        return true;
    }

    private class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();

            // don't let the object get too small or too large.
            //  scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));

            invalidate();
            return true;
        }

    }

//    public boolean onTouch(View view, MotionEvent event) {
//        final int X = (int) event.getRawX();
//        final int Y = (int) event.getRawY();
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                _xDelta = X - lParams.leftMargin;
//                _yDelta = Y - lParams.topMargin;
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//                break;
//            case MotionEvent.ACTION_POINTER_UP:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                layoutParams.leftMargin = X - _xDelta;
//                layoutParams.topMargin = Y - _yDelta;
//                layoutParams.rightMargin = -250;
//                layoutParams.bottomMargin = -250;
//                view.setLayoutParams(layoutParams);
//                break;
//        }
//        invalidate();
//        return true;
//    }
}
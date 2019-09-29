package com.hanwool.imagestorage.CustomView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hanwool.imagestorage.R;

public class TextviewscaleandmoveActivity extends AppCompatActivity  implements View.OnTouchListener {
   ImageView imageView;
    private float scaleFactor = 1.0f;
    private ScaleGestureDetector scaleGestureDetector;
    private int _xDelta;
    private int _yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textviewscaleandmove);
        imageView = findViewById(R.id.imgTest);
        imageView.setImageResource(R.drawable.aaaaa);
        scaleGestureDetector = new ScaleGestureDetector(this,
                new ScaleListener());
        imageView.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        imageView.setScaleX(scaleFactor);
        imageView.setScaleY(scaleFactor);
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                    LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    lParams.rightMargin = -250;
                    lParams.bottomMargin = -250;

                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 1) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    imageView.setLayoutParams(layoutParams);
                }
                else if (event.getPointerCount() == 2) {
                    scaleGestureDetector.onTouchEvent(event);
                    imageView.setScaleX(scaleFactor);
                    imageView.setScaleY(scaleFactor);

                }
                break;
//
            case MotionEvent.ACTION_POINTER_DOWN:
                scaleGestureDetector.onTouchEvent(event);
                imageView.setScaleX(scaleFactor);
                imageView.setScaleY(scaleFactor);

                break;
            case MotionEvent.ACTION_POINTER_UP:
                scaleGestureDetector.onTouchEvent(event);
                imageView.setScaleX(scaleFactor);
                imageView.setScaleY(scaleFactor);

                break;
        }

        imageView.invalidate();
        return true;
    }
    private class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();

            // don't let the object get too small or too large.
            //  scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));

            imageView.invalidate();
            return true;
        }


    }
}
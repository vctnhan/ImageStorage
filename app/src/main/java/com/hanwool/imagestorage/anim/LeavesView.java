package com.hanwool.imagestorage.anim;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.hanwool.imagestorage.R;

public class LeavesView extends View {
    private static final int NUM_SNOWFLAKES = 30;
    private static final int DELAY = 5;
    Bitmap bitmap;
    private LeavesModel[] snowflakes;

    public LeavesView(Context context) {
        super(context);
    }

    public LeavesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources res = getResources();
        bitmap = BitmapFactory.decodeResource(res, R.drawable.lathuroi);
    }

    public LeavesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void resize(int width, int height) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        snowflakes = new LeavesModel[NUM_SNOWFLAKES];
        for (int i = 0; i < NUM_SNOWFLAKES; i++) {
            snowflakes[i] = LeavesModel.create(width, height, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            resize(w, h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap b = Bitmap.createScaledBitmap(bitmap, 120, 120, false);

        for (LeavesModel snowFlake : snowflakes) {
            snowFlake.draw(canvas, b);
        }
        getHandler().postDelayed(runnable, DELAY);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };
}
package com.hanwool.imagestorage.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hanwool.imagestorage.R;

public class Custom_EditImage extends RelativeLayout {
    ImageView imgNeedToEdit;
    Custom_HorizonalView customHorizonalView;
    Custom_ImageViewWithScaleAndMove imageViewWithScaleAndMove;

    public Custom_EditImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
        //imgNeedToEdit = findViewById(R.id.imgNeedToEdit);
        customHorizonalView = findViewById(R.id.horizontalView);
        imageViewWithScaleAndMove = findViewById(R.id.imgIcon);

        imageViewWithScaleAndMove.setBackgroundResource(Custom_HorizonalView.ImageId);

    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_view_merge_horizontalview_scalemove, this);
    }
}

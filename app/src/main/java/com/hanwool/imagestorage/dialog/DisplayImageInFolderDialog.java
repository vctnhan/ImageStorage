package com.hanwool.imagestorage.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.hanwool.imagestorage.adapter.DisplayImageInFolderAdapter;
import com.hanwool.imagestorage.fragment.DisplayImgInFolderFragment;
import com.hanwool.imagestorage.R;

public class DisplayImageInFolderDialog extends Dialog {
    Context a;
    ImageView imgBack, imgNext, imgDisplay;
    int width, height;

    int index = 0;

    public DisplayImageInFolderDialog(@NonNull Context b) {
        super(b);
        this.a = b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.display_img_dialog);
        imgBack = findViewById(R.id.imgBack);
        imgNext = findViewById(R.id.imgNext);
        imgDisplay = findViewById(R.id.imgDisplay);
        index = DisplayImageInFolderAdapter.index;
        WindowManager manager = (WindowManager) a.getSystemService(Activity.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            width = manager.getDefaultDisplay().getWidth();
            height = manager.getDefaultDisplay().getHeight();
        } else {
            Point point = new Point();
            manager.getDefaultDisplay().getSize(point);
            width = point.x;
            height = point.y;
        }
      displayImageInFolder(index);
        DisplayForImageInFolder();
    }
    public void DisplayForImageInFolder() {
        index = DisplayImageInFolderAdapter.index;
        displayImageInFolder(index);
        imgBack.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
//                int index = AllImageInFolderStorageAdapter.index;
                int count = 0;
                index = index > 0 ? (index - count - 1) : DisplayImgInFolderFragment.arrImageInFolder.size() - 1;
                displayImageInFolder(index);
                count = count - 1;
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int index = AllImageInFolderStorageAdapter.index;
                int count = 0;
                index = index < DisplayImgInFolderFragment.arrImageInFolder.size() - 1 ? (index + count + 1) : 0;
                displayImageInFolder(index);
                count += 1;
            }
        });
    }
    public void displayImageInFolder(int i) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_sync_black_24dp);
        requestOptions.error(R.drawable.ic_sentiment_very_dissatisfied_black_24dp);
        requestOptions.override(width, height);
        Glide.with(a).setDefaultRequestOptions(requestOptions)
                .load("file://" + DisplayImgInFolderFragment.arrImageInFolder.get(i).getPath())

                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imgDisplay);
    }
}
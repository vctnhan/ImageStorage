package com.hanwool.imagestorage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hanwool.imagestorage.adapter.AllImageStorageAdapter;
import com.hanwool.imagestorage.anim.BubbleView;
import com.hanwool.imagestorage.anim.LeavesView;
import com.hanwool.imagestorage.anim.SnowView;
import com.hanwool.imagestorage.customview.Custom_HorizonalView;
import com.hanwool.imagestorage.customview.Custom_ImageViewWithScaleAndMove;
import com.hanwool.imagestorage.fragment.AllImageStorageFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditImageActivity extends AppCompatActivity {
    FrameLayout viewToSave;
    String YOUR_FOLDER_NAME = "Camera";
    Button btnSaveImage, btnSnowEffect, btnBubbleEffect, btnLeavesEffect;
    SnowView snowView;
    BubbleView bubbleView;
    LeavesView leavesView;
    ImageView imgNeedToEdit;
    Custom_HorizonalView customHorizonalView;
    Custom_ImageViewWithScaleAndMove imageViewWithScaleAndMove;
    public static int imgId;
    String imgPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        customHorizonalView = findViewById(R.id.horizontalView);
        imageViewWithScaleAndMove = findViewById(R.id.imgIcon);
        imgNeedToEdit = findViewById(R.id.imgNeedToEdit);
        imgPath = getIntent().getStringExtra("imgPath");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_sync_black_24dp);
        requestOptions.error(R.drawable.ic_sentiment_very_dissatisfied_black_24dp);

        Glide.with(this).setDefaultRequestOptions(requestOptions)
                .load("file://" + imgPath)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imgNeedToEdit);

        snowView = findViewById(R.id.snowView);
        bubbleView = findViewById(R.id.bubbleView);
        leavesView = findViewById(R.id.leavesView);
        //snow
        btnSnowEffect = findViewById(R.id.btnSnowEffect);
        btnSnowEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snowView.setVisibility(View.VISIBLE);
                bubbleView.setVisibility(View.GONE);
                leavesView.setVisibility(View.GONE);
            }
        });
        btnBubbleEffect = findViewById(R.id.btnBubbleEffect);
        btnBubbleEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snowView.setVisibility(View.GONE);
                bubbleView.setVisibility(View.VISIBLE);
                leavesView.setVisibility(View.GONE);
            }
        });
        btnLeavesEffect = findViewById(R.id.btnLeavesEffect);
        btnLeavesEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snowView.setVisibility(View.GONE);
                bubbleView.setVisibility(View.GONE);
                leavesView.setVisibility(View.VISIBLE);
            }
        });

        //Save image
        viewToSave = findViewById(R.id.viewToSave);
        btnSaveImage = findViewById(R.id.btnSaveImage);
        btnSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSaveImageFilePath(viewToSave);
                AllImageStorageFragment.allImageStorageAdapter.notifyDataSetChanged();
            }
        });
    }

    public String getSaveImageFilePath(View view) {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), YOUR_FOLDER_NAME);
        // Create a storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e(YOUR_FOLDER_NAME, "Failed to create directory");
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "IMG_" + timeStamp + ".jpg";

        String selectedOutputPath = mediaStorageDir.getPath() + File.separator + imageName;
        Log.e(YOUR_FOLDER_NAME, "selected camera path " + selectedOutputPath);

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());

        int maxSize = 1080;

        int bWidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();

        if (bWidth > bHeight) {
            int imageHeight = (int) Math.abs(maxSize * ((float) bitmap.getWidth() / (float) bitmap.getHeight()));
            bitmap = Bitmap.createScaledBitmap(bitmap, maxSize, imageHeight, true);
        } else {
            int imageWidth = (int) Math.abs(maxSize * ((float) bitmap.getWidth() / (float) bitmap.getHeight()));
            bitmap = Bitmap.createScaledBitmap(bitmap, imageWidth, maxSize, true);
        }
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();

        OutputStream fOut = null;
        try {
            File file = new File(selectedOutputPath);
            fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, selectedOutputPath, Toast.LENGTH_SHORT).show();
        return selectedOutputPath;
    }

}

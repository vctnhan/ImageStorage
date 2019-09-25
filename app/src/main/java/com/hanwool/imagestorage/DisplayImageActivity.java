package com.hanwool.imagestorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.hanwool.imagestorage.Adapter.AllImageInFolderStorageAdapter;
import com.hanwool.imagestorage.Adapter.AllImageStorageAdapter;
import com.hanwool.imagestorage.Fragment.AllImageStorageFragment;
import com.hanwool.imagestorage.Model.ImageStorage;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DisplayImageActivity extends AppCompatActivity {
    View view;
    RecyclerView lstAllImage;
    ArrayList<ImageStorage> arrayList;
    public static ArrayList<ImageStorage> arrImage;
    AllImageInFolderStorageAdapter allImageInFolderStorageAdapter;
    private boolean isFragmentLoaded = false;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        doStuff();
    }

    private void doStuff() {

        arrayList = new ArrayList<>();
        lstAllImage = findViewById(R.id.lstDisplayImage);
        file = new File(getIntent().getStringExtra("display"));
        getFile(file);
        arrImage = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            ImageStorage imageStorage = arrayList.get(i);
            arrImage.add(new ImageStorage(imageStorage.getPath(), imageStorage.getDate()));
        }
        Log.e(TAG, "getFiledusss: " + arrImage.get(0).getDate());
        Collections.sort(arrImage, new StringDateComparator());
        MainActivity.progressBar.setVisibility(View.GONE);
        allImageInFolderStorageAdapter = new AllImageInFolderStorageAdapter(DisplayImageActivity.this, arrImage);
        //  Log.e("datetime", "datetime dostuff " + " ? " + arrImage.size());
        lstAllImage.hasFixedSize();
        lstAllImage.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        lstAllImage.setAdapter(allImageInFolderStorageAdapter);
        // getFromStorage();


    }

    public void getFile(File dir) {

        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    getFile(listFile[i]);
                } else {
                    if (listFile[i].getName().endsWith(".png")
                            || listFile[i].getName().endsWith(".jpg")
                            || listFile[i].getName().endsWith(".jpeg")
                            || listFile[i].getName().endsWith(".gif")
                            || listFile[i].getName().endsWith(".bmp")) {
                        String temp = listFile[i].getPath();
                        //  String temp = file.getPath().substring(0, file.getPath().lastIndexOf('/'));
                        // String name = temp.substring(temp.lastIndexOf('/')).replace('/', ' ');
                        if (!arrayList.contains(temp)) {
                            File file = new File(temp);
                            Date lastModifiedDate = new Date(file.lastModified());
                            String format = "MM-dd-yyyy HH:mm:ss";
                            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.ENGLISH);
                            String dt = formatter.format(new Date(String.valueOf(lastModifiedDate)));
                            ;
                            arrayList.add(new ImageStorage(temp, dt));

                        }

                    }
                }
            }
        }


    }

    class StringDateComparator implements Comparator<ImageStorage> {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

        public int compare(ImageStorage s1, ImageStorage s2) {
            Date d1 = null;
            Date d2 = null;
            try {
                d1 = dateFormat.parse(s1.getDate());
                d2 = dateFormat.parse(s2.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (d1.getTime() == d2.getTime())
                return 0;
            else if (d1.getTime() < d2.getTime())
                return 1;
            else
                return -1;


        }


    }
}

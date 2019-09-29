package com.hanwool.imagestorage.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanwool.imagestorage.adapter.AllImageStorageAdapter;
import com.hanwool.imagestorage.MainActivity;
import com.hanwool.imagestorage.model.ImageStorage;
import com.hanwool.imagestorage.R;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AllImageStorageFragment extends Fragment {
    View view;
    RecyclerView lstAllImage;
    ArrayList<ImageStorage> arrayList;
    public static ArrayList<ImageStorage> arrImage;
    AllImageStorageAdapter allImageStorageAdapter;
    private boolean isFragmentLoaded = false;
    File file;


    public AllImageStorageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.all_image_storage_fragment, container, false);
        MainActivity.progressBar.setVisibility(View.VISIBLE);
       doStuff();
        new MyAsyncTask().execute();
        return view;
    }

    private void doStuff() {
        lstAllImage = view.findViewById(R.id.lstAllImage);
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        arrayList = new ArrayList<>();
        arrImage = new ArrayList<>();
    }

    private class MyAsyncTask extends AsyncTask<String, Void, ArrayList<ImageStorage>> {
        @Override
        protected ArrayList<ImageStorage> doInBackground(String... strings) {
            return getFile(file);
        }
        @Override
        protected void onPostExecute(ArrayList<ImageStorage> arrImageStorages) {
            super.onPostExecute(arrImageStorages);

            MainActivity.progressBar.setVisibility(View.GONE);

            for (int i = 0; i < arrImageStorages.size(); i++) {
                ImageStorage imageStorage = arrImageStorages.get(i);
                arrImage.add(new ImageStorage(imageStorage.getPath(), imageStorage.getDate()));
            }
            Collections.sort(arrImage, new StringDateComparator());
            allImageStorageAdapter = new AllImageStorageAdapter(getContext(), arrImage);
            //  Log.e("datetime", "datetime dostuff " + " ? " + arrImage.size());
            lstAllImage.hasFixedSize();
            lstAllImage.setLayoutManager(new GridLayoutManager(getContext(), 3));
            lstAllImage.setAdapter(allImageStorageAdapter);
        }
    }

    public ArrayList<ImageStorage> getFile(File dir) {

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
        return arrayList;

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


//    private void getFromStorage() {
//        ContentResolver contentResolver = getActivity().getContentResolver();
//        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DATE_TAKEN};
//        Cursor imageCursor = contentResolver.query(imageUri, projection, null,
//                null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC" );
//
//        if (imageCursor != null && imageCursor.moveToFirst()) {
//            int date = imageCursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN);
//            int location = imageCursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            do {
//                String currentDate = imageCursor.getString(date);
//                String currentLocation = imageCursor.getString(location);
//                String format = "MM-dd-yyyy HH:mm:ss";
//                SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.ENGLISH);
//                String dateTime = formatter.format(new Date(Long.parseLong(currentDate)));
//                arrImage.add(new ImageStorage(currentLocation, dateTime));
//                allImageStorageAdapter.notifyDataSetChanged();
//            } while (imageCursor.moveToNext());
//
//        }
//    }

}
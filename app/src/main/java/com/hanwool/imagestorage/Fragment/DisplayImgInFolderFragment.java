package com.hanwool.imagestorage.Fragment;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanwool.imagestorage.Adapter.AllImageStorageAdapter;
import com.hanwool.imagestorage.Adapter.DisplayImageInFolderAdapter;
import com.hanwool.imagestorage.Adapter.FolderStorageAdapter;
import com.hanwool.imagestorage.MainActivity;
import com.hanwool.imagestorage.Model.ImageStorage;
import com.hanwool.imagestorage.R;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DisplayImgInFolderFragment extends Fragment {
    View view;
    RecyclerView lstAllImage;
    ArrayList<ImageStorage> arrayList;
    public static ArrayList<ImageStorage> arrImageInFolder;
    DisplayImageInFolderAdapter displayImageInFolderAdapter;
    private boolean isFragmentLoaded = false;
    File file;


    public DisplayImgInFolderFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.display_img_in_folder_fragment, container, false);
        doStuff();
        return view;
    }

    private void doStuff() {

        arrayList = new ArrayList<>();
        lstAllImage = view.findViewById(R.id.lstAllImage);
        file = new File(FolderFragment.arrFolder.get(FolderStorageAdapter.index).getPath());
        getFile(file);
        arrImageInFolder = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            ImageStorage imageStorage = arrayList.get(i);
            arrImageInFolder.add(new ImageStorage(imageStorage.getPath(), imageStorage.getDate()));
        }
        Log.e(TAG, "getFile: " + arrImageInFolder.get(0).getDate());
        Collections.sort(arrImageInFolder, new DisplayImgInFolderFragment.StringDateComparator());
        MainActivity.progressBar.setVisibility(View.GONE);
        displayImageInFolderAdapter = new DisplayImageInFolderAdapter(getContext(), arrImageInFolder);
        //  Log.e("datetime", "datetime dostuff " + " ? " + arrImage.size());
        lstAllImage.hasFixedSize();
        lstAllImage.setLayoutManager(new GridLayoutManager(getContext(), 3));
        lstAllImage.setAdapter(displayImageInFolderAdapter);
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
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFragmentLoaded) {
            // Load your data here or do network operations here
            doStuff();
            isFragmentLoaded = true;
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

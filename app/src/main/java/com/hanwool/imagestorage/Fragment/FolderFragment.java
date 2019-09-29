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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.hanwool.imagestorage.Adapter.FolderStorageAdapter;
import com.hanwool.imagestorage.Model.FolderStorage;
import com.hanwool.imagestorage.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FolderFragment extends Fragment {
    View view;
    ArrayList<FolderStorage> arrayList;
   public static ArrayList<FolderStorage> arrFolder;
    FolderStorageAdapter folderStorageAdapter;
    RecyclerView lstFolder;
    private boolean isFragmentLoaded =false;
    File file;
public static  ViewPager viewPager;
    public FolderFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.folder_fragment,container, false);
        return view;
    }
    private void doStuff() {
        arrayList = new ArrayList<>();
        arrFolder = new ArrayList<>();
        lstFolder = view.findViewById(R.id.lstFolder);
      file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
    getFile(file);
        for (FolderStorage a : arrayList) {
            boolean isFound = false;
            // check if the event name exists in noRepeat
            for (FolderStorage e : arrFolder) {


                String tempA = a.getPath().substring(0, a.getPath().lastIndexOf('/'));
                String nameA = tempA.substring(tempA.lastIndexOf('/')).replace('/', ' ').trim();

                String tempB = e.getPath().substring(0, e.getPath().lastIndexOf('/'));
                String nameB = tempB.substring(tempB.lastIndexOf('/')).replace('/', ' ').trim();

                if (nameA.equalsIgnoreCase(nameB) || (e.equals(a))) {
                    isFound = true;
                    break;
                }
            }

            if (!isFound) arrFolder.add(a);
        }
        Collections.sort(arrFolder, new Comparator<FolderStorage>() {
            @Override
            public int compare(FolderStorage t1, FolderStorage t2) {
                String name1 = t1.getPath().substring(t1.getPath().lastIndexOf('/')).replace('/', ' ').trim();
                String name2 = t2.getPath().substring(t2.getPath().lastIndexOf('/')).replace('/', ' ').trim();

                return name1.compareToIgnoreCase(name2);
            }
        });
        folderStorageAdapter = new FolderStorageAdapter(getContext(),arrFolder);

        lstFolder.hasFixedSize();
        lstFolder.setLayoutManager(new LinearLayoutManager(getContext()));
        lstFolder.setAdapter(folderStorageAdapter);
        Log.e("folder", "file2332 " + arrFolder.get(0).getPath());

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
    public ArrayList<FolderStorage> getFile(File dir) {

        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i =0; i <listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    getFile(listFile[i]);
                } else {
                    if (listFile[i].getName().endsWith(".png")
                            || listFile[i].getName().endsWith(".jpg")
                            || listFile[i].getName().endsWith(".jpeg")
                            || listFile[i].getName().endsWith(".gif")
                            || listFile[i].getName().endsWith(".bmp")) {
                        String  path = listFile[i].getPath();

//
                        String tempB = path.substring(0, path.lastIndexOf('/'));
                        String nameB = tempB.substring(tempB.lastIndexOf('/')).replace('/', ' ').trim();

                        if (!arrayList.contains(path))
                            arrayList.add(new FolderStorage(tempB));
                        Log.e("folder", "file2332 " + tempB);
                    }
                }
            }
        }

        return arrayList;

    }

}

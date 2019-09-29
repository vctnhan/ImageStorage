package com.hanwool.imagestorage.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hanwool.imagestorage.Adapter.DirAdapter;
import com.hanwool.imagestorage.Model.FolderStorage;
import com.hanwool.imagestorage.Model.PathAndName;
import com.hanwool.imagestorage.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ExplorerFragment extends Fragment {
    View view;
    private List<FolderStorage> item = null;
    private List<FolderStorage> path = null;
    private String root = "";
    private TextView myPath;
    DirAdapter dirAdapter;
    ListView lstDir;
    public static ArrayList<PathAndName> arrDir;
    File file;
boolean isFragmentLoaded = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.explorer_fragment, container, false);


        return view;
    }

    private void doStuff() {
        myPath = (TextView) view.findViewById(R.id.path);
        lstDir = view.findViewById(R.id.lstDir);
        arrDir = new ArrayList<>();

        root = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.e(TAG, "onCreate: " + root);

        getDir(root);



    }

    private void getDir(String dirPath) {
        myPath.setText("Location: " + dirPath);
        item = new ArrayList<>();
        path = new ArrayList<>();
        File f = new File(dirPath);
        File[] files = f.listFiles();
        if (!dirPath.equals(root)) {
            item.add(new FolderStorage(root));
            path.add(new FolderStorage(root));
            item.add(new FolderStorage("../"));
            path.add(new FolderStorage(f.getParent()));
        }
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            path.add(new FolderStorage(file.getPath()));
            if (file.isDirectory()) {
                item.add(new FolderStorage(file.getName() + "/"));
            } else {
                item.add(new FolderStorage(file.getPath()));
            }
            //  arrDir.add(new PathAndName(path.get(i), item.get(i)));
        }
//        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,
//                R.layout.explorer_row, item);
//
        dirAdapter = new DirAdapter(getContext(), item);
        lstDir.setAdapter(dirAdapter);
        lstDir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                file = new File(path.get(i).getPath());

                if (file.isDirectory()) {
                    if (file.canRead())
                    {
                        getDir(path.get(i).getPath());

                    }

                    else {
                        new AlertDialog.Builder(getActivity())
                                .setIcon(R.drawable.ic_launcher_background)
                                .setTitle("[" + file.getName() + "] folder can't be read!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                    }

                }
                else {

                }
            }
        });
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
}

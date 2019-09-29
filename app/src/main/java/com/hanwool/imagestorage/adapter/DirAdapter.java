package com.hanwool.imagestorage.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hanwool.imagestorage.model.FolderStorage;
import com.hanwool.imagestorage.R;

import java.util.List;

public class DirAdapter extends BaseAdapter {
    Context context;
    List<FolderStorage> arrayList;

    public DirAdapter(Context context, List<FolderStorage> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView txtFolderName;
        ImageView imgFolder;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_folder, null);
            viewHolder.txtFolderName = view.findViewById(R.id.txtFolderName);
            viewHolder.imgFolder = view.findViewById(R.id.imgFolder);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String name;
        if (arrayList.get(i).getPath().contains("storage/emulated/0")) {
            name = arrayList.get(i).getPath().substring(arrayList.get(i).getPath().lastIndexOf('/')).replace('/', ' ').trim();
        }else {
            name = arrayList.get(i).getPath();
        }
        viewHolder.txtFolderName.setText(name);
        if (arrayList.get(i).getPath().endsWith(".png")
                || arrayList.get(i).getPath().endsWith(".jpg")
                || arrayList.get(i).getPath().endsWith(".jpeg")
                || arrayList.get(i).getPath().endsWith(".gif")
                || arrayList.get(i).getPath().endsWith(".bmp")) {

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_sync_black_24dp);
            requestOptions.error(R.drawable.folder_icon);
            requestOptions.override(150, 150);
            Glide.with(context).setDefaultRequestOptions(requestOptions)
                    .load("file://" + arrayList.get(i).getPath())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(viewHolder.imgFolder);
            Log.e("testttttt", "fdf" + arrayList.get(i));
        }
        if (arrayList.get(i).getPath().endsWith(".mp3")
                ||arrayList.get(i).getPath().endsWith(".wma")
                ||arrayList.get(i).getPath().endsWith(".wav")){
            viewHolder.imgFolder.setImageResource(R.drawable.mp3_icon);
        }

        return view;
    }
}

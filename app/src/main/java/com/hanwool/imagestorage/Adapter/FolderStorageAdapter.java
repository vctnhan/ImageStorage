package com.hanwool.imagestorage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hanwool.imagestorage.DisplayImageActivity;
import com.hanwool.imagestorage.Model.FolderStorage;
import com.hanwool.imagestorage.R;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FolderStorageAdapter extends RecyclerView.Adapter<FolderStorageAdapter.ItemHolder> {
    Context context;
    ArrayList<FolderStorage> arrFolder;

    public FolderStorageAdapter(Context context, ArrayList<FolderStorage> arrFolder) {
        this.context = context;
        this.arrFolder = arrFolder;
    }

    @NonNull
    @Override
    public FolderStorageAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_folder, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FolderStorageAdapter.ItemHolder holder, int position) {


//        String[] arrtemp = arrFolder.get(position).getPath().split("0/", 2);
//        String s = arrtemp[1];
//        String[] arrS = s.split("/",2 );
//        String name = arrS[0];

        String name = arrFolder.get(position).getPath().substring(arrFolder.get(position).getPath().lastIndexOf('/')).replace('/', ' ').trim();
        holder.txtFolder.setText(name);
        Log.e(TAG, "tempexxx " + name );
    }

    @Override
    public int getItemCount() {
        return arrFolder.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView txtFolder;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            txtFolder = itemView.findViewById(R.id.txtFolderName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
               public void onClick(View view) {
                   Intent intent = new Intent(context, DisplayImageActivity.class);
                   intent.putExtra("display",arrFolder.get(getPosition()).getPath());
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent); }
           });
        }
    }
}

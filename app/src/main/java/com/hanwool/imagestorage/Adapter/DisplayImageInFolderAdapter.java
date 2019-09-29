package com.hanwool.imagestorage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hanwool.imagestorage.EditImageActivity;
import com.hanwool.imagestorage.Model.ImageStorage;
import com.hanwool.imagestorage.R;

import java.util.ArrayList;

public class DisplayImageInFolderAdapter extends RecyclerView.Adapter<DisplayImageInFolderAdapter.ItemHolder> {
    Context context;
    ArrayList<ImageStorage> arrImage;
public static int index = 0;
    public DisplayImageInFolderAdapter(Context context, ArrayList<ImageStorage> arrImage) {
        this.context = context;
        this.arrImage = arrImage;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_all_image_storage, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_sync_black_24dp);
        requestOptions.error(R.drawable.ic_sentiment_very_dissatisfied_black_24dp);
        Glide.with(context).setDefaultRequestOptions(requestOptions)
                .load("file://" + arrImage.get(position).getPath())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.imgAllImage);
       //

holder.imgAllImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(context,arrImage.get(position).getDate(),Toast.LENGTH_SHORT).show();
        index = position;
        Intent i = new Intent(context, EditImageActivity.class);
        i.putExtra("imgPath", arrImage.get(position).getPath());
        context.startActivity(i);
    }
});
    }

    @Override
    public int getItemCount() {
        return arrImage.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        ImageView imgAllImage;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgAllImage = itemView.findViewById(R.id.imgAllImage);

        }
    }
}

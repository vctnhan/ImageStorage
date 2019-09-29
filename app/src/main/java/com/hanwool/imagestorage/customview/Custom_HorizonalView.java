package com.hanwool.imagestorage.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanwool.imagestorage.fragment.DisplayEmojiOnEditImageFragment;
import com.hanwool.imagestorage.model.Data;
import com.hanwool.imagestorage.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Custom_HorizonalView extends RelativeLayout {
    Context context;
    RecyclerView horizontal_recycler_view;
    HorizontalAdapter horizontalAdapter;
    private List<Data> data;
    int size = 0;
    public static int ImageId = 0;

    public Custom_HorizonalView(Context context) {
        super(context);
    }


    public Custom_HorizonalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeViews(context);
        horizontal_recycler_view = findViewById(R.id.horizontal_recycler_view);
        data = fill_with_data();
        horizontalAdapter = new HorizontalAdapter(data, getContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManager);
        horizontal_recycler_view.setAdapter(horizontalAdapter);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Custom_HorizonalView);
        size = typedArray.getInt(R.styleable.Custom_HorizonalView_amount, 0);

        typedArray.recycle();
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.horizontalview_, this);
    }

    public List<Data> fill_with_data() {

        List<Data> data = new ArrayList<>();
//        for (int i = 0; i < size; i++) {
//            data.add(new Data(getResources().getDrawable(R.drawable.vozis_), "Image " + (i + 1)));
//        }
        data.add(new Data(R.drawable.vozis_1, "Image 1"));
        data.add(new Data(R.drawable.vozis_2, "Image 2"));
        data.add(new Data(R.drawable.vozis_3, "Image 3"));
        data.add(new Data(R.drawable.vozis_4, "Image 4"));;
//        data.add(new Data(R.drawable.ic_launcher_foreground, "Image 1"));
//        data.add(new Data(R.drawable.ic_launcher_foreground, "Image 2"));
//        data.add(new Data(R.drawable.ic_launcher_foreground, "Image 3"));


        return data;
    }

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        List<Data> horizontalList ;

        Context context;


        public HorizontalAdapter(List<Data> horizontalList, Context context) {
            this.horizontalList = horizontalList;
            this.context = context;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView txtview;

            public MyViewHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.imageview);
                txtview =  view.findViewById(R.id.txtview);
            }
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_icon_in_horizontalview_, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


            holder.imageView.setImageResource(horizontalList.get(position).imageId);
            holder.txtview.setText(horizontalList.get(position).txt);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    String list = horizontalList.get(position).txt.toString();
                    Custom_HorizonalView.ImageId = horizontalList.get(position).getImageId();
                    DisplayEmojiOnEditImageFragment displayEmojiOnEditImageFragment = new DisplayEmojiOnEditImageFragment();
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayoutEmoji, displayEmojiOnEditImageFragment); // give your fragment container id in first parameter
                    transaction.commit();
                }

            });
        }


        @Override
        public int getItemCount() {
            if (size > horizontalList.size() || size < 0) {
                size = horizontalList.size();
            }

            return size;
        }
    }
}

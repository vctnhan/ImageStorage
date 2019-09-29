package com.hanwool.imagestorage.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hanwool.imagestorage.customview.Custom_HorizonalView;
import com.hanwool.imagestorage.customview.Custom_ImageViewWithScaleAndMove;
import com.hanwool.imagestorage.R;

public class DisplayEmojiOnEditImageFragment extends Fragment {
    View view;
    boolean isFragmentLoaded = false;
    Custom_ImageViewWithScaleAndMove imgEmoji;

    public DisplayEmojiOnEditImageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.display_emoji_fragment, container, false);
        doStuff();
        return view;
    }

    private void doStuff() {
        imgEmoji = view.findViewById(R.id.imgEmoji);


          //  Drawable drawable = getResources().getDrawable(R.drawable.aaaaa);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imgEmoji.setBackgroundResource(Custom_HorizonalView.ImageId);
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

}

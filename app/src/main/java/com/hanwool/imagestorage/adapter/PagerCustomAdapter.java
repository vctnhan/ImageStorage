package com.hanwool.imagestorage.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.hanwool.imagestorage.fragment.AllImageStorageFragment;
import com.hanwool.imagestorage.fragment.ExplorerFragment;
import com.hanwool.imagestorage.fragment.FolderFragment;


public class PagerCustomAdapter extends FragmentStatePagerAdapter {


    public PagerCustomAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new AllImageStorageFragment();
                return fragment;
            case 1:
                fragment = new FolderFragment();
                return fragment;
            case 2:
                fragment = new ExplorerFragment();
                return fragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Tất cả";
                break;
            case 1:
                title = "Thư mục";
                break;
            case 2:
                title = "Explorer";
                break;
        }
        return title;
    }
}
package com.example.CubeX.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.CubeX.Commands;
import com.example.CubeX.DeviceExpanded;
import com.example.CubeX.Homepage;
import com.example.CubeX.Information;
import com.example.CubeX.MapsFragment;
import com.example.CubeX.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

class SectionsPagerAdapter extends FragmentPagerAdapter {

    String ID = null;

    public void setID(String ID){this.ID = ID;}
    public String getID(){return this.ID;}



    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

        Bundle bundle = new Bundle();

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putString("ID", ID);
        switch(position)
        {
            case 0:
                fragment = new Information();
                break;

            case 1:
                fragment = new Commands();
                break;

            case 2:
                fragment = new MapsFragment();
                break;
        }
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

}
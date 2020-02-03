package com.codates.plantie.view.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.codates.plantie.model.Hari;
import com.codates.plantie.model.Minggu;
import com.codates.plantie.model.MingguTemp;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private static final String[] TITLE = new String[]{};
    Minggu minggu;
    String tanamanUserId;
    MingguTemp mingguTemp;

    public SectionsPagerAdapter(Context context, FragmentManager fm, Minggu minggu, String tanamanUserId, MingguTemp mingguTemp) {
        super(fm);
        mContext = context;
        this.minggu = minggu;
        this.tanamanUserId =tanamanUserId;
        this.mingguTemp = mingguTemp;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position, minggu,tanamanUserId,mingguTemp);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Hari " + (position+1);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 7;
    }
}
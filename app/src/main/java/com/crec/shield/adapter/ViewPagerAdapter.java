package com.crec.shield.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] mFragmentArrays ;
    private String[] mTabTitles ;
    public ViewPagerAdapter(FragmentManager fragmentManager, Fragment[] fragmentArrays, String[] tabTitles ) {
        super(fragmentManager);
        mFragmentArrays =fragmentArrays;
        mTabTitles= tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentArrays[position];
    }

    @Override
    public int getCount() {
        return mFragmentArrays.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];

    }
}

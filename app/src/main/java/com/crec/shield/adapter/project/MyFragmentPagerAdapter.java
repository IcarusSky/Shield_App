package com.crec.shield.adapter.project;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public List<Fragment> fragmentList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList){
        super(fm);
        this.fragmentList=fragmentList;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

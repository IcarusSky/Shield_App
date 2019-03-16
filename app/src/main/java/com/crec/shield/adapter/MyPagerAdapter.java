package com.crec.shield.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
    private Context myContext;
    private List<Integer> myList;
    public MyPagerAdapter(Context context){
        this.myContext=context;
    }
    public MyPagerAdapter(Context context,List<Integer>imageList){
        this.myContext=context;
        this.myList=imageList;
    }
    @Override
    public  int getCount(){return Integer.MAX_VALUE;}
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o){return view==o;}
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        ImageView view=new ImageView(this.myContext);
        int location=position%(this.myList.size());
        view.setBackgroundResource(this.myList.get(location));
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView((View)object);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return super.getPageTitle(position);
    }
}

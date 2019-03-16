package com.crec.shield.adapter.project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.entity.crec22.project.projectdevice.Data;

import java.util.List;

import butterknife.BindView;

public class HomeViewPagerAdapter extends PagerAdapter {
//    @BindView(R.id.tv_InProject)
//    TextView tvInProject;
//    @BindView(R.id.tv_InLine)
//    TextView tvInLine;
//    @BindView(R.id.tv_TbmNumber)
//    TextView tvTbmNumber;
//    @BindView(R.id.tv_FinishLine)
//    TextView tvFinishLine;
    private Context context;
    private Data data;
    public HomeViewPagerAdapter(Context context,Data data){
        this.context=context;
        this.data=data;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int pos = position % (3);
        View view =null;
        switch (pos) {
            case 0:
                View view1 = View.inflate(context, R.layout.fragment_viewpager_item1, null);
                TextView tvInProject =(TextView)view1.findViewById(R.id.tv_InProject);
                TextView tvInLine1 =(TextView)view1.findViewById(R.id.tv_InLine1);
                TextView tvInLine2 =(TextView)view1.findViewById(R.id.tv_InLine2);
                String word1 = "中铁一局在建项目" + data.getInProject() + "个";
                String word2 = "中铁一局在建线路" + data.getInLine() + "米" + "，总长度" ;
                String word5 = data.getTotalLong() + "米，已掘进" + data.getFinish() + "米";
                tvInProject.setText(word1);
                tvInLine1.setText(word2);
                tvInLine2.setText(word5);
                container.addView(view1);
                view=view1;
            break;
            case 1:
                View view2 = View.inflate(context, R.layout.fragment_viewpager_item2, null);
                TextView tvTbmNumber1 =(TextView)view2.findViewById(R.id.tv_TbmNumber1);
                TextView tvTbmNumber2 =(TextView)view2.findViewById(R.id.tv_TbmNumber2);
                String word3 = "城轨公司共有盾构机" + data.getTbmNumber() + "台，" + "自有";
                String word6 =data.getFree() + "台,租赁" + data.getLease() + "台" + data.getWorkNow() + "台正在施工";
                tvTbmNumber1.setText(word3);
                tvTbmNumber2.setText(word6);
                container.addView(view2);
                view=view2;
            break;
            case 2:
                View view3 = View.inflate(context, R.layout.fragment_viewpager_item3, null);
                TextView tvFinishLine =(TextView)view3.findViewById(R.id.tv_FinishLine);
                String word4 = data.getFinishProject().get(0).getLineName() + "线路已完成";
                tvFinishLine.setText(word4);
                container.addView(view3);
                view=view3;
            break;
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView((View)object);
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o){return view==o;}
}
/*
public class HomeViewPagerAdapter extends PagerAdapter {
//    @BindView(R.id.tv_InProject)
//    TextView tvInProject;
//    @BindView(R.id.tv_InLine)
//    TextView tvInLine;
//    @BindView(R.id.tv_TbmNumber)
//    TextView tvTbmNumber;
//    @BindView(R.id.tv_FinishLine)
//    TextView tvFinishLine;
    private Context context;
    private Data data;
    public HomeViewPagerAdapter(Context context,Data data){
        this.context=context;
        this.data=data;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int pos = position % (3);
        View view =null;
        switch (pos) {
            case 0:
                View view1 = View.inflate(context, R.layout.fragment_viewpager_item1, null);
                String word1 = "中铁一局在建项目" + data.getInProject() + "个";
                String word2 = "中铁一局在建项目" + data.getInLine() + "个" + "，总长度" + data.getTotalLong() + "米，已掘进" + data.getFinish() + "米";
                tvInProject.setText(word1);
                tvInLine.setText(word2);
                container.addView(view1);
                view=view1;
            break;
            case 1:
                View view2 = View.inflate(context, R.layout.fragment_viewpager_item2, null);


                String word3 = "城轨公司共有盾构机" + data.getTbmNumber() + "台，" + "自有" + data.getFree() + "台,租赁" + data.getLease() + "台" + data.getWorkNow() + "台正在施工";
                tvTbmNumber.setText(word3);
                container.addView(view2);
                view=view2;
            break;
            case 2:
                View view3 = View.inflate(context, R.layout.fragment_viewpager_item3, null);
                String word4 = data.getFinishProject().get(0).getLineName() + "线路已完成";
                tvFinishLine.setText(word4);
                container.addView(view3);
                view=view3;
            break;
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView((View)object);
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o){return view==o;}
}
 */

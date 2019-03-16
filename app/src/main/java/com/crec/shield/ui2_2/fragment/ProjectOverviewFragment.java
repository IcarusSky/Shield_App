package com.crec.shield.ui2_2.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crec.shield.adapter.MyPagerAdapter;
import com.crec.shield.base.BaseFragment;
import com.crec.shield.contract.ProjectOverviewContract;

import com.crec.shield.R;
import com.crec.shield.di.FragmentComponent;
import com.crec.shield.entity.crec22.project.projectoverview.ProjectOverviewData;

import com.crec.shield.presenter.ProjectOverviewPresenter;
import com.crec.shield.utils.SysApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProjectOverviewFragment extends BaseFragment<ProjectOverviewPresenter> implements ProjectOverviewContract.View {



    public Context context;
    private Unbinder unbinder;
    private int images[]={R.mipmap.test1,R.mipmap.test2,R.mipmap.test3,R.mipmap.test5};
    private List<Integer> imageList = new ArrayList<>();


    @BindView(R.id.tx_project)
    TextView txProject;
    @BindView(R.id.tx_plan_time)
    TextView txPlanTime;
    @BindView(R.id.tx_interval_mileage)
    TextView txIntervalMileage;
    @BindView(R.id.tx_condition)
    TextView txCondition;
    @BindView(R.id.tx_line_type)
    TextView txLineType;
    @BindView(R.id.tx_tube)
    TextView txTube;
    @BindView(R.id.tx_invest_resource)
    TextView txInvestResource;
    @BindView(R.id.tx_manager)
    TextView txManager;
    @BindView(R.id.vp_plane_graph)
    ViewPager vpPlaneGraph;
    @BindView(R.id.dot_horizontal)
    LinearLayout dotHorizontal;
    @BindView(R.id.vp_vertical_graph)
    ViewPager vpVerticalGraph;
    @BindView(R.id.dot_horizontal01)
    LinearLayout dotHorizontal01;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    public static Fragment newInstance() {
        ProjectOverviewFragment fragment = new ProjectOverviewFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(this.getLayoutId(), container, false);
        SysApplication.getInstance().addActivity(getActivity());
        unbinder = ButterKnife.bind(this, view);
        mPresenter.attachView(this);
        mPresenter.getData();
        initViewPager();
        initViewPager01();
        return view;
    }

    @Override
    protected void initInject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_overview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showData(ProjectOverviewData projectOverviewData) {

        txProject.setText(projectOverviewData.getProjectName());
        txPlanTime.setText(projectOverviewData.getPlanStartEnd());
        txIntervalMileage.setText(projectOverviewData.getSectionMileage());
        txCondition.setText(projectOverviewData.getCondition());
        //txLineType.setText(projectOverviewData.getLineType());
        txTube.setText("外径"+projectOverviewData.getTube().getDiameter()+"00mm，"+"外径"+projectOverviewData.getTube().getInradium()+"00mm");
        txInvestResource.setText(projectOverviewData.getTbmResource());
        txManager.setText(projectOverviewData.getManager());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showError() {

    }
    /**
     * 平面图轮播
     */
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(getActivity() != null){
                int currentItem = vpPlaneGraph.getCurrentItem();
                int newCurrentItem = currentItem + 1;
                vpPlaneGraph.setCurrentItem(newCurrentItem);
                sendEmptyMessageDelayed(0, 3000);
            }
        }
    };
    public void initViewPager(){
        int current;
        for(current=0;current<images.length;current++){
            imageList.add(images[current]);
        }
        vpPlaneGraph.setAdapter(new MyPagerAdapter(context,imageList));
        vpPlaneGraph.setCurrentItem(1000);
        vpPlaneGraph.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpPlaneGraph.addOnPageChangeListener(new ProjectOverviewFragment.PageIndicator(getContext(), dotHorizontal,4));
        handler.sendEmptyMessageDelayed(0,3000);
        vpPlaneGraph.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v,MotionEvent event){
                int action=event.getAction();
                switch(action){
                    case MotionEvent.ACTION_DOWN:{
                        handler.removeCallbacksAndMessages(null);
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        handler.sendEmptyMessageDelayed(0,3000);
                        break;
                    }
                }
                return false;
            }
        });
    }
    /*小圆点指示器*/
    public class PageIndicator implements ViewPager.OnPageChangeListener {
        private int mPageCount;//页数
        private List<ImageView> mImgList;//保存img总个数
        private int img_select;
        private int img_unSelect;
        public PageIndicator(Context context, LinearLayout linearLayout, int pageCount) {
            this.mPageCount = pageCount;
            mImgList = new ArrayList<>();
            img_select = R.drawable.dot_select;
            img_unSelect = R.drawable.dot_unselect;
            final int imgSize = 25;
            for (int i = 0; i < mPageCount; i++) {
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                //为小圆点左右添加间距
                params.leftMargin = 10;
                params.rightMargin = 10;
                //给小圆点一个默认大小
                params.height = imgSize;
                params.width = imgSize;
                if (i == 0) {
                    imageView.setBackgroundResource(img_select);
                } else {
                    imageView.setBackgroundResource(img_unSelect);
                }
                //为LinearLayout添加ImageView
                linearLayout.addView(imageView, params);
                mImgList.add(imageView);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < mPageCount; i++) {
                //选中的页面改变小圆点为选中状态，反之为未选中
                if ((position % mPageCount) == i) {
                    (mImgList.get(i)).setBackgroundResource(img_select);
                } else {
                    (mImgList.get(i)).setBackgroundResource(img_unSelect);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    }
    /**
     * 纵断面图轮播
     */
    private Handler handler01=new Handler(){
        public void handleMessage(android.os.Message msg) {
            int currentItem = vpVerticalGraph.getCurrentItem();
            int newCurrentItem = currentItem + 1;
            vpVerticalGraph.setCurrentItem(newCurrentItem);
            sendEmptyMessageDelayed(0, 6000);
        }
    };
    public void initViewPager01(){
        int current;
        for(current=0;current<images.length;current++){
            imageList.add(images[current]);
        }
        vpVerticalGraph.setAdapter(new MyPagerAdapter(context,imageList));
        vpVerticalGraph.setCurrentItem(1000);
        vpVerticalGraph.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpVerticalGraph.addOnPageChangeListener(new ProjectOverviewFragment.PageIndicator01(getContext(), dotHorizontal01,4));
        handler01.sendEmptyMessageDelayed(0,6000);
        vpVerticalGraph.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v,MotionEvent event){
                int action=event.getAction();
                switch(action){
                    case MotionEvent.ACTION_DOWN:{
                        handler01.removeCallbacksAndMessages(null);
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        handler01.sendEmptyMessageDelayed(0,6000);
                        break;
                    }
                }
                return false;
            }
        });
    }
    public class PageIndicator01 implements ViewPager.OnPageChangeListener {
        private int mPageCount01;//页数
        private List<ImageView> mImgList01;//保存img总个数
        private int img_select01;
        private int img_unSelect01;
        public PageIndicator01(Context context, LinearLayout linearLayout, int pageCount) {
            this.mPageCount01 = pageCount;
            mImgList01 = new ArrayList<>();
            img_select01 = R.drawable.dot_select;
            img_unSelect01 = R.drawable.dot_unselect;
            final int imgSize = 25;
            for (int i = 0; i < mPageCount01; i++) {
                ImageView imageView01 = new ImageView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                //为小圆点左右添加间距
                params.leftMargin = 10;
                params.rightMargin = 10;
                //给小圆点一个默认大小
                params.height = imgSize;
                params.width = imgSize;
                if (i == 0) {
                    imageView01.setBackgroundResource(img_select01);
                } else {
                    imageView01.setBackgroundResource(img_unSelect01);
                }
                //为LinearLayout添加ImageView
                linearLayout.addView(imageView01, params);
                mImgList01.add(imageView01);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < mPageCount01; i++) {
                //选中的页面改变小圆点为选中状态，反之为未选中
                if ((position % mPageCount01) == i) {
                    (mImgList01.get(i)).setBackgroundResource(img_select01);
                } else {
                    (mImgList01.get(i)).setBackgroundResource(img_unSelect01);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    }


































































































































}


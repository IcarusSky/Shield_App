package com.crec.shield.ui2_2.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.MyPagerAdapter;
import com.crec.shield.adapter.project.HomeViewPagerAdapter;
import com.crec.shield.base.BaseFragment;
import com.crec.shield.contract.CompanyOverviewContract;
import com.crec.shield.contract.ConcernedLinesContract;
import com.crec.shield.di.FragmentComponent;
import com.crec.shield.entity.crec22.project.projectdevice.Data;
import com.crec.shield.presenter.CompanyOverviewPresenter;
import com.crec.shield.ui2_2.activity.ApproachingArrivalActivity;
import com.crec.shield.ui2_2.activity.CameraDataActivity;
import com.crec.shield.ui2_2.activity.CompanyManagementQualityActivity;
import com.crec.shield.ui2_2.activity.ConcernedLinesActivity;
import com.crec.shield.ui2_2.activity.DiggingDataActivity;
import com.crec.shield.ui2_2.activity.ManagementDataActivity;
import com.crec.shield.ui2_2.activity.ParameterListActivity;
import com.crec.shield.ui2_2.activity.RiskSourceDetailsActivity;
import com.crec.shield.utils.SysApplication;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CompanyOverviewFragment extends BaseFragment<CompanyOverviewPresenter> implements CompanyOverviewContract.View {

    @BindView(R.id.concerned_lines)
    Button concernedLines;
    @BindView(R.id.digging_data)
    Button diggingData;
    @BindView(R.id.camera_data)
    Button cameraData;
    @BindView(R.id.management_data)
    Button managementData;
    @BindView(R.id.dot_horizontal)
    LinearLayout dotHorizontal;
    @BindView(R.id.company_overview)
    ViewPager viewPager;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.btn_testLine)
    Button btnTestLine;



    //private int images[]={R.mipmap.test1,R.mipmap.test2,R.mipmap.test3,R.mipmap.test5};
    //private List<Integer> imageList = new ArrayList<>();
    private Unbinder unbinder;
    private Data data01;
    //private List<View>viewList;

    @Override
    protected void initInject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_company_overview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(this.getLayoutId(), container, false);
        SysApplication.getInstance().addActivity(getActivity());
        unbinder = ButterKnife.bind(this, view);
        mPresenter.attachView(this);
        mPresenter.getProjectDeviceData();
        initViewPager();
        initMarqueeView();
        //设置跑马灯的点击事件
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Intent marqueeIntent = new Intent();
                marqueeIntent.setClass(getContext(), ApproachingArrivalActivity.class);
                startActivity(marqueeIntent);
            }
        });

        //在fragment中使用oncreateOptionsMenu时需要在onCrateView中添加此方法，否则不会调用
        /*bCompanyOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ParameterListActivity.class);
                startActivity(intent);
            }
        });*/
        return view;
    }


    @OnClick({R.id.concerned_lines, R.id.digging_data, R.id.camera_data, R.id.management_data,R.id.btn_testLine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.concerned_lines:
                Intent concernedLinesIntent = new Intent();
                concernedLinesIntent.setClass(getContext(), ConcernedLinesActivity.class);
                startActivity(concernedLinesIntent);
                break;
            case R.id.digging_data:
                Intent progressIntent = new Intent();
                progressIntent.setClass(getContext(), DiggingDataActivity.class);
                startActivity(progressIntent);
                break;
            case R.id.camera_data:
                Intent cameraIntent = new Intent();
                cameraIntent.setClass(getContext(), CameraDataActivity.class);
                startActivity(cameraIntent);
                break;
            case R.id.management_data:
                Intent safetyIntent = new Intent();
                safetyIntent.setClass(getContext(), ManagementDataActivity.class);
                startActivity(safetyIntent);
                break;
//            case R.id.btn_testLine:
//                Intent intent = new Intent();
//                intent.setClass(getContext(), CompanyManagementQualityActivity.class);
//                startActivity(intent);
//                break;

        }
    }


    /**
     * 用viewpager实现轮播效果
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if(getActivity() != null){
                int currentItem = viewPager.getCurrentItem();
                int newCurrentItem = currentItem + 1;
                viewPager.setCurrentItem(newCurrentItem);
                sendEmptyMessageDelayed(0, 3000);
            }
        }
    };

    public void initViewPager() {
//        int current;
////        for (current = 0; current < images.length; current++) {
////            imageList.add(images[current]);
////        }
        viewPager.setAdapter(new HomeViewPagerAdapter(getActivity(), data01));
        viewPager.setCurrentItem(1000);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        viewPager.addOnPageChangeListener(new PageIndicator(getContext(), dotHorizontal, 4));

        handler.sendEmptyMessageDelayed(0, 3000);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        handler.removeCallbacksAndMessages(null);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        handler.sendEmptyMessageDelayed(0, 3000);
                        break;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void showProjectDeviceData(Data data) {
        data01=data;
    }

    /**
     * 小圆点指示器
     */
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


    public void initMarqueeView(){
        List<String> info = new ArrayList<>();
        info.add("南宁市轨道交通4号线02标土建8工区工程即将到达");
        info.add("大连地铁3号线刘东区间左线即将到达");
        info.add("西安地铁9号线浐灞区间左线即将到达");
        info.add("无锡地铁6号线南长区间左线即将到达");
        marqueeView.startWithList(info);
//        marqueeView.startWithText("南宁市轨道交通4号线02标土建8工区工程即将到达即将到达即将到达");

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //断开View引用
        mPresenter.detachView();
        unbinder.unbind();
    }
}



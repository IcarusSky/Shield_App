package com.crec.shield.ui2_2.activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.crec.shield.R;
import com.crec.shield.adapter.project.MyFragmentPagerAdapter;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.CompanyManagementQualityContract;
import com.crec.shield.definedviewpager.CustomViewPager;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.crec22.project.companymanagementquality.LineQualityEntity;
import com.crec.shield.presenter.CompanyManagementQualityPresenter;
import com.crec.shield.ui2_2.fragment.threefragment.AmpleListFragment;
import com.crec.shield.ui2_2.fragment.threefragment.BadListFragment;
import com.crec.shield.ui2_2.fragment.threefragment.MediumListFragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
/**
 * @author yyl
 * @data 2019.3.5
 */
public class CompanyManagementQualityActivity extends BaseActivity<CompanyManagementQualityPresenter> implements CompanyManagementQualityContract.View {

    private Unbinder unbinder;
    private CustomViewPager myViewPager;
    List<LineQualityEntity> lineQualityData = new ArrayList<>();
    List<Fragment> fragmentList = new ArrayList<>();//轮播的三个fragment的集合
    //根据评级分开的三个数据
    List<LineQualityEntity> ampleData = new ArrayList<>();
    List<LineQualityEntity> mediumData = new ArrayList<>();
    List<LineQualityEntity> badData = new ArrayList<>();
    @BindView(R.id.quality_chart)//饼图
    PieChart qualityChart;
    @BindView(R.id.dot_horizontal)//小圆点指示器的布局
    LinearLayout dotHorizontal;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_management_quality;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getCompanyManagementQualityData();
        unbinder = ButterKnife.bind(this);
        myViewPager = findViewById(R.id.vp_ProjectQualityList);
        initViewPager();//初始化轮播图
    }

    @Override
    public void showCompanyManagementQualityData(List<LineQualityEntity> data) {
        lineQualityData = data;
        initQualityChart();
    }

    public void initViewPager() {
        intitFragment();//创建三个fragment实现
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);//创建适配器，传参（fragment）
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(0);//设置界面打开的初始页面位置：0
        myViewPager.addOnPageChangeListener(new PagerChangeListener(myViewPager));//根据fragment内容高度，进行页面监听，改变viewpager高度
        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //position:点击滑动的界面位置(viewpager页面的位置为：0,1,2...)
                //positionOffset:滑动页面占整个页面的百分比
                //positionOffsetPixels:屏幕像素位置
            }

            @Override
            public void onPageSelected(int position) {
                //position:滑动完成后页面的位置(0,1,2)，即当前页面的位置编号
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //state:三个值代表当前页面的三个状态
                //0:代表页面静止 1：代表页面开始滑动  2：代表手指从页面上抬起
            }
        });

        //viewpager监听页面的滑动改变
        myViewPager.addOnPageChangeListener(new PageIndicator(getContext(), dotHorizontal, 3));//小圆点指示器的实现
    }

    //向三个fragment传数据，创建fragment
    public void intitFragment() {
        //根据评级分开数据
        for (int i = 0; i < lineQualityData.size(); i++) {
            switch (lineQualityData.get(i).getLevel()) {
                case "优":
                    ampleData.add(lineQualityData.get(i));
                    break;
                case "中":
                    mediumData.add(lineQualityData.get(i));
                    break;
                case "差":
                    badData.add(lineQualityData.get(i));
                    break;
            }
        }
        fragmentList.add(AmpleListFragment.newInstance(ampleData, myViewPager));
        fragmentList.add(MediumListFragment.newInstance(mediumData, myViewPager));
        fragmentList.add(BadListFragment.newInstance(badData, myViewPager));
    }

    //页面监听，改变viewpager的高度
    private class PagerChangeListener implements ViewPager.OnPageChangeListener {

        private CustomViewPager customViewPager;

        public PagerChangeListener(CustomViewPager viewPager) {
            this.customViewPager = viewPager;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            customViewPager.resetHeight(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //小圆点指示器的具体实现
    public class PageIndicator implements ViewPager.OnPageChangeListener {
        private int mPageCount;//页数
        private List<ImageView> mImgList;//保存img总个数
        private int img_select;
        private int img_unSelect;

        //自定义构造器，完成小圆点相关初始化工作
        public PageIndicator(Context context, LinearLayout linearLayout, int pageCount) {
            this.mPageCount = pageCount;
            mImgList = new ArrayList<>();
            img_select = R.drawable.dot_indicator_set;
            img_unSelect = R.drawable.dot_indicator_unset;
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
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            //position为当前页面的编号，for循环确定当前页面的编号，同时设置小圆点的背景
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
        public void onPageScrollStateChanged(int state) {
        }
    }

    /**
     * 初始化饼状图
     */
    private void initQualityChart() {
        int Ample = 0;
        int Medium = 0;
        int Bad = 0;
        for (int i = 0; i < lineQualityData.size(); i++) {
            switch (lineQualityData.get(i).getLevel()) {
                case "优":
                    Ample++;
                    break;
                case "中":
                    Medium++;
                    break;
                case "差":
                    Bad++;
                    break;
            }
        }
        List<PieEntry> string = new ArrayList<>();  //设置饼图数据
        string.add(new PieEntry(Ample, "优"));
        string.add(new PieEntry(Medium, "中"));
        string.add(new PieEntry(Bad, "差"));
        PieDataSet dataSet = new PieDataSet(string, "");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.Safe));
        colors.add(getResources().getColor(R.color.Warning));
        colors.add(getResources().getColor(R.color.Danger));
        dataSet.setColors(colors);//设置颜色
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(0f);
        pieData.setDrawValues(true);
        qualityChart.setData(pieData);
        qualityChart.invalidate();
        qualityChart.setHoleRadius(0);      // 饼状图中间的圆的半径大小
        qualityChart.setTransparentCircleRadius(0);    // 设置中间透明圈的大小
        qualityChart.setExtraOffsets(0, 3, 0, 3);//设置饼状图距离上下左右的偏移量
        Legend mLegend = qualityChart.getLegend();  //设置比例图
        mLegend.setEnabled(false); // 设置禁用比例块
        Description mDescription = qualityChart.getDescription();  // 设置右下方的文字描述
        mDescription.setEnabled(false);  // 设置禁用右下方的文字描述
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        super.onBackPressed();
        finish();
    }
}

package com.crec.shield.ui2_2.activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crec.shield.R;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.RiskSourceDetailsContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.crec22.project.management.RiskDetailsData;
import com.crec.shield.presenter.RiskSourceDetailsPresenter;
import com.crec.shield.adapter.MyPagerAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
public class RiskSourceDetailsActivity extends BaseActivity<RiskSourceDetailsPresenter> implements RiskSourceDetailsContract.View {
    private Unbinder unbinder;
    private int images[]={R.mipmap.test1,R.mipmap.test2,R.mipmap.test3,R.mipmap.test5};
    private List<Integer> imageList = new ArrayList<>();
    @BindView(R.id.risk_name)  //风险名称
    TextView riskName;
    @BindView(R.id.risk_level)  //风险等级
    TextView riskLevel;
    @BindView(R.id.effect_ring_number)  //影响环号
    TextView effectRingNumber;
    @BindView(R.id.risk)  //风险里程
    TextView risk;
    @BindView(R.id.risk_mileage)  //风险临近关系
    TextView riskMileage;
    @BindView(R.id.feed_status)  //反馈状态
    TextView feedStatus;
    @BindView(R.id.risk_events)  //风险事件说明
    TextView riskEvents;
    @BindView(R.id.risk_consequence)  //主要风险及后果
    TextView riskConsequence;
    @BindView(R.id.feed_user)  //反馈人
    TextView feedUser;
    @BindView(R.id.greate_date)  //反馈时间
    TextView greateDate;
    @BindView(R.id.vp_risk_image)  //风险图片
    ViewPager vpRiskImage;
    @BindView(R.id.dot_horizontal)
    LinearLayout dotHorizontal;
    @BindView(R.id.home_page)
    TextView homePage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_risk_source_details;
    }

    @Override
    public void showData(RiskDetailsData riskDetailsData) {
        riskName.setText(riskDetailsData.getRiskName());
        riskLevel.setText(riskDetailsData.getRiskLevel() + "");
        effectRingNumber.setText(riskDetailsData.getStartRegion() + "环-" + riskDetailsData.getEndRegion() + "环");
        risk.setText(riskDetailsData.getRisk() + "");
        riskMileage.setText(riskDetailsData.getRiskMileage());
        feedStatus.setText(riskDetailsData.getFeedStatus()+"");
        riskEvents.setText(riskDetailsData.getRiskEvents());
        riskConsequence.setText(riskDetailsData.getRiskConsequence());
        feedUser.setText(riskDetailsData.getFeedUser());
        greateDate.setText(riskDetailsData.getGreateDate());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        mPresenter.getData();
        initViewPager();
    }

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.iv_back)//返回键
    public void onViewClicked() {
        super.onBackPressed();
        finish();
    }
    /*用viewpager实现轮播效果*/
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            int currentItem = vpRiskImage.getCurrentItem();
            int newCurrentItem = currentItem + 1;
            vpRiskImage.setCurrentItem(newCurrentItem);
            sendEmptyMessageDelayed(0, 3000);
        }
    };
    public void initViewPager(){
        int current;
        for(current=0;current<images.length;current++){
            imageList.add(images[current]);
        }
        vpRiskImage.setAdapter(new MyPagerAdapter(this,imageList));
        vpRiskImage.setCurrentItem(1000);
        vpRiskImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        vpRiskImage.addOnPageChangeListener(new PageIndicator(getContext(), dotHorizontal,4));
        handler.sendEmptyMessageDelayed(0,3000);
        vpRiskImage.setOnTouchListener(new View.OnTouchListener(){
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































}



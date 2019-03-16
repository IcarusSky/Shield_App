package com.crec.shield.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.crec.shield.adapter.ViewPagerAdapter;
import com.crec.shield.R;
import com.crec.shield.ui.fragment.FollowProjectFragment;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.crec.shield.global.StaticConstant.TITLES_ID_1;

public class FollowActivity extends BaseActivity implements View.OnClickListener{

    private static final String ACTIVITY_TAG="FollowActivity";

    @BindView(R.id.btn_left)
    ImageView mBtnBack;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.tab_viewpager)
    ViewPager viewPager;

    public Fragment[] mFragmentArrays = new Fragment[1];
    public String[] mTabTitles = new String[1];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_follow);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.radio));
        }
        ButterKnife.bind(this);
        initFragmentView();
        mBtnBack.setOnClickListener(this);
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    @Override
    public void onClick(View v) {
        Logger.d(v.getId());
        switch (v.getId()) {
            case R.id.btn_left:
                finish();
                break;
        }
    }
    private void initFragmentView() {
        Log.v(FollowActivity.ACTIVITY_TAG, "Enter initFragmentView()");
        mTabTitles[0] = TITLES_ID_1;
        mFragmentArrays[0] = FollowProjectFragment.newInstance();

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        PagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),mFragmentArrays,mTabTitles);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
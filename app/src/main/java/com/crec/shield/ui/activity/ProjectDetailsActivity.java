package com.crec.shield.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.crec.shield.R;
import com.crec.shield.adapter.ViewPagerAdapter;
import com.crec.shield.entity.project.ProjectDetailsEntity;
import com.crec.shield.entity.project.ProjectDetailsResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.ui.fragment.ProjectDetailsCameraFragment;
import com.crec.shield.ui.fragment.ProjectDetailsProgressFragment;
import com.crec.shield.ui.fragment.ProjectDetailsQualityFragment;
import com.crec.shield.ui.fragment.ProjectDetailsRiskFragment;
import com.crec.shield.ui.fragment.ProjectDetailsStatusFragment;
import com.crec.shield.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import okhttp3.Call;
import okhttp3.Response;


import static com.crec.shield.global.StaticConstant.TITLES_ID_1;
import static com.crec.shield.global.StaticConstant.TITLES_ID_2;
import static com.crec.shield.global.StaticConstant.TITLES_ID_3;
import static com.crec.shield.global.StaticConstant.TITLES_ID_4;
import static com.crec.shield.global.StaticConstant.TITLES_ID_5;


public class ProjectDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final String ACTIVITY_TAG = "ProjectDetailsActivity";
    @BindView(R.id.toolbar_title)
    Spinner mToolbar_title;
    @BindView(R.id.btn_left)
    ImageView mBtnBack;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.tab_viewpager)
    ViewPager viewPager;

    public Fragment[] mFragmentArrays = new Fragment[5];
    public String[] mTabTitles = new String[5];
    public Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectdetails);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.radio));
        }
        ButterKnife.bind(this);

        HandlerThread ht = new HandlerThread("myHandlerThread");
        ht.start();
        mHandler = new Handler(ht.getLooper()){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == 0)
                init();
            }
        };
        mHandler.sendEmptyMessage(0);

        mBtnBack.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        finish();
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

    private void init(){
        final List<String> com1 = new ArrayList<String>();
        String mToken = SPUtils.get((AppConstant.LOGINSTATUS.token),"").toString();
        String projectId = SPUtils.get((AppConstant.PROJECT.projectId),"").toString();
        final String lineId = SPUtils.get((AppConstant.PROJECT.lineId),"").toString();
        OkGo.post(Url.BASE_URL+ Url.OVERVIEW_CAMERA_LINE_CONDITION)
                .params("token",mToken)
                .params("projectId",projectId)
                .execute(new JsonCallback<ProjectDetailsResponse>() {

                    @Override
                    public void onSuccess(ProjectDetailsResponse projectDetailsResponse, Call call, Response response) {
                        if (projectDetailsResponse.getCode() == 1){
                            ArrayAdapter<String> adapter = null;
                            int index = 0;
                            final List<ProjectDetailsEntity> projectDetailsEntity= projectDetailsResponse.getData();
                            for (ProjectDetailsEntity project:projectDetailsEntity){
                                com1.add(project.getConditionName()+"（"+project.getTag()+"）");
                                if(lineId.equals(project.getConditionId()))
                                {
                                    index = projectDetailsEntity.indexOf(project);
                                }
                            }
                            adapter = new ArrayAdapter<String>(ProjectDetailsActivity.this, R.layout.spinner_layout_white, com1);  //下拉框字体颜色
                            adapter.setDropDownViewResource(R.layout.spinner_layout_black);
                            mToolbar_title.setAdapter(adapter);
                            if(lineId==null || lineId.length()==0) {
                                SPUtils.put(AppConstant.PROJECT.lineId, projectDetailsEntity.get(index).getConditionId());
                            }
                            mToolbar_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    for (ProjectDetailsEntity project:projectDetailsEntity){
                                        if (com1.get(position).equals(project.getConditionName()+"（"+project.getTag()+"）")){
                                            SPUtils.put(AppConstant.PROJECT.lineTag, project.getTag());
                                            SPUtils.put(AppConstant.PROJECT.lineId,project.getConditionId());
                                            initFragmentView();
                                        }
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    initFragmentView();
                                }
                            });
                            mToolbar_title.setSelection(index,true);
                        }
                    }
                });
    }

    private void initFragmentView() {
        Log.v(ProjectDetailsActivity.ACTIVITY_TAG, "Enter initFragmentView()");
        mTabTitles[0] = TITLES_ID_1;
        mTabTitles[1] = TITLES_ID_2;
        mTabTitles[2] = TITLES_ID_3;
        mTabTitles[3] = TITLES_ID_4;
        mTabTitles[4] = TITLES_ID_5;
        mFragmentArrays[0] = ProjectDetailsProgressFragment.newInstance();
        mFragmentArrays[1] = ProjectDetailsStatusFragment.newInstance();
        mFragmentArrays[2] = ProjectDetailsCameraFragment.newInstance();
        mFragmentArrays[3] = ProjectDetailsRiskFragment.newInstance();
        mFragmentArrays[4] = ProjectDetailsQualityFragment.newInstance();

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        PagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentArrays, mTabTitles);
        viewPager.setAdapter(pagerAdapter);
//        viewPager.setOffscreenPageLimit(1);//设置预加载个数
        tabLayout.setupWithViewPager(viewPager);
        onResumeff();
    }

    protected void onResumeff() {
        int id = getIntent().getIntExtra("id", 0);
        String keyArg = getIntent().getStringExtra("key");
        String keyRiskArg = getIntent().getStringExtra("keyRisk");
        if (id == 1) {
            viewPager.setCurrentItem(1);
        }
        if (id == 2) {
            viewPager.setCurrentItem(2);
        }
        if (id == 3) {
            viewPager.setCurrentItem(3);
        }
        if (!(keyArg == null)) {
            viewPager.setCurrentItem(0);
        }
        if (!(keyRiskArg == null)) {
            viewPager.setCurrentItem(3);
        }
        super.onResume();
    }
}
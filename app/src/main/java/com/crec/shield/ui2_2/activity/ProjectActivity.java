package com.crec.shield.ui2_2.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.ProjectContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.project.ProjectDetailsEntity;
import com.crec.shield.entity.project.ProjectDetailsResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.presenter.ProjectPresenter;
import com.crec.shield.service.DemoIntentService;
import com.crec.shield.service.DemoPushService;

import com.crec.shield.ui.activity.ProjectDetailsActivity;
import com.crec.shield.ui.activity.SplashActivity;
import com.crec.shield.ui2_2.fragment.ProjectCameraDataFragment;
import com.crec.shield.ui2_2.fragment.ProjectExcavationDataFragment;
import com.crec.shield.ui2_2.fragment.ProjectManagementFragment;
import com.crec.shield.ui2_2.fragment.ProjectOverviewFragment;

import com.crec.shield.ui2_2.fragment.ProjectPersonalFragment;
import com.crec.shield.utils.SPUtils;
import com.igexin.sdk.PushManager;
import com.lzy.okgo.OkGo;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;


public class ProjectActivity extends BaseActivity<ProjectPresenter> implements ProjectContract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.toolbar_title)
    Spinner mToolbar_title;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigation;


    //默认选择第一个fragment
    private int lastSelectedPosition = 0;

    private ProjectOverviewFragment projectOverviewFragment;
    private ProjectExcavationDataFragment projectExcavationDataFragment;
    private ProjectCameraDataFragment projectCameraDataFragment;
    private ProjectManagementFragment projectManagementFragment;
    private ProjectPersonalFragment projectPersonalFragment;

    private Fragment[] fragments;

    private Context context;
    private Unbinder unbinder;
    public Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        // 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);

        // 设置界面全屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.radio));
        }
        unbinder = ButterKnife.bind(this);
        context = ProjectActivity.this;

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


    }

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_project;
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
                            adapter = new ArrayAdapter<String>(ProjectActivity.this, R.layout.spinner_layout_white, com1);  //下拉框字体颜色
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
                                            initFragments();
                                        }
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    initFragments();
                                }
                            });
                            mToolbar_title.setSelection(index,true);
                        }
                    }
                });
    }


    private void initFragments() {
        //监听切换事件
        navigation.setOnNavigationItemSelectedListener(this);
        //平均布局
        setItemType(navigation);
     /*   //添加角标消息数
        setAddNumber();*/
        projectOverviewFragment = new ProjectOverviewFragment();
        projectExcavationDataFragment = new ProjectExcavationDataFragment();
        projectCameraDataFragment = new ProjectCameraDataFragment();
        projectManagementFragment = new ProjectManagementFragment();
        projectPersonalFragment = new ProjectPersonalFragment();
        fragments = new Fragment[]{projectOverviewFragment, projectExcavationDataFragment, projectCameraDataFragment,projectManagementFragment,projectPersonalFragment};
        //默认提交第一个
        if(lastSelectedPosition == 0){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_container, projectOverviewFragment)//添加
                    .show(projectOverviewFragment)//展示
                    .commit();//提交
            textTitle.setText("项目概览");
            textTitle.setVisibility(View.VISIBLE);
            mToolbar_title.setVisibility(View.GONE);
        }else if(lastSelectedPosition == 1){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_container, projectExcavationDataFragment)//添加
                    .show(projectExcavationDataFragment)//展示
                    .commit();//提交
            textTitle.setVisibility(View.GONE);
            mToolbar_title.setVisibility(View.VISIBLE);
        }else if(lastSelectedPosition == 2){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_container, projectCameraDataFragment)//添加
                    .show(projectCameraDataFragment)//展示
                    .commit();//提交
            textTitle.setVisibility(View.GONE);
            mToolbar_title.setVisibility(View.VISIBLE);
        }else if(lastSelectedPosition == 3){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_container, projectManagementFragment)//添加
                    .show(projectManagementFragment)//展示
                    .commit();//提交
            textTitle.setVisibility(View.GONE);
            mToolbar_title.setVisibility(View.VISIBLE);
        }else if(lastSelectedPosition == 4){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_container, projectPersonalFragment)//添加
                    .show(projectPersonalFragment)//展示
                    .commit();//提交
            textTitle.setText(SPUtils.get(AppConstant.LOGINSTATUS.company_name,"").toString());
            textTitle.setVisibility(View.VISIBLE);
            mToolbar_title.setVisibility(View.GONE);
        }
    }

//    private void setAddNumber() {
//        //获取整个的NavigationView
//        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
//        //获取所添加的每一个Tab，并给第三个Tab添加消息角标
//        View tabView = menuView.getChildAt(2 );
//        BottomNavigationItemView itemView = (BottomNavigationItemView) tabView;
//        //加载我们的角标布局View，新创建的一个布局
//        View badgeView = LayoutInflater.from(this).inflate(R.layout.number_badge, menuView, false);
//        TextView number=badgeView.findViewById(R.id.msg_number);
//        //设置显示的内容
//        number.setText("99");
//        //添加到Tab上
//        itemView.addView(badgeView);
//    }

    /**
     * 切换Fragment
     * @param lastIndex 上个显示Fragment的索引
     * @param index     需要显示的Fragment的索引
     */
    private void setDefaultFragment(int lastIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.fl_container, fragments[index]);
        }
        //需要展示fragment下标的位置
        //commit：安排该事务的提交。这一承诺不会立即发生;它将被安排在主线程上，以便在线程准备好的时候完成。
        //commitAllowingStateLoss：与 commit类似，但允许在活动状态保存后执行提交。这是危险的，因为如果Activity需要从其状态恢复，
        // 那么提交就会丢失，因此，只有在用户可以意外地更改UI状态的情况下，才可以使用该提交
        transaction.show(fragments[index]).commit();
    }


    /**
     * 切换事件
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_overview:
                if (0 != lastSelectedPosition) {
                    setDefaultFragment(lastSelectedPosition, 0);
                    textTitle.setText("项目概览");
                    textTitle.setVisibility(View.VISIBLE);
                    mToolbar_title.setVisibility(View.GONE);
                    lastSelectedPosition = 0;
                }
                return true;
            case R.id.action_excavation:
                if (1 != lastSelectedPosition) {
                    setDefaultFragment(lastSelectedPosition, 1);
                    textTitle.setVisibility(View.GONE);
                    mToolbar_title.setVisibility(View.VISIBLE);
                    lastSelectedPosition = 1;
                }
                return true;
            case R.id.action_camera:
                if (2 != lastSelectedPosition) {
                    setDefaultFragment(lastSelectedPosition, 2);
                    textTitle.setVisibility(View.GONE);
                    mToolbar_title.setVisibility(View.VISIBLE);
                    lastSelectedPosition = 2;
                }
                return true;
            case R.id.action_management:
                if (3 != lastSelectedPosition) {
                    setDefaultFragment(lastSelectedPosition, 3);
                    textTitle.setVisibility(View.GONE);
                    mToolbar_title.setVisibility(View.VISIBLE);
                    lastSelectedPosition = 3;
                }
                return true;
            case R.id.action_personal:
                if (4 != lastSelectedPosition) {
                    setDefaultFragment(lastSelectedPosition, 4);
                    textTitle.setText("个人中心");
                    textTitle.setVisibility(View.VISIBLE);
                    mToolbar_title.setVisibility(View.GONE);
                    lastSelectedPosition = 4;
                }
                return true;
        }
        return false;
    }

    /**
     * 防止超过3个fragment布局不平分
     */
    @SuppressLint("RestrictedApi")
    private  void setItemType(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}


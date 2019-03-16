package com.crec.shield.ui.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.crec.shield.R;
import com.crec.shield.adapter.ViewPagerAdapter;
import com.crec.shield.entity.message.MessageItemData;
import com.crec.shield.entity.message.MessageItemResponse;
import com.crec.shield.entity.project.ProjectDetailsEntity;
import com.crec.shield.entity.project.ProjectDetailsResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.ui.fragment.ProjectDetailsProgressFragment;
import com.crec.shield.ui.fragment.ProjectDetailsStatusFragment;
import com.crec.shield.ui.fragment.ProjectDetailsCameraFragment;
import com.crec.shield.ui.fragment.ProjectDetailsRiskFragment;
import com.crec.shield.ui.fragment.ProjectDetailsQualityFragment;
import com.crec.shield.ui2_2.activity.SettingActivity;
import com.crec.shield.utils.DBManager;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.SqliteHelper;
import com.jauker.widget.BadgeView;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import static com.crec.shield.global.StaticConstant.WITHOUT_LINE_DATA;

public class ProjectUserDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final String ACTIVITY_TAG = "ProjectUserDetailsAct";

    @BindView(R.id.toolbar_title)
    Spinner mToolbar_title;
    @BindView(R.id.iv_left)
    ImageView mBtnLeft;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.tab_viewpager)
    ViewPager viewPager;
    @BindView(R.id.project)
    Button mProject;
    @BindView(R.id.message)
    Button mMessage;
    @BindView(R.id.badge_view)
    BadgeView badge_view;

    public Fragment[] mFragmentArrays = new Fragment[5];
    public String[] mTabTitles = new String[5];

    private List<MessageItemData> messageItemData;
    private SqliteHelper sqlHelper;
    private SQLiteDatabase db;
    private String token;
    private String projectId;
    private String lineId;
    private Integer Moveize;
    private Integer RiskLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectuserdetails);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.radio));
        }

        sqlHelper = DBManager.getInstance(this);
        db = sqlHelper.getWritableDatabase();
        token = sqlHelper.queryUser(db);
        projectId = sqlHelper.queryUserProjectId(db);

        selectLine();

        mToolbar_title = (Spinner) findViewById(R.id.toolbar_title);

        mProject.setOnClickListener(this);
        mMessage.setOnClickListener(this);
        mBtnLeft.setOnClickListener(this);

        initUnReadMessage();
    }

    /**
     * 显示底部导航栏消息上的未读消息个数
     */

    private void initUnReadMessage() {

        token = sqlHelper.queryUser(db);

        String AlarmMoveize = (String) SPUtils.get(AppConstant.MESSAGE.AlarmMoveize, "");
        String AlarmRiskLevel = (String) SPUtils.get(AppConstant.MESSAGE.AlarmRiskLevel, "");

        if (null == AlarmMoveize || AlarmMoveize == "") {
            Moveize = 80;
        } else {
            Moveize = Integer.parseInt(AlarmMoveize);
        }
        if (null == AlarmRiskLevel || AlarmMoveize == "") {
            RiskLevel = 0;
        } else {
            RiskLevel = Integer.parseInt(AlarmRiskLevel);
        }

        OkGo.post(Url.BASE_URL + Url.MESSAGE_MESSAGE_LIST)
                .params("token", token)
                .execute(new JsonCallback<MessageItemResponse>() {
                    @Override
                    public void onSuccess(MessageItemResponse messageItemResponse, Call call, Response response) {
                        messageItemData = messageItemResponse.getData();

                        Collections.sort(messageItemData, new Comparator<MessageItemData>() {
                            @Override
                            public int compare(MessageItemData messageItemData, MessageItemData t1) {
                                return (t1.getTime()).compareTo(messageItemData.getTime());
                            }
                        });
                        Integer isRead = 3;
                        Integer unReadCount = 0;
                        Integer messageType = (Integer) SPUtils.get(AppConstant.MESSAGE.MessageType,0);
                        for (MessageItemData message : messageItemData) {


                            if (message.getType() == 1 && Math.abs(message.getMoveSize()) >= Moveize && messageType == 0) {
                                sqlHelper.MessageInit(db, message);  // 查看dqlite是否有这条数据，没有就在本数据库插入这条数据
                                isRead = sqlHelper.queryMessageStatus(db, message);

                                if (isRead == 1) {
                                    unReadCount++;
                                }
                            }

                            if (message.getType() == 2 && message.getRiskLevel() >= RiskLevel && messageType == 1) {
                                sqlHelper.MessageInit(db, message);  // 查看dqlite是否有这条数据，没有就在本数据库插入这条数据
                                isRead = sqlHelper.queryMessageStatus(db, message);

                                if (isRead == 1) {
                                    unReadCount++;
                                }
                            }
                        }
                            badge_view.setTextSize(8);
                            badge_view.setBadgeCount(unReadCount);

                    }
                });
    }

    @Override
    public void onClick(View v) {
        Logger.d(v.getId());
        switch (v.getId()) {
            case R.id.project:
                Intent intent1 = new Intent(ProjectUserDetailsActivity.this, ProjectUserDetailsActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent1);
                break;
            case R.id.message:
                Intent intent2 = new Intent(ProjectUserDetailsActivity.this, ProjectMessageActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent2);
                break;
            case R.id.iv_left:
                Intent intent3 = new Intent(ProjectUserDetailsActivity.this, SettingActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent3);
                break;
        }
    }

    private void initFragmentView() {
        Log.v(ProjectUserDetailsActivity.ACTIVITY_TAG, "Enter initFragmentView()");
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
        viewPager.setOffscreenPageLimit(1);//设置预加载个数
        tabLayout.setupWithViewPager(viewPager);
        onResumeff();
    }

    private void selectLine() {
        final List<String> com1 = new ArrayList<String>();
        lineId = SPUtils.get((AppConstant.PROJECT.lineId), "").toString();
        OkGo.post(Url.BASE_URL + Url.OVERVIEW_CAMERA_LINE_CONDITION)
                .params("token", token)
                .params("projectId", projectId)
                .execute(new JsonCallback<ProjectDetailsResponse>() {
                    @Override
                    public void onSuccess(ProjectDetailsResponse projectDetailsResponse, Call call, Response response) {
                        if (projectDetailsResponse.getCode() == 1) {
                            ArrayAdapter<String> adapter = null;
                            int index = 0;
                            final List<ProjectDetailsEntity> projectDetailsEntity = projectDetailsResponse.getData();
                            if (projectDetailsEntity.size() > 0) {
                                lineId = projectDetailsEntity.get(0).getConditionId();
                                for (ProjectDetailsEntity project : projectDetailsEntity) {
                                    com1.add(project.getConditionName() + "（" + project.getTag() + "）");
                                    if (lineId.equals(project.getConditionId())) {
                                        index = projectDetailsEntity.indexOf(project);
                                    }
                                }
                                adapter = new ArrayAdapter<String>(ProjectUserDetailsActivity.this, R.layout.spinner_layout_white, com1);  //下拉框字体颜色
                                adapter.setDropDownViewResource(R.layout.spinner_layout_black);
                                mToolbar_title.setAdapter(adapter);
                                mToolbar_title.setSelection(index);
                                if (lineId == null || lineId.length() == 0) {
                                    lineId = projectDetailsEntity.get(index).getConditionId();
                                    SPUtils.put(AppConstant.PROJECT.lineId, projectDetailsEntity.get(index).getConditionId());
                                }

                                mToolbar_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        for (ProjectDetailsEntity project : projectDetailsEntity) {
                                            if (com1.get(position).equals(project.getConditionName() + "（" + project.getTag() + "）")) {
                                                SPUtils.put(AppConstant.PROJECT.lineId, project.getConditionId());
                                                initFragmentView();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        SPUtils.put(AppConstant.PROJECT.lineId, projectDetailsEntity.get(0).getConditionId());
                                        initFragmentView();
                                    }
                                });
                            } else if (projectDetailsEntity.size() == 0) {
                                Toast toast = Toast.makeText(getApplicationContext(), WITHOUT_LINE_DATA, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            }
                        }
                    }
                });
    }

    protected void onResumeff() {
        int id = getIntent().getIntExtra("id", 0);
        if (id == 1) {
            viewPager.setCurrentItem(1);
        }
        if (id == 3) {
            viewPager.setCurrentItem(3);
        }
        super.onResume();
    }
}
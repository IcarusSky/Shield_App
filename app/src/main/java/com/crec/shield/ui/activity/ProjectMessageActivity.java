package com.crec.shield.ui.activity;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.MessageAdapter;
import com.crec.shield.entity.message.MessageItemData;
import com.crec.shield.entity.message.MessageItemResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.ui2_2.activity.MessageSettingActivity;
import com.crec.shield.ui2_2.activity.SettingActivity;
import com.crec.shield.utils.DBManager;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.SqliteHelper;
import com.jauker.widget.BadgeView;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.WARN_MOVESIZE;
import static com.crec.shield.global.StaticConstant.WARN_RISK;
import static com.crec.shield.global.StaticConstant._MOVESIZE;
import static com.crec.shield.global.StaticConstant._MOVESIZ_UNIT;
import static com.crec.shield.global.StaticConstant._RISK_LEVEL;

public class ProjectMessageActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener {
    private static final String ACTIVITY_TAG = "ProjectMessageActivity";

    @BindView(R.id.iv_left)
    ImageView mBtnLeft;
    @BindView(R.id.iv_right)
    ImageView mBtnRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.project)
    Button mProject;
    @BindView(R.id.message)
    Button mMessage;
    @BindView(R.id.toolbar_title)
    TextView tvTitle;
    @BindView(R.id.tableNULL)
    TextView tableNull;
    @BindView(R.id.guide_shift)
    Button mbtnGuideShift;
    @BindView(R.id.source_of_risk)
    Button mbtnSourceOfRisk;
    @BindView(R.id.badge_view)
    BadgeView badge_view;

    private List<String> warnId, warnName, warnTime, warnContent,project,line;
    private List<Integer> image, warnStatus;
    private MessageAdapter recycleAdapter;
    private List<MessageItemData> messageItemData;
    private Integer Moveize;
    private Integer RiskLevel;

    private SqliteHelper sqlHelper;
    private SQLiteDatabase db;
    private Integer messageType = 0;
    private Integer flag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_projectmessage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.radio));
        }
        ButterKnife.bind(this);

        tvTitle.setText(SPUtils.get(AppConstant.LOGINSTATUS.project_name, "").toString());
        sqlHelper = DBManager.getInstance(this);
        db = sqlHelper.getWritableDatabase();

        mProject.setOnClickListener(this);    // 初始化底部导航栏项目按钮监听
        mMessage.setOnClickListener(this);    // 初始化底部导航栏消息按钮监听
        mBtnLeft.setOnClickListener(this);   // 个人中心按钮监听
        mBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProjectMessageActivity.this, MessageSettingActivity.class);
                startActivityForResult(intent, 98);
            }
        });

        mbtnGuideShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbtnGuideShift.setTextColor(Color.parseColor("#FFFFFF"));
                mbtnGuideShift.setBackgroundColor(Color.parseColor("#5A7BEF"));
                mbtnSourceOfRisk.setTextColor(getResources().getColor(R.color.font_black));
                mbtnSourceOfRisk.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                messageType = 0;
                SPUtils.put(AppConstant.MESSAGE.MessageType,messageType);
                initData();
            }
        });
        mbtnSourceOfRisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbtnSourceOfRisk.setTextColor(Color.parseColor("#FFFFFF"));
                mbtnSourceOfRisk.setBackgroundColor(Color.parseColor("#5A7BEF"));
                mbtnGuideShift.setTextColor(getResources().getColor(R.color.font_black));
                mbtnGuideShift.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                messageType = 1;
                SPUtils.put(AppConstant.MESSAGE.MessageType,messageType);
                initData();
            }
        });

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.autoRefresh();

    }

    /**
     * @company vinelinx
     * @author buji
     * @date 2018.9.10
     * @description 初始化消息界面
     */
    private void initData() {
        Log.v(ProjectMessageActivity.ACTIVITY_TAG, "Enter initData()");
        String AlarmMoveize = (String) SPUtils.get(AppConstant.MESSAGE.AlarmMoveize, "");
        String AlarmRiskLevel = (String) SPUtils.get(AppConstant.MESSAGE.AlarmRiskLevel, "");
        warnId = new ArrayList<>();
        warnName = new ArrayList<>();
        warnTime = new ArrayList<>();
        warnContent = new ArrayList<>();
        warnStatus = new ArrayList<>();
        project = new ArrayList<>();
        line = new ArrayList<>();
        image = new ArrayList<>();
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
                .params("token", SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString())
                .execute(new JsonCallback<MessageItemResponse>() {
                    @Override
                    public void onSuccess(MessageItemResponse messageItemResponse, Call call, Response response) {
                        refreshLayout.finishRefresh();
                        messageItemData = messageItemResponse.getData();

                        Collections.sort(messageItemData, new Comparator<MessageItemData>() {
                            @Override
                            public int compare(MessageItemData messageItemData, MessageItemData t1) {
                                return (t1.getTime()).compareTo(messageItemData.getTime());
                            }
                        });
                        Integer isRead = 1;
                        Integer unReadCount = 0;
                        String messageRiskLevel = null;
                        for (MessageItemData message : messageItemData) {

                            // 偏移量预警
                            if (message.getType() == 1 && Math.abs(message.getMoveSize()) >= Moveize && messageType == 0) {
                                warnId.add(message.getMessageId());
                                warnName.add(WARN_MOVESIZE);
                                warnTime.add(message.getTime());
                                warnContent.add(message.getProjectName() + _MOVESIZE + message.getMoveSize() + _MOVESIZ_UNIT);
                                warnStatus.add(message.getType());
                                project.add(message.getProjectId());
                                line.add(message.getLineId());
                                sqlHelper.MessageInit(db, message);  // 查看dqlite是否有这条数据，没有就在本数据库插入这条数据
                                isRead = sqlHelper.queryMessageStatus(db, message);
                                if (isRead == 0) {
                                    image.add(R.mipmap.img_msg_item_none);
                                } else {
                                    image.add(R.mipmap.img_msg_item);
                                    unReadCount++;
                                }
                            }

                            // 风险预警
                            if (message.getType() == 2 && message.getRiskLevel() >= RiskLevel && messageType == 1) {

                                if (message.getRiskLevel() == 1) {
                                    messageRiskLevel = "Ⅰ";
                                }
                                if (message.getRiskLevel() == 2) {
                                    messageRiskLevel = "Ⅱ";
                                }
                                if (message.getRiskLevel() == 3) {
                                    messageRiskLevel = "Ⅲ";
                                }
                                if (message.getRiskLevel() == 4) {
                                    messageRiskLevel = "Ⅳ";
                                }

                                warnId.add(message.getMessageId());
                                warnName.add(WARN_RISK);
                                warnTime.add(message.getTime());
                                warnContent.add(message.getProjectName() + _RISK_LEVEL + messageRiskLevel);
                                warnStatus.add(message.getType());
                                project.add(message.getProjectId());
                                line.add(message.getLineId());
                                sqlHelper.MessageInit(db, message);  // 查看dqlite是否有这条数据，没有就在本数据库插入这条数据
                                isRead = sqlHelper.queryMessageStatus(db, message);
                                if (isRead == 0) {
                                    image.add(R.mipmap.img_msg_item_none);
                                } else {
                                    image.add(R.mipmap.img_msg_item);
                                    unReadCount++;
                                }
                            }
                        }
                        recycleAdapter = new MessageAdapter(ProjectMessageActivity.this, warnName, warnTime, warnContent, warnStatus, image, project, line);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(ProjectMessageActivity.this);
                        //设置布局管理器
                        recyclerView.setLayoutManager(layoutManager);
                        //设置为垂直布局，这也是默认的
                        layoutManager.setOrientation(OrientationHelper.VERTICAL);
                        //设置Adapter
                        recyclerView.setAdapter(recycleAdapter);
                        //设置增加或删除条目的动画
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        //调用方法,传入一个接口回调
                        recycleAdapter.setItemClickListener(new MessageAdapter.MyItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                if (image.get(position) == R.mipmap.img_msg_item) {
                                    image.set(position, R.mipmap.img_msg_item_none);
                                    flag += 1;
                                }
                                Intent intent = new Intent();

                                if (warnStatus.get(position) == 1) {
                                    intent.setClass(ProjectMessageActivity.this, ProjectUserDetailsActivity.class);
                                    SPUtils.put(AppConstant.PROJECT.projectId, project.get(position));
                                    SPUtils.put(AppConstant.PROJECT.lineId, line.get(position));
                                    sqlHelper.changeMessageToDB(db, warnId.get(position));
                                    intent.putExtra("id", 1);
                                } else if (warnStatus.get(position) == 2) {
                                    intent.setClass(ProjectMessageActivity.this, ProjectUserDetailsActivity.class);
                                    SPUtils.put(AppConstant.PROJECT.projectId, project.get(position));
                                    SPUtils.put(AppConstant.PROJECT.lineId, line.get(position));
                                    sqlHelper.changeMessageToDB(db, warnId.get(position));
                                    intent.putExtra("id", 3);
                                }
                                startActivity(intent);
                            }
                        });

                            badge_view.setTextSize(8);
                            badge_view.setBadgeCount(unReadCount);

                        if (warnName.size() == 0) {
                            tableNull.setVisibility(View.VISIBLE);
                        } else {
                            tableNull.setVisibility(View.GONE);
                        }
                        SPUtils.put(AppConstant.MESSAGE.MessageCount, unReadCount);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        Logger.d(v.getId());
        switch (v.getId()) {
            case R.id.project:
                Intent intent2 = new Intent(ProjectMessageActivity.this, ProjectUserDetailsActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent2);
                break;
            case R.id.message:
                Intent intent3 = new Intent(ProjectMessageActivity.this, ProjectMessageActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent3);
                break;
            case R.id.iv_left:
                Intent intent4 = new Intent(ProjectMessageActivity.this, SettingActivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent4);
                break;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        initData();
    }

}
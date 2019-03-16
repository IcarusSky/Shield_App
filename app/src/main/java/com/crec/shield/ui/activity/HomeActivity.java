package com.crec.shield.ui.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.crec.shield.R;
import com.crec.shield.entity.message.MessageItemData;
import com.crec.shield.entity.message.MessageItemResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.service.DemoIntentService;
import com.crec.shield.service.DemoPushService;
import com.crec.shield.ui.fragment.MainFragment;
import com.crec.shield.ui.fragment.MsgFragment;
import com.crec.shield.ui.fragment.ProjectFragment;
import com.crec.shield.utils.DBManager;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.SqliteHelper;
import com.crec.shield.weidget.BottomBar;
import com.igexin.sdk.PushManager;
import com.jauker.widget.BadgeView;
import com.lzy.okgo.OkGo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by hahaliu on 2018/10/1.
 */

public class HomeActivity extends BaseActivity implements MsgFragment.MyListener {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;
    @BindView(R.id.badge_view)
    BadgeView badgeView;

    private Context context;
    private Integer Moveize;
    private Integer RiskLevel;
    private String mtoken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
    private List<MessageItemData> messageItemData;
    private SqliteHelper sqlHelper;
    private SQLiteDatabase db;
    private Integer unReadCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        ButterKnife.bind(this);

        context = HomeActivity.this;

        sqlHelper = DBManager.getInstance(context);
        db = sqlHelper.getWritableDatabase();

        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#878787", "#5A7BEF")
                .addItem(MainFragment.class,
                        "总览",
                        R.mipmap.img_main_none,
                        R.mipmap.img_main_select)
                .addItem(ProjectFragment.class,
                        "项目",
                        R.mipmap.img_pro_none,
                        R.mipmap.img_pro_select)
                .addItem(MsgFragment.class,
                        "消息",
                        R.mipmap.img_msg_none,
                        R.mipmap.img_msg_select)
                .build();

        initUnReadMessage();
    }

    @Override
    public void sendContent(Integer info) {
        badgeView.setTextSize(8);
        badgeView.setBadgeCount(info);
    }

    // 初始化未读信息个数
    private void initUnReadMessage() {
        String AlarmMoveize = ((String) SPUtils.get(AppConstant.MESSAGE.AlarmMoveize, "")).equals("") ? "80" : (String) SPUtils.get(AppConstant.MESSAGE.AlarmMoveize, "");
        String AlarmRiskLevel = ((String) SPUtils.get(AppConstant.MESSAGE.AlarmRiskLevel, "")).equals("") ? "1" : (String) SPUtils.get(AppConstant.MESSAGE.AlarmRiskLevel, "");
        if (null == AlarmMoveize || AlarmMoveize.equals("")) {
            Moveize = 80;
        } else {
            Moveize = Integer.parseInt(AlarmMoveize);
        }
        if (null == AlarmRiskLevel || AlarmRiskLevel.equals("")) {
            RiskLevel = 1;
        } else {
            RiskLevel = Integer.parseInt(AlarmRiskLevel);
        }
//        if()
        OkGo.post(Url.BASE_URL + Url.MESSAGE_MESSAGE_LIST)
                .params("token", mtoken)
                .execute(new JsonCallback<MessageItemResponse>() {
                    @Override
                    public void onSuccess(MessageItemResponse messageItemResponse, Call call, Response response) {
                        if (messageItemResponse.getCode() == 1) {
                            messageItemData = messageItemResponse.getData();

                            Collections.sort(messageItemData, new Comparator<MessageItemData>() {
                                @Override
                                public int compare(MessageItemData messageItemData, MessageItemData t1) {
                                    return (t1.getTime() == null ? "":t1.getTime()).compareTo(messageItemData.getTime() == null ? "":messageItemData.getTime());
                                }
                            });
                            Integer isRead = 3;

                            for (MessageItemData message : messageItemData) {

                                if (message.getType() == 1 && Math.abs(message.getMoveSize()) >= Moveize) {
                                    sqlHelper.MessageInit(db, message);  // 查看dqlite是否有这条数据，没有就在本数据库插入这条数据
                                    isRead = sqlHelper.queryMessageStatus(db, message);
                                    if (isRead == 1) {
                                        unReadCount++;
                                    }
                                }
                                if (message.getType() == 2 && message.getRiskLevel() <= RiskLevel) {
                                    sqlHelper.MessageInit(db, message);  // 查看dqlite是否有这条数据，没有就在本数据库插入这条数据
                                    isRead = sqlHelper.queryMessageStatus(db, message);
                                    if (isRead == 1) {
                                        unReadCount++;
                                    }
                                }
                            }
                                badgeView.setTextSize(8);
                                badgeView.setBadgeCount(unReadCount);
                                SPUtils.put(AppConstant.MESSAGE.MessageCount, unReadCount);

                        }
                    }
                });
        onResumeff();
    }

    protected void onResumeff() {
        int setting = getIntent().getIntExtra("setting", 0);
        if (setting == 1) {
            bottomBar.MsgSettingReturn();
        }
        super.onResume();
    }

}

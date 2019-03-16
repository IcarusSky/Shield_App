package com.crec.shield.ui2_2.activity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.crec.shield.R;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.CompanyContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.message.MessageItemData;
import com.crec.shield.entity.message.MessageItemResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.presenter.CompanyPresenter;
import com.crec.shield.ui2_2.fragment.CompanyMessageFragment;
import com.crec.shield.ui2_2.fragment.CompanyOverviewFragment;
import com.crec.shield.ui2_2.fragment.CompanyPersonalFragment;
import com.crec.shield.ui2_2.fragment.CompanyProjectFragment;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.SqliteHelper;
import com.jauker.widget.BadgeView;
import com.lzy.okgo.OkGo;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

public class CompanyActivity extends BaseActivity<CompanyPresenter> implements CompanyContract.View, BottomNavigationView.OnNavigationItemSelectedListener, CompanyMessageFragment.MyListener {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigation;
    @BindView(R.id.badge_view)
    BadgeView badgeView;

    private Fragment[] fragments;
    private CompanyOverviewFragment companyOverviewFragment;
    private CompanyProjectFragment companyProjectFragment;
    private CompanyMessageFragment companyMessageFragment;
    private CompanyPersonalFragment companyPersonalFragment;

    private Integer Moveize;
    private Integer RiskLevel;
    private String mtoken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
    private List<MessageItemData> messageItemData;
    private Integer unReadCount = 0;
    //默认选择第一个fragment
    private int lastSelectedPosition = 0;
    private Unbinder unbinder;

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_company;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // 设置界面全屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.radio));
        }

        unbinder = ButterKnife.bind(this);
        initFragments();
        initUnReadMessage();
    }

    private void initFragments() {
        //监听切换事件
        navigation.setOnNavigationItemSelectedListener(this);
        //平均布局
        setItemType(navigation);
        companyOverviewFragment = new CompanyOverviewFragment();
        companyProjectFragment = new CompanyProjectFragment();
        companyMessageFragment = new CompanyMessageFragment();
        companyPersonalFragment = new CompanyPersonalFragment();
        fragments = new Fragment[]{companyOverviewFragment, companyProjectFragment, companyMessageFragment, companyPersonalFragment};
        //默认提交第一个
        if(lastSelectedPosition == 0){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_container, companyOverviewFragment)//添加
                    .show(companyOverviewFragment)//展示
                    .commit();//提交
        }else if(lastSelectedPosition == 1){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_container, companyProjectFragment)//添加
                    .show(companyProjectFragment)//展示
                    .commit();//提交
        }else if(lastSelectedPosition == 2){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_container, companyMessageFragment)//添加
                    .show(companyMessageFragment)//展示
                    .commit();//提交
        }else if(lastSelectedPosition == 3){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_container, companyPersonalFragment)//添加
                    .show(companyPersonalFragment)//展示
                    .commit();//提交
        }


    }

    @Override
    public void sendContent(Integer info) {
        badgeView.setTextSize(8);
        badgeView.setBadgeCount(info);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main:
                if (0 != lastSelectedPosition) {
                    setDefaultFragment(lastSelectedPosition, 0);
                    lastSelectedPosition = 0;
                }
                return true;
            case R.id.action_project:
                if (1 != lastSelectedPosition) {
                    setDefaultFragment(lastSelectedPosition, 1);
                    lastSelectedPosition = 1;
                }
                return true;
            case R.id.action_message:
                if (2 != lastSelectedPosition) {
                    setDefaultFragment(lastSelectedPosition, 2);
                    lastSelectedPosition = 2;
                }
                return true;
            case R.id.action_personal:
                if (3 != lastSelectedPosition) {
                    setDefaultFragment(lastSelectedPosition, 3);
                    lastSelectedPosition = 3;
                }
                return true;
        }
        return false;
    }

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
    protected void onDestroy() {
        super.onDestroy();
//        unbinder.unbind();
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
//                                    sqlHelper.MessageInit(db, message);  // 查看dqlite是否有这条数据，没有就在本数据库插入这条数据
//                                    isRead = sqlHelper.queryMessageStatus(db, message);
                                    isRead = message.getIsRead();
                                    if (isRead == 1) {
                                        unReadCount++;
                                    }
                                }
                                if (message.getType() == 2 && message.getRiskLevel() <= RiskLevel) {
//                                    sqlHelper.MessageInit(db, message);  // 查看dqlite是否有这条数据，没有就在本数据库插入这条数据
//                                    isRead = sqlHelper.queryMessageStatus(db, message);
                                    isRead = message.getIsRead();
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
            setDefaultFragment(lastSelectedPosition, 2);
            lastSelectedPosition = 2;
        }
        super.onResume();
    }
}

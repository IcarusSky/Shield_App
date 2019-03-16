package com.crec.shield.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.crec.shield.R;
import com.crec.shield.demo.LoginActivity;
import com.crec.shield.entity.login.DataLogin;
import com.crec.shield.entity.login.LoginResponse;
import com.crec.shield.entity.login.UserInfo;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.DialogCallback;
import com.crec.shield.network.Url;
import com.crec.shield.service.DemoApplication;
import com.crec.shield.service.DemoIntentService;
import com.crec.shield.service.DemoPushService;
import com.crec.shield.ui2_2.activity.ProjectActivity;
import com.crec.shield.utils.DBManager;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.SqliteHelper;
import com.crec.shield.utils.SysApplication;
import com.crec.shield.utils.T;
import com.crec.shield.utils.UserManage;
import com.igexin.sdk.PushManager;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.DB_TABLE_HISTORY;
import static com.crec.shield.global.StaticConstant.DB_TABLE_MESSAGE;
import static com.crec.shield.global.StaticConstant.DB_TABLE_USER;

/**
 * Created by diaokaibin@gmail.com on 2017/7/14.
 */

public class SplashActivity extends BaseActivity {


    private static final String TAG = "GetuiSdkDemo";
    private static final String MASTERSECRET = "AhxX0jh5WmAa5O9b7EIiS3";
    private static final String ACTIVITY_TAG="GetuiSdkDemoActivity";

    // SDK服务是否启动.
    private boolean isServiceRunning = true;
    private Context context;

    private String appkey = "";
    private String appsecret = "";
    private String appid = "";
    private String user;
    private String password;
    private String cid;
    private UserInfo userInfo;
    private DataLogin dataLogin;
    private SqliteHelper sqlHelper;
    private SQLiteDatabase db;
    private Unbinder unbinder;
    private static final int REQUEST_PERMISSION = 0;

    // DemoPushService.class 自定义服务名称, 核心服务
    private Class userPushService = DemoPushService.class;

    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";

    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @BindView(R.id.iv_splash)
    ImageView mIvSplash;

    public static boolean isForeground = false;

    private static final int GO_HOME = 0;//去主页
    private static final int GO_LOGIN = 1;//去登录页

    // 跳转判断
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME://去主页
                    load();
                    break;
                case GO_LOGIN://去登录页
                    Intent intent2 = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        context = this;
        isServiceRunning = true;

        //创建数据库
        sqlHelper = new SqliteHelper(this);
        db = sqlHelper.getWritableDatabase();

        // 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        // 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);

        cid = PushManager.getInstance().getClientid(getApplicationContext());

        DemoApplication.pushActivity = this;
        parseManifests();

        Log.d(TAG, "initializing sdk...");

        SysApplication.getInstance().addActivity(this);
        resquestPermission();

        checkVersion(); // 检查表格是否存在

        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                goLoginActivity();
            }
        }.start();

        if (UserManage.getInstance().hasUserInfo(this))//自动登录判断，SharePrefences中有数据，则跳转到主页，没数据则跳转到登录页
        {
            userInfo = UserManage.getInstance().getUserInfo(context);
            user = userInfo.getUserName();
            password = userInfo.getPassword();
            mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
        } else {
            mHandler.sendEmptyMessageAtTime(GO_LOGIN, 2000);
        }

    }

    private void goLoginActivity() {

        Log.v(SplashActivity.ACTIVITY_TAG, "Enter goLoginActivity()");

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    private void checkVersion() {
        checkDBTables();
    }

    private void parseManifests() {
        String packageName = getApplicationContext().getPackageName();
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                appid = appInfo.metaData.getString("PUSH_APPID");
                appsecret = appInfo.metaData.getString("PUSH_APPSECRET");
                appkey = appInfo.metaData.getString("PUSH_APPKEY");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    *   根据用户名密码登陆
    * */
    private void load() {

        if ((!TextUtils.isEmpty(user)) && (!TextUtils.isEmpty(password))) {

            OkGo.post(Url.BASE_URL + Url.LOGIN)
                    .params("loginName", user)
                    .params("password", password)
                    .params("cid", cid)
                    .execute(new DialogCallback<LoginResponse>(SplashActivity.this) {
                        @Override
                        public void onSuccess(LoginResponse loginResponse, Call call, Response response) {
                            if (loginResponse.getCode()== 1) {
                                UserManage.getInstance().saveUserInfo(SplashActivity.this, user, password);
                                dataLogin = loginResponse.getData();
                                String mToken = dataLogin.getToken();
                                String code = dataLogin.getCode();
                                String username= dataLogin.getUsername();
                                Integer type= dataLogin.getType();
                                SPUtils.put(AppConstant.LOGINSTATUS.code, code);
                                SPUtils.put(AppConstant.LOGINSTATUS.token, mToken);
                                SPUtils.put(AppConstant.LOGINSTATUS.username, username);
                                SPUtils.put(AppConstant.LOGINSTATUS.password, password);
                                SPUtils.put(AppConstant.LOGINSTATUS.type, type);

                                Intent intent = new Intent();
                                if (type == 0 || type == 1 || type == 3) {
                                    String company_name = dataLogin.getCompany_name();
                                    SPUtils.put(AppConstant.LOGINSTATUS.company_name,company_name);
//                                    intent.setClass(SplashActivity.this, HomeActivity.class);
                                    intent.setClass(SplashActivity.this, ProjectActivity.class);
                                }else if(type == 2 || type == 4) {
                                    String project_id= dataLogin.getProject_id();
                                    String project_name = dataLogin.getProject_name();
                                    SPUtils.put(AppConstant.LOGINSTATUS.project_id, project_id);
                                    SPUtils.put(AppConstant.LOGINSTATUS.project_name,project_name);
//                                    intent.setClass(SplashActivity.this, ProjectUserDetailsActivity.class);
                                    intent.setClass(SplashActivity.this, ProjectActivity.class);
                                }

                                // 清除数据库中缓存部分数据
                                clearDBData();

                                // 向数据库中初始化部分基础数据
                                initBaseDataToDB();

                                startActivity(intent);
                                finish();
                            }
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            T.showLong(SplashActivity.this,R.string.login_failure);
                        }
                    });

              /*  Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                T.showLong(SplashActivity.this, getResources().getString(R.string.login_succuss));
            finish();*/
        } else {
            T.showLong(this, "请输入账号或密码");
        }
    }

    public void  clearDBData(){
        sqlHelper = DBManager.getInstance(this);
        db = sqlHelper.getWritableDatabase();
        if (null != sqlHelper && null != db) {
            clearDutyInfo(sqlHelper, db);      //  清除用户信息
        }
    }

    private void clearDutyInfo(SqliteHelper sqlHelper, SQLiteDatabase db) {
        boolean isExist = sqlHelper.checkUserInfoExist(db);
        if (isExist) {
            String sql = "delete from " + DB_TABLE_USER ;
            DBManager.execSQL(db, sql.toString());
        }
    }

    public void initBaseDataToDB(){
        if (null != sqlHelper && null != db) {
            sqlHelper.UserInit(db, dataLogin);
        }
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    private void resquestPermission() {

        Log.v(SplashActivity.ACTIVITY_TAG, "Enter resquestPermission()");

        List<String> permissionList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(SplashActivity.this, permissions, 1);
        } else {
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.v(SplashActivity.ACTIVITY_TAG, "Enter onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:

                if (grantResults.length > 0) {
                    Intent localIntent = new Intent();
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= 9) {
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                    } else if (Build.VERSION.SDK_INT <= 8) {
                        localIntent.setAction(Intent.ACTION_VIEW);
                        localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                        localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
                    }

                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "安装成功", Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }
                    }
                } else {
                    finish();
                }
                break;
        }
        if (requestCode == REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
            } else {
                Log.e(TAG, "We highly recommend that you need to grant the special permissions before initializing the SDK, otherwise some "
                        + "functions will not work");
                PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.gray1));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DemoApplication.payloadData.delete(0, DemoApplication.payloadData.length());

    }

    private void checkDBTables() {
        SqliteHelper sqlHelper = DBManager.getInstance(this);
        SQLiteDatabase db = sqlHelper.getWritableDatabase();

        if (!sqlHelper.isTableExist(db, DB_TABLE_USER)) {
            if (null != sqlHelper && null != db) {
                sqlHelper.createUserTable(db);
            }
        }

        if (!sqlHelper.isTableExist(db, DB_TABLE_HISTORY)) {
            if (null != sqlHelper && null != db) {
                sqlHelper.createHistoryTable(db);
            }
        }

        if (!sqlHelper.isTableExist(db, DB_TABLE_MESSAGE)) {
            if (null != sqlHelper && null != db) {
                sqlHelper.createMessageTable(db);
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {

    }
}

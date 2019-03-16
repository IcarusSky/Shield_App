package com.crec.shield.ui.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.CameraItemAdapter;
import com.crec.shield.adapter.overview.camera.MyExpandableListViewAdapter;
import com.crec.shield.entity.overview.camera.CameraResponse;
import com.crec.shield.entity.project.camera.Camera;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.utils.CameraUtils;
import com.crec.shield.utils.PlayCameraSurfaceView;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.SqliteHelper;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_PICCFG_V30;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.WITHOUT_DATA;

/**
 * @author wangqi
 * @company vinelinx
 * @data 2018/08/18
 * @description 盾构app总览界面的实时视频
 */
public class CameraActivity extends BaseActivity implements View.OnClickListener {

    private static final String ACTIVITY_TAG = "CameraActivity";

    @BindView(R.id.btn_left) // 返回箭头id
    ImageView mBtnBack;
    @BindView(R.id.toolbar_title) //顶部名称
    TextView textTitle;
    @BindView(R.id.RecycleNULL) //顶部名称
    TextView RecycleNULL;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private List<CameraResponse.CameraEntity> parentEntities = new ArrayList<>();
    private String mToken;

    SqliteHelper sqlHelper;
    SQLiteDatabase db;

    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;
    private int m_iStartChan = 0; // start channel no
    private int m_iChanNum = 0; // channel number
    private int m_iLogID = -1;

    private List<Integer> camera_photo = new ArrayList<>();
    private List<Integer> player = new ArrayList<>();
    private List<String> camera_name = new ArrayList<>();
    private List<String> cameraIds = new ArrayList<>();
    private List<Integer> star = new ArrayList<>();
    private List<NET_DVR_PREVIEWINFO> previewInfos = new ArrayList();
    private List<Camera> cameraData = new ArrayList<>();
    private static PlayCameraSurfaceView[] playViews;
    private CameraItemAdapter cameraItemAdapter;
    private RecyclerView rcv;
    private GridLayoutManager manager;
    int size = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_camera);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.radio));
        }
        ButterKnife.bind(this);

        mBtnBack.setOnClickListener(this);
        sqlHelper = new SqliteHelper(this);
        db = sqlHelper.getWritableDatabase();
        mToken = sqlHelper.queryUser(db);

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                size = 0;
                initialData();
            }
        });
        rcv = (RecyclerView) findViewById(R.id.rcv);
        manager = new GridLayoutManager(getApplicationContext(), 2);
        rcv.setLayoutManager(manager);
        refreshLayout.autoRefresh();
    }

    /**
     * 初始化视频数据
     */
    private void initialData() {
        Log.v(CameraActivity.ACTIVITY_TAG, "Enter initialData()");
        OkGo.post(Url.BASE_URL + Url.OVERVIEW_FOLLOW_CAMERA)
                .params("token", mToken)
                .execute(new JsonCallback<CameraResponse>() {
                    @Override
                    public void onSuccess(CameraResponse cameraResponse, Call call, Response response) {
                        if (cameraResponse.getCode() == 1) {
                            refreshLayout.finishRefresh();
                            parentEntities = cameraResponse.getData();
                            if(parentEntities.size() == 0){
                                RecycleNULL.setText(WITHOUT_DATA);
                                RecycleNULL.setTextSize(15);
                                RecycleNULL.setTextColor(getResources().getColor(R.color.color_Aluminum));
                                RecycleNULL.setVisibility(View.VISIBLE);
                            }else if( parentEntities.size() != size ){
                                camera_name = new ArrayList<>();
                                camera_photo = new ArrayList<>();
                                player = new ArrayList<>();
                                cameraData = new ArrayList<Camera>();
                                Camera _camobj = null;
                                int k=0;
                                for (CameraResponse.CameraEntity camera : parentEntities) {
                                    if (!CameraUtils.initeSdk()) {
                                        return;
                                    }
                                    loginNormalDevice(camera.getIp(), camera.getPort(), camera.getUsername(), camera.getPassword());

                                    int chan_id = camera.getChannel();
                                    _camobj = new Camera();

                                    NET_DVR_PICCFG_V30 net_dvr_piccfg_v30 = new NET_DVR_PICCFG_V30();
                                    HCNetSDK.getInstance().NET_DVR_GetDVRConfig(m_iLogID,HCNetSDK.NET_DVR_GET_PICCFG_V30,chan_id,net_dvr_piccfg_v30);
                                    byte[] b=net_dvr_piccfg_v30.sChanName;
                                    String[] chanName = new String[2];
                                    try {
                                        chanName = new String(b,"GB2312").split("\0", 2);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    if(null == chanName||null == chanName[0]||chanName[0].length()==0){
                                        //continue;
                                    }
                                    String lineTag = (String)SPUtils.get(AppConstant.PROJECT.lineTag, "");
                                    NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
                                    previewInfo.lChannel = chan_id;
                                    previewInfo.dwStreamType = 0; // substream
                                    previewInfo.bBlocked = 1;
                                    cameraIds.add(camera.getId());
                                    previewInfos.add(previewInfo);
                                    camera_name.add(chanName[0]);
                                    camera_photo.add(R.drawable.camera_background);
                                    player.add(R.drawable.camera_play);
                                    star.add(1);
                                    _camobj.setChannel(chan_id + "");
                                    _camobj.setIp(camera.getIp());
                                    _camobj.setPort(camera.getPort());
                                    _camobj.setUsername(camera.getUsername());
                                    _camobj.setPassword(camera.getPassword());
                                    _camobj.setMlogId(m_iLogID);
                                    _camobj.setLogIdSign(k);
                                    cameraData.add(_camobj);
                                    k++;
                                }
                                playViews = new PlayCameraSurfaceView[k];
                                size = k;
                                for(int i=0; i<k; i++){
                                    ChangeSingleSurFace(true, i);
                                }
                                String token = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
                                cameraItemAdapter = new CameraItemAdapter(null, getApplicationContext(), token, cameraIds, cameraData, camera_photo, player, camera_name, star, previewInfos, playViews);
                                rcv.setAdapter(cameraItemAdapter);
                                RecycleNULL.setVisibility(View.GONE);
                            }
                        } else {
                            refreshLayout.finishRefresh();
                            RecycleNULL.setText(WITHOUT_DATA);
                            RecycleNULL.setTextSize(15);
                            RecycleNULL.setTextColor(getResources().getColor(R.color.color_Aluminum));
                            RecycleNULL.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void loginNormalDevice(String ip, int port, String username, String password) {
        m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
        if (null == m_oNetDvrDeviceInfoV30) {
            return;
        }
        // call NET_DVR_Login_v30 to login on, port 8000 as default
        m_iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(ip, port,
                username, password, m_oNetDvrDeviceInfoV30);
        if (m_iLogID < 0) {
            return;
        }
        if (m_oNetDvrDeviceInfoV30.byChanNum > 0) {
            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartChan;
            m_iChanNum = m_oNetDvrDeviceInfoV30.byChanNum;
        } else if (m_oNetDvrDeviceInfoV30.byIPChanNum > 0) {
            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartDChan;
            m_iChanNum = m_oNetDvrDeviceInfoV30.byIPChanNum
                    + m_oNetDvrDeviceInfoV30.byHighDChanNum * 256;
        }
    }

    private void ChangeSingleSurFace(boolean bSingle, int i) {
        DisplayMetrics metric = new DisplayMetrics();

        if (playViews[i] == null) {
            playViews[i] = new PlayCameraSurfaceView(getApplicationContext());
            playViews[i].setParam(500);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = 0;
            params.leftMargin = 0;
            params.gravity = Gravity.CENTER;
            //addContentView(playViews[i], params);
            playViews[i].setVisibility(View.INVISIBLE);

        }

        if (bSingle) {
            playViews[i].setVisibility(View.INVISIBLE);
            playViews[i].setParam(500 * 2);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
//            params.bottomMargin = playViews[3].getM_iHeight() - (3 / 2)
//                    * playViews[3].getM_iHeight();
            params.topMargin = 0;
            params.leftMargin = 0;
            // params.
            params.gravity = Gravity.CENTER;
            playViews[i].setLayoutParams(params);
            playViews[i].setVisibility(View.VISIBLE);
        } else {
            playViews[i].setVisibility(View.VISIBLE);

            playViews[i].setParam(metric.widthPixels);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = 0;
            params.leftMargin = (0 % 2) * playViews[i].getM_iWidth();
            params.gravity = Gravity.CENTER;
            playViews[i].setLayoutParams(params);
        }

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

    @Override
    public void onResume(){
        super.onResume();
        refreshLayout.autoRefresh();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        for(int i = 0 ; i < size ; i++){
            playViews[i].logout();
        }
    }
}
package com.crec.shield.ui2_2.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.CameraItemProjectAdapter;
import com.crec.shield.base.BaseFragment;
import com.crec.shield.contract.ProjectCameraDataContract;
import com.crec.shield.di.FragmentComponent;
import com.crec.shield.entity.crec22.project.management.RiskDetailsData;
import com.crec.shield.entity.project.ProjectDetailsEntity;
import com.crec.shield.entity.project.ProjectDetailsResponse;
import com.crec.shield.entity.project.camera.Camera;
import com.crec.shield.entity.project.camera.ProjectCameraResp;
import com.crec.shield.entity.project.camera.ProjectCameraResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.presenter.ProjectCameraDataPresenter;
import com.crec.shield.ui.activity.ProjectDetailsActivity;
import com.crec.shield.ui.fragment.ProjectDetailsCameraFragment;
import com.crec.shield.ui2_2.fragment.ProjectOverviewFragment;
import com.crec.shield.utils.CameraUtils;
import com.crec.shield.utils.PlayCameraSurfaceView;
import com.crec.shield.utils.SPUtils;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_PICCFG_V30;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.WITHOUT_DATA;


public class ProjectCameraDataFragment extends BaseFragment<ProjectCameraDataPresenter> implements ProjectCameraDataContract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.RecycleNULL)
    TextView RecycleNULL;

    private static final String ACTIVITY_TAG = "ProjectCameraDataFrag";

    private List<Integer> camera_photo = new ArrayList<>();
    private List<Integer> player = new ArrayList<>();
    private List<String> camera_name = new ArrayList<>();
    private List<Integer> star = new ArrayList<>();
    private List<String> cameraIds = new ArrayList<>();
    private List<NET_DVR_PREVIEWINFO> previewInfos = new ArrayList();

    private CameraItemProjectAdapter cameraItemProjectAdapter;
    private GridLayoutManager manager;
    private int m_iStartChan = 0; // start channel no
    private int m_iChanNum = 0; // channel number
    private int m_iLogID = -1;
    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;
    private List<Camera> cameraData = new ArrayList<>();
    private static PlayCameraSurfaceView[] playViews;
//    private static PlayCameraSurfaceView[] playViews;

    private boolean isViewCreated;  //Fragment的View加载完毕的标记
    private boolean isUIVisible;    //Fragment对用户可见的标记

    private int num = 0;
    private int sum = 0;
    private List<Integer> logId = new ArrayList<>();

    private View v;
    public Context context;
    private Unbinder unbinder;

    @Override
    protected void initInject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_camera;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    public static Fragment newInstance() {
        ProjectCameraDataFragment fragment = new ProjectCameraDataFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(this.getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, v);
        manager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(manager);
        initData();
        Log.e("onCreateView","onCreateView");
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
            Log.e("lazyLoad","lazyLoad");
        } else {
            for (int i = 0; i < sum; i++) {
                playViews[i].stopPreview();
                playViews[i].setM_lUserID(logId.get(i));
                playViews[i].logout();
            }
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            refreshLayout.setEnableLoadMore(false);
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    sum = 0;
                    num = 0;
                    initData();
                }
            });
            refreshLayout.autoRefresh();
            for (int i = 0; i < sum; i++) {
                playViews[i].setM_lUserID(logId.get(i));
                playViews[i].startPreview(m_iLogID, previewInfos.get(i).lChannel);
            }
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;

        }else {
            for (int i = 0; i < sum; i++) {
                playViews[i].stopPreview();
                playViews[i].setM_lUserID(logId.get(i));
                playViews[i].logout();
            }
        }
    }

    private void initData() {
        Log.v(ACTIVITY_TAG, "Enter initData()");

        final String mtoken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
        final String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
//        final String lineId = "53b3abe82c01411da20db55a4a96e1d7";

        cameraData.clear();
        camera_photo.clear();
        player.clear();
        camera_name.clear();
        star.clear();
        OkGo.post(Url.BASE_URL + Url.PROJECT_CAMERA)
                .params("token", mtoken)
                .params("lineId", lineId)
                .execute(new JsonCallback<ProjectCameraResponse>() {

                    @Override
                    public void onSuccess(ProjectCameraResponse CameraResponse, Call call, Response response) {
                        refreshLayout.finishRefresh();
                        if (CameraResponse.getCode() == 1) {
                            List<ProjectCameraResp> tempcameraData = CameraResponse.getData();
                            if ( tempcameraData.size() == 0){
                                RecycleNULL.setText(WITHOUT_DATA);
                                RecycleNULL.setTextSize(15);
                                if (isAdded()) {
                                    RecycleNULL.setTextColor(getResources().getColor(R.color.color_Aluminum));
                                }
                                RecycleNULL.setVisibility(View.VISIBLE);
                            } else {
                                camera_name.clear();
                                camera_photo.clear();
                                player.clear();
                                star.clear();
                                cameraIds.clear();
                                logId.clear();
                                if (!CameraUtils.initeSdk()) {
                                    return;
                                }
                                cameraData.clear();
                                Camera _camobj = null;
                                if(tempcameraData .size() != num){
                                    for (ProjectCameraResp camera : tempcameraData) {

                                        int k = 0;
                                        num++;
                                        loginNormalDevice(camera.getIp(), camera.getPort(), camera.getUsername(), camera.getPassword());
                                        int chan_id = 0;
                                        for (int i = 0; i < m_iChanNum; i++) {
                                            _camobj = new Camera();
                                            chan_id = m_iStartChan + i;
                                            NET_DVR_PICCFG_V30 net_dvr_piccfg_v30 = new NET_DVR_PICCFG_V30();
                                            HCNetSDK.getInstance().NET_DVR_GetDVRConfig(m_iLogID, HCNetSDK.NET_DVR_GET_PICCFG_V30, chan_id, net_dvr_piccfg_v30);
                                            byte[] b = net_dvr_piccfg_v30.sChanName;
                                            String[] chanName = new String[2];
                                            try {
                                                chanName = new String(b, "GB2312").split("\0", 2);
                                                //chanName =new String(b,"GB2312");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            if (null == chanName || null == chanName[0] || chanName[0].length() == 0) {
                                                continue;
                                            }
                                            String lineTag = (String) SPUtils.get(AppConstant.PROJECT.lineTag, "");
                                            if (lineTag.contains("右线")) {
                                                if (!chanName[0].contains("左线")) {
                                                    NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
                                                    previewInfo.lChannel = chan_id;
                                                    previewInfo.dwStreamType = 0; // substream
                                                    previewInfo.bBlocked = 1;

                                                    previewInfos.add(previewInfo);
                                                    camera_name.add(chanName[0]);
                                                    camera_photo.add(R.drawable.camera_background);

                                                    player.add(R.drawable.camera_play);

                                                    if (camera.getChannel().size() == 0) {
                                                        star.add(0);
                                                    } else {
                                                        boolean isBreak = false;
                                                        for (Integer channel : camera.getChannel()) {
                                                            if (channel == chan_id) {
                                                                star.add(1);
                                                                isBreak = true;
                                                                break;
                                                            }
                                                        }
                                                        if (!isBreak) {
                                                            star.add(0);
                                                        }
                                                    }
                                                    _camobj.setChannel(chan_id + "");
                                                    _camobj.setIp(camera.getIp());
                                                    _camobj.setPort(camera.getPort());
                                                    _camobj.setUsername(camera.getUsername());
                                                    _camobj.setPassword(camera.getPassword());
                                                    _camobj.setMlogId(m_iLogID);
                                                    _camobj.setLogIdSign(k);
                                                    logId.add(m_iLogID);
                                                    cameraData.add(_camobj);
                                                    cameraIds.add(camera.getCameraId());
                                                    k++;
                                                }
                                            } else {
                                                if (!chanName[0].contains("右线")) {
                                                    NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
                                                    previewInfo.lChannel = chan_id;
                                                    previewInfo.dwStreamType = 0; // substream
                                                    previewInfo.bBlocked = 1;

                                                    previewInfos.add(previewInfo);
                                                    camera_name.add(chanName[0]);
                                                    camera_photo.add(R.drawable.camera_background);

                                                    player.add(R.drawable.camera_play);

                                                    if (camera.getChannel().size() == 0) {
                                                        star.add(0);
                                                    } else {
                                                        boolean isBreak = false;
                                                        for (Integer channel : camera.getChannel()) {
                                                            if (channel == chan_id) {
                                                                star.add(1);
                                                                isBreak = true;
                                                                break;
                                                            }
                                                        }
                                                        if (!isBreak) {
                                                            star.add(0);
                                                        }
                                                    }
                                                    _camobj.setChannel(chan_id + "");
                                                    _camobj.setIp(camera.getIp());
                                                    _camobj.setPort(camera.getPort());
                                                    _camobj.setUsername(camera.getUsername());
                                                    _camobj.setPassword(camera.getPassword());
                                                    _camobj.setMlogId(m_iLogID);
                                                    _camobj.setLogIdSign(k);
                                                    logId.add(m_iLogID);
                                                    cameraData.add(_camobj);
                                                    cameraIds.add(camera.getCameraId());
                                                    k++;
                                                }
                                            }
                                        }
                                        sum = k+sum;
                                    }
                                    playViews = new PlayCameraSurfaceView[sum];
                                    for(int i=0; i<sum; i++){
                                        ChangeSingleSurFace(true, i);
                                    }
                                    String token = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
                                    cameraItemProjectAdapter = new CameraItemProjectAdapter(ProjectCameraDataFragment.this, context, token, cameraIds, cameraData, camera_photo, player, camera_name, star, previewInfos, playViews);
                                    recyclerView.setAdapter(cameraItemProjectAdapter);
                                    RecycleNULL.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            RecycleNULL.setText(WITHOUT_DATA);
                            RecycleNULL.setTextSize(15);
                            if (isAdded()) {
                                RecycleNULL.setTextColor(getResources().getColor(R.color.color_Aluminum));
                            }
                            RecycleNULL.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        refreshLayout.finishRefresh();
                    }
                });

    }

    private void loginNormalDevice(String ip, int port, String username, String password) {
        m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
        if (null == m_oNetDvrDeviceInfoV30) {
            return;
        }
        m_iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(ip, port,
                username, password, m_oNetDvrDeviceInfoV30);
        if (m_iLogID < 0) {
            return;
        }
        else {
            Log.e(ACTIVITY_TAG, m_iLogID+"用户登录id");
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
            playViews[i] = new PlayCameraSurfaceView(context);
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            setUserVisibleHint(false);
        } else {
            setUserVisibleHint(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isUIVisible = false;
        for (int i = 0; i < sum; i++) {
            playViews[i].stopPreview();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < sum; i++) {
            playViews[i].setM_lUserID(logId.get(i));
            playViews[i].logout();
        }
        unbinder.unbind();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showError() {

    }
}

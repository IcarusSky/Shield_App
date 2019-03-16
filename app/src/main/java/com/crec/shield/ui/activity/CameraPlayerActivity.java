package com.crec.shield.ui.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crec.shield.R;
import com.crec.shield.entity.common.IsAttentionEntity;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.utils.CrashUtil;
import com.crec.shield.utils.HCNetSDKJNAInstance;
import com.crec.shield.utils.PlaySurfaceView;
import com.crec.shield.utils.SPUtils;
import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.hikvision.netsdk.StdDataCallBack;
import com.lzy.okgo.OkGo;
import com.universalvideoview.UniversalVideoView;
import org.MediaPlayer.PlayM4.Player;
import java.io.FileOutputStream;
import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.ADD_CAMERA_ATTENTION_TOAST;
import static com.crec.shield.global.StaticConstant.FAILED_ADD_CAMERA_ATTENTION_TOAST;
import static com.crec.shield.global.StaticConstant.FAILED_REMOVE_CAMERA_ATTENTION_TOAST;
import static com.crec.shield.global.StaticConstant.REMOVE_CAMERA_ATTENTION_TOAST;

public class CameraPlayerActivity extends Activity implements Callback,UniversalVideoView.VideoViewCallback {

    private final String TAG = "CameraPlayerActivity";

    private UniversalVideoView m_videoView = null;
    private ImageView m_isAttention = null;

    private int position = 0;
    private String[] _camerainfo =new String[5];
    private String[] _channels = null;
    private String[] _ids = null;
    private int[] _isAttentions = null;
    private ImageView mBtnBack;
    private TextView mTvTitle;
    private StdDataCallBack cbf = null;

    private int[] m_iLogIDs = null; // return by NET_DVR_Login_v30
    private int[] mLogIdSigns = null; // return by NET_DVR_Login_v30
    private int m_iPlayID = -1; // return by NET_DVR_RealPlay_V30
    private int m_iPlaybackID = -1; // return by NET_DVR_PlayBackByTime

    private int m_iPort = -1; // play port
    private int m_iStartChan = 0; // start channel no
    private static PlaySurfaceView[] playView = null;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    private int mSeekPosition;
    private int cachedHeight;
    private boolean isFullscreen;
    private View mBottomLayout;
    private View mVideoLayout;

    final String mToken = SPUtils.get(AppConstant.LOGINSTATUS.token,"").toString();

    private int count = 0;//点击次数
    private long firstClick = 0;//第一次点击时间
    private long secondClick = 0;//第二次点击时间
    private boolean isFinished = false;
    /**
     * 两次点击时间间隔，单位毫秒
     */
    private final int totalTime = 500;

    public CameraPlayerActivity()
    {

    }
    public CameraPlayerActivity Demo;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashUtil crashUtil = CrashUtil.getInstance();
        verifyStoragePermissions(this);
        crashUtil.init(this);
        setContentView(R.layout.activity_camera_player);

        String[] camset = getIntent().getStringArrayExtra("camerainfo");
        String[] channels = getIntent().getStringArrayExtra("channels");
        String [] ids = getIntent().getStringArrayExtra("Ids");
        int[] isAttentions = getIntent().getIntArrayExtra("isAttentions");
        position = getIntent().getIntExtra("position",0);
        m_iLogIDs = getIntent().getIntArrayExtra("mLogIds");
        mLogIdSigns = getIntent().getIntArrayExtra("mLogIdSigns");
        if(camset!=null)
        {
            _camerainfo = camset;
            _channels = channels;
            _ids = ids;
            _isAttentions = isAttentions;
        }

        playView = new PlaySurfaceView[1];
        ChangeSingleSurFace(true);

        if (!initeActivity()) {
            this.finish();
            return;
        }

        m_videoView.setOnTouchListener(new View.OnTouchListener() {
            float x1 = 0;
            float x2 = 0;
            float y1 = 0;
            float y2 = 0;
            boolean isMoved = false;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    count++;
                    if (1 == count) {
                        firstClick = System.currentTimeMillis();      //  第一次点击时间
                    } else if (2 == count) {
                        secondClick = System.currentTimeMillis();     //  第二次点击时间
                        if (secondClick - firstClick < totalTime) { //  判断二次点击时间间隔是否在设定的间隔时间之内
                            finish();
                            isFinished = true;
                            count = 0;
                            firstClick = 0;
                        } else {
                            firstClick = secondClick;
                            count = 1;
                        }
                        secondClick = 0;
                    }
                }
                if(isFinished){
                    return true;
                }

                // TODO Auto-generated method stub
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        isMoved = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x2 = event.getX();
                        y2 = event.getY();
                        if(isMoved){
                            if (x1-x2 > 0
                                    && (Math.abs(x2 - x1) > 25)) {
                                isMoved = false;
                                stopMultiPreview();
                                for(int i =0;i<mLogIdSigns.length;i++)
                                {
                                    if(mLogIdSigns[position]==mLogIdSigns[i])
                                    {
                                        if(i == mLogIdSigns.length-1){
                                            position = 0;
                                            break;
                                        }
                                        else {
                                            position = i+1;
                                            break;
                                        }
                                    }
                                }
                                startMultiPreview();
                                updateAttention(position);
                            }else if(x1-x2 < 0
                                    && (Math.abs(x2 - x1) > 25)){
                                isMoved = false;
                                stopMultiPreview();
                                for(int i =0;i<mLogIdSigns.length;i++)
                                {
                                    if(mLogIdSigns[position]==mLogIdSigns[i])
                                    {
                                        if(i == 0){
                                            position = mLogIdSigns.length-1;
                                            break;
                                        }
                                        else {
                                            position = i-1;
                                            break;
                                        }
                                    }
                                }
                                startMultiPreview();
                                updateAttention(position);
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stopMultiPreview();
    }

    private View.OnClickListener goback_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            stopMultiPreview();
            finish();
        }
    };



    // @Override
    public void surfaceCreated(SurfaceHolder holder) {
        m_videoView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        Log.i(TAG, "surface is created" + m_iPort);
        if (-1 == m_iPort) {
            return;
        }
        Surface surface = holder.getSurface();
        if (true == surface.isValid()) {
            if (false == Player.getInstance()
                    .setVideoWindow(m_iPort, 0, holder)) {
            }
        }
    }

    // @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    // @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "Player setVideoWindow release!" + m_iPort);
        if (-1 == m_iPort) {
            return;
        }
        if (true == holder.getSurface().isValid()) {
            if (false == Player.getInstance().setVideoWindow(m_iPort, 0, null)) {
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("m_iPort", m_iPort);
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        m_iPort = savedInstanceState.getInt("m_iPort");
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }

    // GUI init
    private boolean initeActivity() {
        findViews();
        m_videoView.getHolder().addCallback(this);
        setListeners();
        return true;
    }

    private void ChangeSingleSurFace(boolean bSingle) {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        for (int i = 0; i < 1; i++) {
            if (playView[i] == null) {
                playView[i] = new PlaySurfaceView(this);
                playView[i].setParam(metric.widthPixels);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
//                params.bottomMargin = playView[i].getM_iHeight() - (i / 2)
//                        * playView[i].getM_iHeight();
                params.topMargin = 0;
                params.leftMargin = (i % 2) * playView[i].getM_iWidth();
                params.gravity = Gravity.CENTER;
                addContentView(playView[i], params);
                playView[i].setVisibility(View.INVISIBLE);

            }
        }

        if (bSingle) {
            for (int i = 0; i < 1; ++i) {
                playView[i].setVisibility(View.INVISIBLE);
            }
            playView[0].setParam(metric.widthPixels * 2);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
//            params.bottomMargin = playView[3].getM_iHeight() - (3 / 2)
//                    * playView[3].getM_iHeight();
            params.topMargin = 0;
            params.leftMargin = 0;
            // params.
            params.gravity = Gravity.CENTER;
            playView[0].setLayoutParams(params);
            playView[0].setVisibility(View.VISIBLE);
        } else {
            for (int i = 0; i < 1; ++i) {
                playView[i].setVisibility(View.VISIBLE);
            }

            playView[0].setParam(metric.widthPixels);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
//            params.bottomMargin = playView[0].getM_iHeight() - (0 / 2)
//                    * playView[0].getM_iHeight();
            params.topMargin = 0;
            params.leftMargin = (0 % 2) * playView[0].getM_iWidth();
            params.gravity = Gravity.CENTER;
            playView[0].setLayoutParams(params);
        }

    }

    // get controller instance
    private void findViews() {
        mBtnBack = (ImageView) findViewById(R.id.btn_left);
        mTvTitle = (TextView)findViewById(R.id.toolbar_title);
        mVideoLayout = findViewById(R.id.video_layout);
        mBottomLayout = findViewById(R.id.bottom_layout);
        m_videoView = (UniversalVideoView) findViewById(R.id.videoView);
        m_isAttention = (ImageView) findViewById(R.id.isAttention);

        setVideoAreaSize();

        m_isAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m_isAttention.getTag().equals("select") && _ids[position] != null) {
                    OkGo.post(Url.BASE_URL+Url.OVERVIEW_FOLLOW_REMOVE_CAMERA_ATTENTION)     // 摄像机取消关注
                            .params("token", mToken)
                            .params("cameraId", _ids[position])
                            .params("cameraChannel", Integer.parseInt(_channels[position]))
                            .execute(new JsonCallback<IsAttentionEntity>() {
                                @Override
                                public void onSuccess(IsAttentionEntity isAttentionEntity, Call call, Response response) {
                                    if(isAttentionEntity.getCode()==1){
                                        if(isAttentionEntity.isData()){
                                            m_isAttention.setTag("unSelect");
                                            m_isAttention.setImageResource(R.mipmap.img_star_none);
                                            _isAttentions[position] = 0;
                                            Toast.makeText(getApplicationContext(), REMOVE_CAMERA_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), FAILED_REMOVE_CAMERA_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                } else if(m_isAttention.getTag().equals("unSelect") && _ids[position] != null){
                    OkGo.post(Url.BASE_URL+Url.OVERVIEW_FOLLOW_ADD_CAMERA_ATTENTION)    //  摄像机关注
                            .params("token", mToken)
                            .params("cameraId", _ids[position])
                            .params("cameraChannel", Integer.parseInt(_channels[position]))
                            .execute(new JsonCallback<IsAttentionEntity>() {
                                @Override
                                public void onSuccess(IsAttentionEntity isAttentionEntity, Call call, Response response) {
                                    if(isAttentionEntity.getCode()==1){
                                        if(isAttentionEntity.isData()){
                                            m_isAttention.setTag("select");
                                            m_isAttention.setImageResource(R.mipmap.img_star);
                                            _isAttentions[position] = 1;
                                            Toast.makeText(getApplicationContext(), ADD_CAMERA_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), FAILED_ADD_CAMERA_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                }
            }
        });
        m_videoView.setVideoViewCallback(this);
        if (mSeekPosition > 0) {
            m_videoView.seekTo(mSeekPosition);
        }
        m_videoView.start();
        mTvTitle.setText(SPUtils.get(AppConstant.LOGINSTATUS.company_name,"").toString());
        }


    private void updateAttention(int position){
        if (_isAttentions[position] == 1) {
            m_isAttention.setImageResource(R.mipmap.img_star);
            m_isAttention.setTag("select");
        } else {
            m_isAttention.setImageResource(R.mipmap.img_star_none);
            m_isAttention.setTag("unSelect");
        }
    }

    /**
     * 置视频区域大小
     */
    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                startMultiPreview();
                updateAttention(position);
                m_videoView.requestFocus();
            }
        });
    }


    // listen
    private void setListeners() {
        mBtnBack.setOnClickListener(goback_Listener);
    }



    /**
     * @fn processRealData
     * @author zhuzhenlei
     * @brief process real data
     * @param iPlayViewNo
     *            - player channel [in]
     * @param iDataType
     *            - data type [in]
     * @param pDataBuffer
     *            - data buffer [in]
     * @param iDataSize
     *            - data size [in]
     * @param iStreamMode
     *            - stream mode [in]
     * @return NULL
     */
    public void processRealData(int iPlayViewNo, int iDataType,
                                byte[] pDataBuffer, int iDataSize, int iStreamMode) {
        if (HCNetSDK.NET_DVR_SYSHEAD == iDataType) {
            if (m_iPort >= 0) {
                return;
            }
            m_iPort = Player.getInstance().getPort();
            if (m_iPort == -1) {
//                Log.e(TAG, "getPort is failed with: "
//                        + Player.getInstance().getLastError(m_iPort));
                return;
            }
            Log.i(TAG, "getPort succ with: " + m_iPort);
            if (iDataSize > 0) {
                if (!Player.getInstance().setStreamOpenMode(m_iPort,
                        iStreamMode)) // set stream mode
                {
//                    Log.e(TAG, "setStreamOpenMode failed");
                    return;
                }
                if (!Player.getInstance().openStream(m_iPort, pDataBuffer,
                        iDataSize, 2 * 1024 * 1024)) // open stream
                {
//                    Log.e(TAG, "openStream failed");
                    return;
                }
                if (!Player.getInstance().play(m_iPort,
                        m_videoView.getHolder())) {
//                    Log.e(TAG, "play failed");
                    return;
                }
                if (!Player.getInstance().playSound(m_iPort)) {
//                    Log.e(TAG, "playSound failed with error code:"
//                            + Player.getInstance().getLastError(m_iPort));
                    return;
                }
            }
        } else {

            try {
                FileOutputStream file = new FileOutputStream("/sdcard/StdPlayData.mp4", true);
                file.write(pDataBuffer, 0, iDataSize);
                file.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void startSinglePreview() {
        if (m_iPlaybackID >= 0) {
            Log.i(TAG, "Please stop palyback first");
            return;
        }

        Log.i(TAG, "m_iStartChan:" + m_iStartChan);

        NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
        previewInfo.lChannel = Integer.parseInt(_camerainfo[4]);
        previewInfo.dwStreamType = 0; // substream
        previewInfo.bBlocked = 1;
        previewInfo.hHwnd = playView[0].getHolder();

        m_iPlayID = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(m_iLogIDs[position],
                previewInfo, null);
        if (m_iPlayID < 0) {
//            Log.e(TAG, "NET_DVR_RealPlay is failed!Err:"
//                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return;
        }
//        else {
//            Log.e(TAG, "返回了"+m_iPlayID+"，用户id"+m_iLogIDs[position]);
//        }

        boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_OpenSound(m_iPlayID);
        if(bRet){
//            Log.e(TAG, "NET_DVR_OpenSound Succ!");
        }


        Log.i(TAG,
                "NetSdk Play sucess ***********************3***************************");
    }

    private void startMultiPreview() {
        playView[0].startPreview(m_iLogIDs[position], Integer.parseInt(_channels[position]));
        m_iPlayID = playView[0].m_iPreviewHandle;
    }

    private void stopMultiPreview() {
        int i = 0;
        for (i = 0; i < 1; i++) {
            playView[i].stopPreview();
            playView[i].logout();
        }
        m_iPlayID = -1;
    }

    /**
     * @fn stopSinglePreview
     * @author zhuzhenlei
     * @brief stop preview
     * @return NULL
     */
    private void stopSinglePreview() {
        if (m_iPlayID < 0) {
//            Log.e(TAG, "m_iPlayID < 0");
            return;
        }

        if(HCNetSDKJNAInstance.getInstance().NET_DVR_CloseSound()){
//            Log.e(TAG, "NET_DVR_CloseSound Succ!");
        }

        // net sdk stop preview
        if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPlayID)) {
            return;
        }
        m_iPlayID = -1;
    }


    /**
     * @fn getExceptiongCbf
     * @author zhuzhenlei
     * @brief process exception
     * @return exception instance
     */
    private ExceptionCallBack getExceptiongCbf() {
        ExceptionCallBack oExceptionCbf = new ExceptionCallBack() {
            public void fExceptionCallBack(int iType, int iUserID, int iHandle) {
                System.out.println("recv exception, type:" + iType);
            }
        };
        return oExceptionCbf;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        if (isFullscreen) {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.GONE);

        } else {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = this.cachedHeight;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {
        super.onPause();
        Log.d(TAG, "onPause ");
        if (m_videoView != null && m_videoView.isPlaying()) {
            mSeekPosition = m_videoView.getCurrentPosition();
            Log.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
            m_videoView.pause();
        }
    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {

    }

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


package com.crec.shield.ui.activity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.crec.shield.R;
import com.crec.shield.global.AppConstant;
import com.crec.shield.utils.PlaySurfaceView;
import com.crec.shield.utils.SPUtils;
import com.universalvideoview.UniversalVideoView;

import org.MediaPlayer.PlayM4.Player;

public class CameraPlayerLayout extends FrameLayout {

    private final String TAG = "CameraPlayerLayout";

    private Context mContext;
    private FrameLayout frameLayout;
    private ImageView isAttention;
    private UniversalVideoView player;
    private SurfaceHolder holder;
    private static PlaySurfaceView[] playView = null;
    public int m_iPreviewHandle = -1;
    private int m_iPlayID = -1; // return by NET_DVR_RealPlay_V30
    private int m_iLogID;
    private int lChannel;
    private int port;

    private String mToken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();


    public CameraPlayerLayout(Context context, final int m_iLogID, final int lChannel, final int port) {
        super(context);
        mContext = context;
        playView = new PlaySurfaceView[1];
        ChangeSingleSurFace(true, 0);
        this.m_iLogID = m_iLogID;
        this.lChannel = lChannel;
        this.port = port;
        View view = LayoutInflater.from(context).inflate(R.layout.activity_camera_player_item, this);
        frameLayout = (FrameLayout) view.findViewById(R.id.video_layout);
        player = (UniversalVideoView) view.findViewById(R.id.videoView);
        isAttention = (ImageView) view.findViewById(R.id.isAttention);

        frameLayout.addView(playView[0]);
        //frameLayout.ad
        holder = player.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                holder.setFormat(PixelFormat.TRANSLUCENT);
                Log.i(TAG, "surface is created" + port);
                if (-1 == port) {
                    return;
                }
                holder.addCallback(this);
                Surface surface = surfaceHolder.getSurface();
                if (true == surface.isValid()) {
                    if (false == Player.getInstance()
                            .setVideoWindow(port, 0, surfaceHolder)) {
                        Log.e(TAG, "Player setVideoWindow failed!");
                    }
                }

                player.post(new Runnable() {
                    @Override
                    public void run() {
                        playView[0].startPreview(m_iLogID, lChannel);
                    }
                });
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                player.getHolder().setFormat(PixelFormat.TRANSLUCENT);
                Log.i(TAG, "surface is created" + port);
                if (-1 == port) {
                    return;
                }
                player.getHolder().addCallback(this);
                Surface surface = holder.getSurface();
                if (true == surface.isValid()) {
                    if (false == Player.getInstance()
                            .setVideoWindow(port, 0, holder)) {
                        Log.e(TAG, "Player setVideoWindow failed!");
                    }
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                Log.i(TAG, "Player setVideoWindow release!" + port);
                if (-1 == port) {
                    return;
                }
                if (true == surfaceHolder.getSurface().isValid()) {
                    if (false == Player.getInstance().setVideoWindow(port, 0, null)) {
                        Log.e(TAG, "Player setVideoWindow failed!");
                    }
                }
            }
        });
    }

    private void ChangeSingleSurFace(boolean bSingle, int i) {

        if (playView[i] == null) {
            playView[i] = new PlaySurfaceView(mContext);
            playView[i].setParam(800);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = 0;
            params.leftMargin = 0;
            params.gravity = Gravity.CENTER;
            //addContentView(playViews[i], params);
            playView[i].setVisibility(View.INVISIBLE);

        }

        if (bSingle) {
            playView[i].setVisibility(View.INVISIBLE);
            playView[i].setParam(800 * 2);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
//            params.bottomMargin = playViews[3].getM_iHeight() - (3 / 2)
//                    * playViews[3].getM_iHeight();
            params.topMargin = 0;
            params.leftMargin = 0;

            params.gravity = Gravity.CENTER;
            playView[i].setLayoutParams(params);
            playView[i].setVisibility(View.VISIBLE);
        } else {
            playView[i].setVisibility(View.VISIBLE);

            playView[i].setParam(800);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = 0;
            params.leftMargin = 0;
            params.gravity = Gravity.CENTER;
            playView[i].setLayoutParams(params);
        }

    }

    public void startMultiPreview() {
        player.post(new Runnable() {
            @Override
            public void run() {
                playView[0].startPreview(m_iLogID, lChannel);
                m_iPlayID = playView[0].m_iPreviewHandle;
            }
        });
    }

    public void stopMultiPreview() {
        playView[0].stopPreview();
        m_iPlayID = -1;
    }


}

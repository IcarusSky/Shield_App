package com.crec.shield.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.crec.shield.R;
import com.crec.shield.adapter.holder.CameraItemAdapterHolder;
import com.crec.shield.entity.project.camera.Camera;
import com.crec.shield.ui.activity.CameraPlayerActivity;
import com.crec.shield.ui.fragment.ProjectDetailsCameraFragment;
import com.crec.shield.utils.PlayCameraSurfaceView;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;

import org.MediaPlayer.PlayM4.Player;
import java.util.List;


public class CameraItemAdapter extends RecyclerView.Adapter<CameraItemAdapterHolder>{

    private final String TAG = "CameraItemAdapter";

    private List<Integer> background;
    private List<Integer> player;
    private List<String> camera_name;
    private List<Integer> AttentionList;
    private List<Camera> camera_list;
    private List<String > IdList;
    private List<NET_DVR_PREVIEWINFO> previewInfos;
    private static PlayCameraSurfaceView[] playViews;
    public int m_iPreviewHandle = -1;
    private String mToken;


    public Context mContext;
    public ProjectDetailsCameraFragment framement;
    public int count;

    public CameraItemAdapter(ProjectDetailsCameraFragment fragment, Context mContext, String mToken, List<String > IdList, List<Camera> camlist, List<Integer> background, List<Integer> player, List<String> camera_name, List<Integer> AttentionList, List<NET_DVR_PREVIEWINFO> previewinfos, PlayCameraSurfaceView[] playViews) {
        this.background = background;
        this.player = player;
        this.mToken = mToken;
        this.IdList = IdList;
        this.camera_name = camera_name;
        this.AttentionList = AttentionList;
        this.mContext = mContext;
        this.framement = fragment;
        this.camera_list = camlist;
        this.previewInfos = previewinfos;
        this.playViews = playViews;
        this.count = background.size();
    }

    /**
     * 设置数据源总的条目
     */
    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public void onBindViewHolder(final CameraItemAdapterHolder holder, final int position) {
        holder.camera_name.setText(camera_name.get(position));
        holder.player.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Camera temcam = camera_list.get(position);
                String[] _caminfo = new String[]{temcam.getIp(),temcam.getPort()+"",temcam.getUsername(),temcam.getPassword(),temcam.getChannel(), temcam.getMlogId()+""};//ip、端口、用户名、密码、通信管道id、登录id
                if(framement != null){
                    Intent intent = new Intent(framement.getActivity(), CameraPlayerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("camerainfo", _caminfo);
                    intent.putExtra("position", position);
                    String[] channlstr = new String[camera_list.size()];
                	String[] ids = new String[camera_list.size()];
                	int[] isAttentions = new int[camera_list.size()];
                	int[] mLogIds = new int[camera_list.size()];
                    int[] mLogIdSigns = new int[camera_list.size()];
                    for(Camera camera:camera_list)
                    {
                        channlstr[camera_list.indexOf(camera)] = camera.getChannel();
						ids[camera_list.indexOf(camera)] = IdList.get(camera_list.indexOf(camera));
                    	isAttentions[camera_list.indexOf(camera)] = AttentionList.get(camera_list.indexOf(camera)).intValue();
                        mLogIds[camera_list.indexOf(camera)] = camera_list.get(camera_list.indexOf(camera)).getMlogId();
                        mLogIdSigns[camera_list.indexOf(camera)] = camera_list.get(camera_list.indexOf(camera)).getLogIdSign();
                    }
                    intent.putExtra("channels", channlstr);
                    intent.putExtra("mLogIds", mLogIds);
                    intent.putExtra("mLogIdSigns", mLogIdSigns);
                	intent.putExtra("Ids", ids);
                	intent.putExtra("isAttentions", isAttentions);
                    mContext.startActivity(intent);
                }else {
                    Intent intent = new Intent(mContext, CameraPlayerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("camerainfo", _caminfo);
                    intent.putExtra("position", position);
                    String[] channlstr = new String[camera_list.size()];
                    String[] ids = new String[camera_list.size()];
                    int[] isAttentions = new int[camera_list.size()];
                    int[] mLogIds = new int[camera_list.size()];
                    int[] mLogIdSigns = new int[camera_list.size()];
                    for(Camera camera:camera_list)
                    {
                        channlstr[camera_list.indexOf(camera)] = camera.getChannel();
                        ids[camera_list.indexOf(camera)] = IdList.get(camera_list.indexOf(camera));
                        isAttentions[camera_list.indexOf(camera)] = AttentionList.get(camera_list.indexOf(camera)).intValue();
                        mLogIds[camera_list.indexOf(camera)] = camera_list.get(camera_list.indexOf(camera)).getMlogId();
                        mLogIdSigns[camera_list.indexOf(camera)] = camera_list.get(camera_list.indexOf(camera)).getLogIdSign();
                    }
                    intent.putExtra("channels", channlstr);
                    intent.putExtra("mLogIds", mLogIds);
                    intent.putExtra("mLogIdSigns", mLogIdSigns);
                    intent.putExtra("Ids", ids);
                    intent.putExtra("isAttentions", isAttentions);
                    mContext.startActivity(intent);
                }
            }
        });
        holder.player.post(new Runnable() {
            @Override
            public void run() {
                playViews[position].startPreview(camera_list.get(position).getMlogId(), previewInfos.get(position).lChannel);
            }
        });

        holder.player.setVisibility(View.VISIBLE);
        SurfaceHolder serfaceHolder = holder.player.getHolder();
        //SurfaceHolder serfaceHolder = playViews[position].getHolder();
        previewInfos.get(position).hHwnd = playViews[position].getHolder();
        serfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                holder.player.getHolder().setFormat(PixelFormat.TRANSLUCENT);
                Log.i(TAG, "surface is created" + camera_list.get(position).getPort());
                if (-1 == camera_list.get(position).getPort()) {
                    return;
                }
                holder.player.getHolder().addCallback(this);
                Surface surface = surfaceHolder.getSurface();
                if (true == surface.isValid()) {
                    if (false == Player.getInstance()
                            .setVideoWindow(camera_list.get(position).getPort(), 0, surfaceHolder)) {
//                        Log.e(TAG, "Player setVideoWindow failed!");
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                holder.player.getHolder().setFormat(PixelFormat.TRANSLUCENT);
                Log.i(TAG, "surface is created" + camera_list.get(position).getPort());
                if (-1 == camera_list.get(position).getPort()) {
                    return;
                }
                holder.player.getHolder().addCallback(this);
                Surface surface = surfaceHolder.getSurface();
                if (true == surface.isValid()) {
                    if (false == Player.getInstance()
                            .setVideoWindow(camera_list.get(position).getPort(), 0, surfaceHolder)) {
//                        Log.e(TAG, "Player setVideoWindow failed!");
                    }
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                Log.i(TAG, "Player setVideoWindow release!" + camera_list.get(position).getPort());
                if (-1 == camera_list.get(position).getPort()) {
                    return;
                }
                if (true == surfaceHolder.getSurface().isValid()) {
                    if (false == Player.getInstance().setVideoWindow(camera_list.get(position).getPort(), 0, null)) {
//                        Log.e(TAG, "Player setVideoWindow failed!");
                    }
                }
            }
        });

    }

    @Override
    public CameraItemAdapterHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shield_camera_item, parent, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
//                params.bottomMargin = playView[i].getM_iHeight() - (i / 2)
//                        * playView[i].getM_iHeight();
        params.topMargin = 0;
        params.leftMargin = 0;
        params.gravity = Gravity.CENTER;

        FrameLayout fl = (FrameLayout) view.findViewById(R.id.Surface_layout);
        fl.addView(playViews[position], params);

        //playViews[position].setLayoutParams(params);
        //addContentView(playViews[position], params);
        return new CameraItemAdapterHolder(view, mContext);
    }

    @Override
    public int getItemViewType(int position){
        return  position;
    }
}

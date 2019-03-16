package com.crec.shield.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;

@SuppressLint("NewApi")
public class PlayCameraSurfaceView extends SurfaceView implements Callback {

    private final String TAG = "PlayCameraSurfaceView";
    private int m_iWidth = 0;
    public int getM_iWidth() {
        return m_iWidth;
    }

    public void setM_iWidth(int m_iWidth) {
        this.m_iWidth = m_iWidth;
    }

    public int getM_iHeight() {
        return m_iHeight;
    }

    public void setM_iHeight(int m_iHeight) {
        this.m_iHeight = m_iHeight;
    }

    private int m_iHeight = 0;
    private int m_iPreviewHandle = -1;
    private SurfaceHolder m_hHolder;
    private boolean bCreate = false;

    public int m_lUserID = -1;

    public int getM_lUserID() {
        return m_lUserID;
    }

    public void setM_lUserID(int m_lUserID) {
        this.m_lUserID = m_lUserID;
    }

    public int m_iChan = 0;
    private boolean isPlayed;

    public PlayCameraSurfaceView(Activity activity) {
        super((Context) activity);
        // TODO Auto-generated constructor stub

        m_hHolder = this.getHolder();
        getHolder().addCallback(this);
    }

    public PlayCameraSurfaceView(Context context){
        super(context);
        m_hHolder = this.getHolder();
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        setZOrderOnTop(true);
        setZOrderMediaOverlay(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        System.out.println("surfaceChanged");
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        bCreate = true;
        Log.i(TAG, "surfaceCreated");

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        System.out.println("surfaceDestroyed");
        stopPreview();
        bCreate = false;

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.setMeasuredDimension(m_iWidth - 1, m_iHeight - 1);
    }

    public void setParam(int nScreenSize) {
        m_iWidth = nScreenSize / 2;
        m_iHeight = (m_iWidth * 3) / 4;
    }

    public void startPreview(int iUserID, int iChan) {
        Log.i(TAG, "preview channel:" + iChan);
        this.m_lUserID = iUserID;
        this.m_iChan = iChan;
//        while (!bCreate) {
//            try {
//                Thread.sleep(100);
//                Log.i(TAG, "wait for surface create");
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }


        NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
        previewInfo.lChannel = iChan;
        previewInfo.dwStreamType = 1; // substream
        previewInfo.bBlocked = 1;
        previewInfo.hHwnd = m_hHolder;
        // HCNetSDK start preview

        m_iPreviewHandle = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(iUserID,
                previewInfo, null);
        if (m_iPreviewHandle < 0) {
        }else{
//            Log.e(TAG, "返回了"+m_iPreviewHandle+"，用户id"+iUserID);
            isPlayed = true;
        }
        
        boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_OpenSound(m_iPreviewHandle);

    }

    public void stopPreview() {
        boolean isStop  = HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPreviewHandle);
//        if(isStop){
//            Log.e(TAG, "关闭了"+m_iPreviewHandle);
//        }
    }

    public void logout() {
        boolean isLogout = HCNetSDK.getInstance().NET_DVR_Logout_V30(m_lUserID);
//        if(isLogout){
//            Log.e(TAG,m_lUserID+"id取消登录");
//        }
    }
}

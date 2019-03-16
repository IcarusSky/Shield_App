package com.crec.shield.adapter.holder;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.utils.ImageViewUtil;

import org.MediaPlayer.PlayM4.Player;

public class CameraItemAdapterHolder extends RecyclerView.ViewHolder{
//	public ImageView camera_background;
//	public ImageView player;
	public SurfaceView player;
	//public ImageView isAttention;
	public TextView camera_name;
	public Context mContext;

	public CameraItemAdapterHolder(View itemView,Context mContext) {
		super(itemView);
		this.mContext = mContext;
		initView(itemView);
	}
	private void initView(View itemView) {
//		camera_background = (ImageView) itemView.findViewById(R.id.camera_background_img);
//		player = (ImageView) itemView.findViewById(R.id.player);
		player = (SurfaceView) itemView.findViewById(R.id.Surface_Player);
		//isAttention = (ImageView) itemView.findViewById(R.id.isAttention);
		camera_name = (TextView) itemView.findViewById(R.id.camera_name);
//		ImageViewUtil.matchAll(mContext, camera_background);
	}
}

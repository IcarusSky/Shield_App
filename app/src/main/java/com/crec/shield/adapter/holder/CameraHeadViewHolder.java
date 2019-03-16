package com.crec.shield.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crec.shield.R;

public class CameraHeadViewHolder extends RecyclerView.ViewHolder{
	
	public ImageView iv_head;
	public TextView tv_head;

	public CameraHeadViewHolder(View itemView) {
		super(itemView);
		initView(itemView);
	}
	
	
	private void initView(View itemView) {
		iv_head = (ImageView)itemView.findViewById(R.id.camera_head);
		tv_head = (TextView)itemView.findViewById(R.id.playing);
	}

}

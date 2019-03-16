package com.crec.shield.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crec.shield.R;

import java.util.List;

public class FollowCameraAdapter extends RecyclerView.Adapter<FollowCameraAdapter.MyViewHolder>{

    private List<String> AreaList;
    private List<String>  ProjectList;
    private List<String>  LineList;
    private List<String>  CameraList;
    private Context mContext;
    private LayoutInflater inflater;

    public FollowCameraAdapter(Context context, List<String> AreaList, List<String> ProjectList, List<String> LineList, List<String> CameraList){
        this. mContext=context;
        this. AreaList=AreaList;
        this. ProjectList=ProjectList;
        this. LineList=LineList;
        this. CameraList=CameraList;
        inflater=LayoutInflater. from(mContext);
    }

    @Override
    public int getItemCount() {

        return AreaList.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(FollowCameraAdapter.MyViewHolder holder, final int position) {

        holder.area.setText( AreaList.get(position));
        holder.project.setText( ProjectList.get(position));
        holder.line.setText( LineList.get(position));
        holder.camera.setText( CameraList.get(position));

    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public FollowCameraAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.fragment_follow_camera_recycle,parent, false);
        FollowCameraAdapter.MyViewHolder holder= new FollowCameraAdapter.MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView area;
        TextView project;
        TextView line;
        TextView camera;
        public MyViewHolder(View view) {
            super(view);
            area=(TextView) view.findViewById(R.id.child_area);
            project=(TextView) view.findViewById(R.id.child_project);
            line=(TextView) view.findViewById(R.id.child_line);
            camera=(TextView) view.findViewById(R.id.child_camera);
        }

    }
}

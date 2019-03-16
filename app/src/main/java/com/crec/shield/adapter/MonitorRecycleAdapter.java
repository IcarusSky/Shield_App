package com.crec.shield.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crec.shield.R;
import com.crec.shield.entity.common.IsAttentionEntity;
import com.crec.shield.entity.project.camera.Camera;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.lzy.okgo.OkGo;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.ADD_CAMERA_ATTENTION_TOAST;
import static com.crec.shield.global.StaticConstant.FAILED_ADD_CAMERA_ATTENTION_TOAST;
import static com.crec.shield.global.StaticConstant.FAILED_REMOVE_CAMERA_ATTENTION_TOAST;
import static com.crec.shield.global.StaticConstant.REMOVE_CAMERA_ATTENTION_TOAST;

public class MonitorRecycleAdapter extends RecyclerView.Adapter<MonitorRecycleAdapter.AuthorViewHolder>{

    private List<String>  AreaList;
    private List<String>  ProjectList;
    private List<String>  LineList;
    private List<String>  CameraList;
    private List<Integer>  isAttentionList;
    private List<String>  IdList;
    private Context mContext;
    private String mToken;
    private LayoutInflater inflater;
    private ItemClickListener ClickListener;
    private Button mbtn;
    private List<Camera> camera_list;


    public MonitorRecycleAdapter(Context context, String mToken, List<String >  IdList, List<String> AreaList, List<String> ProjectList, List<String> LineList, List<String> CameraList, List<Integer>  isAttentionList, List<Camera>camera_list ){
        this. mContext=context;
        this.mToken = mToken;
        this.IdList = IdList;
        this. AreaList=AreaList;
        this. ProjectList=ProjectList;
        this. LineList=LineList;
        this. CameraList=CameraList;
        this. isAttentionList = isAttentionList;
        this.camera_list = camera_list;
        inflater=LayoutInflater. from(mContext);

//        View _view = inflater.inflate(R.layout.activity_follow,null);
       /* Button btn = (Button)_view.findViewById(R.id.btn_batch);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, ADD_CAMERA_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {

        return AreaList.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(final MonitorRecycleAdapter.AuthorViewHolder holder, final int position) {

        String tdata = AreaList.get(position);
        holder.area.setText( AreaList.get(position));
        holder.project.setText( ProjectList.get(position));
        holder.line.setText( LineList.get(position));
        holder.camera.setText( CameraList.get(position));
        if (isAttentionList.get(position) == 1) {
            holder.isAttention.setImageResource(R.mipmap.img_star);
            holder.isAttention.setTag("select");
        } else {
            holder.isAttention.setImageResource(R.mipmap.img_star_none);
            holder.isAttention.setTag("unSelect");
        }

        holder.isAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (holder.isAttention.getTag().equals("select")) {
                    //isAttentionList.get(position) = 0;
                    holder.isAttention.setTag("unSelect");
                    holder.isAttention.setImageResource(R.drawable.star_grey);
                    Toast.makeText(mContext, REMOVE_CAMERA_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                } else {
                    //IdList.get(position).setIsAttention(1);
                    holder.isAttention.setTag("select");
                    holder.isAttention.setImageResource(R.drawable.star);
                    Toast.makeText(mContext, ADD_CAMERA_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                }*/
                if (holder.isAttention.getTag().equals("select") && IdList.get(position).toString() != null) {
                    OkGo.post(Url.BASE_URL+Url.OVERVIEW_FOLLOW_REMOVE_CAMERA_ATTENTION)     // 摄像机取消关注
                            .params("token", mToken)
                            .params("cameraId", IdList.get(position).toString())
                            .params("cameraChannel", camera_list.get(position).getChannel())
                            .execute(new JsonCallback<IsAttentionEntity>() {
                                @Override
                                public void onSuccess(IsAttentionEntity isAttentionEntity, Call call, Response response) {
                                    if(isAttentionEntity.getCode()==1){
                                        if(isAttentionEntity.isData()){
                                            holder.isAttention.setTag("unSelect");
                                            holder.isAttention.setImageResource(R.mipmap.img_star_none);
                                            Toast.makeText(mContext, REMOVE_CAMERA_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Toast.makeText(mContext, FAILED_REMOVE_CAMERA_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                } else if(holder.isAttention.getTag().equals("unSelect") && IdList.get(position).toString() != null){
                    OkGo.post(Url.BASE_URL+Url.OVERVIEW_FOLLOW_ADD_CAMERA_ATTENTION)    //  摄像机关注
                            .params("token", mToken)
                            .params("cameraId", IdList.get(position).toString())
                            .params("cameraChannel", camera_list.get(position).getChannel())
                            .execute(new JsonCallback<IsAttentionEntity>() {
                                @Override
                                public void onSuccess(IsAttentionEntity isAttentionEntity, Call call, Response response) {
                                    if(isAttentionEntity.getCode()==1){
                                        if(isAttentionEntity.isData()){
                                            holder.isAttention.setTag("select");
                                            holder.isAttention.setImageResource(R.mipmap.img_star);
                                            Toast.makeText(mContext, ADD_CAMERA_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(mContext, FAILED_ADD_CAMERA_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                }
            }
        });

    }

    public MonitorRecycleAdapter setClickListener(ItemClickListener ClickListener){
        this.ClickListener = ClickListener;
        return this;
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MonitorRecycleAdapter.AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.fragment_follow_camera_recycle,parent, false);
        MonitorRecycleAdapter.AuthorViewHolder holder= new MonitorRecycleAdapter.AuthorViewHolder(view);

        return holder;
    }

    class AuthorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView area;
        TextView project;
        TextView line;
        TextView camera;
        ImageView isAttention;
        public AuthorViewHolder(View view) {
            super(view);
            area=(TextView) view.findViewById(R.id.child_area);
            project=(TextView) view.findViewById(R.id.child_project);
            line=(TextView) view.findViewById(R.id.child_line);
            camera=(TextView) view.findViewById(R.id.child_camera);
            isAttention=(ImageView) view.findViewById(R.id.attention);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (ClickListener != null) {
                ClickListener.OnItemClick(v,getPosition());
            }
        }
    }



    public interface ItemClickListener{
        //声明接口ItemClickListener
        void OnItemClick(View view,int position);
//        void OnTitleClick(View view,int position);
//        void OnInfoClick(View view,int position);
//        void OnTypeClick(View view,int position);
    }
}


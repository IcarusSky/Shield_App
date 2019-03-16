package com.crec.shield.adapter.project;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.entity.crec22.project.parameterlist.ParameterListEntity;

import java.util.List;

import butterknife.BindView;

public class SystemListAdapter extends RecyclerView.Adapter<SystemListAdapter.MyViewHolder> {
    //@BindView(R.id.tv_systemlist)
    //TextView tvSystemList;



    private Context context;


    private List <ParameterListEntity>system_name;
    private LayoutInflater inflate;
    private OnItemClickListener mOnItemClickListener;

    public SystemListAdapter(Context context,List <ParameterListEntity> system_name ){
        this.context=context;
        this.system_name=system_name;
        this.inflate=LayoutInflater.from(context);
    }
    public interface OnItemClickListener{
        void onItemClick( int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
         mOnItemClickListener=onItemClickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflate.inflate(R.layout.item_systemlist,parent,false));
    }
    @Override
    public void onBindViewHolder( MyViewHolder holder, final int position) {
        holder.item_SystemListView.setText(system_name.get(position).getSunSystem());
        //给每行设置监听
        if(mOnItemClickListener!=null){
            holder.item_SystemListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });

        }
    }




    @Override
    public int getItemCount() {
        return system_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView item_SystemListView;
        public MyViewHolder(View itemView) {
            super(itemView);
            item_SystemListView= itemView.findViewById(R.id.tv_systemlist);
        }
    }
}

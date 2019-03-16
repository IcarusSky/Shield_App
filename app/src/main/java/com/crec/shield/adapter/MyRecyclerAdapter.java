package com.crec.shield.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crec.shield.R;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context context, List<String> datas){
        this. mContext=context;
        this. mDatas=datas;
        inflater=LayoutInflater. from(mContext);
    }

    @Override
    public int getItemCount() {

        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv.setText( mDatas.get(position));
        holder.tv.setText( mDatas.get(position));
        holder.tv.setText( mDatas.get(position));

    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout. activity_message_item,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        Button mBtn;
        public MyViewHolder(View view) {
            super(view);
            tv=(TextView) view.findViewById(R.id.warn_type);
            tv=(TextView) view.findViewById(R.id.warn_time);
            tv=(TextView) view.findViewById(R.id.warn_project);
            mBtn=(Button) view.findViewById(R.id.btn_right);
        }

    }
}

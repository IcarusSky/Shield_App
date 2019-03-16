package com.crec.shield.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.entity.project.search.HistoryTableEntity;
import com.crec.shield.entity.project.search.ProjectSearchEntity;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.MyViewHolder> {
    private List<HistoryTableEntity> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private MyItemClickListener mItemClickListener = null;

    public SearchHistoryAdapter(Context context, List<HistoryTableEntity> datas){
        this. mContext=context;
        this. mDatas=datas;
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public SearchHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = View.inflate(mContext,R.layout.activity_project_history,null);
        View view = inflater.from(mContext).inflate(R.layout.activity_project_history, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(SearchHistoryAdapter.MyViewHolder holder, final int position) {

//        holder.project_history.setTag(position);
        holder.project_history.setText( mDatas.get(position).getProjectName());

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView project_history;
        public MyViewHolder(View view) {
            super(view);
            project_history=(TextView) itemView.findViewById(R.id.project_history);
            itemView.setOnClickListener(this);
        }
        /**
         * 实现OnClickListener接口重写的方法
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v,getPosition());
            }
        }
    }


    /**
     * 创建一个回调接口
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }
}

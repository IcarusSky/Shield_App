package com.crec.shield.adapter.project;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crec.shield.R;

import java.util.List;

/**
 * Created by hahaliu on 2018/9/28.
 */

public class ProDetailQualityChildAdapter extends RecyclerView.Adapter<ProDetailQualityChildAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<String> datas;
    /**
     * 每一项最大字符的集合，为了占位使用
     */
    private List<String> maxDatas;
    /**
     * 1 正常  0 异常
     */
    private int type = 1;
    /**
     * 父index， 决定显示状态
     */
    private int parentPosition;

    public ProDetailQualityChildAdapter(Context context, List<String> datas, List<String> maxDatas, int type, int parentPosition) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.datas = datas;
        this.maxDatas = maxDatas;
        this.type = type;
        this.parentPosition = parentPosition;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_pro_detail_quality_child, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String data = datas.get(position);
        holder.tvItem.setText(data);
        holder.tvItemPlaceholder.setText(maxDatas.get(position));

        //父类第一栏显示标题栏
        if (parentPosition == 0) {
            holder.flItem.setBackgroundResource(R.color.radio);
            holder.tvItem.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            //非标题栏
            //异常 并且异常类是第二项 的字体颜色一致
            if (type == 0 && position == 1) {
                holder.tvItem.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                holder.tvItem.setTextColor(ContextCompat.getColor(context, R.color.text_com_color));
            }
            //黑白相间的背景色
            if (parentPosition % 2 == 1) {
                holder.flItem.setBackgroundResource(R.color.white);
            } else {
                holder.flItem.setBackgroundResource(R.color.bg_gray);
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItem;
        private TextView tvItemPlaceholder;
        private ConstraintLayout flItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tvItem);
            tvItemPlaceholder = (TextView) itemView.findViewById(R.id.tvItemPlaceholder);
            flItem = (ConstraintLayout) itemView.findViewById(R.id.clItem);
        }
    }
}

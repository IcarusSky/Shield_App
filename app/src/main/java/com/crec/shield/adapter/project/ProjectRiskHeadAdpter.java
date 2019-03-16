package com.crec.shield.adapter.project;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.entity.project.risk.RiskHeadCurrentRisks;
import com.crec.shield.entity.project.risk.RiskHeadReachRisks;

import java.util.List;

public class ProjectRiskHeadAdpter extends RecyclerView.Adapter<ProjectRiskHeadAdpter.AuthorViewHolder> {

    private List<RiskHeadCurrentRisks> riskHeadCurrencyRisks;
    private List<RiskHeadReachRisks> riskHeadReachRisks;
    private Context mContext;
    private MyItemClickListener mItemClickListener;
    private int currentRiskCount;
    private int reachRiskCount;
    private int totalCount;


    public ProjectRiskHeadAdpter(Context mContext, List<RiskHeadCurrentRisks> riskHeadCurrencyRisks, List<RiskHeadReachRisks> riskHeadReachRisks) {
        this.riskHeadCurrencyRisks = riskHeadCurrencyRisks;
        this.riskHeadReachRisks = riskHeadReachRisks;
        this.mContext = mContext;
    }

    /**
     * 设置数据源总的条目
     */
    @Override
    public int getItemCount() {
        currentRiskCount = riskHeadCurrencyRisks.size();
        reachRiskCount = riskHeadReachRisks.size();
        totalCount = currentRiskCount + reachRiskCount;
        return riskHeadCurrencyRisks.size() + riskHeadReachRisks.size();
    }


    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.fragment_projectdetails_risk_item, parent, false);
        AuthorViewHolder viewHolder = new AuthorViewHolder(childView, mItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProjectRiskHeadAdpter.AuthorViewHolder holder, int position) {
        if (position < currentRiskCount) {
            RiskHeadCurrentRisks currentRisk = riskHeadCurrencyRisks.get(position);
            holder.head_title.setText("当前风险源");
            holder.head_title.setBackgroundResource(R.drawable.bg_blue_half_radiu);
            holder.head_status.setText("盾构机当前处于");
            holder.currency_risk_detail.setText(currentRisk.getCurrent_risk_detail());
            holder.currency_risk_detail.setTextColor(ContextCompat.getColor(mContext, R.color.radio));
            holder.head_content.setText("风险源名称：");
            holder.currency_risk_cross.setTextColor(ContextCompat.getColor(mContext, R.color.radio));
            holder.currency_risk_cross.setText(currentRisk.getCurrent_risk_cross());
//            holder.rings_or_range.setText("当前环数：");
//            holder.currency_circle_num.setTextColor(ContextCompat.getColor(mContext, R.color.radio));
//            holder.currency_circle_num.setText(currentRisk.getCurrency_circle_num());
            holder.time_or_grade.setText("预计通过时间：");
            holder.currency_expect_date.setTextColor(ContextCompat.getColor(mContext, R.color.radio));
            holder.currency_expect_date.setText(currentRisk.getCurrent_expect_date());
        } else if (position < totalCount && position >= currentRiskCount) {
            RiskHeadReachRisks reachRisk = riskHeadReachRisks.get(position - currentRiskCount);
            holder.head_title.setText("临近风险源");
            holder.head_title.setBackgroundResource(R.drawable.bg_red_half_radiu);
            holder.head_status.setText("盾构机当前处于");
            holder.currency_risk_detail.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            holder.currency_risk_detail.setText(reachRisk.getReach_risk_detail());
            holder.head_content.setText("临近风险源名称：");
            holder.currency_risk_cross.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            holder.currency_risk_cross.setText(reachRisk.getReach_risk_cross());
//            holder.rings_or_range.setText("风险区间为：");
//            holder.currency_circle_num.setTextColor(ContextCompat.getColor(mContext, R.color.red));
//            holder.currency_circle_num.setText(reachRisk.getReach_risk_area());
            holder.time_or_grade.setText("预计到达时间：");
            holder.currency_expect_date.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            holder.currency_expect_date.setText(reachRisk.getReach_expect_date());
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class AuthorViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView head_title;       // 常量“当前风险点”或者“临近风险点：”
        TextView head_status;      // 常量“盾构机当前处于”或者“当前存在风险：”
        TextView currency_risk_detail;   // 风险状态
        TextView head_content;      // 常量“风险源名称：”或者“风险内容为：”
        TextView currency_risk_cross;     //  风险内容
//        TextView rings_or_range;    // 常量“当前环数：”或者“风险区间为：”
//        TextView currency_circle_num;    //   风险位置
        TextView time_or_grade;      // 常量“预计通过时间：”或者“风险等级为：”
        TextView currency_expect_date;   // 时间或等级

        private MyItemClickListener mListener;

        public AuthorViewHolder(View itemView, MyItemClickListener myItemClickListener) {
            super(itemView);

            head_title = (TextView) itemView.findViewById(R.id.head_title);
            head_status = (TextView) itemView.findViewById(R.id.head_status);
            currency_risk_detail = (TextView) itemView.findViewById(R.id.currency_risk_detail);
            head_content = (TextView) itemView.findViewById(R.id.head_content);
            currency_risk_cross = (TextView) itemView.findViewById(R.id.currency_risk_cross);
//            rings_or_range = (TextView) itemView.findViewById(R.id.rings_or_range);
//            currency_circle_num = (TextView) itemView.findViewById(R.id.currency_circle_num);
            time_or_grade = (TextView) itemView.findViewById(R.id.time_or_grade);
            currency_expect_date = (TextView) itemView.findViewById(R.id.currency_expect_date);

            /*warnImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    warnImage.setImageResource(R.mipmap.message_unread);
                }
            });*/

            this.mListener = myItemClickListener;
            itemView.setOnClickListener(this);
        }

        /**
         * 实现OnClickListener接口重写的方法
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
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

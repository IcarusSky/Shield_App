package com.crec.shield.adapter.project;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalHeadCurrentUnnormal;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalHeadReachUnnormal;

import java.util.List;

public class ProjectUnnormalHeadAdpter extends RecyclerView.Adapter<ProjectUnnormalHeadAdpter.AuthorViewHolder> {
    private List<UnnormalHeadCurrentUnnormal> unnormalHeadCurrencyUnnormal;
    private List<UnnormalHeadReachUnnormal> unnormalHeadReachUnnormal;
    private Context mContext;
    private MyItemClickListener mItemClickListener;
    private int currentUnnormalCount;
    private int reachUnnormalCount;
    private int totalCount;

    public ProjectUnnormalHeadAdpter(Context mContext, List<UnnormalHeadCurrentUnnormal> unnormalHeadCurrencyUnnormal, List<UnnormalHeadReachUnnormal> unnormalHeadReachUnnormal) {
        this.unnormalHeadCurrencyUnnormal = unnormalHeadCurrencyUnnormal;
        this.unnormalHeadReachUnnormal = unnormalHeadReachUnnormal;
        this.mContext = mContext;
    }
    @Override
    public int getItemCount() {
        currentUnnormalCount = unnormalHeadCurrencyUnnormal.size();
        reachUnnormalCount = unnormalHeadReachUnnormal.size();
        totalCount = currentUnnormalCount + reachUnnormalCount;
        return unnormalHeadCurrencyUnnormal.size() + unnormalHeadReachUnnormal.size();
    }
    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.fragment_projectdetails_unnormal, parent, false);
        AuthorViewHolder viewHolder = new AuthorViewHolder(childView, mItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AuthorViewHolder holder, int position) {

        if (position < currentUnnormalCount) {
            UnnormalHeadCurrentUnnormal currentUnnormal = unnormalHeadCurrencyUnnormal.get(position);
            holder.head_title.setText("当前异常");
            holder.head_title.setBackgroundResource(R.drawable.bg_blue_half_radiu);
            holder.head_status.setText("盾构机当前处于");
            holder.currency_unnormal_detail.setText(currentUnnormal.getCurrent_unusual_detail());
            holder.currency_unnormal_detail.setTextColor(ContextCompat.getColor(mContext, R.color.radio));
            holder.head_content.setText("异常名称：");
            holder.currency_unnormal_cross.setTextColor(ContextCompat.getColor(mContext, R.color.radio));
            holder.currency_unnormal_cross.setText(currentUnnormal.getCurrent_unusual_cross());
//            holder.rings_or_range.setText("当前环数：");
//            holder.currency_circle_num.setTextColor(ContextCompat.getColor(mContext, R.color.radio));
//            holder.currency_circle_num.setText(currentRisk.getCurrency_circle_num());
            holder.time_or_grade.setText("预计通过时间：");
            holder.currency_expect_date.setTextColor(ContextCompat.getColor(mContext, R.color.radio));
            holder.currency_expect_date.setText(currentUnnormal.getCurrent_expect_date());
        } else if (position < totalCount && position >= currentUnnormalCount) {
            UnnormalHeadReachUnnormal reachUnnormal = unnormalHeadReachUnnormal.get(position - currentUnnormalCount);
            holder.head_title.setText("临近异常");
            holder.head_title.setBackgroundResource(R.drawable.bg_red_half_radiu);
            holder.head_status.setText("盾构机当前处于");
            holder.currency_unnormal_detail.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            holder.currency_unnormal_detail.setText(reachUnnormal.getReach_unusual_detail());
            holder.head_content.setText("临近异常名称：");
            holder.currency_unnormal_cross.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            holder.currency_unnormal_cross.setText(reachUnnormal.getReach_unusual_cross());
//            holder.rings_or_range.setText("风险区间为：");
//            holder.currency_circle_num.setTextColor(ContextCompat.getColor(mContext, R.color.red));
//            holder.currency_circle_num.setText(reachRisk.getReach_risk_area());
            holder.time_or_grade.setText("预计到达时间：");
            holder.currency_expect_date.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            holder.currency_expect_date.setText(reachUnnormal.getReach_expect_date());
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class AuthorViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView head_title;       // 常量“当前风险点”或者“临近风险点：”
        TextView head_status;      // 常量“盾构机当前处于”或者“当前存在风险：”
        TextView currency_unnormal_detail;   // 风险状态
        TextView head_content;      // 常量“风险源名称：”或者“风险内容为：”
        TextView currency_unnormal_cross;     //  风险内容
        //        TextView rings_or_range;    // 常量“当前环数：”或者“风险区间为：”
//        TextView currency_circle_num;    //   风险位置
        TextView time_or_grade;      // 常量“预计通过时间：”或者“风险等级为：”
        TextView currency_expect_date;   // 时间或等级

        private MyItemClickListener mListener;

        public AuthorViewHolder(View itemView, MyItemClickListener myItemClickListener) {
            super(itemView);

            head_title = (TextView) itemView.findViewById(R.id.head_title);
            head_status = (TextView) itemView.findViewById(R.id.head_status);
            currency_unnormal_detail = (TextView) itemView.findViewById(R.id.currency_unnormal_detail);
            head_content = (TextView) itemView.findViewById(R.id.head_content);
            currency_unnormal_cross = (TextView) itemView.findViewById(R.id.currency_unnormal_cross);
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

package com.crec.shield.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crec.shield.R;

import java.util.List;

import static com.crec.shield.global.StaticConstant.APPROACHING_ARRIVAL;
import static com.crec.shield.global.StaticConstant.APPROACHING_ARRIVAL_PROMPT;
import static com.crec.shield.global.StaticConstant.APPROACHING_HAVING_ARRIVED;
import static com.crec.shield.global.StaticConstant.APPROACHING_START;
import static com.crec.shield.global.StaticConstant.APPROACHING_START_PROMPT;

public class PromptAdapter extends RecyclerView.Adapter<PromptAdapter.AuthorViewHolder> {
    private List<Integer> infoType;
    private List<String> infoLine;
    private List<String> infoLineStatus;
    private List<String> infoRemain;
    private List<String> infoRing;
    private List<String> infoExpect;
    private List<String> infoDate;
    private List<String> infoArrive;
    private List<String> infoDayOrTime;
    private List<String> infoDay;
    private List<String> infoDayinfoStartStation;
    private List<String> infoEndStation;
    private Context mContext;
    private SpannableStringBuilder spannableString;
    private SpannableStringBuilder arrivalTime;
    private MyItemClickListener mItemClickListener;
    private String str;

    public PromptAdapter(Context context, List<Integer> infoType, List<String> infoLine, List<String> infoLineStatus, List<String> infoRemain, List<String> infoRing, List<String> infoExpect, List<String> infoDate, List<String> infoArrive, List<String> infoDayOrTime, List<String> infoDay, List<String> infoDayinfoStartStation, List<String> infoEndStation) {
        this.mContext = context;
        this.infoType = infoType;
        this.infoLine = infoLine;
        this.infoLineStatus = infoLineStatus;
        this.infoRemain = infoRemain;
        this.infoRing = infoRing;
        this.infoExpect = infoExpect;
        this.infoDate = infoDate;
        this.infoArrive = infoArrive;
        this.infoDayOrTime = infoDayOrTime;
        this.infoDay = infoDay;
        this.infoDayinfoStartStation = infoDayinfoStartStation;
        this.infoEndStation = infoEndStation;
    }

    //holder初始化
    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.activity_main_item, parent, false);
        AuthorViewHolder viewHolder = new AuthorViewHolder(childView, mItemClickListener);
        return viewHolder;
    }

    //数据持久化
    @Override
    public void onBindViewHolder(AuthorViewHolder holder, final int position) {

        /* SpannableStringBuilder builder2 = new SpannableStringBuilder(infoLine.get(position));
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.parseColor("#77ACFF"));
        builder2.setSpan(blueSpan, 0, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableStringBuilder builder3 = new SpannableStringBuilder(infoRing.get(position));
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan blueSpan3 = new ForegroundColorSpan(Color.parseColor("#77ACFF"));
        builder3.setSpan(blueSpan, 3, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder3.setSpan(blueSpan, 8, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableStringBuilder builder4 = new SpannableStringBuilder(infoDate.get(position));
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan blueSpan4 = new ForegroundColorSpan(Color.parseColor("#77ACFF"));
        builder4.setSpan(blueSpan, 7, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //textView.setText(builder);*/
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
//            holder.ll_line.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }

        //  type为0是始发提示，type为1是即将到达，type为2是已经到达
        if(infoType.get(position) == 0){
            holder.promptType.setText(APPROACHING_START_PROMPT);
            spannableString = new SpannableStringBuilder(infoLine.get(position));
            spannableString.append(APPROACHING_START);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#5A7BEF"));
            int end = infoLine.get(position).length();
            spannableString.setSpan(colorSpan, 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.promptProject.setText(spannableString);
            holder.dayOrTime.setText(infoDayOrTime.get(position));
            holder.promptResidualTime.setText(infoDay.get(position));
        }else if(infoType.get(position) == 1){
            holder.promptType.setText(APPROACHING_ARRIVAL_PROMPT);
            spannableString = new SpannableStringBuilder(infoLine.get(position));
            spannableString.append(APPROACHING_ARRIVAL);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#5A7BEF"));
            int end = infoLine.get(position).length();
            spannableString.setSpan(colorSpan, 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.promptProject.setText(spannableString);
            holder.dayOrTime.setText(infoDayOrTime.get(position));
            holder.promptResidualTime.setText(infoDay.get(position));
        }else if(infoType.get(position) == 2){
            holder.promptType.setText(APPROACHING_ARRIVAL_PROMPT);
            spannableString = new SpannableStringBuilder(infoLine.get(position));
            spannableString.append(APPROACHING_HAVING_ARRIVED);
            arrivalTime = new SpannableStringBuilder(infoDayOrTime.get(position));
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#5A7BEF"));
            int end = infoLine.get(position).length();
            int endTime = infoDayOrTime.get(position).length();
            spannableString.setSpan(colorSpan, 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            arrivalTime.setSpan(colorSpan, 2, endTime, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.promptProject.setText(spannableString);
            holder.dayOrTime.setText(arrivalTime);
            holder.promptResidualTime.setText(infoDay.get(position));
            holder.promptResidualTime.setTextColor(ContextCompat.getColor(mContext,R.color.text_com_color));
        }

        holder.prompt_remain.setText(infoRemain.get(position));
        holder.remainingRings.setText(infoRing.get(position));
        holder.remainingExpect.setText(infoExpect.get(position));
        holder.date.setText(infoDate.get(position));
        holder.arrive.setText(infoArrive.get(position));
       // holder.dayOrTime.setText(infoDayOrTime.get(position));
       // holder.promptResidualTime.setText(infoDay.get(position));
        holder.promptStart.setText(infoDayinfoStartStation.get(position));
        holder.promptEnd.setText(infoEndStation.get(position));
    }

    //计数
    @Override
    public int getItemCount() {
        return infoType == null ? 0 : infoType.size();
    }

    //绑定
    class AuthorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        LinearLayout ll_line;
        TextView promptType;
        TextView promptProject;
        // TextView prompt_status;
        TextView prompt_remain;
        TextView remainingRings;
        TextView remainingExpect;
        TextView date;
        TextView arrive;
        TextView dayOrTime;
        TextView promptResidualTime;
        TextView promptStart;
        TextView promptEnd;

        private MyItemClickListener mListener;

        public AuthorViewHolder(View itemView, MyItemClickListener myItemClickListener) {
            super(itemView);
//            ll_line = (LinearLayout) itemView.findViewById(R.id.ll_line);
            promptType = (TextView) itemView.findViewById(R.id.prompt_type);
            promptProject = (TextView) itemView.findViewById(R.id.prompt_project);
            //prompt_status=(TextView) itemView.findViewById(R.id.prompt_status);
            prompt_remain = (TextView) itemView.findViewById(R.id.remain);
            remainingRings = (TextView) itemView.findViewById(R.id.remaining_rings);
            remainingExpect = (TextView) itemView.findViewById(R.id.expect);
            date = (TextView) itemView.findViewById(R.id.date);
            arrive = (TextView) itemView.findViewById(R.id.arrive);
            dayOrTime = (TextView) itemView.findViewById(R.id.prompt_dayortime);
            promptResidualTime = (TextView) itemView.findViewById(R.id.prompt_residualTime);
            promptStart = (TextView) itemView.findViewById(R.id.station_start);
            promptEnd = (TextView) itemView.findViewById(R.id.station_end);

            this.mListener = myItemClickListener;
            itemView.setOnClickListener(this);
        }

        /**
         * 实现OnClickListener接口重写的方法
         *
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

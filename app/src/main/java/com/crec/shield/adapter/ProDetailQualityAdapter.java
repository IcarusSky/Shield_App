package com.crec.shield.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.entity.crec22.project.companymanagementquality.LineQualityEntity;
import com.crec.shield.entity.crec22.project.parameterlist.ParameterListEntity;
import com.crec.shield.entity.crec22.project.parameterlist.StatusData;
import com.crec.shield.entity.project.quality.Quality;
import com.crec.shield.entity.project.risk.RiskListItemEntity;
import com.crec.shield.entity.project.status.PointParameterEntity;
import com.crec.shield.entity.crec22.project.companyequipmentmanagement.EquipmentData;
import com.crec.shield.ui2_2.activity.QualityManagementActivity;
import com.crec.shield.ui2_2.activity.RiskSourceDetailsActivity;
import com.crec.shield.entity.crec22.project.companydiggingdata.PointParameter;

import java.util.ArrayList;
import java.util.List;

import static android.companion.BluetoothLeDeviceFilter.getRenamePrefixLengthLimit;

/**
 * Created by hahaliu on 2018/9/28.
 */

public class ProDetailQualityAdapter extends RecyclerView.Adapter<ProDetailQualityAdapter.MyViewHolder> {

    private Context context;
    private List datas;
    /**
     * 每一项最大字符的集合，为了占位使用
     */
    private List<String> maxDatas;
    private LayoutInflater inflate;

    public ProDetailQualityAdapter(Context context, List datas, List<String> maxDatas) {
        this.context = context;
        this.datas = datas;
        this.maxDatas = maxDatas;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflate.inflate(R.layout.item_pro_detail_quality, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.recyclerViewItem.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        Object obj = datas.get(position == 0 ? position : position - 1);

        List<String> childDatas = new ArrayList<>();
        int type = 1;
        //每个obj不同 显示不同
        if (obj instanceof Quality) {
            //质量统计
            //第一项添加标题数据
            if (position == 0) {
                childDatas.add("环号");
                childDatas.add("状态");
                childDatas.add("管片平偏");
                childDatas.add("管片高偏");
                childDatas.add("相邻管片错台");
                childDatas.add("相邻环片错台");
            } else {
                Quality data = (Quality) obj;
                String state = "正常";
                if (data.getStatus() != null && data.getStatus() == 0) {
                    type = 0;
                    state = "异常";
                }
                childDatas.add(data.getCircleNum());
                childDatas.add(state);
                childDatas.add(data.getSemi_bias());
                childDatas.add(data.getHigh_bias());
                childDatas.add(data.getAdjacent_segment_dislocation());
                childDatas.add(data.getAdjacent_ring_dislocation());
            }

        } else if (obj instanceof RiskListItemEntity) {
            //风险源列表
            //第一项添加标题数据
            if (position == 0) {
                childDatas.add("详情");
                childDatas.add("开始");
                childDatas.add("结束");
                childDatas.add("等级");
                childDatas.add("类型");
                childDatas.add("标地");
                childDatas.add("状态");
            } else {
                RiskListItemEntity data = (RiskListItemEntity) obj;
                childDatas.add("查看");
                childDatas.add(String.valueOf(data.getStart_num()));
                childDatas.add(String.valueOf(data.getEnd_num()));
                childDatas.add(data.getRisk_level());
                childDatas.add(data.getRisk_content());
                childDatas.add(data.getCross_building());
                childDatas.add(data.getStatus());
            }
        } else if (obj instanceof PointParameterEntity) {
            //第一项添加标题数据
            if (position == 0) {
                childDatas.add("名称");
                childDatas.add("实时值");
                childDatas.add("单位");
                childDatas.add("状态");
            } else {
                PointParameterEntity data = (PointParameterEntity) obj;
                childDatas.add(data.getName());
                childDatas.add(data.getValue());
                childDatas.add(data.getUnit());
                childDatas.add(data.getStatus());
            }

        }else if (obj instanceof PointParameter) {
            //第一项添加标题数据
            if (position == 0) {
                childDatas.add("名称");
                childDatas.add("实时值");
                childDatas.add("单位");
                childDatas.add("状态");
            } else {
                PointParameter data = (PointParameter) obj;
                childDatas.add(data.getName());
                childDatas.add(data.getValue());
                childDatas.add(data.getUnit());
                childDatas.add(data.getStatus());
            }

        }else if(obj instanceof StatusData){
            if (position ==0){
                childDatas.add("名称");
                childDatas.add("数据");
                childDatas.add("单位");
                childDatas.add("状态");
            }else{
                StatusData data=(StatusData)obj;
                childDatas.add(data.getPoint_name());
                childDatas.add(data.getTagvalue());
                childDatas.add(data.getUnit());
                childDatas.add(data.getStatus());
            }
        }else if(obj instanceof LineQualityEntity){
            if(position==0){
                childDatas.add("详情");
                childDatas.add("项目");
                childDatas.add("线路");
                childDatas.add("评级");
            }else{
                LineQualityEntity data =(LineQualityEntity)obj;
                childDatas.add("查看");
                childDatas.add(data.getProject());
                childDatas.add(data.getLine());
                childDatas.add(data.getLevel());
            }
        }

        //父类第一栏显示标题栏
        if (position == 0) {
            if(obj instanceof StatusData){holder.ll_item.setBackgroundResource(R.color.lightseagreen);}
            else holder.ll_item.setBackgroundResource(R.color.radio);
        } else {
            //黑白相间的背景色
            if (position % 2 == 1) {
                holder.ll_item.setBackgroundResource(R.color.white);
            } else {
                holder.ll_item.setBackgroundResource(R.color.bg_gray);
            }
        }
        holder.ll_item.removeAllViews();
        int childPosiiton = 0;
        if (obj instanceof RiskListItemEntity) {
            for (int i = 0; i < childDatas.size(); i++) {
                //设置权重比例 为了数据少的时候横向满屏
                Space space = new Space(context);
                holder.ll_item.addView(space);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) space.getLayoutParams();
                params.width = 0;
                params.height = 1;
                params.weight = 1;
                space.setLayoutParams(params);

                //设置显示
                if (i == 0 && position != 0) {
                    View childView = inflate.inflate(R.layout.item_pro_detail_quality_child_view, null);
                    Button btItem = (Button) childView.findViewById(R.id.btItem);
                    btItem.setText(childDatas.get(i));
                    btItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(context, RiskSourceDetailsActivity.class);
                            context.startActivity(intent);
                        }
                    });
                    childPosiiton++;
                    holder.ll_item.addView(childView);
                } else {
                    View childView = inflate.inflate(R.layout.item_pro_detail_quality_child, null);
                    TextView tvItem = (TextView) childView.findViewById(R.id.tvItem);
                    TextView tvItemPlaceholder = (TextView) childView.findViewById(R.id.tvItemPlaceholder);
                    tvItem.setText(childDatas.get(i));
                    tvItemPlaceholder.setText(maxDatas.get(childPosiiton));
                    //父类第一栏显示标题栏
                    if (position == 0) {
                        tvItem.setTextColor(ContextCompat.getColor(context, R.color.white));
                    } else {
                        //非标题栏
                        //异常 并且异常类是第二项 的字体颜色一致
                        if (type == 0 && childPosiiton == 1) {
                            tvItem.setTextColor(ContextCompat.getColor(context, R.color.red));
                        } else {
                            tvItem.setTextColor(ContextCompat.getColor(context, R.color.text_com_color));
                        }
                    }
                    childPosiiton++;
                    holder.ll_item.addView(childView);
                }
            }
        }else if(obj instanceof LineQualityEntity) {//质量管理列表设计
            childPosiiton = 0;
            LineQualityEntity data =(LineQualityEntity)obj;
            for (int i = 0; i < childDatas.size(); i++) {
                //设置权重比例 为了数据少的时候横向满屏
                Space space = new Space(context);
                holder.ll_item.addView(space);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) space.getLayoutParams();
                params.width = 0;
                params.height = 1;
                params.weight = 1;
                space.setLayoutParams(params);
                //设置显示
                if (i == 0 && position != 0) {
                    View childView = inflate.inflate(R.layout.item_child_check, null);
                    TextView tvCheck=childView.findViewById(R.id.tv_check);
                    tvCheck.setText(childDatas.get(i));
                    tvCheck.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(context, QualityManagementActivity.class);
                            intent.putExtra("line_id",data.getLine_id());
                            context.startActivity(intent);
                        }
                    });
                    childPosiiton++;
                    holder.ll_item.addView(childView);
                }else if(position==0){//设置列表第一行
                    View childView = inflate.inflate(R.layout.item_pro_detail_quality_child, null);
                    TextView tvItem = (TextView) childView.findViewById(R.id.tvItem);
                    TextView tvItemPlaceholder = (TextView) childView.findViewById(R.id.tvItemPlaceholder);
                    tvItem.setText(childDatas.get(i));
                    tvItemPlaceholder.setText(maxDatas.get(childPosiiton));
                    tvItem.setTextColor(ContextCompat.getColor(context, R.color.white));
                    childPosiiton++;
                    holder.ll_item.addView(childView);
                }else if(i==3&& position != 0){//设置评级列
                    View levelView=inflate.inflate(R.layout.item_child_level,null);
                    TextView tvLevel=levelView.findViewById(R.id.tv_level);
                    switch (childDatas.get(3)){
                        case "优":
                            tvLevel.setText("优");
                            tvLevel.setBackgroundResource(R.drawable.background_child_item_ample);
                            childPosiiton++;
                            holder.ll_item.addView(levelView);
                            break;
                        case "中":
                            tvLevel.setText("中");
                            tvLevel.setBackgroundResource(R.drawable.background_child_item_medium);
                            childPosiiton++;
                            holder.ll_item.addView(levelView);
                            break;
                        case "差":
                            tvLevel.setText("差");
                            tvLevel.setBackgroundResource(R.drawable.background_child_item_bad);
                            childPosiiton++;
                            holder.ll_item.addView(levelView);
                            break;
                    }
                }else{
                    View childView = inflate.inflate(R.layout.item_pro_detail_quality_child, null);
                    TextView tvItem = (TextView) childView.findViewById(R.id.tvItem);
                    TextView tvItemPlaceholder = (TextView) childView.findViewById(R.id.tvItemPlaceholder);
                    tvItem.setText(childDatas.get(i));
                    tvItemPlaceholder.setText(maxDatas.get(childPosiiton));
                    childPosiiton++;
                    holder.ll_item.addView(childView);
                }
            }
        }else if(obj instanceof StatusData){//全部子系统列表设置
            childPosiiton = 0;

            /////////////////////////////////////
            for (int i = 0; i < childDatas.size(); i++){
                Space space = new Space(context);
                holder.ll_item.addView(space);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) space.getLayoutParams();
                params.width = 0;
                params.height = 1;
                params.weight = 1;
                space.setLayoutParams(params);
                if(i==3&&position!=0){
                    View statusView=inflate.inflate(R.layout.item_child_stutas,null);
                    TextView tvStatus = statusView.findViewById(R.id.tv_status);
                    switch (childDatas.get(3)){
                        case "不正常":
                            tvStatus.setText("不正常");
                            tvStatus.setBackgroundResource(R.drawable.background_child_item_abnormal_status);
                            childPosiiton++;
                            holder.ll_item.addView(statusView);
                            break;
                        case "正常":
                            tvStatus.setText("正 常");
                            tvStatus.setBackgroundResource(R.drawable.background_child_item_normal_status);
                            childPosiiton++;
                            holder.ll_item.addView(statusView);
                            break;
                    }

                }else if(position==0){
                    View childView = inflate.inflate(R.layout.item_pro_detail_quality_child, null);
                    TextView tvItem = (TextView) childView.findViewById(R.id.tvItem);
                    TextView tvItemPlaceholder = (TextView) childView.findViewById(R.id.tvItemPlaceholder);
                    tvItem.setText(childDatas.get(i));
                    tvItemPlaceholder.setText(maxDatas.get(childPosiiton));
                    tvItem.setTextColor(ContextCompat.getColor(context, R.color.white));
                    childPosiiton++;
                    holder.ll_item.addView(childView);
                }else {
                    View childView = inflate.inflate(R.layout.item_pro_detail_quality_child, null);
                    TextView tvItem = (TextView) childView.findViewById(R.id.tvItem);
                    TextView tvItemPlaceholder = (TextView) childView.findViewById(R.id.tvItemPlaceholder);
                    tvItem.setText(childDatas.get(i));
                    tvItemPlaceholder.setText(maxDatas.get(childPosiiton));
                    childPosiiton++;
                    holder.ll_item.addView(childView);

                }
            }

            ////////////////////////////////////

        }
        else {
            for (String msg : childDatas) {
                //设置权重比例 为了数据少的时候横向满屏
                Space space = new Space(context);
                holder.ll_item.addView(space);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) space.getLayoutParams();
                params.width = 0;
                params.height = 1;
                params.weight = 1;
                space.setLayoutParams(params);
                //设置显示
                View childView = inflate.inflate(R.layout.item_pro_detail_quality_child, null);
                TextView tvItem = (TextView) childView.findViewById(R.id.tvItem);
                TextView tvItemPlaceholder = (TextView) childView.findViewById(R.id.tvItemPlaceholder);
                tvItem.setText(msg);
                tvItemPlaceholder.setText(maxDatas.get(childPosiiton));
                //父类第一栏显示标题栏
                if (position == 0) {
                    tvItem.setTextColor(ContextCompat.getColor(context, R.color.white));
                } else {
                    //非标题栏
                    //异常 并且异常类是第二项 的字体颜色一致
                    if (type == 0 && childPosiiton == 1) {
                        tvItem.setTextColor(ContextCompat.getColor(context, R.color.red));
                    } else {
                        tvItem.setTextColor(ContextCompat.getColor(context, R.color.text_com_color));
                    }
                }
                childPosiiton++;
                holder.ll_item.addView(childView);
            }
        }

        Space space = new Space(context);
        holder.ll_item.addView(space);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) space.getLayoutParams();
        params.width = 0;
        params.height = 1;
        params.weight = 1;
        space.setLayoutParams(params);
//        ProDetailQualityChildAdapter childAdapter = new ProDetailQualityChildAdapter(context, childDatas, maxDatas, type, position);
//        holder.recyclerViewItem.setAdapter(childAdapter);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (datas != null && !datas.isEmpty()) {
            count = 1 + datas.size();
        }
        return count;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerViewItem;
        private LinearLayout ll_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            recyclerViewItem = (RecyclerView) itemView.findViewById(R.id.recyclerViewItem);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }
}

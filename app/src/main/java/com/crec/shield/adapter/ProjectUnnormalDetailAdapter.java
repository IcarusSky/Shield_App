package com.crec.shield.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.crec.shield.entity.project.quality.Quality;
import com.crec.shield.entity.project.status.PointParameterEntity;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalListItemEntity;
import com.crec.shield.ui2_2.activity.EquipmentManagementActivity;
import com.crec.shield.ui2_2.activity.RiskSourceDetailsActivity;
import java.util.ArrayList;
import java.util.List;

public class ProjectUnnormalDetailAdapter  extends RecyclerView.Adapter<ProjectUnnormalDetailAdapter.MyViewHolder> {
    private Context context;
    private List datas;
    /**
     * 每一项最大字符的集合，为了占位使用
     */
    private List<String> maxDatas;
    private LayoutInflater inflate;

    public ProjectUnnormalDetailAdapter(Context context, List datas, List<String> maxDatas) {
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

        } else if (obj instanceof UnnormalListItemEntity) {
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
                UnnormalListItemEntity data = (UnnormalListItemEntity) obj;
                childDatas.add("查看");
                childDatas.add(String.valueOf(data.getStart_num()));
                childDatas.add(String.valueOf(data.getEnd_num()));
                childDatas.add(data.getUnusual_level());
                childDatas.add(data.getUnusual_content());
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
        }

        //父类第一栏显示标题栏
        if (position == 0) {
            holder.ll_item.setBackgroundResource(R.color.radio);
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
        if (obj instanceof UnnormalListItemEntity) {
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
                            intent.setClass(context, EquipmentManagementActivity.class);
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
        } else {
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

        //设置最后一个权重比例 为了数据少的时候横向满屏、并且均分
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

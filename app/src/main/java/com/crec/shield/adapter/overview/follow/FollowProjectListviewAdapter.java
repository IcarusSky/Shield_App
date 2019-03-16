package com.crec.shield.adapter.overview.follow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crec.shield.R;
import com.crec.shield.entity.common.AttentionChildEntity;
import com.crec.shield.entity.common.IsAttentionEntity;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.lzy.okgo.OkGo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.ADD_PROJECT_ATTENTION_TOAST;
import static com.crec.shield.global.StaticConstant.FAILED_ACTION_TOAST;
import static com.crec.shield.global.StaticConstant.FAILED_ADD_PROJECT_ATTENTION_TOAST;
import static com.crec.shield.global.StaticConstant.FAILED_REMOVE_PROJECT_ATTENTION_TOAST;
import static com.crec.shield.global.StaticConstant.REMOVE_PROJECT_ATTENTION_TOAST;
import static com.crec.shield.global.StaticConstant.WITHOUT_LINE_DATA;

public class FollowProjectListviewAdapter extends BaseExpandableListAdapter {

    private Map<String, List<AttentionChildEntity>> datasets = new HashMap<>();
    //private List<AttentionParentEntity> parentEntities;
    private List<String> IdList;
    private List<String> PList;
    private List<String> AreaList;
    private List<String> ProjectList;
    private List<String> StatusList;
    private List<Integer> IsAttentionList;
    private String mToken;
    private Context mContext;
    private HashMap<Integer, Boolean> groupCheckBox;

    public FollowProjectListviewAdapter(Context mContext, String mToken, Map<String, List<AttentionChildEntity>> datasets, List<String> IdList, List<String> PList, List<String> AreaList, List<String> ProjectList, List<String> StatusList, List<Integer> IsAttentionList) {
        this.mContext = mContext;
        this.mToken = mToken;
        this.datasets = datasets;
        this.AreaList = AreaList;
        this.IdList = IdList;
        this.PList = PList;
        this.ProjectList = ProjectList;
        this.StatusList = StatusList;
        this.IsAttentionList = IsAttentionList;
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int parentPos, int childPos) {
        return datasets.get(PList.get(parentPos)).get(childPos);
    }

    //  获得父项的数量
    @Override
    public int getGroupCount() {
        if (datasets == null) {
            Toast.makeText(mContext, "dataset为空", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return datasets.size();
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int parentPos) {
        if (datasets.get(PList.get(parentPos)) == null) {
            Toast.makeText(mContext, "\" + parentList[parentPos] + \" + 数据为空", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return datasets.get(PList.get(parentPos)).size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int parentPos) {
        return datasets.get(PList.get(parentPos));
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int parentPos) {
        return parentPos;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int parentPos, int childPos) {
        return childPos;
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个函数目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @SuppressLint("ResourceAsColor")
    @Override
    public View getGroupView(final int parentPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_follow_project_parent, null);
        }
        view.setTag(R.layout.fragment_follow_project_parent, parentPos);
        view.setTag(R.layout.fragment_follow_project_child, -1);
        TextView text_area = (TextView) view.findViewById(R.id.fllow_area);
        TextView text_project = (TextView) view.findViewById(R.id.fllow_project);
        TextView text_status = (TextView) view.findViewById(R.id.status);
        TextView withoutData = (TextView) view.findViewById(R.id.withoutData);
        final ImageView isAttention = (ImageView) view.findViewById(R.id.parent_img);

        withoutData.setVisibility(View.GONE);
        // 设置展开和收缩的文字颜色
        if (b) {
            text_project.setTextColor(Color.parseColor("#000000"));
            if (getChildrenCount(parentPos) == 0) {
                withoutData.setText(WITHOUT_LINE_DATA);
                withoutData.setTextSize(15);
                withoutData.setTextColor(R.color.color_Aluminum);
                withoutData.setVisibility(View.VISIBLE);
            }

        } else {
            text_project.setTextColor(Color.parseColor("#000000"));
            withoutData.setVisibility(View.GONE);
        }

        if (AreaList.size() != 0) {
            text_area.setText(AreaList.get(parentPos));
        }
        if (ProjectList.size() != 0) {
            text_project.setText(ProjectList.get(parentPos));
        }
        if (StatusList.size() != 0) {
            text_status.setText(StatusList.get(parentPos));
        }

        if (IsAttentionList.size() != 0) {
            if (IsAttentionList.get(parentPos) == 1) {
                isAttention.setImageResource(R.mipmap.img_star);
                isAttention.setTag("select");
            } else {
                isAttention.setImageResource(R.mipmap.img_star_none);
                isAttention.setTag("unSelect");
            }
        }

        isAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAttention.getTag().equals("select") && IdList.get(parentPos) != null) {
                    isAttention.setTag("unSelect");
                    isAttention.setImageResource(R.mipmap.img_star);
                    //parentEntities.get(parentPos).setIsAttention(0);
                    OkGo.post(Url.BASE_URL + Url.OVERVIEW_FOLLOW_REMOVE_PROJECT_ATTENTION)
                            .params("token", mToken)
                            .params("projectId", IdList.get(parentPos))
                            .execute(new JsonCallback<IsAttentionEntity>() {
                                @Override
                                public void onSuccess(IsAttentionEntity isAttentionEntity, Call call, Response response) {
                                    if (isAttentionEntity.getCode() == 1) {
                                        if (isAttentionEntity.isData()) {
                                            isAttention.setTag("unSelect");
                                            isAttention.setImageResource(R.mipmap.img_star_none);
                                            Toast.makeText(mContext, REMOVE_PROJECT_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(mContext, FAILED_REMOVE_PROJECT_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                } else if (isAttention.getTag().equals("unSelect") && IdList.get(parentPos) != null) {
                    OkGo.post(Url.BASE_URL + Url.OVERVIEW_FOLLOW_ADD_PROJECT_ATTENTION)
                            .params("token", mToken)
                            .params("projectId", IdList.get(parentPos))
                            .execute(new JsonCallback<IsAttentionEntity>() {
                                @Override
                                public void onSuccess(IsAttentionEntity isAttentionEntity, Call call, Response response) {
                                    if (isAttentionEntity.getCode() == 1) {
                                        if (isAttentionEntity.isData()) {
                                            isAttention.setTag("select");
                                            isAttention.setImageResource(R.mipmap.img_star);
                                            Toast.makeText(mContext, ADD_PROJECT_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(mContext, FAILED_ADD_PROJECT_ATTENTION_TOAST, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                } else {
                    Toast.makeText(mContext, FAILED_ACTION_TOAST, Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(final int parentPos, final int childPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_follow_project_child, null);
        }
        view.setTag(R.layout.fragment_follow_project_child, parentPos);
        view.setTag(R.layout.activity_camera_child, childPos);
        TextView text_project = (TextView) view.findViewById(R.id.fllow_project);
//        TextView text_line = (TextView) view.findViewById(R.id.line);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        TextView text_today_rings = (TextView) view.findViewById(R.id.today_rings);
        TextView text_present_rings = (TextView) view.findViewById(R.id.present_rings);
        TextView text_total_rings = (TextView) view.findViewById(R.id.total_rings);
        iv_icon.setImageResource(R.mipmap.img_left);
        /*if(datasets.get(PList.get(parentPos)).get(childPos).getType().equals("左线")){
            text_line.setText("左");
            text_line.setBackgroundResource(R.drawable.circle_solid_blue);
        }
            iv_icon.setImageResource(R.mipmap.img_right);
}
        text_today_rings.setText("今日：" + datasets.get(PList.get(parentPos)).get(childPos).getToday_rings() + "环");
        text_present_rings.setText("当前：" + datasets.get(PList.get(parentPos)).get(childPos).getPersent_rings() + "环");
        text_total_rings.setText("总环数：" + datasets.get(PList.get(parentPos)).get(childPos).getTotal_rings() + "环");

        if (childPos + 1 == getChildrenCount(parentPos)){
            view_line.setVisibility(View.GONE);
        }else {
            view_line.setVisibility(View.VISIBLE);
        }*/
        if (datasets.size() != 0) {
            text_project.setText(datasets.get(PList.get(parentPos)).get(childPos).getLine());
//            text_line.setText(datasets.get(PList.get(parentPos)).get(childPos).getType().substring(0,1));
//            text_line.setBackgroundResource(R.drawable.circle_solid_blue);
            String icon = datasets.get(PList.get(parentPos)).get(childPos).getType().substring(0, 1);
            if (icon.equals("右")) {
                iv_icon.setImageResource(R.mipmap.img_right);
            } else {
                iv_icon.setImageResource(R.mipmap.img_left);
            }
            text_today_rings.setText("今日：" + datasets.get(PList.get(parentPos)).get(childPos).getToday_rings() + "环");
            text_present_rings.setText("当前：" + datasets.get(PList.get(parentPos)).get(childPos).getPersent_rings() + "环");
            text_total_rings.setText("总环数：" + datasets.get(PList.get(parentPos)).get(childPos).getTotal_rings() + "环");
        }
        return view;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}

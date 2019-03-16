package com.crec.shield.adapter.overview.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.crec.shield.R;
import com.crec.shield.entity.common.AttentionChildEntity;
import com.crec.shield.entity.common.IsAttentionEntity;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.utils.CustomExpandableListView;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

public class MyProjectListViewAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> selectList;
    private List<String> idList;
    private List<String> areaList;
    private List<String> ProjectList;
    private List<String> StatusList;
    private List<Integer> IsAttentionList;
    private Map<String, List<AttentionChildEntity>> selectData = new HashMap<>();
    private String mToken;


    public MyProjectListViewAdapter(Context mContext, String mToken, Map<String, List<AttentionChildEntity>> selectData, List<String> idList, List<String> selectList, List<String> areaList, List<String> ProjectList, List<String> StatusList, List<Integer> IsAttentionList) {
        this.mContext = mContext;
        this.mToken = mToken;
        this.selectData = selectData;
        this.idList = idList;
        this.areaList = areaList;
        this.selectList = selectList;
        this.ProjectList = ProjectList;
        this.StatusList = StatusList;
        this.IsAttentionList = IsAttentionList;
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int parentPos, int childPos) {
        return selectData.get(selectList.get(parentPos)).get(childPos);
    }

    //  获得父项的数量
    @Override
    public int getGroupCount() {
        if (selectData == null) {
            Toast.makeText(mContext, "selectData为空", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return selectData.size();
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int parentPos) {
        if (selectData.get(selectList.get(parentPos)) == null) {
            Toast.makeText(mContext, "\" + parentList.get(parentPos) + \" + 数据为空", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return selectData.get(selectList.get(parentPos)).size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int parentPos) {
        return selectData.get(selectList.get(parentPos));
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
    public View getGroupView(final int parentPos, final boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_follow_project_parent, null);
        }
        view.setTag(R.layout.fragment_follow_project_parent, parentPos);
        view.setTag(R.layout.fragment_follow_project_child, -1);
        TextView text_area = (TextView) view.findViewById(R.id.fllow_area);
        TextView text_project = (TextView) view.findViewById(R.id.fllow_project);
        TextView text_status = (TextView) view.findViewById(R.id.status);
        TextView withoutData = (TextView) view.findViewById(R.id.withoutData);
        final ImageView isAttention = (ImageView) view.findViewById(R.id.parent_img);

        if (areaList.size() != 0) {
            text_area.setText(areaList.get(parentPos));
        }
        if (selectList.size() != 0) {
            text_project.setText(selectList.get(parentPos));
        }
        if (StatusList.size() != 0) {
            text_status.setText(StatusList.get(parentPos));
        }
        //isAttention.setImageResource(R.drawable.star);

//            设置展开和收缩的文字颜色
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
                if (isAttention.getTag().equals("select") && idList.get(parentPos) != null) {
                    /*isAttention.setTag("unSelect");
                    isAttention.setImageResource(R.drawable.star_grey);*/
                    //parentEntities.get(parentPos).setIsAttention(0);
                    OkGo.post(Url.BASE_URL + Url.OVERVIEW_FOLLOW_REMOVE_PROJECT_ATTENTION)
                            .params("token", mToken)
                            .params("projectId", idList.get(parentPos))
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
                } else if (isAttention.getTag().equals("unSelect") && idList.get(parentPos) != null) {
                    OkGo.post(Url.BASE_URL + Url.OVERVIEW_FOLLOW_ADD_PROJECT_ATTENTION)
                            .params("token", mToken)
                            .params("projectId", idList.get(parentPos))
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
    public View getChildView(final int parentPos, final int childPos, boolean b, View
            view, ViewGroup viewGroup) {
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
        if (selectData.size() != 0) {
            text_project.setText(selectData.get(selectList.get(parentPos)).get(childPos).getLine());
            if (selectData.get(selectList.get(parentPos)).get(childPos).getType() != null) {
//                text_line.setText(selectData.get(selectList.get(parentPos)).get(childPos).getType().substring(0,1));
//                text_line.setBackgroundResource(R.drawable.circle_solid_blue);
                String icon = selectData.get(selectList.get(parentPos)).get(childPos).getType().substring(0, 1);
                if (icon.equals("右")) {
                    iv_icon.setImageResource(R.mipmap.img_right);
                } else {
                    iv_icon.setImageResource(R.mipmap.img_left);
                }
            }
            text_today_rings.setText("今日：" + selectData.get(selectList.get(parentPos)).get(childPos).getToday_rings());
            text_present_rings.setText("当前：" + selectData.get(selectList.get(parentPos)).get(childPos).getPersent_rings());
            text_total_rings.setText("总环数：" + selectData.get(selectList.get(parentPos)).get(childPos).getTotal_rings());
        }

        /*if (selectData.get(selectList.get(parentPos)).get(childPos).getType().equals("左线")) {
//            text_line.setText("左");
//            text_line.setBackgroundResource(R.drawable.circle_solid_blue);
            iv_icon.setImageResource(R.mipmap.img_left);
        } else {
//            text_line.setText("右");
//            text_line.setBackgroundResource(R.drawable.circle_solid_green);
            iv_icon.setImageResource(R.mipmap.img_right);
        }*/
        return view;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    /**
     * 动态改变listView的高度
     */
    public void setListViewHeightBasedOnChildren(ListView listview) {
        ListAdapter listAdapter = listview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listview);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listview.getLayoutParams();
        // params.height = 80 * (listAdapter.getCount() - 1);
        // params.height = 80 * (listAdapter.getCount());
        params.height = totalHeight
                + (listview.getDividerHeight() * (listAdapter.getCount() - 1));
        ((ViewGroup.MarginLayoutParams) params).setMargins(0, 0, 0, 0);
        listview.setLayoutParams(params);

    }

    /**
     * 可扩展listview展开时调用
     *
     * @param listView
     * @param groupPosition
     */
    public static void setExpandedListViewHeightBasedOnChildren(
            CustomExpandableListView listView, int groupPosition) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        if (listAdapter == null) {
            return;
        }
        View listItem = listAdapter.getChildView(groupPosition, 0, true, null,
                listView);
        listItem.measure(0, 0);
        int appendHeight = 0;
        for (int i = 0; i < listAdapter.getChildrenCount(groupPosition); i++) {
            appendHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        Log.d(TAG, "Expand params.height" + params.height);
        params.height += appendHeight;
        listView.setLayoutParams(params);
    }

    /**
     * 可扩展listview收起时调用
     *
     * @param listView
     * @param groupPosition
     */
    public static void setCollapseListViewHeightBasedOnChildren(
            CustomExpandableListView listView, int groupPosition) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        if (listAdapter == null) {
            return;
        }
        View listItem = listAdapter.getChildView(groupPosition, 0, true, null,
                listView);
        listItem.measure(0, 0);
        int appendHeight = 0;
        for (int i = 0; i < listAdapter.getChildrenCount(groupPosition); i++) {
            appendHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height -= appendHeight;
        listView.setLayoutParams(params);
    }
}

package com.crec.shield.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.overview.follow.FollowProjectListviewAdapter;
import com.crec.shield.entity.common.AttentionChildEntity;
import com.crec.shield.entity.common.AttentionParentEntity;
import com.crec.shield.entity.common.AttentionResponse;
import com.crec.shield.entity.overview.camera.CameraResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.ui.activity.ProjectDetailsActivity;
import com.crec.shield.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.WITHOUT_DATA;


public class FollowProjectFragment extends Fragment implements View.OnClickListener {

    private static final String ACTIVITY_TAG = "FollowProjectFragment";


    public static Fragment newInstance() {
        FollowProjectFragment fragment = new FollowProjectFragment();
        return fragment;
    }

    private String mToken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
    private TextView RecycleNULL;
    private ExpandableListView listview;
    private SmartRefreshLayout refreshLayout;
    private FollowProjectListviewAdapter adapter;
    private Map<String, List<AttentionChildEntity>> datasets = new HashMap<>();
    private List<String> PList = new ArrayList<>();
    private List<String> IdList = new ArrayList<>();
    private List<String> AreaList = new ArrayList<>();
    private List<String> ProjectList = new ArrayList<>();
    private List<String> StatusList = new ArrayList<>();
    private List<Integer> IsAttentionList = new ArrayList<>();

    List<AttentionParentEntity> parentEntities;
    List<AttentionChildEntity> childEntities;

    private int lastClick = -1;//上一次点击的group的position

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_follow_project, container, false);
        RecycleNULL = (TextView) v.findViewById(R.id.RecycleNULL);
        listview = (ExpandableListView) v.findViewById(R.id.projectlistview);
        refreshLayout = (SmartRefreshLayout) v.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initialData();
            }
        });
        refreshLayout.autoRefresh();

//        Group点击事件，点击一个Group隐藏其他的(只显示一个)
        listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if (lastClick == -1) {
                    listview.expandGroup(i);
                }
                if (lastClick != -1 && lastClick != i) {
                    listview.collapseGroup(lastClick);
                    listview.expandGroup(i);
                } else if (lastClick == i) {
                    if (listview.isGroupExpanded(i)) {
                        listview.collapseGroup(i);
                    } else if (!listview.isGroupExpanded(i)) {
                        listview.expandGroup(i);
                    }
                }
                lastClick = i;
                return true;
            }
        });
//        子项点击事件
        listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view,
                                        int parentPos, int childPos, long l) {

                Intent intent5 = new Intent(getContext(), ProjectDetailsActivity.class);
                intent5.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                SPUtils.put(AppConstant.PROJECT.projectId,IdList.get(parentPos));
                SPUtils.put(AppConstant.PROJECT.lineId, datasets.get(ProjectList.get(parentPos)).get(childPos).getId());
                startActivity(intent5);
                return true;
            }
        });


        return v;
    }

    /**
     * 初始化数据
     */
    private void initialData() {
        Log.v(FollowProjectFragment.ACTIVITY_TAG, "Enter initialData()");
        parentEntities = new ArrayList<>();
        childEntities = new ArrayList<>();
        PList.clear();
        IdList.clear();
        AreaList.clear();
        ProjectList.clear();
        StatusList.clear();
        IsAttentionList.clear();
        datasets.clear();
        CameraResponse.CameraEntity childEntity;

        OkGo.post(Url.BASE_URL + Url.OVERVIEW_FOLLOW_PROJECT)
                .params("token", mToken)
                .execute(new JsonCallback<AttentionResponse>() {
                    @Override
                    public void onSuccess(AttentionResponse attentionResponse, Call call, Response response) {
                        refreshLayout.finishRefresh();
                        if (attentionResponse.getCode() == 1) {
                            parentEntities = attentionResponse.getData();

                                if (parentEntities.size() == 0) {
                                    RecycleNULL.setText(WITHOUT_DATA);
                                    RecycleNULL.setTextSize(15);
                                    if (isAdded()) {
                                        RecycleNULL.setTextColor(getResources().getColor(R.color.color_Aluminum));
                                    }
                                    RecycleNULL.setVisibility(View.VISIBLE);
                                }

                            for (AttentionParentEntity projectData : parentEntities) {
                                PList.add(projectData.getProject());
                                IdList.add(projectData.getId());
                                AreaList.add(projectData.getArea());
                                ProjectList.add(projectData.getProject());
                                StatusList.add(projectData.getStatus());
                                IsAttentionList.add(projectData.getIsAttention());
                                datasets.put(projectData.getProject(), projectData.getLines());
                            }
                            adapter = new FollowProjectListviewAdapter(context, mToken,datasets, IdList, PList, AreaList,ProjectList, StatusList, IsAttentionList);
                            listview.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        refreshLayout.finishRefresh();
                    }
                });

    }

    @Override
    public void onClick(View v) {

        Logger.d(v.getId());

        switch (v.getId()) {

        }

    }
}

package com.crec.shield.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.SearchHistoryAdapter;
import com.crec.shield.adapter.overview.follow.FollowProjectListviewAdapter;
import com.crec.shield.entity.common.AttentionChildEntity;
import com.crec.shield.entity.common.AttentionParentEntity;
import com.crec.shield.entity.common.AttentionResponse;
import com.crec.shield.entity.common.ProjectSelectionArea;
import com.crec.shield.entity.common.ProjectSelectionEntity;
import com.crec.shield.entity.common.ProjectSelectionResponse;
import com.crec.shield.entity.common.ProjectSelectionStatus;
import com.crec.shield.entity.project.search.HistoryAddResult;
import com.crec.shield.entity.project.search.HistoryTableEntity;
import com.crec.shield.entity.project.search.HistoryTableResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.ui.activity.ProjectDetailsActivity;
import com.crec.shield.ui2_2.activity.SettingActivity;
import com.crec.shield.utils.CustomExpandableListView;
import com.crec.shield.utils.SPUtils;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by hahaliu on 2018/10/1.
 */

public class ProjectFragment extends Fragment implements View.OnClickListener {

    private String mToken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
    private Integer type = (Integer) SPUtils.get(AppConstant.LOGINSTATUS.type, 0);

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.btn_project_search)
    Button mBtnProjectSearch;
    @BindView(R.id.project_status)
    Spinner project_status;
    @BindView(R.id.project_area)
    Spinner project_area;
    @BindView(R.id.project_project)
    Spinner project_project;
    @BindView(R.id.history_recyclerview)
    RecyclerView history_recyclerview;
    @BindView(R.id.no_history)
    TextView no_history;
    private SearchHistoryAdapter searchHistoryAdapter;
    private Unbinder unbinder;
    private Context context;
    private String status = null;
    private String area = null;
    private String project = null;
    private String projectId = null;
    private SQLiteDatabase db;
    private List<ProjectSelectionStatus> projectSelectionStatus = new ArrayList<ProjectSelectionStatus>();  // 所有状态
    private List<ProjectSelectionArea> projectSelectionArea = new ArrayList<ProjectSelectionArea>();   //  所有片区
    private List<ProjectSelectionEntity> projectSelectionEntity = new ArrayList<ProjectSelectionEntity>();   //  所有项目
    private List<HistoryTableEntity> historyProject= new ArrayList<HistoryTableEntity>();
    private List<AttentionParentEntity> selectParentEntities = new ArrayList<>();
    private List<AttentionChildEntity> selectChildEntities;
    private String mCode;
    private HistoryTableEntity history;
    private CustomExpandableListView selectListView;
    private FollowProjectListviewAdapter selectAdapter;
    private Map<String, List<AttentionChildEntity>> selectData = new HashMap<>();
    private List<String> IdList = new ArrayList<>();
    private List<String> SelectList = new ArrayList<>();
    private List<String> AreaList = new ArrayList<>();
    private List<String> ProjectList = new ArrayList<>();
    private List<String> StatusList = new ArrayList<>();
    private List<Integer> IsAttentionList = new ArrayList<>();
    private int lastClick = -1;//上一次点击的group的position

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        ButterKnife.bind(this, view);
        context = getContext();

        if(type == 0||type == 1||type == 3){
            String company_name = SPUtils.get(AppConstant.LOGINSTATUS.company_name,"").toString();
            title.setText(company_name);
        }
        else {
            String project_name = SPUtils.get(AppConstant.LOGINSTATUS.project_name,"").toString();
            title.setText(project_name);
        }

        ivLeft.setOnClickListener(this);   // 个人中心按钮监听
        mBtnProjectSearch.setOnClickListener(this);   // 查询事件监听

        initData();
        initHistory();   //  初始化历史记录

        selectListView = (CustomExpandableListView) view.findViewById(R.id.selectlistview);
        selectAdapter = new FollowProjectListviewAdapter(context, mToken, selectData, IdList, SelectList, AreaList, ProjectList, StatusList, IsAttentionList);
        selectListView.setAdapter(selectAdapter);

         /*
        Group点击事件，点击一个Group隐藏其他的(只显示一个)项目搜索
        */
        selectListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if (lastClick == -1) {
                    selectListView.expandGroup(i);
                }
                if (lastClick != -1 && lastClick != i) {
                    selectListView.collapseGroup(lastClick);
                    selectListView.expandGroup(i);
                } else if (lastClick == i) {
                    if (selectListView.isGroupExpanded(i)) {
                        selectListView.collapseGroup(i);
                    } else if (!selectListView.isGroupExpanded(i)) {
                        selectListView.expandGroup(i);
                    }
                }
                lastClick = i;
                return true;
            }
        });
        /*
        子项点击事件
        */
        selectListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view,
                                        int parentPos, int childPos, long l) {
                selectParentEntities.get(parentPos).getId();
                Intent intent5 = new Intent(getActivity(), ProjectDetailsActivity.class);
                intent5.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                SPUtils.put(AppConstant.PROJECT.projectId, IdList.get(parentPos));
                SPUtils.put(AppConstant.PROJECT.lineId, selectData.get(ProjectList.get(parentPos)).get(childPos).getId());
                startActivity(intent5);
                return true;
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * @company vinelinx
     * @author wangqi
     * @date 2018.9.2
     * @description 初始化历史记录
     */
    private void initHistory() {

        historyProject.clear();
        OkGo.post(Url.BASE_URL+Url.PROJECT_GET_SEARCH_HISTORY)
                .params("token",mToken)
                .execute(new JsonCallback<HistoryTableResponse>() {
                    @Override
                    public void onSuccess(HistoryTableResponse historyTableResponse, Call call, Response response) {
                        if(historyTableResponse.getCode() == 1){
                            historyProject = historyTableResponse.getData();
                            List<HistoryTableEntity> newList = new ArrayList<>();
                            int i = 0;
                            //获取所有key
                            if (historyProject.size() != 0) {
                                no_history.setText(null);
                                for (HistoryTableEntity item : historyProject) {
                                    if (i < 5) {
                                        newList.add(item);
                                        i++;
                                    }
                                }
                            } else {
                                no_history.setText("暂无历史");
                            }

                            searchHistoryAdapter = new SearchHistoryAdapter(context, newList);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                            //设置布局管理器
                            history_recyclerview.setLayoutManager(layoutManager);
                            //设置为垂直布局，这也是默认的
                            layoutManager.setOrientation(OrientationHelper.VERTICAL);
                            //设置Adapter
                            history_recyclerview.setAdapter(searchHistoryAdapter);
                            //设置增加或删除条目的动画
                            history_recyclerview.setItemAnimator(new DefaultItemAnimator());

                            //调用方法,传入一个接口回调
                            searchHistoryAdapter.setItemClickListener(new SearchHistoryAdapter.MyItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent5 = new Intent(getActivity(), ProjectDetailsActivity.class);
                                    intent5.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    SPUtils.put(AppConstant.PROJECT.projectId, historyProject.get(position).getProjectId());
                                    startActivity(intent5);

                                }
                            });
                        }
                    }
                });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        OkGo.post(Url.BASE_URL + Url.PROJECT_SEARCH_CONDITION)
                .params("token", mToken)
                .execute(new JsonCallback<ProjectSelectionResponse>() {
                    @Override
                    public void onSuccess(ProjectSelectionResponse projectSelectionResponse, Call call, Response response) {
                        if (projectSelectionResponse.getCode() == 1) {
                            projectSelectionStatus = projectSelectionResponse.getData();
                            createStatus();
                        }
                    }
                });
    }

    /*
 * 选择状态
 * */
    private void createStatus() {
        ArrayAdapter<String> adapter = null;
        List<String> com1 = new ArrayList<String>();
        for (int i = 0; i < projectSelectionStatus.size(); i++) {
            com1.add(projectSelectionStatus.get(i).getStatus());
        }
        adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout_com, com1);
        adapter.setDropDownViewResource(R.layout.spinner_layout_black);
        project_status.setAdapter(adapter);
        project_status.setOnItemSelectedListener(new spinnerSelectedListenner1());
        createArea();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_project_search:
                initProjectResult();
                break;
            case R.id.iv_left:
                Intent intent4 = new Intent(getActivity(), SettingActivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent4);
                break;
                default:
                    break;
        }
    }

    /**
     * 点击搜索出现项目
     */
    private void initProjectResult() {
        selectParentEntities = new ArrayList<>();
        selectChildEntities = new ArrayList<>();
        history = new HistoryTableEntity();
        IdList.clear();
        SelectList.clear();
        AreaList.clear();
        ProjectList.clear();
        StatusList.clear();
        IsAttentionList.clear();
        selectData.clear();
        String selectText = null;
        if (null != project_project.getSelectedItem()) {
            selectText = project_project.getSelectedItem().toString();
        } else {
            projectId = null;
        }


        OkGo.post(Url.BASE_URL+Url.PROJECT_ADD_SEARCH_HISTORY)
                .params("token",mToken)
                .params("projectId",projectId)
                .params("projectName",project)
                .execute(new JsonCallback<HistoryAddResult>() {
                    @Override
                    public void onSuccess(HistoryAddResult historyAddResult, Call call, Response response) {
                        if(historyAddResult.getCode() == 1){
                            initHistory();
                        }
                    }
                });


        if (null == selectText) {
            selectAdapter.notifyDataSetChanged();
        } else if (null != mToken && null != projectId) {
            OkGo.post(Url.BASE_URL + Url.PROJECT_SEARCH_RESULT)
                    .params("token", mToken)
                    .params("projectId", projectId)
                    .execute(new JsonCallback<AttentionResponse>() {
                        @Override
                        public void onSuccess(AttentionResponse attentionResponse, Call call, Response response) {
                            if (attentionResponse.getCode() == 1) {
                                selectParentEntities = attentionResponse.getData();
                                for (AttentionParentEntity projectData : selectParentEntities) {
                                    IdList.add(projectData.getId());
                                    SelectList.add(projectData.getProject());
                                    AreaList.add(projectData.getArea());
                                    ProjectList.add(projectData.getProject());
                                    StatusList.add(projectData.getStatus());
                                    IsAttentionList.add(projectData.getIsAttention());
                                    selectData.put(projectData.getProject(), projectData.getLines());
                                }
                                selectAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                        }
                    });
        }
    }

    /*
   * 状态监听
   * */
    private class spinnerSelectedListenner1 implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            String tempstr = parent.getItemAtPosition(position).toString();
            for (ProjectSelectionStatus se : projectSelectionStatus) {
                if (se.getStatus().equals(tempstr)) {
                    status = se.getStatus();
                }
            }
            createArea();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    /**
       * 选择片区
       * */
    private void createArea() {
        List<String> com2 = new ArrayList<String>();
        int i = 0;
        for (ProjectSelectionStatus se : projectSelectionStatus) {
            if (se.getStatus().equals(status)) {
                projectSelectionArea = se.getArea();
                for (ProjectSelectionArea sa : projectSelectionArea) {
                    com2.add(se.getArea().get(i).getAreaname());
                    i++;
                }
                break;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout_com, com2);
        adapter.setDropDownViewResource(R.layout.spinner_layout_black);
        project_area.setAdapter(adapter);
        project_area.setOnItemSelectedListener(new spinnerSelectedListenner2());
        createProject();
    }

    /*
    * 片区监听
    * */
    private class spinnerSelectedListenner2 implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            String tempstr = parent.getItemAtPosition(position).toString();
            for (ProjectSelectionArea se : projectSelectionArea) {
                if (se.getAreaname().equals(tempstr)) {
                    area = se.getAreaname();
                }
            }
            createProject();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    /*
     * 选择项目
     * */
    private void createProject() {
        List<String> com3 = new ArrayList<String>();
        int i = 0;
        for (ProjectSelectionArea ar : projectSelectionArea) {
            if (ar.getAreaname().equals(area)) {
                projectSelectionEntity = ar.getProject();
                if (!projectSelectionEntity.equals(null)) {
                    for (ProjectSelectionEntity se : ar.getProject()) {
                        com3.add(se.getProjectname());
                    }
                } else {
                    com3.add("");
                }
                break;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout_com, com3);
        adapter.setDropDownViewResource(R.layout.spinner_layout_black);
        project_project.setAdapter(adapter);
        project_project.setOnItemSelectedListener(new spinnerSelectedListenner3());

    }

    /**
     * 项目监听
     */
    private class spinnerSelectedListenner3 implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            String tempstr = parent.getItemAtPosition(position).toString();
            for (ProjectSelectionEntity se : projectSelectionEntity) {
                if (se.getProjectname().equals(tempstr)) {
                    project = se.getProjectname();
                    projectId = se.getProjectid();
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}

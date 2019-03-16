package com.crec.shield.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.ProDetailQualityAdapter;
import com.crec.shield.entity.project.quality.Quality;
import com.crec.shield.entity.project.quality.QualityResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.utils.DBManager;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.SqliteHelper;
import com.crec.shield.weidget.CheckableTextView;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


public class ProjectDetailsQualityFragment extends Fragment implements View.OnClickListener {

    private static final String ACTIVITY_TAG = "ProjectDetailsQualityFragment";

    private View v;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerViewDatas;
    private CheckableTextView getAll, getError;
    private ProDetailQualityAdapter qualityAdapter;
    private TextView tableNull;
    private List<Quality> qualityListData = new ArrayList<>();
    private SqliteHelper sqlHelper;
    private SQLiteDatabase db;
    private String mToken;

    public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }
    /**
     * -1全部 0异常
     */
    String status = "-1";

    public static Fragment newInstance() {
        ProjectDetailsQualityFragment fragment = new ProjectDetailsQualityFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_projectdetails_quality, container, false);
        // 获取token
        sqlHelper = DBManager.getInstance(context);
        db = sqlHelper.getWritableDatabase();
        mToken = sqlHelper.queryUser(db);

        tableNull = (TextView) v.findViewById(R.id.tableNULL);
        recyclerViewDatas = (RecyclerView) v.findViewById(R.id.recyclerViewDatas);
        refreshLayout = (SmartRefreshLayout) v.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadMore(false);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initTableData();
            }
        });

        getAll = (com.crec.shield.weidget.CheckableTextView) v.findViewById(R.id.getAll);
        getError = (com.crec.shield.weidget.CheckableTextView) v.findViewById(R.id.getError);
        getAll.setOnClickListener(this);
        getError.setOnClickListener(this);

        recyclerViewDatas.setLayoutManager(new LinearLayoutManager(context));
        getAll.setChecked(true);
        refreshLayout.autoRefresh();
        status = "-1";
        initTableData();
        return v;
    }

    private void initTableData() {
        String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
        getAll.setEnabled(false);
        getError.setEnabled(false);
        OkGo.post(Url.BASE_URL + Url.PROJECT_QUALITY)
                .params("token", mToken)
                .params("lineId", lineId)
                .params("status", status)
                .execute(new JsonCallback<QualityResponse>() {
                    @Override
                    public void onSuccess(QualityResponse quality, Call call, Response response) {
                        refreshLayout.finishRefresh();
                        if (quality.getCode() == 1) {
                            qualityListData = quality.getData();
                            if (qualityListData != null && qualityListData.size() > 0) {
                                if (qualityListData.get(0).getHigh_bias() != null) {
                                    tableNull.setVisibility(View.GONE);
                                }
                            } else if (qualityListData == null || qualityListData.size() == 0) {
                                tableNull.setVisibility(View.VISIBLE);
                            }
                            initTable();

                            /**
                             * 占位字符集合
                             */
                            List<String> maxDatas = new ArrayList<>();
                            maxDatas.add("环号中");
                            maxDatas.add("状态");
                            maxDatas.add("管片平偏");
                            maxDatas.add("管片高偏");
                            maxDatas.add("相邻管片错台");
                            maxDatas.add("相邻环片错台");
                            qualityAdapter = new ProDetailQualityAdapter(context, qualityListData, maxDatas);
                            recyclerViewDatas.setAdapter(qualityAdapter);
                        }
                        getAll.setEnabled(true);
                        getError.setEnabled(true);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        refreshLayout.finishRefresh();
                        qualityListData = null;
                        tableNull.setVisibility(View.VISIBLE);
                        if (qualityAdapter != null) {
                            qualityAdapter.notifyDataSetChanged();
                        }

                        getAll.setEnabled(true);
                        getError.setEnabled(true);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        Logger.d(v.getId());
        switch (v.getId()) {
            case R.id.getAll:
                if (!refreshLayout.isRefreshing()) {
                    getAll.setChecked(true);
                    getError.setChecked(false);
                    status = "-1";
                    refreshLayout.autoRefresh();
                    initTableData();
                } else {
                    refreshLayout.finishRefresh();
                }
                break;
            case R.id.getError:
                if (!refreshLayout.isRefreshing()) {
                    getAll.setChecked(false);
                    getError.setChecked(true);
                    status = "0";
                    refreshLayout.autoRefresh();
                    initTableData();
                } else {
                    refreshLayout.finishRefresh();
                }
                break;
            default:
                break;
        }
    }


    @SuppressLint("LongLogTag")
    private void initTable() {

        String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
        OkGo.post(Url.BASE_URL + Url.PROJECT_QUALITY)
                .params("token", mToken)
                .params("lineId", lineId)
                .params("status", -1)
                .execute(new JsonCallback<QualityResponse>() {
                    @Override
                    public void onSuccess(QualityResponse quality, Call call, Response response) {
                        if (quality.getCode() == 1){
                            List<Quality> qualityData = quality.getData();
                        int right = 0;
                        int error = 0;
                        for (Quality q : qualityData) {
                            if (null != q.getStatus()) {
                                if (q.getStatus() == 0) {
                                    error++;
                                } else {
                                    right++;
                                }
                            }
                        }
                        TextView right_ring = (TextView) v.findViewById(R.id.right_ring);
                        TextView error_ring = (TextView) v.findViewById(R.id.error_ring);
                        right_ring.setText(right+ "");
                        error_ring.setText(error + "");
                    }
                    }
                });
    }
}

package com.crec.shield.ui2_2.activity;

import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.ProjectQualityListContract;
import com.crec.shield.di.ActivityComponent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.ProDetailQualityAdapter;
import com.crec.shield.entity.project.quality.Quality;
import com.crec.shield.entity.project.quality.QualityResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.presenter.ProjectQualityListPresenter;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.WITHOUT_DATA;

public class ProjectQualityListActivity extends BaseActivity<ProjectQualityListPresenter> implements ProjectQualityListContract.View {

    @BindView(R.id.getAll)
    CheckableTextView getAll;
    @BindView(R.id.getError)
    CheckableTextView getError;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.right_ring)
    TextView rightRing;
    @BindView(R.id.error_ring)
    TextView errorRing;
    @BindView(R.id.tableNULL)
    TextView tableNull;
    @BindView(R.id.recyclerViewDatas)
    RecyclerView recyclerViewDatas;
    @BindView(R.id.btn_left)
    ImageView button;


    String status = "-1";
    String mToken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
    private List<Quality> qualities = new ArrayList<Quality>();
    String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
    private ProDetailQualityAdapter qualityAdapter;
    private Unbinder unbinder;
    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_projectdetails_quality;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDatas.setLayoutManager(layoutManager);

        unbinder = ButterKnife.bind(this);
        mPresenter.getData(mToken,lineId,status);

    }
    @OnClick(R.id.btn_left)//返回键
    public void onViewClicked() {
        super.onBackPressed();
        finish();
    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.28
     * @description 显示整个界面
     */
    @Override
    public void showData(List<Quality> data) {
        initTable(data);
        qualities=data;
        if (data != null && data.size() > 0) {
            if (data.get(0).getStatus() != null) {
                initQualityTable();
            }
            tableNull.setVisibility(View.GONE);
        } else if (data == null || data.size() == 0) {
            tableNull.setText(WITHOUT_DATA);
            tableNull.setTextSize(15);
            tableNull.setTextColor(getResources().getColor(R.color.color_Aluminum));
            tableNull.setVisibility(View.VISIBLE);
            if (qualityAdapter != null) {
                qualityAdapter.notifyDataSetChanged();
            }
        }

    }

    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.28
     * @description 点击切换全部或异常列表
     */
    @OnClick({R.id.getError,R.id.getAll})
    public void onViewClicked(CheckableTextView button) {
        switch (button.getId()) {
            case R.id.getAll:
                if (!refreshLayout.isRefreshing()) {
                    getAll.setChecked(true);
                    getError.setChecked(false);
                    status = "-1";
                    refreshLayout.autoRefresh();
                    mPresenter.getData(mToken,lineId,status);//通过重新取参数来重新显示界面
//                    initQualityTable();
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
                    mPresenter.getData(mToken,lineId,status);//通过重新取参数来重新显示界面
//                    initQualityTable();
                } else {
                    refreshLayout.finishRefresh();
                }
                break;
            default:
                break;
        }
    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.28
     * @description 初始化质量环数
     */
    private void initTable(List<Quality> data) {
            int right = 0;
            int error = 0;
            for (Quality q : data) {
                if (null != q.getStatus()) {
                    if (q.getStatus() == 0) {
                        error++;
                    } else {
                        right++;
                    }
                }
            }
            rightRing.setText(right+ "");
            errorRing.setText(error + "");

    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.28
     * @description 初始化质量列表
     */

    private void initQualityTable(){

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
        qualityAdapter = new ProDetailQualityAdapter(getContext(), qualities, maxDatas);
        recyclerViewDatas.setAdapter(qualityAdapter);                                          //通过适配器来画表格
        getAll.setEnabled(true);
        getError.setEnabled(true);
    }

}

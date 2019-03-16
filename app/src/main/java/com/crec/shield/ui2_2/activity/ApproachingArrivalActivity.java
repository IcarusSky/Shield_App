package com.crec.shield.ui2_2.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.PromptAdapter;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.ApproachingArrivalContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.overview.overview.ApproachingPromptEntity;
import com.crec.shield.entity.overview.overview.ApproachingPromptResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.presenter.ApproachingArrivalPresenter;
import com.crec.shield.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.APPROACHING_ARRIVAL;
import static com.crec.shield.global.StaticConstant.APPROACHING_ARRIVED;
import static com.crec.shield.global.StaticConstant.APPROACHING_HAVING;
import static com.crec.shield.global.StaticConstant.APPROACHING_START;
import static com.crec.shield.global.StaticConstant.ARRIVAL;
import static com.crec.shield.global.StaticConstant.DAY;
import static com.crec.shield.global.StaticConstant.DISTANCE_ARRIVAL;
import static com.crec.shield.global.StaticConstant.ESTIMMATE;
import static com.crec.shield.global.StaticConstant.REMAINING_RING;
import static com.crec.shield.global.StaticConstant.RING;
import static com.crec.shield.global.StaticConstant.START_DATE;
import static com.crec.shield.global.StaticConstant.TOTAL_RING;
import static com.crec.shield.global.StaticConstant.WITHOUT_DATA;

public class ApproachingArrivalActivity extends BaseActivity<ApproachingArrivalPresenter> implements ApproachingArrivalContract.View {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.begin_endRecycleNULL)
    TextView begin_endRecycleNULL;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String mtoken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
    private List<String> infoLine, infoLineStatus, infoRemain, infoRing, infoExpect, infoDate, infoArrive, infoDayOrTime, infoDay, infoStartStation, infoEndStation;
    private List<Integer> infoType;
    List<ApproachingPromptEntity> approachingPromptData;
    private PromptAdapter recycleAdapter;
    private Unbinder unbinder;

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_approaching_arrival;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initApproachingArrivalPrompt();        //始发到达提示
            }
        });
        refreshLayout.autoRefresh();
    }

    /**
     * @company vinelinx
     * @author wangqi
     * @description 始发到达数据
     * @modifiedBy AnnieYoung
     */
    private void initApproachingArrivalPrompt() {
        infoType = new ArrayList<>();
        infoLine = new ArrayList<>();
        infoLineStatus = new ArrayList<>();
        infoRemain = new ArrayList<>();
        infoRing = new ArrayList<>();
        infoExpect = new ArrayList<>();
        infoDate = new ArrayList<>();
        infoArrive = new ArrayList<>();
        infoDayOrTime = new ArrayList<>();
        infoDay = new ArrayList<>();
        infoStartStation = new ArrayList<>();
        infoEndStation = new ArrayList<>();
        if (mtoken != null) {
            OkGo.post(Url.BASE_URL + Url.OVERVIEW_START_ARRIVALS)
                    .params("token", mtoken)
                    .execute(new JsonCallback<ApproachingPromptResponse>() {
                        @Override
                        public void onSuccess(ApproachingPromptResponse approachingPromptResponse, Call call, Response response) {
                            if(refreshLayout == null){
                                return;
                            }
                            refreshLayout.finishRefresh();
                            if(approachingPromptResponse == null){
                                return;
                            }
                            if (approachingPromptResponse.getCode() == 1) {
                                approachingPromptData = approachingPromptResponse.getData();
                                if (approachingPromptData.size() == 0) {
                                    begin_endRecycleNULL.setText(WITHOUT_DATA);
                                    begin_endRecycleNULL.setTextSize(15);
                                    begin_endRecycleNULL.setTextColor(getResources().getColor(R.color.color_Aluminum));
                                    begin_endRecycleNULL.setVisibility(View.VISIBLE);
                                } else {
                                    for (ApproachingPromptEntity i : approachingPromptData) {
                                        if (i.getRemainDays() == null) {
                                            i.setRemainDays("0");
                                        }
                                        if (i.getLineName() == null) {
                                            i.setLineName("");
                                        }
                                        if (i.getTotalRings() == null) {
                                            i.setTotalRings("0");
                                        }
                                        if (i.getRemainRings() == null) {
                                            i.setRemainRings("0");
                                        }
                                        if (i.getStartDate() == null) {
                                            i.setStartDate("");
                                        }
                                        if (i.getEndDate() == null) {
                                            i.setEndDate("");
                                        }
                                        if (i.getSectionStart() == null) {
                                            i.setSectionStart("");
                                        }
                                        if (i.getSectionEnd() == null) {
                                            i.setSectionEnd("");
                                        }
                                        //  type为0是始发提示，type为1是即将到达，type为2是已经到达
                                        if (i.getType() == 0) {
                                            infoType.add(i.getType()); //APPROACHING_START_PROMPT
                                            infoLine.add(i.getLineName());
                                            infoLineStatus.add(APPROACHING_START);
                                            infoRemain.add(TOTAL_RING);
                                            infoRing.add(i.getTotalRings() + RING);
                                            infoExpect.add("");
                                            infoDate.add("");
                                            infoArrive.add("");
                                            infoDayOrTime.add(START_DATE);
                                            infoDay.add(i.getStartDate());
                                            infoStartStation.add(i.getSectionStart());
                                            infoEndStation.add(i.getSectionEnd());
                                        } else if (i.getType() == 1) {
                                            infoType.add(i.getType()); // APPROACHING_ARRIVAL_PROMPT
                                            infoLine.add(i.getLineName());
                                            infoLineStatus.add(APPROACHING_ARRIVAL);
                                            infoRemain.add(REMAINING_RING);
                                            infoRing.add(i.getRemainRings() + RING);
                                            infoExpect.add(ESTIMMATE);
                                            infoDate.add(i.getEndDate());
                                            infoArrive.add(ARRIVAL);
                                            infoDayOrTime.add(DISTANCE_ARRIVAL);
                                            infoDay.add(i.getRemainDays() + DAY);
                                            infoStartStation.add(i.getSectionStart());
                                            infoEndStation.add(i.getSectionEnd());
                                        }else if(i.getType() == 2){
                                            infoType.add(i.getType()); // APPROACHING_ARRIVAL_PROMPT
                                            infoLine.add(i.getLineName());
                                            infoLineStatus.add("");
                                            infoRemain.add(TOTAL_RING);
                                            infoRing.add(i.getTotalRings() + RING);
                                            infoExpect.add("");
                                            infoDate.add("");
                                            infoArrive.add("");
                                            infoDayOrTime.add(APPROACHING_HAVING+i.getEndDate());
                                            infoDay.add(APPROACHING_ARRIVED);
                                            infoStartStation.add(i.getSectionStart());
                                            infoEndStation.add(i.getSectionEnd());
                                        }
                                    }
                                    recycleAdapter = new PromptAdapter(getContext(), infoType, infoLine, infoLineStatus, infoRemain, infoRing, infoExpect, infoDate, infoArrive, infoDayOrTime, infoDay, infoStartStation, infoEndStation);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                                    //设置布局管理器
                                    recyclerView.setLayoutManager(layoutManager);
                                    DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                                    itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_line_com));
                                    recyclerView.addItemDecoration(itemDecoration);
                                    //设置Adapter
                                    recyclerView.setAdapter(recycleAdapter);
                                    begin_endRecycleNULL.setVisibility(View.GONE);
                                }
                            } else {
                                begin_endRecycleNULL.setText(WITHOUT_DATA);
                                begin_endRecycleNULL.setTextSize(15);
                                begin_endRecycleNULL.setTextColor(getResources().getColor(R.color.color_Aluminum));
                                begin_endRecycleNULL.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            refreshLayout.finishRefresh();
                        }
                    });
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //断开View引用
        mPresenter.detachView();
        unbinder.unbind();
    }

}

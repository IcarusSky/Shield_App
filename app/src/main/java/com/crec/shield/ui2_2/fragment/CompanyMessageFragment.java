package com.crec.shield.ui2_2.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.MessageAdapter;
import com.crec.shield.base.BaseFragment;
import com.crec.shield.contract.CompanyMessageContract;
import com.crec.shield.di.FragmentComponent;
import com.crec.shield.entity.message.MessageItemData;
import com.crec.shield.entity.message.MessageItemResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.presenter.CompanyMessagePresenter;
import com.crec.shield.ui2_2.activity.MessageSettingActivity;
import com.crec.shield.ui.activity.ProjectDetailsActivity;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.SysApplication;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.WARN_MOVESIZE;
import static com.crec.shield.global.StaticConstant.WARN_RISK;
import static com.crec.shield.global.StaticConstant._MOVESIZE;
import static com.crec.shield.global.StaticConstant._MOVESIZ_UNIT;
import static com.crec.shield.global.StaticConstant._RISK_LEVEL;

public class CompanyMessageFragment extends BaseFragment<CompanyMessagePresenter> implements CompanyMessageContract.View, OnRefreshListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.btn_right)
    ImageView btnRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.guide_shift)
    Button mbtnGuideShift;
    @BindView(R.id.source_of_risk)
    Button mbtnSourceOfRisk;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tableNULL)
    TextView tableNull;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private Integer type = (Integer) SPUtils.get(AppConstant.LOGINSTATUS.type, 0);
    private List<String> warnId = new ArrayList<>();
    private List<String> warnName = new ArrayList<>();
    private List<String> warnTime = new ArrayList<>();
    private List<String> warnContent = new ArrayList<>();
    private List<String> project = new ArrayList<>();
    private List<String> line = new ArrayList<>();
    private List<Integer> image = new ArrayList<>();
    private List<Integer> warnStatus = new ArrayList<>();
    private MessageAdapter recycleAdapter;
    private List<MessageItemData> messageItemData;
    private Integer Moveize;
    private Integer RiskLevel;
    private MyListener myListener;
    private Integer messageType = 0;
    private Integer clickPosition = -1;
    private Integer unReadCount = 0;
    private boolean unRead = false;
    public Context context;
    private Unbinder unbinder;

    @Override
    protected void initInject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_company_message;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        initData();
    }

    public interface MyListener {
        public void sendContent(Integer info);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myListener = (MyListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(this.getLayoutId(), container, false);
        SysApplication.getInstance().addActivity(getActivity());
        unbinder = ButterKnife.bind(this, view);

        if (type == 0|| type == 1 || type == 3) {
            String company_name = SPUtils.get(AppConstant.LOGINSTATUS.company_name, "").toString();
            toolbarTitle.setText(company_name);
        } else {
            String project_name = SPUtils.get(AppConstant.LOGINSTATUS.project_name, "").toString();
            toolbarTitle.setText(project_name);
        }

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageSettingActivity.class);
                startActivityForResult(intent, 98);
            }
        });

        mbtnGuideShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbtnGuideShift.setTextColor(Color.parseColor("#FFFFFF"));
                mbtnGuideShift.setBackgroundColor(Color.parseColor("#5A7BEF"));
                mbtnSourceOfRisk.setTextColor(getResources().getColor(R.color.font_black));
                mbtnSourceOfRisk.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                messageType = 0;
                SPUtils.put(AppConstant.MESSAGE.MessageType, messageType);
                initData();
            }
        });
        mbtnSourceOfRisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbtnSourceOfRisk.setTextColor(Color.parseColor("#FFFFFF"));
                mbtnSourceOfRisk.setBackgroundColor(Color.parseColor("#5A7BEF"));
                mbtnGuideShift.setTextColor(getResources().getColor(R.color.font_black));
                mbtnGuideShift.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                messageType = 1;
                SPUtils.put(AppConstant.MESSAGE.MessageType, messageType);
                initData();
            }
        });

        recycleAdapter = new MessageAdapter(context, warnName, warnTime, warnContent, warnStatus, image, project, line);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //调用方法,传入一个接口回调
        recycleAdapter.setItemClickListener(new MessageAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                if (image.get(position) == R.mipmap.img_msg_item) {
                    unRead = true;
                }
                image.set(position, R.mipmap.img_msg_item_none);
                //sqlHelper.changeMessageToDB(db, warnId.get(position));
                clickPosition = position;

                Intent intent = new Intent();

                if (warnStatus.get(position) == 1) {
                    intent.setClass(context, ProjectDetailsActivity.class);
                    SPUtils.put(AppConstant.PROJECT.projectId, project.get(position));
                    SPUtils.put(AppConstant.PROJECT.lineId, line.get(position));
                    new Thread(){
                        public void run(){
                            try{
                                OkGo.post(Url.BASE_URL + Url.MESSAGE_ADD_MESSAGE_LIST)
                                        .params("token", SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString())
                                        .params("type",1)
                                        .params("messageId",warnId.get(position))
                                        .execute();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    //sqlHelper.changeMessageToDB(db, warnId.get(position));
                    intent.putExtra("id", 1);
                } else if (warnStatus.get(position) == 2) {
                    intent.setClass(context, ProjectDetailsActivity.class);
                    SPUtils.put(AppConstant.PROJECT.projectId, project.get(position));
                    SPUtils.put(AppConstant.PROJECT.lineId, line.get(position));
                    new Thread(){
                        public void run(){
                            try{
                                OkGo.post(Url.BASE_URL + Url.MESSAGE_ADD_MESSAGE_LIST)
                                        .params("token", SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString())
                                        .params("type",0)
                                        .params("messageId",warnId.get(position))
                                        .execute();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    //sqlHelper.changeMessageToDB(db, warnId.get(position));
                    intent.putExtra("id", 3);
                }
                startActivity(intent);
            }
        });

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.autoRefresh();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();  //释放所有绑定的view
    }

    private void initData() {
        String AlarmMoveize = (String) SPUtils.get(AppConstant.MESSAGE.AlarmMoveize, "");
        String AlarmRiskLevel = (String) SPUtils.get(AppConstant.MESSAGE.AlarmRiskLevel, "");
        warnId.clear();
        warnName.clear();
        warnTime.clear();
        warnContent.clear();
        warnStatus.clear();
        project.clear();
        line.clear();
        image.clear();
        recycleAdapter.notifyDataSetChanged();
        if (null == AlarmMoveize || AlarmMoveize == "") {
            Moveize = 80;
        } else {
            Moveize = Integer.parseInt(AlarmMoveize);
        }
        if (null == AlarmRiskLevel || AlarmMoveize == "") {
            RiskLevel = 1;
        } else {
            RiskLevel = Integer.parseInt(AlarmRiskLevel);
        }

        OkGo.post(Url.BASE_URL + Url.MESSAGE_MESSAGE_LIST)
                .params("token", SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString())
                .execute(new JsonCallback<MessageItemResponse>() {
                    @Override
                    public void onSuccess(MessageItemResponse messageItemResponse, Call call, Response response) {
                        refreshLayout.finishRefresh();
                        if (messageItemResponse.getCode() == 1) {
                            messageItemData = messageItemResponse.getData();

                            Collections.sort(messageItemData, new Comparator<MessageItemData>() {
                                @Override
                                public int compare(MessageItemData messageItemData, MessageItemData t1) {
                                    return (t1.getTime() == null ? "" : t1.getTime()).compareTo(messageItemData.getTime() == null ? "" : messageItemData.getTime());
                                }
                            });
                            Integer isRead = 1;
                            unReadCount = 0;
                            String messageRiskLevel = null;
                            for (MessageItemData message : messageItemData) {

                                // 偏移量预警
                                if (message.getType() == 1 && Math.abs(message.getMoveSize()) >= Moveize && messageType == 0) {
                                    warnId.add(message.getMessageId() == null ? "" : message.getMessageId());
                                    warnName.add(WARN_MOVESIZE);
                                    warnTime.add(message.getTime() == null ? "" : message.getTime());
                                    warnContent.add(message.getProjectName() + _MOVESIZE + message.getMoveSize() + _MOVESIZ_UNIT);
                                    warnStatus.add(message.getType() == null ? 1 : message.getType());
                                    project.add(message.getProjectId() == null ? "" : message.getProjectId());
                                    line.add(message.getLineId() == null ? "" : message.getLineId());
                                    if(message.getIsRead() == 0){
                                        image.add(R.mipmap.img_msg_item);
                                        unReadCount++;
                                    }else {
                                        image.add(R.mipmap.img_msg_item_none);
                                    }
//                                    sqlHelper.MessageInit(db, message);  // 查看dqlite是否有这条数据，没有就在本数据库插入这条数据
//                                    isRead = sqlHelper.queryMessageStatus(db, message);
//                                    if (isRead == 0) {
//                                        image.add(R.mipmap.img_msg_item_none);
//                                    } else {
//                                        image.add(R.mipmap.img_msg_item);
//                                        unReadCount++;
//                                    }
                                }

                                // 风险预警
                                if (message.getType() == 2 && message.getRiskLevel() <= RiskLevel && messageType == 1) {
                                    if (message.getRiskLevel() == 1) {
                                        messageRiskLevel = "Ⅰ";
                                    } else if (message.getRiskLevel() == 2) {
                                        messageRiskLevel = "Ⅱ";
                                    } else if (message.getRiskLevel() == 3) {
                                        messageRiskLevel = "Ⅲ";
                                    } else if (message.getRiskLevel() == 4) {
                                        messageRiskLevel = "Ⅳ";
                                    }else {
                                        messageRiskLevel = "未知";
                                    }

                                    warnId.add(message.getMessageId() == null ? "" : message.getMessageId());
                                    warnName.add(WARN_RISK);
                                    warnTime.add(message.getTime() == null ? "" : message.getTime());
                                    warnContent.add(message.getProjectName() + _RISK_LEVEL + messageRiskLevel);
                                    warnStatus.add(message.getType() == null ? 2 : message.getType());
                                    project.add(message.getProjectId() == null ? "" : message.getProjectId());
                                    line.add(message.getLineId() == null ? "" : message.getLineId());
                                    if(message.getIsRead() == 0){
                                        image.add(R.mipmap.img_msg_item);
                                        unReadCount++;
                                    }else {
                                        image.add(R.mipmap.img_msg_item_none);
                                    }
//                                    sqlHelper.MessageInit(db, message);  // 查看dqlite是否有这条数据，没有就在本数据库插入这条数据
//                                    isRead = sqlHelper.queryMessageStatus(db, message);
//                                    if (isRead == 0) {
//                                        image.add(R.mipmap.img_msg_item_none);
//                                    } else {
//                                        image.add(R.mipmap.img_msg_item);
//                                        unReadCount++;
//                                    }
                                }
                            }
                            recycleAdapter.notifyDataSetChanged();
                            if (warnName.size() == 0) {
                                tableNull.setVisibility(View.VISIBLE);
                            } else {
                                tableNull.setVisibility(View.GONE);
                            }

                            myListener.sendContent(unReadCount);//将内容进行回传
                            SPUtils.put(AppConstant.MESSAGE.MessageCount, unReadCount);

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
    public void onResume() {
        super.onResume();
        if (clickPosition >= 0) {
            recycleAdapter.notifyItemChanged(clickPosition);
            if(unRead){
                unReadCount = unReadCount-1;
                myListener.sendContent(unReadCount);//将内容进行回传
                SPUtils.put(AppConstant.MESSAGE.MessageCount, unReadCount);
                unRead = false;
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //断开View引用
        mPresenter.detachView();
        unbinder.unbind();
    }
}

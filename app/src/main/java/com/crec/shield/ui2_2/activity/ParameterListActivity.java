package com.crec.shield.ui2_2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crec.shield.R;
import com.crec.shield.adapter.ProDetailQualityAdapter;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.ParameterListContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.crec22.project.parameterlist.ParameterListEntity;
import com.crec.shield.entity.crec22.project.parameterlist.StatusData;
import com.crec.shield.presenter.ParameterListPresenter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.crec.shield.global.StaticConstant.WITHOUT_DATA;

public class ParameterListActivity extends BaseActivity<ParameterListPresenter> implements ParameterListContract.View{

    @BindView(R.id.rv_ParameterListTable)
    RecyclerView rvParameterListTable;
    @BindView(R.id.tx_TableNULL)
    TextView txTableNULL;
    @BindView(R.id.layout_Subsystem)
    LinearLayout layoutSubsystem;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    private Unbinder unbinder;
    private List<ParameterListEntity> parameterListData = new ArrayList<>();
    private ProDetailQualityAdapter parameterListAdapter;
    List<String> maxDatas_constant = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_parameterlist;
    }

    //跳转子系统
    @OnClick(R.id.layout_Subsystem)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(),SystemListActivity.class);
        startActivityForResult(intent,22);
    }
    //子系统销毁后调用，接受处理子系统返回的数据，刷新列表
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==22&&resultCode==10){
        Bundle bundle = data.getExtras();
        List <StatusData> subsystemData = (List<StatusData>) bundle.getSerializable("subsystemData");
        parameterListAdapter = new ProDetailQualityAdapter(getApplicationContext(), subsystemData, maxDatas_constant);
            rvParameterListTable.setAdapter(parameterListAdapter);}
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvParameterListTable.setLayoutManager(layoutManager);
        mPresenter.getParameterListData();
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void showParameterListData(List<ParameterListEntity> data) {
        parameterListData = data;
        if (data != null && data.size() > 0) {
            if (data.get(0).getStatusData().get(0).getPoint_name() != null) {
                initTable();
            }
            txTableNULL.setVisibility(View.GONE);
        } else if (data == null || data.size() == 0) {
            txTableNULL.setText(WITHOUT_DATA);
            txTableNULL.setTextSize(15);
            txTableNULL.setTextColor(getResources().getColor(R.color.color_Aluminum));
            txTableNULL.setVisibility(View.VISIBLE);
            if (parameterListAdapter != null) {
                parameterListAdapter.notifyDataSetChanged();
            }
        }

    }
    private void initTable() {
        String point_name = "";
        String tagvalue = "";
        //将所有statusData数据取出并赋给新List
        List <StatusData> statusData=new ArrayList<>();
        for(int i=0,k=0;i<parameterListData.size();i++)
            for(int j=0;j<parameterListData.get(i).getStatusData().size();j++)
                statusData.add(parameterListData.get(i).getStatusData().get(j));
        //用循环给两个占位字符赋值
            for(int k=0;k<statusData.size();k++){
                if(k == 0){
                    point_name=statusData.get(k).getPoint_name();
                    tagvalue=statusData.get(k).getTagvalue();
                }else {
                    if(point_name != null&&statusData.get(k).getPoint_name()!=null){
                        if(point_name.length()<statusData.get(k).getPoint_name().length()){
                            point_name=statusData.get(k).getPoint_name();
                        }
                    }
                    if(tagvalue != null &&statusData.get(k).getTagvalue()!=null){
                        if(tagvalue.length()<statusData.get(k).getTagvalue().length()){
                            tagvalue=statusData.get(k).getTagvalue();
                        }

                    }
                }

            }
        /**
         * 占位字符集合
         */
        List<String> maxDatas = new ArrayList<>();
        maxDatas.add(point_name);
        maxDatas.add(tagvalue);
        maxDatas.add("单位单位");
        maxDatas.add("状态状态");
        for(int i=0;i<maxDatas.size();i++)
            maxDatas_constant.add(maxDatas.get(i));
        parameterListAdapter = new ProDetailQualityAdapter(getApplicationContext(), statusData, maxDatas_constant);
        rvParameterListTable.setAdapter(parameterListAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @OnClick(R.id.iv_back)
    public void onViewClicked(ImageView back) {
        super.onBackPressed();
        finish();
    }
}

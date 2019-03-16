package com.crec.shield.ui2_2.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.crec.shield.R;
import com.crec.shield.adapter.project.SystemListAdapter;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.SystemListContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.crec22.project.parameterlist.ParameterListEntity;
import com.crec.shield.entity.crec22.project.parameterlist.StatusData;
import com.crec.shield.presenter.SystemListPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SystemListActivity extends BaseActivity<SystemListPresenter>implements SystemListContract.View {
    private Unbinder unbinder;
    private SystemListAdapter systemListAdapter;
    @BindView(R.id.rv_SystemlistTable)
    RecyclerView rvSystemlistTable;

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_system_list;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSystemlistTable.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider,null));
        rvSystemlistTable.addItemDecoration(decoration);

        //rvSystemlistTable.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mPresenter.getSystemListData();
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void showSystemListData(final List<ParameterListEntity> data) {
       systemListAdapter=new SystemListAdapter(getApplicationContext(),data);
       rvSystemlistTable.setAdapter(systemListAdapter);
       systemListAdapter.setOnItemClickListener(new SystemListAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position) {
                List <StatusData>subsystemData=new ArrayList<>(data.get(position).getStatusData());
                Intent intent = new Intent(getContext(),ParameterListActivity.class);
                intent.putExtra("subsystemData",(Serializable)subsystemData);
                setResult(10,intent);
                finish();
            }
        });
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
}

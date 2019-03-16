package com.crec.shield.ui2_2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.MonitorRecycleAdapter;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.CameraDataContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.overview.camera.CameraResponse;
import com.crec.shield.entity.project.camera.Camera;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.presenter.CameraDataPresenter;
import com.crec.shield.ui.activity.CameraPlayerActivity;
import com.crec.shield.ui.fragment.FollowCameraFragment;
import com.crec.shield.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.WITHOUT_DATA;

public class CameraDataActivity extends BaseActivity<CameraDataPresenter> implements CameraDataContract.View{

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.RecycleNULL)
    TextView RecycleNULL;


    private String mToken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
    private Unbinder unbinder;

    private MonitorRecycleAdapter recycleAdapter;

    private Map<String, List<CameraResponse.CameraEntity>> datasets = new HashMap<>();

    private List<CameraResponse.CameraEntity> childEntity;
    public List<String> mData1 = new ArrayList<String>();
    public List<String> mData2 = new ArrayList<String>();
    public List<String> mData3 = new ArrayList<String>();
    public List<String> mData4 = new ArrayList<String>();
    public List<String> IdList = new ArrayList<String>();
    public List<Integer> isAttention = new ArrayList<Integer>();
    private List<Camera> cameraData = new ArrayList<>();

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_camera_data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
        refreshLayout.autoRefresh();
    }

    private void initData() {


        childEntity = new ArrayList<>();

        IdList.clear();
        mData1.clear();
        mData2.clear();
        mData3.clear();
        mData4.clear();
        isAttention.clear();

        //json比接口多一个字段
        OkGo.post(Url.BASE_URL+Url.OVERVIEW_FOLLOW_CAMERA)
                .params("token", mToken)
                .execute(new JsonCallback<CameraResponse>() {
                    @Override
                    public void onSuccess(CameraResponse cameraResponse, Call call, Response response) {
                        refreshLayout.finishRefresh();
                        if (cameraResponse.getCode() == 1){
                            childEntity = cameraResponse.getData();

                            if (childEntity.size() == 0) {
                                RecycleNULL.setText(WITHOUT_DATA);
                                RecycleNULL.setTextSize(15);
                                RecycleNULL.setTextColor(getResources().getColor(R.color.color_Aluminum));
                                RecycleNULL.setVisibility(View.VISIBLE);
                            }

                            int i = 0;
                            for (CameraResponse.CameraEntity cameraChildEntity : childEntity) {
                                Camera camera = new Camera();
                                camera.setChannel(cameraChildEntity.getChannel()+"");
                                camera.setChannelname(cameraChildEntity.getChannelname());
                                camera.setId(cameraChildEntity.getId());
                                camera.setIp(cameraChildEntity.getIp());
                                camera.setIsAttention(cameraChildEntity.getIsAttention());
                                camera.setLocation(cameraChildEntity.getLocation());
                                camera.setPassword(cameraChildEntity.getPassword());
                                camera.setPort(cameraChildEntity.getPort());
                                camera.setUsername(cameraChildEntity.getUsername());
                                cameraData.add(camera);
                                IdList.add(cameraChildEntity.getId());
                                mData1.add(cameraChildEntity.getArea()==null?"":cameraChildEntity.getArea());
                                mData2.add(cameraChildEntity.getProject()==null?"":cameraChildEntity.getProject());
                                mData3.add(cameraChildEntity.getLine()==null?"":cameraChildEntity.getLine());
                                mData4.add(cameraChildEntity.getLocation()==null?"":cameraChildEntity.getLocation());
                                isAttention.add(cameraChildEntity.getIsAttention());
                                i++;
                            }
                            recycleAdapter= new MonitorRecycleAdapter(getContext(),mToken,IdList,mData1,mData2,mData3,mData4,isAttention,cameraData);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(recycleAdapter);
                            recycleAdapter.setClickListener(new MonitorRecycleAdapter.ItemClickListener(){
                                public void OnItemClick(View view, int position) {
                                    CameraResponse.CameraEntity cameraChildEntity =  childEntity.get(position);
                                    String[] _caminfo = new String[]{cameraChildEntity.getIp(),cameraChildEntity.getPort()+"",cameraChildEntity.getUsername(),cameraChildEntity.getPassword(),cameraChildEntity.getChannel()+""};//ip、端口、用户名、密码、通信管道id
                                    Log.e("-----Item clicked-----",String.valueOf(position));
                                    Intent intent5 = new Intent(getContext(), CameraPlayerActivity.class);
                                    intent5.putExtra("camerainfo", _caminfo);
                                    intent5.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent5);
                                }
                            });
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }


}

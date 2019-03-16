package com.crec.shield.ui2_2.fragment.threefragment;
/**
 *@author yyl
 *@data 2019.3.6
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crec.shield.R;
import com.crec.shield.adapter.ProDetailQualityAdapter;
import com.crec.shield.definedviewpager.CustomViewPager;
import com.crec.shield.entity.crec22.project.companymanagementquality.LineQualityEntity;
import com.crec.shield.utils.SysApplication;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
public class AmpleListFragment extends Fragment {
    private Unbinder unbinder;
    private  List<LineQualityEntity> ampleData;
    private ProDetailQualityAdapter ampleListAdapter;
    private CustomViewPager vp;
    private View view;

    @BindView(R.id.rv_AmpleList)
    RecyclerView rvAmpleList;
    //fragment实例化，重写构造器，并用Bundle传值（官方推荐）
    public AmpleListFragment(){}
    public static AmpleListFragment newInstance(List<LineQualityEntity> ampleData,CustomViewPager vp){
            AmpleListFragment ampleListFragment=new AmpleListFragment();
            Bundle bundle01=new Bundle();
            //Bundle bundle02=new Bundle();
            //bundle01.putParcelableArrayList("ampleData", (ArrayList<? extends Parcelable>)ampleData);
            bundle01.putSerializable("ampleData",(Serializable)ampleData);
            bundle01.putSerializable("vp01",vp);
            //bundle02.putSerializable("vp",vp);
            ampleListFragment.setArguments(bundle01);
            //ampleListFragment.setArguments(bundle02);
            return ampleListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ample_project_list,container, false);
        this.view=view;
        SysApplication.getInstance().addActivity(getActivity());
        unbinder = ButterKnife.bind(this, view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAmpleList.setLayoutManager(layoutManager);
        initList();
        return view;
    }

    public void initList(){
        String project="";
        String line="";
        Bundle bundle01 = getArguments();//获得数据
        ampleData=(List<LineQualityEntity>) bundle01.getSerializable("ampleData");
        for(int i=0;i<ampleData.size();i++){
            if(ampleData.get(i).getProject().length()>project.length())
                project=ampleData.get(i).getProject();
            if(ampleData.get(i).getLine().length()>line.length())
                line=ampleData.get(i).getLine();
        }
        List<String> maxDatas = new ArrayList<>(Arrays.asList("详情",project,line,"评级"));
        //Bundle bundle02 =getArguments();
        vp=(CustomViewPager)bundle01.getSerializable("vp01");//获得另一组数据（CustomViewpager类），用于高度自适应
        vp.setObjectForView(view,0);//view和位置绑定
        ampleListAdapter = new ProDetailQualityAdapter(getActivity(),ampleData,maxDatas);
        rvAmpleList.setAdapter(ampleListAdapter);


    }
}



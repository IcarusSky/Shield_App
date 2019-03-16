package com.crec.shield.ui2_2.fragment.threefragment;

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

/**
 * @author yyl
 * @date 2019.3.6
 */

public class BadListFragment extends Fragment{
    private Unbinder unbinder;
    private  List<LineQualityEntity> badData;
    private ProDetailQualityAdapter badListAdapter;
    private CustomViewPager vp;
    private View view;
    @BindView(R.id.rv_BadList)
    RecyclerView rvBadList;
    public BadListFragment(){}
    //fragment实例化，并用Bundle传值（官方推荐）
    public static BadListFragment newInstance(List<LineQualityEntity> badData,CustomViewPager vp){
        BadListFragment badListFragment=new BadListFragment();
        Bundle bundle01=new Bundle();
        //Bundle bundle02=new Bundle();
        //bundle01.putParcelableArrayList("badData", (ArrayList<? extends Parcelable>)badData);
        bundle01.putSerializable("badData",(Serializable)badData);
        bundle01.putSerializable("vp03",vp);
        //bundle02.putSerializable("vp",vp);
        badListFragment.setArguments(bundle01);
        //badListFragment.setArguments(bundle02);
        return badListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bad_project_list,container, false);
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
        rvBadList.setLayoutManager(layoutManager);
        initList();
        return view;
    }
    public void initList(){
        String project="";
        String line="";
        Bundle bundle02 = getArguments();
        //badData=bundle02.getParcelableArrayList("badData");
        badData=(List<LineQualityEntity>) bundle02.getSerializable("badData");
        for(int i=0;i<badData.size();i++){
            if(badData.get(i).getProject().length()>project.length())
                project=badData.get(i).getProject();
            if(badData.get(i).getLine().length()>line.length())
                line=badData.get(i).getLine();
        }
        List<String> maxDatas = new ArrayList<>(Arrays.asList("详情",project,line,"评级"));
        vp=(CustomViewPager)bundle02.getSerializable("vp03");//获得另一组数据（CustomViewpager类），用于高度自适应
        vp.setObjectForView(view,2);//该view和位置0绑定
        badListAdapter = new ProDetailQualityAdapter(getActivity(),badData,maxDatas);
        rvBadList.setAdapter(badListAdapter);

    }
}

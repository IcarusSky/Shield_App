package com.crec.shield.ui2_2.fragment.threefragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * @data 2019.3.6
 */

public class MediumListFragment extends Fragment{
    private Unbinder unbinder;
    private  List<LineQualityEntity> mediumData;
    private ProDetailQualityAdapter mediuListAdapter;
    private CustomViewPager vp;
    private View view;
    @BindView(R.id.rv_MediumList)
    RecyclerView rvMediumList;
    public MediumListFragment(){}
    public static MediumListFragment newInstance(List<LineQualityEntity> mediumData,CustomViewPager vp){
        MediumListFragment mediumListFragment=new MediumListFragment();
        Bundle bundle01=new Bundle();
        //Bundle bundle02=new Bundle();
        //bundle01.putParcelableArrayList("mediumData", (ArrayList<? extends Parcelable>)mediumData);
        bundle01.putSerializable("mediumData",(Serializable)mediumData);
        bundle01.putSerializable("vp02",vp);
        //bundle02.putSerializable("vp",vp);
        mediumListFragment.setArguments(bundle01);
        //mediumListFragment.setArguments(bundle02);
        return mediumListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medium_project_list,container, false);
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
        rvMediumList.setLayoutManager(layoutManager);
        initList();
        return view;
    }
    public void initList(){
            String project="";
            String line="";
            Bundle bundle02 = getArguments();
            mediumData=(List<LineQualityEntity>) bundle02.getSerializable("mediumData");
            for(int i=0;i<mediumData.size();i++){
                if(mediumData.get(i).getProject().length()>project.length())
                    project=mediumData.get(i).getProject();
                if(mediumData.get(i).getLine().length()>line.length())
                    line=mediumData.get(i).getLine();
            }
            List<String> maxDatas = new ArrayList<>(Arrays.asList("详情",project,line,"评级"));
            vp=(CustomViewPager)bundle02.getSerializable("vp02");
            vp.setObjectForView(view,1);
            mediuListAdapter = new ProDetailQualityAdapter(getActivity(),mediumData,maxDatas);
            rvMediumList.setAdapter(mediuListAdapter);

    }
}

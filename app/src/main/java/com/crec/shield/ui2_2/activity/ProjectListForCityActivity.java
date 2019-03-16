package com.crec.shield.ui2_2.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.crec.shield.R;
import com.crec.shield.adapter.CityAdapter;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.ProjectListForCityContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.crec22.project.projectlistforcity.ProjectListForCityData;
import com.crec.shield.presenter.ProjectListForCityPresenter;
import com.crec.shield.utils.ChineseCharToEnUtil;
import com.crec.shield.utils.City;
import com.crec.shield.utils.LetterComparator;
import com.crec.shield.utils.PinnedHeaderDecoration;

import com.crec.shield.utils.PinyinUtils;
import com.crec.shield.utils.WaveSideBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

public class ProjectListForCityActivity extends BaseActivity<ProjectListForCityPresenter> implements ProjectListForCityContract.View{
    //数据绑定
    @BindView(R.id.side_bar)
    WaveSideBarView mSideBarView;
    @BindView(R.id.rv_contacts)
    RecyclerView mRecyclerView;
    @BindView(R.id.edit_text)
    EditText mEditText;
    @BindView(R.id.image_view)
    ImageView mImageView;
    CityAdapter adapter;
    private List<ProjectListForCityData> projectListForCityData = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_projectlistforcity;
    }
    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinyinUtils.makeDic(getContext());
        initView();


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final PinnedHeaderDecoration decoration = new PinnedHeaderDecoration();
        decoration.registerTypePinnedHeader(1, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
        mRecyclerView.addItemDecoration(decoration);


        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new CityAdapter(ProjectListForCityActivity.this, getChackDate());
                        mRecyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();

        mSideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int pos = adapter.getLetterPosition(letter);
                List<City> checkData = new ArrayList<>();
                if (pos != -1) {
                    //进入前刷新列表，主要应对从加星标位置返回其他位置后造成的数据无法对齐问题
                    adapter = new CityAdapter(ProjectListForCityActivity.this, getChackDate());
                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.scrollToPosition(pos);
                    LinearLayoutManager mLayoutManager =
                            (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(pos, 0);
                }else{
                    //用來處理數據將選中星號的數據進行處理,该位置需求确定后再行决定是否保留，目前先留在这里
                    if(letter.toString().equals("☆")){
                        ChineseCharToEnUtil chnToEng = new ChineseCharToEnUtil();
                        for(int i=0;i<projectListForCityData.size();i++){
                            City city = new City();
                            int count=0;
                            city.name = projectListForCityData.get(i).getCity();
                            city.pys = chnToEng.getAllFirstLetter(projectListForCityData.get(i).getCity()).substring(0,1);
                            city.type=1;
                            if(projectListForCityData.get(i).getCity().equals("关注项目")){
                                city.sortby=0;
                            }else{
                                city.sortby=letterToNumber(city.pys)+(float)(i+1)/10;
                            }
                            checkData.add(city);
                            if(projectListForCityData.get(i).getProjectList().size()!=0){
                                for(int j=0;j<projectListForCityData.get(i).getProjectList().size();j++){
                                    if(projectListForCityData.get(i).getProjectList().get(j).getFollowStatus()==1){
                                        City cityInfo = new City();
                                        cityInfo.name = projectListForCityData.get(i).getProjectList().get(j).getLineName();
                                        cityInfo.pys = chnToEng.getAllFirstLetter(projectListForCityData.get(i).getCity());
                                        cityInfo.type=0;
                                        cityInfo.lineID=projectListForCityData.get(i).getProjectList().get(j).getLineID();
                                        cityInfo.followStatus=projectListForCityData.get(i).getProjectList().get(j).getFollowStatus();
                                        if(projectListForCityData.get(i).getCity().equals("关注项目")){
                                            cityInfo.sortby=(float)(i+1)/10+(float)(j+1)/100;
                                        }else{
                                            cityInfo.sortby=letterToNumber(city.pys)+(float)(i+1)/10+(float)(j+1)/100;
                                        }
                                        checkData.add(cityInfo);
                                        count++;
                                    }
                                }
                            }
                            if(count==0){
                                checkData.remove(city);
                            }
                        }
                        // 排序：
                        Collections.sort(checkData, new Comparator<City>() {
                            @Override
                            public int compare(City u1, City u2) {
                                if (u1.sortby > u2.sortby) {
                                    return 1;
                                }
                                if (u1.sortby == u2.sortby) {
                                    return 0;
                                }
                                return -1;
                            }
                        });
                        adapter = new CityAdapter(ProjectListForCityActivity.this, checkData);
                        mRecyclerView.setAdapter(adapter);
                        //构造完特殊数据后重置checkData
                        adapter = new CityAdapter(ProjectListForCityActivity.this, getChackDate());
                    }
                }
            }
        });
    }
    @Override
    public void showData(List<ProjectListForCityData> data) {
        projectListForCityData = data;
    }

    private void initView() {
        //设置删除图片的点击事件
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把EditText内容设置为空
                mEditText.setText("");
                //把ListView隐藏
                //mListView.setVisibility(View.GONE);
                showListView();
            }
        });

        //EditText添加监听
        mEditText.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}//文本改变之前执行

            @Override
            //文本改变的时候执行
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //如果长度为0
                if (s.length() == 0) {
                    //隐藏“删除”图片
                    mImageView.setVisibility(View.GONE);
                } else {//长度不为0
                    //显示“删除图片”
                    mImageView.setVisibility(View.VISIBLE);
                    //显示ListView
                }
                showListView();
            }
            public void afterTextChanged(Editable s) { }//文本改变之后执行
        });
    }

    /**
     * 查询输入框触发的列表方法
     *
     */
    private void showListView() {
        List<City> checkData = new ArrayList<>();
        //获得输入的内容
        int data=0;
        String str = mEditText.getText().toString().trim();
        mPresenter.getData();
        ChineseCharToEnUtil chnToEng = new ChineseCharToEnUtil();
        for(int i=0;i<projectListForCityData.size();i++){
            City city = new City();
            city.name = projectListForCityData.get(i).getCity();
            city.pys = chnToEng.getAllFirstLetter(projectListForCityData.get(i).getCity()).substring(0,1);
            city.type=1;
            city.sonSum=projectListForCityData.get(i).getProjectList().size();
            if(projectListForCityData.get(i).getCity().equals("关注项目")){
                city.sortby=0;
            }else{
                city.sortby=letterToNumber(city.pys)+(float)(i+1)/10;
            }
            checkData.add(city);
            if(projectListForCityData.get(i).getProjectList().size()!=0){
                for(int j=0;j<projectListForCityData.get(i).getProjectList().size();j++){
                    if(projectListForCityData.get(i).getProjectList().get(j).getLineName().contains(str)){
                        City cityInfo = new City();
                        cityInfo.name = projectListForCityData.get(i).getProjectList().get(j).getLineName();
                        cityInfo.pys = chnToEng.getAllFirstLetter(projectListForCityData.get(i).getCity());
                        cityInfo.type=0;
                        cityInfo.lineID=projectListForCityData.get(i).getProjectList().get(j).getLineID();
                        cityInfo.followStatus=projectListForCityData.get(i).getProjectList().get(j).getFollowStatus();
                        if(projectListForCityData.get(i).getCity().equals("关注项目")){
                            cityInfo.sortby=(float)(i+1)/10+(float)(j+1)/100;
                        }else{
                            cityInfo.sortby=letterToNumber(city.pys)+(float)(i+1)/10+(float)(j+1)/100;
                        }
                        checkData.add(cityInfo);
                    }else{
                        data++;
                    }
                    city.sonSum = city.sonSum-data;
                    data=0;
                }
            }
        }
        // 降序：
        Collections.sort(checkData, new Comparator<City>() {
            @Override
            public int compare(City u1, City u2) {
                if (u1.sortby > u2.sortby) {
                    return 1;
                }
                if (u1.sortby == u2.sortby) {
                    return 0;
                }
                return -1;
            }
        });

        if(str!=null&&str!=""){
            for(int i=checkData.size()-1;i>=0;i--){
                if(checkData.get(i).sonSum==0&&checkData.get(i).type==1){
                    checkData.remove(i);
                }
            }
        }else{
            checkData=getChackDate();
        }


        adapter = new CityAdapter(ProjectListForCityActivity.this, checkData);
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 字母转数字  A-Z ：1-26
     * @param letter
     * @return String
     */
    public int letterToNumber(String letter) {
        int length = letter.length();
        int num = 0;
        int number = 0;
        for(int i = 0; i < length; i++) {
            char ch = letter.charAt(length - i - 1);
            num = (int)(ch - 'A' + 1);
            num *= Math.pow(26, i);
            number += num;
        }
        return number;
    }

    /**
     * @company vinelinx
     * @author zhangding
     * @date 2019.2.23
     * @description 构造数据
     */
    public List<City> getChackDate(){
        mPresenter.getData();
        final List<City> checkData = new ArrayList<>();
        ChineseCharToEnUtil chnToEng = new ChineseCharToEnUtil();
        for(int i=0;i<projectListForCityData.size();i++){
            City city = new City();
            city.name = projectListForCityData.get(i).getCity();
            city.pys = chnToEng.getAllFirstLetter(projectListForCityData.get(i).getCity()).substring(0,1);
            city.type=1;
            if(projectListForCityData.get(i).getCity().equals("关注项目")){
                city.sortby=0;
            }else{
                city.sortby=letterToNumber(city.pys)+(float)(i+1)/10;
            }
            checkData.add(city);
            if(projectListForCityData.get(i).getProjectList().size()!=0){
                for(int j=0;j<projectListForCityData.get(i).getProjectList().size();j++){
                    City cityInfo = new City();
                    cityInfo.name = projectListForCityData.get(i).getProjectList().get(j).getLineName();
                    cityInfo.pys = chnToEng.getAllFirstLetter(projectListForCityData.get(i).getCity());
                    cityInfo.type=0;
                    cityInfo.lineID=projectListForCityData.get(i).getProjectList().get(j).getLineID();
                    cityInfo.followStatus=projectListForCityData.get(i).getProjectList().get(j).getFollowStatus();
                    if(projectListForCityData.get(i).getCity().equals("关注项目")){
                        cityInfo.sortby=(float)(i+1)/10+(float)(j+1)/100;
                    }else{
                        cityInfo.sortby=letterToNumber(city.pys)+(float)(i+1)/10+(float)(j+1)/100;
                    }
                    checkData.add(cityInfo);
                }
            }
        }
        // 排序，排序依据为通过字母转换成对应的ascii码，城市列表级别通过追加小数点。
        Collections.sort(checkData, new Comparator<City>() {
            @Override
            public int compare(City u1, City u2) {
                if (u1.sortby > u2.sortby) {
                    return 1;
                }
                if (u1.sortby == u2.sortby) {
                    return 0;
                }
                return -1;
            }
        });
        return checkData;
    }
}

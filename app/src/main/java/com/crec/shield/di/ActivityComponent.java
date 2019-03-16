package com.crec.shield.di;

import com.crec.shield.base.ActivityModule;
import com.crec.shield.base.ActivityScope;
import com.crec.shield.base.AppComponent;
import com.crec.shield.contract.ProjectQualityListContract;
import com.crec.shield.demo.LoginActivity;
import com.crec.shield.ui2_2.activity.ApproachingArrivalActivity;
import com.crec.shield.ui2_2.activity.CameraDataActivity;
import com.crec.shield.ui2_2.activity.CompanyActivity;
import com.crec.shield.ui2_2.activity.CompanyManagementQualityActivity;
import com.crec.shield.ui2_2.activity.ConcernedLinesActivity;
import com.crec.shield.ui2_2.activity.DiggingDataActivity;
import com.crec.shield.ui2_2.activity.EquipmentManagementActivity;
import com.crec.shield.ui2_2.activity.ManagementDataActivity;
import com.crec.shield.ui2_2.activity.ParameterListActivity;
import com.crec.shield.ui2_2.activity.ProcessManagementActivity;
import com.crec.shield.ui2_2.activity.ProgressManagementActivity;
import com.crec.shield.ui2_2.activity.ProjectActivity;
import com.crec.shield.ui2_2.activity.ProjectListForCityActivity;
import com.crec.shield.ui2_2.activity.QualityManagementActivity;
import com.crec.shield.ui2_2.activity.QualityUploadActivity;
import com.crec.shield.ui2_2.activity.RiskSourceActivity;
import com.crec.shield.ui2_2.activity.RiskSourceDetailsActivity;
import com.crec.shield.ui2_2.activity.SafetyManagementActivity;
import com.crec.shield.ui2_2.activity.SystemListActivity;
import com.crec.shield.ui2_2.activity.UnnormalWorkConditionActivity;
import com.crec.shield.ui2_2.activity.SubsidenceDataMonitorActivity;

import com.crec.shield.ui2_2.activity.VedioListForCityActivity;

import com.crec.shield.ui2_2.activity.CompanyEquipmentManagementActivity;
import com.crec.shield.ui2_2.activity.CompanySafeManagementActivity;
import com.crec.shield.ui2_2.activity.ProjectQualityListActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivity activity);

    void inject(ProjectActivity activity);

    void inject(CompanyActivity activity);

    void inject(UnnormalWorkConditionActivity activity);

    void inject(ProgressManagementActivity activity);

    void inject(QualityManagementActivity activity);

    void inject(EquipmentManagementActivity activity);

    void inject(SafetyManagementActivity activity);

    void inject(RiskSourceActivity activity);

    void inject(RiskSourceDetailsActivity activity);

    void inject(SubsidenceDataMonitorActivity activity);


    void inject(DiggingDataActivity activity);


    void inject(ManagementDataActivity activity);


    void inject(QualityUploadActivity activity);


    void inject(ParameterListActivity activity);


    void inject(ConcernedLinesActivity activity);


    void inject(CameraDataActivity activity);

    void inject(ApproachingArrivalActivity activity);

    void inject(SystemListActivity activity);

	void inject(CompanyManagementQualityActivity activity);

    void inject(VedioListForCityActivity activity);

    void inject(ProjectListForCityActivity activity);


    void inject(CompanyEquipmentManagementActivity activity);


    void inject(CompanySafeManagementActivity activity);

    void inject(ProjectQualityListActivity activity);


    void inject(ProcessManagementActivity activity);



}

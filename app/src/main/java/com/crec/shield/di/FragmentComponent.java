package com.crec.shield.di;

import com.crec.shield.base.AppComponent;
import com.crec.shield.base.FragmentModule;
import com.crec.shield.base.FragmentScope;
import com.crec.shield.ui2_2.fragment.CompanyMessageFragment;
import com.crec.shield.ui2_2.fragment.CompanyOverviewFragment;
import com.crec.shield.ui2_2.fragment.CompanyPersonalFragment;
import com.crec.shield.ui2_2.fragment.CompanyProjectFragment;
import com.crec.shield.ui2_2.fragment.ProjectCameraDataFragment;
import com.crec.shield.ui2_2.fragment.ProjectManagementFragment;
import com.crec.shield.ui2_2.fragment.ProjectOverviewFragment;
import com.crec.shield.ui2_2.fragment.threefragment.AmpleListFragment;
import com.crec.shield.ui2_2.fragment.threefragment.BadListFragment;
import com.crec.shield.ui2_2.fragment.threefragment.MediumListFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class,
        modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(ProjectManagementFragment fragment);
    void inject(ProjectOverviewFragment fragment);
    void inject(CompanyOverviewFragment fragment);
    void inject(CompanyProjectFragment fragment);
    void inject(CompanyMessageFragment fragment);
    void inject(CompanyPersonalFragment fragment);
    void inject(ProjectCameraDataFragment projectCameraDataFragment);
    void inject(AmpleListFragment fragment);
    void inject(MediumListFragment fragment);
    void inject(BadListFragment fragment);
}

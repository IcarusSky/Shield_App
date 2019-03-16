package com.crec.shield.base;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    BaseFragment fragment;

    public FragmentModule(BaseFragment fragment) {
        this.fragment = fragment;
    }
    @Provides
    @FragmentScope
    public BaseFragment provideBindMobFragment() {
        return fragment;

    }
}
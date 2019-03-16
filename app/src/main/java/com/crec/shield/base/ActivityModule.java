package com.crec.shield.base;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }
    @Provides
    @ActivityScope
    public BaseActivity provideBindMobActivity() {
        return activity;

    }
}
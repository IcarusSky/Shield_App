package com.crec.shield.base;

import android.app.Application;
import dagger.Module;

@Module
public class AppModule {
    private Application application;
    public AppModule(Application application) {
        this.application = application;
    }

}
package com.darvishiyan.architecture.core;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.darvishiyan.architecture.dagger.AndroidModule;
import com.darvishiyan.architecture.dagger.DaggerApplicationComponent;

/**
 * Created by Hesam on 2/12/2018.
 */

public class BaseApplication extends MultiDexApplication {

    static DaggerApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        applicationComponent = (DaggerApplicationComponent) DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    public static DaggerApplicationComponent getComponent() {
        return applicationComponent;
    }
}

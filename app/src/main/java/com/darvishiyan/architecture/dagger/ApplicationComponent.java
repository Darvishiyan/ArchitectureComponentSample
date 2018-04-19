package com.darvishiyan.architecture.dagger;

import com.darvishiyan.architecture.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Hesam on 2/12/2018.
 */

@Singleton
@Component(modules =
        {
                AndroidModule.class,
                DataStoreModule.class
        })
public interface ApplicationComponent {

    void inject(MainActivity activity);

}

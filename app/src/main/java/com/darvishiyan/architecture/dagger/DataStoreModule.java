package com.darvishiyan.architecture.dagger;

import android.content.Context;

import com.darvishiyan.architecture.data.DataBase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hesam on 2/12/2018.
 */

@Module
public class DataStoreModule {

    @Provides
    @Singleton
    public DataBase provideDataBase(Context context) {
        return DataBase.getInstance(context);
    }

}

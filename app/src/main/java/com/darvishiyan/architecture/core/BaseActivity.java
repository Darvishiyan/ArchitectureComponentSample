package com.darvishiyan.architecture.core;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hesam on 2/15/2018.
 */

public abstract class BaseActivity extends AppCompatActivity implements LifecycleOwner {

    protected LifecycleRegistry lifecycleRegistry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.markState(LifecycleRegistry.State.CREATED);
        init();
        loadUi();
        load();
        listener();
    }

    @Override
    protected void onStart() {
        lifecycleRegistry.markState(LifecycleRegistry.State.STARTED);
        super.onStart();
    }


    @Override
    protected void onDestroy() {
        lifecycleRegistry.markState(LifecycleRegistry.State.DESTROYED);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        lifecycleRegistry.markState(LifecycleRegistry.State.RESUMED);
        super.onResume();
    }

    protected abstract void listener();

    protected abstract void load();

    protected abstract void loadUi();

    protected abstract void init();

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}

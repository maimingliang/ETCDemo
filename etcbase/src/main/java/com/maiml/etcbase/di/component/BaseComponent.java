package com.maiml.etcbase.di.component;


import com.maiml.etcbase.BaseApplication;
import com.maiml.etcbase.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class})
public interface BaseComponent {
    void inject(BaseApplication application);
}

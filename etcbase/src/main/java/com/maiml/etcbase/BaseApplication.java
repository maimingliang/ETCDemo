package com.maiml.etcbase;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.maiml.etcbase.base.AppManager;
import com.maiml.etcbase.di.component.DaggerBaseComponent;
import com.maiml.etcbase.di.module.AppModule;
import com.maiml.etcbase.di.module.ClientModule;
import com.maiml.etcbase.di.module.GlobeConfigModule;

import javax.inject.Inject;

public abstract class BaseApplication extends Application {

    protected static BaseApplication mApplication;
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected AppManager mAppManager;
    private ClientModule mClientModule;
    private AppModule mAppModule;
    private static int mMainThreadId;
    private static Handler mHandler;
    /**
     * 返回上下文
     *
     * @return
     */
    public static Context getContext() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mMainThreadId = android.os.Process.myTid();

        mHandler = new Handler();
        this.mAppModule = new AppModule(this);//提供application
        DaggerBaseComponent
                .builder()
                .appModule(mAppModule)
                .build()
                .inject(this);
        this.mClientModule = new ClientModule(mAppManager);//用于提供okhttp和retrofit的单例

    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mClientModule != null)
            this.mClientModule = null;
        if (mAppModule != null)
            this.mAppModule = null;
        if (mAppManager != null) {//释放资源
            this.mAppManager.release();
            this.mAppManager = null;
        }
        if (mApplication != null)
            mApplication = null;
    }

    /**
     * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     *
     * @return
     */
    protected abstract GlobeConfigModule getGlobeConfigModule();

    public ClientModule getClientModule() {
        return mClientModule;
    }

    public AppModule getAppModule() {
        return mAppModule;
    }

    public AppManager getAppManager() {
        return mAppManager;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static Handler getHandler() {
        return mHandler;
    }
}

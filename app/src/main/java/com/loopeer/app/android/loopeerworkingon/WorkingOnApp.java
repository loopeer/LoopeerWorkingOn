package com.loopeer.app.android.loopeerworkingon;

import android.app.Application;
import android.content.Context;

public class WorkingOnApp extends Application {

    private static WorkingOnApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static Context getAppContext() {
        return sInstance;
    }
}

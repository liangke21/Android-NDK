package com.muc.store;

import android.app.Application;
import android.content.Context;

import com.liulishuo.filedownloader.FileDownloader;
import com.tencent.bugly.crashreport.CrashReport;

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        CrashReport.initCrashReport(getApplicationContext(), "05b1ff9e4e", true);
        FileDownloader.setup(getInstance());
    }

    public static Context getInstance() {
        return mContext;
    }
}
package com.learnproject.aoh1996;

import android.app.Application;
import android.content.Context;

public class AvitoTraineeApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        AvitoTraineeApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return AvitoTraineeApplication.context;
    }
}

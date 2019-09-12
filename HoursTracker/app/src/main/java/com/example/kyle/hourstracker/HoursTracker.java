// Used to get application context statically
package com.example.kyle.hourstracker;

import android.app.Application;
import android.content.Context;

public class HoursTracker extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        HoursTracker.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return HoursTracker.context;
    }
}

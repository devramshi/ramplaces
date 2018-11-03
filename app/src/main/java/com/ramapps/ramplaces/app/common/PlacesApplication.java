package com.ramapps.ramplaces.app.common;

import android.app.Application;
import android.content.Context;

public class PlacesApplication extends Application {

    private static PlacesApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Context getContext() {
        if (application != null) {
            return application.getApplicationContext();
        }
        return null;
    }

}

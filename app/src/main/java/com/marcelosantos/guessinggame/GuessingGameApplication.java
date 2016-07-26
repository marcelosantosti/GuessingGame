package com.marcelosantos.guessinggame;

import android.app.Application;

import roboguice.RoboGuice;

/**
 * Created by Marcelo on 17/10/2015.
 */
public class GuessingGameApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        RoboGuice.setUseAnnotationDatabases(false);
    }
}

package com.example.korzhik.testproject;

import android.app.Application;
import android.arch.persistence.room.Room;

public class MyApplication extends Application {
    public static MyApplication instance;
    private static LocalBDClass database;

    public static MyApplication getInstance() {
        return instance;
    }

    public LocalBDClass getDatabase () {
        return database;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(),
                LocalBDClass.class, "baseData").build();
    }
}

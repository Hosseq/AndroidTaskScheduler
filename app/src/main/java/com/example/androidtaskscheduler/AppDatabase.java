package com.example.androidtaskscheduler;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Days.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaysDao userDao();
}


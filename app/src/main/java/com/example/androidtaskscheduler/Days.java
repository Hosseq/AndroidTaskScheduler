package com.example.androidtaskscheduler;

import androidx.room.*;
import java.sql.Timestamp;

@Entity
public class Days {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "dateStart")
    public long dateStart;

    @ColumnInfo(name = "dateFinish")
    public long dateFinish;

    Days(String name, String description, long dateStart, long dateFinish)
    {
        this.name = name;
        this.description = description;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
    }
}

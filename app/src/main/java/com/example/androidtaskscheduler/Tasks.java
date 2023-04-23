package com.example.androidtaskscheduler;

import androidx.room.*;

@Entity
public class Tasks {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "dateStart")
    public long dateStart;

    @ColumnInfo(name = "dateFinish")
    public long dateFinish;

    Tasks(String name, String description, long dateStart, long dateFinish)
    {
        this.name = name;
        this.description = description;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
    }

    //Getters
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public long getStartDate() {
        return dateStart;
    }
    public long getFinishDate() {
        return dateFinish;
    }

}

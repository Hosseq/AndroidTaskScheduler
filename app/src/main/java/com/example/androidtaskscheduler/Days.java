package com.example.androidtaskscheduler;

import androidx.room.*;
import java.sql.Timestamp;

@Entity
public class Days {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "dateStart")
    public Long dateStart;

    @ColumnInfo(name = "dateFinish")
    public Long dateFinish;
}

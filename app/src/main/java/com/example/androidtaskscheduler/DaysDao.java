package com.example.androidtaskscheduler;

import androidx.room.*;

import java.util.List;

@Dao
public interface DaysDao {
    @Query("SELECT * FROM Days")
    List<Days> getAll();

    @Query("SELECT * FROM Days WHERE uid IN (:userIds)")
    List<Days> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM Days WHERE dateFinish > :time AND dateStart < (:time + 86400000)")
    List<Days> findByDay(long time);

    @Insert
    void insert(Days... day);

    @Delete
    void delete(Days day);
}


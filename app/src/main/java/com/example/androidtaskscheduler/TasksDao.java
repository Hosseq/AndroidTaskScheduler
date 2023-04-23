package com.example.androidtaskscheduler;

import androidx.room.*;

import java.util.List;

@Dao
public interface TasksDao {
    @Query("SELECT * FROM Tasks")
    List<Tasks> getAll();

    @Query("SELECT * FROM Tasks WHERE id IN (:userIds)")
    List<Tasks> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM Tasks WHERE dateFinish > :time AND dateStart < (:time + 86400000)")
    List<Tasks> findByDay(long time);

    @Insert
    void insert(Tasks... day);

    @Delete
    void delete(Tasks day);
}


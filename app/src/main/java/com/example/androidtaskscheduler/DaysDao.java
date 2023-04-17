package com.example.androidtaskscheduler;

import androidx.room.*;

import java.util.List;

@Dao
public interface DaysDao {
    @Query("SELECT * FROM Days")
    List<Days> getAll();

    @Query("SELECT * FROM Days WHERE uid IN (:userIds)")
    List<Days> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM Days WHERE name LIKE :first")
    Days findByName(String first);

    @Insert
    void insertAll(Days... users);

    @Delete
    void delete(Days user);
}


package com.example.tracker.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tracker.entity.AppLoc;

import java.util.List;

@Dao
public interface AppLocDao {
    @Insert
    void insertAll(AppLoc... locs);
    @Update
    void update(AppLoc loc);
    @Query("SELECT * FROM 'AppLoc'")
    List<AppLoc> getAllLoc();
}

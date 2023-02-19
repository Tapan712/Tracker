package com.example.tracker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.tracker.entity.Credits;
import com.example.tracker.entity.Person;

@Dao
public interface CreditsDao {
    @Insert
    void insertAll(Credits... credits);
    @Update
    void update(Credits credits);
    @Delete
    void delete(Credits credits);
}

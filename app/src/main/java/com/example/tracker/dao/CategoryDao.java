package com.example.tracker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tracker.entity.Category;

import java.util.List;
@Dao
public interface CategoryDao {
    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);

    @Update
    void update(Category category);

    @Query("SELECT * FROM Category")
    List<Category> getAllCategory();

    @Query("SELECT * FROM Category WHERE id = :id")
    List<Category> getCategoryById(Integer id);
}

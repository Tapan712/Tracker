package com.example.tracker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.tracker.dto.PersonWithCredits;
import com.example.tracker.entity.Person;

import java.util.List;

@Dao
public interface PersonDao {
    @Insert
    void insertAll(Person... person);
    @Update
    void update(Person person);
    @Delete
    void delete(Person person);
    @Query("SELECT * FROM Person")
    List<Person> getAllPerson();

    @Transaction
    @Query("SELECT * FROM Person WHERE cNumber = :num")
    List<PersonWithCredits> getPersonDetailsByNumber(String num);

}

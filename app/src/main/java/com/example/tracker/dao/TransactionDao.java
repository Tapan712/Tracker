package com.example.tracker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tracker.entity.Transaction;

import java.util.List;
import java.util.Optional;

@Dao
public interface TransactionDao {
    @Insert
    void insertAll(Transaction... transactions);
    @Delete
    void delete(Transaction transaction);
    @Update
    void update(Transaction transaction);
    @Query("Select * from `Transaction` WHERE id = :id")
    Optional<Transaction> findById(Integer id);
    @Query("Select * from `Transaction` WHERE trnType = :trnType AND trnDate = :trnDate")
    List<Transaction> findByTypeAndDate(String trnType,Long trnDate);
    @Query("Select * from `Transaction`")
    List<Transaction> findAll();
    @Query("SELECT * FROM `Transaction` WHERE trnDate BETWEEN :fromDate AND :toDate ORDER BY trnDate DESC")
    List<Transaction> findBetweenDates(Long fromDate,Long toDate);
    @Query("SELECT * FROM `Transaction` WHERE trnType = :trnType AND trnDate BETWEEN :fromDate AND :toDate ORDER BY trnDate DESC")
    List<Transaction> findByTypeAndBetweenDates(Long fromDate,Long toDate,String trnType);

}

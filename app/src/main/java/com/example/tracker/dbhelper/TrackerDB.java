package com.example.tracker.dbhelper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tracker.dao.AppLocDao;
import com.example.tracker.dao.CategoryDao;
import com.example.tracker.dao.CreditsDao;
import com.example.tracker.dao.PersonDao;
import com.example.tracker.dao.TransactionDao;
import com.example.tracker.entity.AppLoc;
import com.example.tracker.entity.Category;
import com.example.tracker.entity.Credits;
import com.example.tracker.entity.Person;
import com.example.tracker.entity.Transaction;

@Database(entities = {Category.class, Transaction.class, AppLoc.class, Person.class , Credits.class},exportSchema = false,version = 5)
public abstract class TrackerDB extends RoomDatabase {
    public static final String DB_NAME = "tracker_db";
    private static TrackerDB instance;
    public static synchronized TrackerDB getDb(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context,TrackerDB.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract CategoryDao categoryDao();
    public abstract TransactionDao transactionDao();
    public abstract AppLocDao appLocDao();
    public abstract PersonDao personDao();
    public abstract CreditsDao creditsDao();
}

package com.example.tracker.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class AppLoc implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public String locLang;
}

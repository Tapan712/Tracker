package com.example.tracker.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Person implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public String cName;
    public String cNumber;
    public Float grossAmount;
    public String amtStatus;

}

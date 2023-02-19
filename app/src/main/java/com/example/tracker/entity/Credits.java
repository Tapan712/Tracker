package com.example.tracker.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Credits {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public Integer pId;
    public Float amount;
    public String crdType;
    public Long crdDate;
    public String crdChannel;
    public String desc;
}

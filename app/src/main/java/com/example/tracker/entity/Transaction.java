package com.example.tracker.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Transaction implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public String trnCat;
    public String trnName;
    public String trnType;
    public Long trnDate;
    public Float price;
    public Integer noOfItems;
    public Float total;
    public String byWhom;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", trnCat='" + trnCat + '\'' +
                ", trnName='" + trnName + '\'' +
                ", trnType='" + trnType + '\'' +
                ", trnDate=" + trnDate +
                ", price=" + price +
                ", noOfItems=" + noOfItems +
                ", total=" + total +
                ", byWhom='" + byWhom + '\'' +
                '}';
    }
}

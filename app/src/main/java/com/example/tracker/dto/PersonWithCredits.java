package com.example.tracker.dto;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.tracker.entity.Credits;
import com.example.tracker.entity.Person;

import java.io.Serializable;
import java.util.List;

public class PersonWithCredits implements Serializable {
    @Embedded public Person person;
    @Relation(
            parentColumn = "id",
            entityColumn = "pId"
    )
    public List<Credits> creditsList;
}

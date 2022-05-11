package com.example.ics3u;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SavedMeal {
    @PrimaryKey// (autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "meal_name")
    public String name;

    @ColumnInfo(name = "meal_date")
    public String date;

}

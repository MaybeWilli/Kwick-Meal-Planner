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

    @ColumnInfo(name = "meal_ingredients")
    public String ingredients;

    @ColumnInfo(name = "meal_groups")
    public String groups;

    @ColumnInfo(name = "meal_calories")
    public float calories;

    @ColumnInfo(name = "meal_servings")
    public float servings;

    @ColumnInfo(name = "meal_servings_list")
    public String servingsStr;

}

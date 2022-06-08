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

    @ColumnInfo(name = "meal_total_calories")
    public float calories;

    @ColumnInfo(name = "meal_calories_list")
    public String caloriesStr; //calories per ingredient

    @ColumnInfo(name = "meal_totalServings")
    public float totalServings; //total servings per standard meal

    @ColumnInfo(name = "meal_servings")
    public float servings; //servings for the plannedMeal

    @ColumnInfo(name = "meal_servings_list")
    public String servingsStr; //servings per ingredient

}

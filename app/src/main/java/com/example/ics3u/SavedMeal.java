/*
* Object used to define the table rows
 */
package com.example.ics3u;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SavedMeal {
    //This field is used as the database's "main identifier" for finding rows
    @PrimaryKey
    public int uid;

    //meal name
    @ColumnInfo(name = "meal_name")
    public String name;

    //date of meal eaten (not used for SavedMealDatabase)
    @ColumnInfo(name = "meal_date")
    public String date;

    //a string of ingredients seperated by commas.
    @ColumnInfo(name = "meal_ingredients")
    public String ingredients;

    //string of groups seperated by commas
    @ColumnInfo(name = "meal_groups")
    public String groups;

    //total number of calories in the meal inputed by the user
    @ColumnInfo(name = "meal_total_calories")
    public float calories;

    //calories per ingredient
    @ColumnInfo(name = "meal_calories_list")
    public String caloriesStr;

    //total amount of servings in the meal inputed by the user
    @ColumnInfo(name = "meal_totalServings")
    public float totalServings;

    //servings for the plannedMeal
    @ColumnInfo(name = "meal_servings")
    public float servings;

    //servings per ingredient for the meal inputed by the user
    @ColumnInfo(name = "meal_servings_list")
    public String servingsStr;

}

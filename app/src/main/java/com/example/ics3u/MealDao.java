/*
* This file is the data access object used to run queries to fetch things
* from the database it's attached to.
 */
package com.example.ics3u;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MealDao {
    //insert a single meal
    @Insert
    public void insertOneMeal(SavedMeal savedMeal);

    //delete all meals with a certain uid
    @Query("DELETE FROM savedmeal WHERE uid = :id")
    public void deleteOneMeal(int id);

    //update one meal
    @Update()
    public void updateOneMeal(SavedMeal savedMeal);

    //delete all meals with a certain name
    @Query("DELETE FROM savedmeal WHERE meal_name = :mealName")
    public void deleteAllMealsWithName(String mealName);

    //get everything in the table
    @Query("SELECT * FROM savedmeal")
    public List<SavedMeal> getAll();
}

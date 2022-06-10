package com.example.ics3u;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MealDao {
    @Insert
    public void insertOneMeal(SavedMeal savedMeal);

    @Query("DELETE FROM savedmeal WHERE uid = :id")
    public void deleteOneMeal(int id);

    @Update()
    public void updateOneMeal(SavedMeal savedMeal);


    @Query("SELECT * FROM savedmeal")
    public List<SavedMeal> getAll();
}

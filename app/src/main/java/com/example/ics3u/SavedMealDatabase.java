/*
* Defines a database object with a mealDao.
 */
package com.example.ics3u;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SavedMeal.class}, version = 1)
public abstract class SavedMealDatabase extends RoomDatabase {
    public abstract MealDao mealDao();
}

package com.example.ics3u;

import android.util.Log;

import androidx.constraintlayout.motion.widget.Debug;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class MealManager {
    public static ArrayList<Meal> meals = new ArrayList<Meal>();
    public static ArrayList<PlannedMeal> plannedMeals = new ArrayList<PlannedMeal>();

    public static void addMeal(String name) //look, I'll add some more overloads later, ok?
    {
        Meal newMeal = new Meal(name);
        meals.add(newMeal);
        PrintElements();
    }

    public static void addPlannedMeal(Meal meal, String date)
    {
        MealManager.plannedMeals.add(new PlannedMeal(meal, date));
        PrintElements();
    }

    private static void PrintElements()
    {
        for (int i = 0; i < meals.size(); i++)
        {
            Log.e("hmm", meals.get(i).name);
        }
    }

}

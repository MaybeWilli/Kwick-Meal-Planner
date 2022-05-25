package com.example.ics3u;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealManager {
    public static ArrayList<Meal> meals = new ArrayList<Meal>();
    public static ArrayList<PlannedMeal> plannedMeals = new ArrayList<PlannedMeal>();
    public static int mealId = 0;
    public static int plannedMealId = 0;

    public static void addMeal(String name) //look, I'll add some more overloads later, ok?
    {
        Meal newMeal = new Meal(name, new ArrayList<String>());
        meals.add(newMeal);
        InsertSavedMeal insertSavedMeal = new InsertSavedMeal(name, "", 0);
        insertSavedMeal.thread.start();
        PrintElements();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void addMeal(String name, ArrayList<String> ingredients) //look, I'll add some more overloads later, ok?
    {
        Meal newMeal = new Meal(name, ingredients);
        meals.add(newMeal);
        String ingredientStr = String.join(",", ingredients);
        Log.e("hmm", ingredientStr);
        InsertSavedMeal insertSavedMeal = new InsertSavedMeal(name, ingredientStr, 0);
        insertSavedMeal.thread.start();
        PrintElements();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void addMeal(String name, ArrayList<String> ingredients, float calories)
    {
        Meal newMeal = new Meal(name, new ArrayList<String>(), calories);
        newMeal.ingredients.addAll(ingredients);
        meals.add(newMeal);
        /*for (int i = 0; i < meals.size(); i++)
        {
            Log.e("MealManager", "First ingredient:"+meals.get(i).ingredients.get(i));
        }*/
        String ingredientStr = String.join(",", ingredients);
        Log.e("mealmanager", ingredientStr);
        InsertSavedMeal insertSavedMeal = new InsertSavedMeal(name, ingredientStr, calories);
        insertSavedMeal.thread.start();
        PrintElements();
    }

    public static void addPlannedMeal(Meal meal, String date, float servings)
    {
        MealManager.plannedMeals.add(new PlannedMeal(meal, date, servings));
        PrintElements();
        Log.e("hmm", "Not here either!");
        InsertSavedPlannedMeal insertSavedPlannedMeal = new InsertSavedPlannedMeal(meal.name, date, meal.calories, servings);
        insertSavedPlannedMeal.thread.start();
    }

    private static void PrintElements()
    {
        for (int i = 0; i < meals.size(); i++)
        {
            Log.e("hmm", meals.get(i).name);
        }
    }

    public static void InstantiateArrays()
    {
        Log.e("hmm", "hmm");
        MealManager.meals.clear();
        Log.e("hmm", "bruh");
        List<SavedMeal> savedMeals = MainActivity.mealDao.getAll();
        Log.e("hmm", "check");
        mealId = 0;
        Log.e("hmm", "well");
        Log.e("hmm", "uh "+savedMeals.size());
        for (int i = 0; i < savedMeals.size(); i++)
        {
            mealId++;
            MealManager.meals.add(new Meal(savedMeals.get(i).name, new ArrayList<String>(Arrays.asList(savedMeals.get(i).ingredients.split(","))), savedMeals.get(i).calories));
        }

        MealManager.plannedMeals.clear();
        List<SavedMeal> savedPlannedMeals = MainActivity.plannedMealDao.getAll();

        plannedMealId = 0;
        for (int i = 0; i < savedPlannedMeals.size(); i++)
        {
            plannedMealId++;
            MealManager.plannedMeals.add(new PlannedMeal(new Meal(savedPlannedMeals.get(i).name, new ArrayList<String>(), savedPlannedMeals.get(i).calories), savedPlannedMeals.get(i).date, savedPlannedMeals.get(i).servings));
        }
    }

    static class InsertSavedMeal implements Runnable {
        Thread thread = new Thread(this, "insert_data");
        String name;
        String ingredients;
        float calories;

        public InsertSavedMeal(String name, String ingredients, float calories)
        {
            this.name = name;
            this.ingredients = ingredients;
            this.calories = calories;
        }
        @Override
        public void run() {
            SavedMeal meal = new SavedMeal();
            meal.name = this.name;
            meal.uid = mealId;
            meal.ingredients = this.ingredients;
            meal.calories = this.calories;
            mealId++;
            MainActivity.savedMealDatabase.mealDao().insertOneMeal(meal);
        }
    }

    static class InsertSavedPlannedMeal implements Runnable {
        Thread thread = new Thread(this, "insert_planned_data");
        String name;
        String date;
        float calories;
        float servings;

        public InsertSavedPlannedMeal(String name, String date, float calories, float servings)
        {
            this.name = name;
            this.date = date;
            this.calories = calories;
            this.servings = servings;
        }
        @Override
        public void run() {
            SavedMeal meal = new SavedMeal();
            meal.name = this.name;
            meal.date = this.date;
            meal.uid = plannedMealId;
            meal.calories = this.calories;
            meal.servings = this.servings;
            plannedMealId++;
            Log.e("hmm", "Is it here?");
            MainActivity.savedPlannedMealDatabase.mealDao().insertOneMeal(meal);
            Log.e("hmm", "uhh");
        }
    }

}

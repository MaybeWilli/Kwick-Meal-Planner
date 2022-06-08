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

    /*public static void addMeal(String name) //look, I'll add some more overloads later, ok?
    {
        Meal newMeal = new Meal(name, new ArrayList<String>());
        meals.add(newMeal);
        InsertSavedMeal insertSavedMeal = new InsertSavedMeal(name, "", 0);
        insertSavedMeal.thread.start();
        PrintElements();
    }*/

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void addMeal(String name, ArrayList<String> ingredients) //look, I'll add some more overloads later, ok?
    {
        Meal newMeal = new Meal(name, ingredients);
        meals.add(newMeal);
        String ingredientStr = String.join(",", ingredients);
        Log.e("hmm", ingredientStr);
        //InsertSavedMeal insertSavedMeal = new InsertSavedMeal(name, ingredientStr, 0);
        //insertSavedMeal.thread.start();
        PrintElements();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void addMeal(String name, ArrayList<String> ingredients, float calories, ArrayList<String> groups, ArrayList<Float> servings, ArrayList<Float> calList, float totalCalories)
    {
        Meal newMeal = new Meal(name, new ArrayList<String>(), calories, groups, servings, calList, totalCalories);
        newMeal.ingredients.addAll(ingredients);
        meals.add(newMeal);
        /*for (int i = 0; i < meals.size(); i++)
        {
            Log.e("MealManager", "First ingredient:"+meals.get(i).ingredients.get(i));
        }*/

        //convert ingredient to string
        String ingredientStr = String.join(",", ingredients);
        Log.e("mealmanager", ingredientStr);

        //convert food groups to string
        String groupsStr = String.join(",", groups);

        //convert servings to string
        ArrayList<String> servingsStrList = new ArrayList<String>();
        for (int i = 0; i < servings.size(); i++)
        {
            servingsStrList.add(servings.get(i).toString());
        }
        String servingsStr = String.join(",", servingsStrList);

        InsertSavedMeal insertSavedMeal = new InsertSavedMeal(name, ingredientStr, calories, servingsStr, groupsStr);
        insertSavedMeal.thread.start();
        PrintElements();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void addPlannedMeal(Meal meal, String date, float servings)
    {
        MealManager.plannedMeals.add(new PlannedMeal(meal, date, servings));
        PrintElements();
        Log.e("hmm", "Not here either!");

        //convert ingredient to string
        String ingredientStr = String.join(",", meal.ingredients);
        Log.e("mealmanager", ingredientStr);

        //convert food groups to string
        String groupsStr = String.join(",", meal.groups);

        //convert servings to string
        ArrayList<String> servingsStrList = new ArrayList<String>();
        for (int i = 0; i < meal.servings.size(); i++)
        {
            servingsStrList.add(meal.servings.get(i).toString());
        }
        String servingsStr = String.join(",", servingsStrList);

        InsertSavedPlannedMeal insertSavedPlannedMeal = new InsertSavedPlannedMeal(meal.name, date, meal.calories, servings, servingsStr, groupsStr);
        insertSavedPlannedMeal.thread.start();
    }

    private static void PrintElements()
    {
        for (int i = 0; i < meals.size(); i++)
        {
            Log.e("hmm", meals.get(i).name);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
            String name = savedMeals.get(i).name;
            ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(savedMeals.get(i).ingredients.split(",")));
            float calories = savedMeals.get(i).calories;
            ArrayList<String> groups = new ArrayList<String>(Arrays.asList(savedMeals.get(i).groups.split(",")));
            ArrayList<String> servingsStrList = new ArrayList<String>(Arrays.asList(savedMeals.get(i).servingsStr.split(",")));
            ArrayList<Float> servings = new ArrayList<Float>();
            ArrayList<String> calorieList = new ArrayList<String>(Arrays.asList(savedMeals.get(i).caloriesStr.split(",")));
            ArrayList<Float> floatCalorieList = new ArrayList<Float>();
            Float totalServings = savedMeals.get(i).totalServings;
            for (int j = 0; j < servingsStrList.size(); j++)
            {
                servings.add(Float.parseFloat(servingsStrList.get(j)));
                floatCalorieList.add(Float.parseFloat(calorieList.get(j)));
            }
            MealManager.addMeal(name, ingredients, calories, groups, servings, floatCalorieList, totalServings);
            //MealManager.meals.add(new Meal(savedMeals.get(i).name, new ArrayList<String>(Arrays.asList(savedMeals.get(i).ingredients.split(","))), savedMeals.get(i).calories));
        }

        MealManager.plannedMeals.clear();
        List<SavedMeal> savedPlannedMeals = MainActivity.plannedMealDao.getAll();
        Log.e("hmm", "Past that part");

        plannedMealId = 0;
        for (int i = 0; i < savedPlannedMeals.size(); i++)
        {
            plannedMealId++;
            Log.e("hmm", "Past that part2");
            String name = savedPlannedMeals.get(i).name;
            Log.e("hmm", "Past that part3");
            //ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(savedPlannedMeals.get(i).ingredients.split(",")));
            //Log.e("hmm", "Past that part4");
            float calories = savedPlannedMeals.get(i).calories;
            Log.e("hmm", "Past that part5");
            ArrayList<String> groups = new ArrayList<String>(Arrays.asList(savedPlannedMeals.get(i).groups.split(",")));
            Log.e("hmm", "Past that part6");
            ArrayList<String> servingsStrList = new ArrayList<String>(Arrays.asList(savedPlannedMeals.get(i).servingsStr.split(",")));
            Log.e("hmm", "Past that part7");
            ArrayList<Float> servings = new ArrayList<Float>();
            for (int j = 0; j < servingsStrList.size(); j++)
            {
                Log.e("hmm", "Past that part8");
                servings.add(Float.parseFloat(servingsStrList.get(j)));
            }
            Meal meal = new Meal(name, new ArrayList<String>(), calories, groups, servings);
            Log.e("hmm", "Past that part9");
            MealManager.plannedMeals.add(new PlannedMeal(meal, savedPlannedMeals.get(i).date, savedPlannedMeals.get(i).servings));
            Log.e("hmm", "Past that part10");
        }
    }

    static class InsertSavedMeal implements Runnable {
        Thread thread = new Thread(this, "insert_data");
        String name;
        String ingredients;
        String servings;
        String groups;
        float calories;

        public InsertSavedMeal(String name, String ingredients, float calories, String servings, String groups)
        {
            this.name = name;
            this.ingredients = ingredients;
            this.calories = calories;
            this.groups = groups;
            this.servings = servings;
        }
        @Override
        public void run() {
            SavedMeal meal = new SavedMeal();
            meal.name = this.name;
            meal.uid = mealId;
            meal.ingredients = this.ingredients;
            meal.calories = this.calories;
            meal.groups = this.groups;
            meal.servingsStr = this.servings;
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
        String servingsStr;
        String groupStr;

        public InsertSavedPlannedMeal(String name, String date, float calories, float servings, String servingsStr, String groupStr)
        {
            this.name = name;
            this.date = date;
            this.calories = calories;
            this.servings = servings;
            this.groupStr = groupStr;
            this.servingsStr = servingsStr;
        }
        @Override
        public void run() {
            SavedMeal meal = new SavedMeal();
            meal.name = this.name;
            meal.date = this.date;
            meal.uid = plannedMealId;
            meal.calories = this.calories;
            meal.servings = this.servings;
            meal.servingsStr = this.servingsStr;
            meal.groups = this.servingsStr;
            plannedMealId++;
            Log.e("hmm", "Is it here?");
            MainActivity.savedPlannedMealDatabase.mealDao().insertOneMeal(meal);
            Log.e("hmm", "uhh");
        }
    }

}

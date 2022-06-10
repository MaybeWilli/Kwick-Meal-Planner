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
    public static List<SavedMeal> savedMeals;

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
    public static void addMeal(String name, ArrayList<String> ingredients, float calories, ArrayList<String> groups, ArrayList<Float> servings, ArrayList<Float> calList, float totalServings)
    {
        Meal newMeal = new Meal(name, new ArrayList<String>(), calories, groups, servings, calList, totalServings);
        newMeal.ingredients.addAll(ingredients);
        meals.add(newMeal);

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

        //convert calories to string
        ArrayList<String> calStrList = new ArrayList<String>();
        for (int i = 0; i < calList.size(); i++)
        {
            calStrList.add(calList.get(i).toString());
        }
        String calStr = String.join(",", calStrList);

        InsertSavedMeal insertSavedMeal = new InsertSavedMeal(name, ingredientStr, calories, servingsStr, groupsStr, calStr, totalServings);
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

        //convert calories to string
        ArrayList<String> calList = new ArrayList<String>();
        for (int i = 0; i < meal.caloriesList.size(); i++)
        {
            calList.add(meal.caloriesList.get(i).toString());
        }
        String calStr = String.join(",", calList);

        InsertSavedPlannedMeal insertSavedPlannedMeal = new InsertSavedPlannedMeal(meal.name, date, meal.calories, servings, servingsStr, groupsStr, calStr, meal.totalServings);
        //(String name, String date, float calories, float servings, String servingsStr, String groupStr, String calorieStr, float totalServings)
        insertSavedPlannedMeal.thread.start();
    }

    private static void PrintElements()
    {
        for (int i = 0; i < meals.size(); i++)
        {
            //Log.e("hmm", meals.get(i).name);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void deleteMeal(int mealPos)
    {
        Meal meal = MealManager.meals.get(mealPos);
        MealManager.meals.remove(mealPos);

        //convert ingredient to string
        String ingredientStr = String.join(",", meal.ingredients);
        Log.e("mealmanager", ingredientStr);

        int id = 0;
        for (int i = 0; i < savedMeals.size(); i++) {
            if (meal.name.equals(savedMeals.get(i).name) && ingredientStr.equals(savedMeals.get(i).ingredients)) {
                id = i;
                break;
            }
        }
        DeleteSavedMeal deleteSavedMeal = new DeleteSavedMeal(id);
        deleteSavedMeal.thread.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void updateMeal(Meal newMeal, String oldName, String oldIngredientStr)
    {
        int updateMealId = 0;

        for (int i = 0; i < savedMeals.size(); i++) {
            if (oldName.equals(savedMeals.get(i).name) && oldIngredientStr.equals(savedMeals.get(i).ingredients)) {
                updateMealId = i;
                break;
            }
        }

        ArrayList<String> strServings = new ArrayList<String>();
        ArrayList<String> strCalories = new ArrayList<String>();

        for (int i = 0; i < newMeal.servings.size(); i++)
        {
            strServings.add(newMeal.servings.get(i).toString());
            strCalories.add(newMeal.caloriesList.get(i).toString());
        }

        SavedMeal meal = new SavedMeal();
        meal.name = newMeal.name;
        meal.uid = updateMealId;
        meal.ingredients = String.join(",", newMeal.ingredients);
        meal.calories = newMeal.calories;
        meal.groups = String.join(",", newMeal.groups);
        meal.servingsStr = String.join(",", strServings);
        meal.caloriesStr = String.join(",", strCalories);
        meal.totalServings = newMeal.calories;

        UpdateSavedMeal updateSavedMeal = new UpdateSavedMeal(meal);
        updateSavedMeal.thread.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void InstantiateArrays()
    {
        //Log.e("hmm", "hmm");
        MealManager.meals.clear();
        //Log.e("hmm", "bruh");
        savedMeals = MainActivity.mealDao.getAll();
        //Log.e("hmm", "check");
        mealId = 0;
        //Log.e("hmm", "well");
        //Log.e("hmm", "uh "+savedMeals.size());
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
            MealManager.meals.add(new Meal(name, ingredients, calories, groups, servings, floatCalorieList, totalServings));
            //(String name, ArrayList<String> ingredients, float calories, ArrayList<String> groups, ArrayList<Float> servings, ArrayList<Float> calorieList, float totServings)
            //MealManager.meals.add(new Meal(savedMeals.get(i).name, new ArrayList<String>(Arrays.asList(savedMeals.get(i).ingredients.split(","))), savedMeals.get(i).calories));
        }

        MealManager.plannedMeals.clear();
        List<SavedMeal> savedPlannedMeals = MainActivity.plannedMealDao.getAll();
        //Log.e("hmm", "Past that part");

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
            ArrayList<String> calorieList = new ArrayList<String>(Arrays.asList(savedMeals.get(i).caloriesStr.split(",")));
            ArrayList<Float> floatCalorieList = new ArrayList<Float>();
            Float totalServings = savedMeals.get(i).totalServings;
            for (int j = 0; j < servingsStrList.size(); j++)
            {
                Log.e("hmm", "Past that part8");
                servings.add(Float.parseFloat(servingsStrList.get(j)));
                floatCalorieList.add(Float.parseFloat(calorieList.get(j)));
            }
            Meal meal = new Meal(name, new ArrayList<String>(), calories, groups, servings, floatCalorieList, totalServings);
            Log.e("hmm", "Past that part9");
            MealManager.plannedMeals.add(new PlannedMeal(meal, savedPlannedMeals.get(i).date, savedPlannedMeals.get(i).servings));
            Log.e("hmm", "Past that part10");
        }
        Log.e("hmm", "I am here again! I dunno.");
    }

    static class InsertSavedMeal implements Runnable {
        Thread thread = new Thread(this, "insert_data");
        String name;
        String ingredients;
        String servings;
        String groups;
        float calories;
        String calorieStr;
        float totalServings;

        public InsertSavedMeal(String name, String ingredients, float calories, String servings, String groups, String calorieStr, float totalServings)
        {
            this.name = name;
            this.ingredients = ingredients;
            this.calories = calories;
            this.groups = groups;
            this.servings = servings;
            this.calorieStr = calorieStr;
            this.totalServings = totalServings;
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
            meal.caloriesStr = this.calorieStr;
            meal.totalServings = this.totalServings;
            mealId++;
            MainActivity.savedMealDatabase.mealDao().insertOneMeal(meal);
        }
    }

    static class DeleteSavedMeal implements Runnable {
        Thread thread = new Thread(this, "insert_data");
        int id;

        public DeleteSavedMeal(int id)
        {
            this.id = id;

        }
        @Override
        public void run() {

            MainActivity.savedMealDatabase.mealDao().deleteOneMeal(id);
        }
    }

    static class UpdateSavedMeal implements Runnable {
        Thread thread = new Thread(this, "insert_data");
        SavedMeal savedMeal;

        public UpdateSavedMeal(SavedMeal savedMeal)
        {
            this.savedMeal = savedMeal;

        }
        @Override
        public void run() {

            MainActivity.savedMealDatabase.mealDao().updateOneMeal(savedMeal);
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
        String calorieStr;
        float totalServings;

        public InsertSavedPlannedMeal(String name, String date, float calories, float servings, String servingsStr, String groupStr, String calorieStr, float totalServings)
        {
            this.name = name;
            this.date = date;
            this.calories = calories;
            this.servings = servings;
            this.groupStr = groupStr;
            this.servingsStr = servingsStr;
            this.calorieStr = calorieStr;
            this.totalServings = totalServings;
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
            meal.caloriesStr = this.calorieStr;
            meal.totalServings = this.totalServings;
            plannedMealId++;
            Log.e("hmm", "Is it here?");
            MainActivity.savedPlannedMealDatabase.mealDao().insertOneMeal(meal);
            Log.e("hmm", "uhh");
        }
    }

}

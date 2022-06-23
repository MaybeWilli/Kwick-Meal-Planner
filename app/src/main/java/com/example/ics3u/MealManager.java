/*
* This java file serves as the main interface for interacting with any form of saved meal
* that every file can look into. It also doubles as the database interface, since our database
* model needs one file that interacts with database related affairs. The functionaltiy includes
* adding, deleting, updating, and fetching from both the meals and the planned meals.
 */
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
    public static List<SavedMeal> savedPlannedMeals;

    //save a new meal to both the ArrayList and the database
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void addMeal(String name, ArrayList<String> ingredients, float calories, ArrayList<String> groups, ArrayList<Float> servings, ArrayList<Float> calList, float totalServings)
    {
        //add tp ArrayList
        Meal newMeal = new Meal(name, new ArrayList<String>(), calories, groups, servings, calList, totalServings);
        newMeal.ingredients.addAll(ingredients);
        meals.add(newMeal);

        //convert to proper format and save into database
        //convert ingredient to string
        String ingredientStr = String.join(",", ingredients);

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

        //run thread to save into database
        InsertSavedMeal insertSavedMeal = new InsertSavedMeal(name, ingredientStr, calories, servingsStr, groupsStr, calStr, totalServings);
        insertSavedMeal.thread.start();
    }

    //add a new planned meal to the ArrayList and database
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void addPlannedMeal(Meal meal, String date, float servings)
    {
        //add to the ArrayList
        MealManager.plannedMeals.add(new PlannedMeal(meal, date, servings));

        //convert everything to String or float, and save into database
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

        //run background thread to save data into planned meal database
        InsertSavedPlannedMeal insertSavedPlannedMeal = new InsertSavedPlannedMeal(meal.name, ingredientStr, date, meal.calories, servings, servingsStr, groupsStr, calStr, meal.totalServings);
        insertSavedPlannedMeal.thread.start();
    }

    //prints out every item in the meals ArrayList (used for debugging purposes)
    private static void PrintElements()
    {
        for (int i = 0; i < meals.size(); i++)
        {
            Log.e("hmm", meals.get(i).name);
        }
    }

    //delete a meal from the ArrayList and Database based on the position in the ArrayList
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void deleteMeal(int mealPos)
    {
        //remove from ArrayList
        Meal meal = MealManager.meals.get(mealPos);
        MealManager.meals.remove(mealPos);

        //convert ingredient to string
        String ingredientStr = String.join(",", meal.ingredients);
        Log.e("mealmanager", ingredientStr);

        //find the id of the SavedMeal in the database
        int id = 0;
        for (int i = 0; i < savedMeals.size(); i++) {
            //compare meals until the right one is found
            if (meal.name.equals(savedMeals.get(i).name) && ingredientStr.equals(savedMeals.get(i).ingredients)) {
                id = savedMeals.get(i).uid;
                break;
            }
        }

        //delete relevant planned meals from arrayList
        for (int i = 0; i <plannedMeals.size(); i++)
        {
            if (meal.name.equals(plannedMeals.get(i).meal.name))
            {
                plannedMeals.remove(i);
                i = 0;
            }
        }
        //start background thread to delete from savedMealDatabase
        DeleteSavedMeal deleteSavedMeal = new DeleteSavedMeal(id, meal.name);
        deleteSavedMeal.thread.start();
    }

    //delete a planned meal from the arrayList and database
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void deletePlannedMeal(int mealPos)
    {
        //remove from arrayList
        PlannedMeal plannedMeal = MealManager.plannedMeals.get(mealPos);
        MealManager.plannedMeals.remove(mealPos);

        //find in database
        String ingredientStr = String.join(",", plannedMeal.meal.ingredients);
        int id = 0;

        for (int i = 0; i < savedPlannedMeals.size(); i++)
        {
            Boolean isDate = plannedMeal.date.equals(savedPlannedMeals.get(i).date);
            Log.e("deleteplannedmeal", plannedMeal.date+" "+savedPlannedMeals.get(i).date+" "+plannedMeal.meal+" "+savedPlannedMeals.get(i).name);
            if (isDate && plannedMeal.meal.name.equals(savedPlannedMeals.get(i).name))
            {
                id = savedPlannedMeals.get(i).uid;
                break;
            }
        }

        //run background thread to remove from database
        DeleteSavedPlannedMeal deleteSavedPlannedMeal = new DeleteSavedPlannedMeal(id);
        deleteSavedPlannedMeal.thread.start();
    }

    //update a meal give its old name and ingredients
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void updateMeal(Meal newMeal, String oldName, String oldIngredientStr)
    {
        //find meal in database
        int updateMealId = 0;

        for (int i = 0; i < savedMeals.size(); i++) {
            if (oldName.equals(savedMeals.get(i).name) && oldIngredientStr.equals(savedMeals.get(i).ingredients)) {
                updateMealId = savedMeals.get(i).uid;
                break;
            }
        }

        //format a SavedMeal and update
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

        //run background thread to update a meal
        UpdateSavedMeal updateSavedMeal = new UpdateSavedMeal(meal);
        updateSavedMeal.thread.start();

    }

    //fetch data from database
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void InstantiateArrays()
    {
        //clear current ArrayList
        MealManager.meals.clear();

        //fetch a list from the database
        savedMeals = MainActivity.mealDao.getAll();

        //reset the mealId
        mealId = 0;
        int maxId = 0;

        //turn the savedmeal into a meal, and add it to the mealArray
        for (int i = 0; i < savedMeals.size(); i++)
        {
            if (savedMeals.get(i).uid > maxId)
            {
                maxId = savedMeals.get(i).uid;
                mealId = maxId; //find the new uid to be used
            }
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
        }

        //clear plannedMeals and fetch data from the planned meal database
        MealManager.plannedMeals.clear();
        savedPlannedMeals = MainActivity.plannedMealDao.getAll();

        //find the new uid, and format the SavedMeals into plannedMeals
        plannedMealId = 0;
        int maxPlannedId = 0;
        for (int i = 0; i < savedPlannedMeals.size(); i++)
        {
            if (savedPlannedMeals.get(i).uid > maxPlannedId)
            {
                maxPlannedId = savedPlannedMeals.get(i).uid;
                plannedMealId = maxPlannedId; //find new uid
            }
            String name = savedPlannedMeals.get(i).name;
            float calories = savedPlannedMeals.get(i).calories;
            ArrayList<String> groups = new ArrayList<String>(Arrays.asList(savedPlannedMeals.get(i).groups.split(",")));
            ArrayList<String> servingsStrList = new ArrayList<String>(Arrays.asList(savedPlannedMeals.get(i).servingsStr.split(",")));
            ArrayList<String> ingredientsStrList = new ArrayList<String>(Arrays.asList(savedPlannedMeals.get(i).ingredients.split(",")));
            ArrayList<Float> servings = new ArrayList<Float>();
            ArrayList<String> calorieList = new ArrayList<String>(Arrays.asList(savedPlannedMeals.get(i).caloriesStr.split(",")));
            ArrayList<Float> floatCalorieList = new ArrayList<Float>();
            Float totalServings = savedPlannedMeals.get(i).totalServings;
            for (int j = 0; j < servingsStrList.size(); j++)
            {
                servings.add(Float.parseFloat(servingsStrList.get(j)));
                floatCalorieList.add(Float.parseFloat(calorieList.get(j)));
            }
            Meal meal = new Meal(name, ingredientsStrList, calories, groups, servings, floatCalorieList, totalServings);
            MealManager.plannedMeals.add(new PlannedMeal(meal, savedPlannedMeals.get(i).date, savedPlannedMeals.get(i).servings));
        }
    }

    //background thread to save a new SavedMeal into the savedMealDatabase
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
            //meal.uid = mealId;
            meal.ingredients = this.ingredients;
            meal.calories = this.calories;
            meal.groups = this.groups;
            meal.servingsStr = this.servings;
            meal.caloriesStr = this.calorieStr;
            meal.totalServings = this.totalServings;
            mealId++;
            meal.uid = mealId;
            MainActivity.savedMealDatabase.mealDao().insertOneMeal(meal);
        }
    }

    //background thread to delete a meal given its uid, and remove all plannedMeals with a certain name
    static class DeleteSavedMeal implements Runnable {
        Thread thread = new Thread(this, "insert_data");
        int id;
        String name;

        public DeleteSavedMeal(int id, String name)
        {
            this.id = id;
            this.name = name;

        }
        @Override
        public void run() {

            MainActivity.savedMealDatabase.mealDao().deleteOneMeal(id);
            MainActivity.savedPlannedMealDatabase.mealDao().deleteAllMealsWithName(name);
        }
    }

    //background thread to delete a single planned meal given its uid
    static class DeleteSavedPlannedMeal implements Runnable {
        Thread thread = new Thread(this, "insert_data");
        int id;

        public DeleteSavedPlannedMeal(int id)
        {
            this.id = id;

        }
        @Override
        public void run() {

            MainActivity.savedPlannedMealDatabase.mealDao().deleteOneMeal(id);
        }
    }

    //background thread to update a saved meal
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

    //background thread to add a new plannedMeal to the database
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
        String ingredients;

        public InsertSavedPlannedMeal(String name, String ingredients, String date, float calories, float servings, String servingsStr, String groupStr, String calorieStr, float totalServings)
        {
            this.name = name;
            this.date = date;
            this.calories = calories;
            this.servings = servings;
            this.groupStr = groupStr;
            this.servingsStr = servingsStr;
            this.calorieStr = calorieStr;
            this.totalServings = totalServings;
            this.ingredients = ingredients;
        }
        @Override
        public void run() {
            SavedMeal meal = new SavedMeal();
            meal.name = this.name;
            meal.date = this.date;
            meal.calories = this.calories;
            meal.servings = this.servings;
            meal.servingsStr = this.servingsStr;
            meal.groups = this.groupStr;
            meal.caloriesStr = this.calorieStr;
            meal.totalServings = this.totalServings;
            meal.ingredients = this.ingredients;
            plannedMealId++;
            meal.uid = plannedMealId;
            Log.e("hmm", "Is it here?");
            MainActivity.savedPlannedMealDatabase.mealDao().insertOneMeal(meal);
            Log.e("hmm", "uhh");
        }
    }

}

/*
* Generates a list of ingredients used for the current monday-sunday week. No duplicates
* are included.
 */
package com.example.ics3u;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ShoppingList extends AppCompatActivity {

    private ArrayList<String> daysList;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private ArrayList<PlannedMeal> weeklyPlannedMeals = new ArrayList<PlannedMeal>();
    private ArrayList<String> ingredientsWeeklyList = new ArrayList<String>();
    private TextView ingredientsTV;

    //setup the page and set the text view to the ingredient list
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        LocalDate date = LocalDate.now(); //find current date
        daysList = getMondayToSunday(date); //get current monday to sunday week
        weeklyPlannedMeals = getWeeklyPlannedMeals(); //get all the plannedMeals in the current week
        ingredientsWeeklyList = getIngredientsWeeklyList(); //get the ingredients in the meals for the current week
        ingredientsTV = findViewById(R.id.ingredientsTV);
        setIngredientsTV(); //set the ingredients to the text view

    }

    //get an arrayList of the days in the current monday to sunday week
    public static ArrayList<String> getMondayToSunday(LocalDate date)
    {
        ArrayList<String> daysList = new ArrayList<String>();
        int day = date.getDayOfWeek().getValue();
        //get the days in the week before it
        for (int i = 1; i < day; i++)
        {
            String value = date.minusDays(i).format(formatter);
            if (value.charAt(0) == '0') //if the date is something like 01 jun 2022, remove the leading 0
            {
                value = value.substring(1);
            }
            daysList.add(value);
        }

        //get the days in the week after it
        for (int i = day; i <= 7; i++)
        {
            String value = date.plusDays(7-i).format(formatter);
            if (value.charAt(0) == '0') //if the date is something like 01 jun 2022, remove the leading 0
            {
                value = value.substring(1);
            }
            daysList.add(value);
        }
        return daysList;
    }

    //return all the plannedMeals with a date in the current week
    private ArrayList<PlannedMeal> getWeeklyPlannedMeals()
    {
        ArrayList<PlannedMeal> meals = new ArrayList<PlannedMeal>();
        for (int i = 0; i < MealManager.plannedMeals.size(); i++)
        {
            if (isInDaysList(MealManager.plannedMeals.get(i).date))
            {
                meals.add(MealManager.plannedMeals.get(i));
            }
        }
        return meals;

    }

    //return true if the date is in the current week
    private Boolean isInDaysList(String date)
    {
        for (int i = 0; i < daysList.size(); i++)
        {
            if (date.equals(daysList.get(i)))
            {
                return true;
            }
        }
        return false;
    }

    //go through the meals for the week, and return a list of ingredients
    private ArrayList<String> getIngredientsWeeklyList()
    {
        ArrayList<String> ingredients = new ArrayList<String>();
        for (int i = 0; i < weeklyPlannedMeals.size(); i++)
        {
            for (int j = 0; j < weeklyPlannedMeals.get(i).meal.ingredients.size(); j++)
            {
                boolean isDuplicate = false;
                for (int k = 0; k < ingredients.size(); k++) //make sure that no duplicates are added
                {
                    if (weeklyPlannedMeals.get(i).meal.ingredients.get(j).equals(ingredients.get(k)))
                    {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate)
                {
                    ingredients.add(weeklyPlannedMeals.get(i).meal.ingredients.get(j));
                }
            }
        }
        return ingredients;
    }

    //format the ingredient string ArrayList into an orderly list, and set it to the TextView
    private void setIngredientsTV()
    {
        String value = "";
        for (int i = 0; i < ingredientsWeeklyList.size(); i++)
        {
            value += "\n-"+ingredientsWeeklyList.get(i);
        }
        ingredientsTV.setText(value);
    }
}
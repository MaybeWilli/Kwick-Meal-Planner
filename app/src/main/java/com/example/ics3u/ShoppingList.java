package com.example.ics3u;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        LocalDate date = LocalDate.now();
        daysList = getMondayToSunday(date);
        for (int i = 0; i < daysList.size(); i++)
        {
            Log.e("shoppinglist", daysList.get(i));
        }
        weeklyPlannedMeals = getWeeklyPlannedMeals();
        for (int i = 0; i < weeklyPlannedMeals.size(); i++)
        {
            Log.e("shoppinglist", weeklyPlannedMeals.get(i).meal.name);
        }
        ingredientsWeeklyList = getIngredientsWeeklyList();
        ingredientsTV = findViewById(R.id.ingredientsTV);
        setIngredientsTV();

    }

    public static ArrayList<String> getMondayToSunday(LocalDate date)
    {
        ArrayList<String> daysList = new ArrayList<String>();
        int day = date.getDayOfWeek().getValue();
        for (int i = 1; i < day; i++)
        {
            String value = date.minusDays(i).format(formatter);
            if (value.charAt(0) == '0')
            {
                value = value.substring(1);
            }
            daysList.add(value);
        }
        for (int i = day; i <= 7; i++)
        {
            String value = date.plusDays(7-i).format(formatter);
            if (value.charAt(0) == '0')
            {
                value = value.substring(1);
            }
            daysList.add(value);
        }
        return daysList;
    }

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

    private ArrayList<String> getIngredientsWeeklyList()
    {
        ArrayList<String> ingredients = new ArrayList<String>();
        for (int i = 0; i < weeklyPlannedMeals.size(); i++)
        {
            for (int j = 0; j < weeklyPlannedMeals.get(i).meal.ingredients.size(); j++)
            {
                boolean isDuplicate = false;
                for (int k = 0; k < ingredients.size(); k++)
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
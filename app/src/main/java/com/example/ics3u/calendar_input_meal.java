package com.example.ics3u;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class calendar_input_meal extends AppCompatActivity {
    private EditText calenderET;
    private TextView dateTV;
    private Spinner mealInputSpinner;
    private ArrayList<String> arrayList = new ArrayList<>();
    private TextView dailyMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_input_meal);
        initWidgets();
        dateTV.setText("Date: "+ CalendarPage.selectedDate);

        //set the spinner
        mealInputSpinner = findViewById(R.id.mealInputSpinner);
        for (int i = 0; i < MealManager.meals.size(); i++)
        {
            arrayList.add(MealManager.meals.get(i).name);
        }
        //*/

        //arrayList.add("Hello");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealInputSpinner.setAdapter(arrayAdapter);

        //set the meals
        String mealText = "";
        Boolean hasMeals = Boolean.FALSE;
        for (int i = 0; i < MealManager.plannedMeals.size(); i++)
        {
            if (MealManager.plannedMeals.get(i).date.equals(CalendarPage.currentDate))
            {
                mealText = mealText + "\n-"+MealManager.plannedMeals.get(i).meal.name;
                hasMeals = Boolean.TRUE;
            }
        }
        if (hasMeals)
        {
            mealText = "Here are today's meals:" + mealText;
        }
        dailyMeal.setText(mealText);
    }

    private void initWidgets()
    {
        calenderET = findViewById(R.id.mealInputET);
        dateTV = findViewById(R.id.dateTV);
        dailyMeal = findViewById(R.id.dailyMeals);
    }

    public void calendarSaveMeal(View view)
    {
        Meal selectedMeal = new Meal();
        String mealName = mealInputSpinner.getSelectedItem().toString();
        for (int i = 0; i < MealManager.meals.size(); i++)
        {
            if (MealManager.meals.get(i).name == mealName)
            {
                selectedMeal = MealManager.meals.get(i);
            }
        }
        MealManager.addPlannedMeal(selectedMeal, CalendarPage.currentDate);
        finish();
    }
}
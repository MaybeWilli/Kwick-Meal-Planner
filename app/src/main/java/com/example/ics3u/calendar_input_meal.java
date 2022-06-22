/*
* This is the java class that controls the page that inputs meals
* It sends the information the user inputs over to the
* MealManager, where the data is stored in the memory as an ArrayList
* and in the storage as a database
*/
package com.example.ics3u;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class calendar_input_meal extends AppCompatActivity {
    private EditText calenderET;
    private TextView dateTV;
    private Spinner mealInputSpinner;
    private ArrayList<String> arrayList = new ArrayList<>();
    //private TextView dailyMeal;
    private EditText servingsET;
    private TextView servingsTV;
    private LinearLayout layout;

    private ArrayList<PlannedMeal> dailyMeals = new ArrayList<PlannedMeal>();
    private ArrayList<Integer> mealPosList = new ArrayList<Integer>();
    private float dailyTotalServings;

    //This function sets the default variables and sets up the texts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_input_meal);
        initWidgets(); //set the variables
        dateTV.setText("Date: "+ CalendarPage.currentDate);

        //set the spinner
        mealInputSpinner = findViewById(R.id.mealInputSpinner);
        for (int i = 0; i < MealManager.meals.size(); i++)
        {
            arrayList.add(MealManager.meals.get(i).name);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealInputSpinner.setAdapter(arrayAdapter);

        //set the text for the daily summary (calories and food groups)
        String mealText = "";
        float totalCalories = 0;

        float vegetables = 0;
        float grains = 0;
        float dairy = 0;
        float meat = 0;
        float other = 0;

        Boolean hasMeals = Boolean.FALSE;
        if (MealManager.plannedMeals.size() != 0)
        {
            for (int i = 0; i < MealManager.plannedMeals.size(); i++)
            {
                Float totalServings = 0f;
                if (MealManager.plannedMeals.get(i).date.equals(CalendarPage.currentDate))
                {
                    mealText = mealText + "\n-"+MealManager.plannedMeals.get(i).meal.name;
                    hasMeals = Boolean.TRUE;
                    dailyMeals.add(MealManager.plannedMeals.get(i));
                    mealPosList.add(i);

                    for (int j = 0; j < MealManager.plannedMeals.get(i).meal.servings.size(); j++)
                    {
                        totalServings += MealManager.plannedMeals.get(i).meal.servings.get(j);
                        dailyTotalServings += MealManager.plannedMeals.get(i).meal.servings.get(j);
                    }

                    for (int j = 0; j < MealManager.plannedMeals.get(i).meal.servings.size(); j++)
                    {
                        Log.e("mealinput", "I am here!");
                        totalCalories += MealManager.plannedMeals.get(i).meal.caloriesList.get(j)/totalServings*MealManager.plannedMeals.get(i).servings;
                        String ingName = MealManager.plannedMeals.get(i).meal.groups.get(j);
                        float servingSize = MealManager.plannedMeals.get(i).meal.servings.get(j);
                        if (ingName.equals("Vegetables"))
                        {
                            vegetables += servingSize/totalServings;
                        }
                        else if (ingName.equals("Grains"))
                        {
                            grains += servingSize/totalServings;
                        }
                        else if (ingName.equals("Dairy"))
                        {
                            dairy += servingSize/totalServings;
                        }
                        else if (ingName.equals("Meat"))
                        {
                            meat += servingSize/totalServings;
                        }
                        else if (ingName.equals("Other"))
                        {
                            other += servingSize/totalServings;
                        }

                    }
                }
                Log.e("hmm", "Hmm");
            }
        }
        Log.e("hmm", "hello3.4");
        if (hasMeals)
        {
            String value = "Daily Summary:\n";
            value += "\nVegetable servings per serving: "+(Math.round(vegetables*dailyTotalServings*100.0)/100.0);//*dailyTotalServings;
            value += "\nGrain servings per serving: "+(Math.round(grains*100.0)/100.0)*dailyTotalServings;
            value += "\nDairy servings per serving: "+(Math.round(dairy*100.0)/100.0)*dailyTotalServings;
            value += "\nMeat servings per serving: "+(Math.round(meat*100.0)/100.0)*dailyTotalServings;
            value += "\nOther servings per serving: "+(Math.round(other*100.0)/100.0)*dailyTotalServings;
            value += "\nToday's total calories: "+(Math.round(totalCalories*100.0)/100.0)*dailyTotalServings+"\n\n";
            Float a = vegetables;
            servingsTV.setText(value);
        }

        //add the buttons for the meals for that day
        addViews();
    }

    //Set the variables
    private void initWidgets()
    {
        calenderET = findViewById(R.id.mealInputET);
        dateTV = findViewById(R.id.dateTV);
        servingsET = findViewById(R.id.servingsET);
        servingsTV = findViewById(R.id.servingsTV);
        layout = findViewById(R.id.editLayout);
    }

    //Send over the information to the MealManager
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void calendarSaveMeal(View view)
    {
        Meal selectedMeal = new Meal();
        String mealName = mealInputSpinner.getSelectedItem().toString();

        //find the relevant meal from the MealManager.meals list
        for (int i = 0; i < MealManager.meals.size(); i++)
        {
            if (MealManager.meals.get(i).name == mealName)
            {
                selectedMeal = MealManager.meals.get(i);
            }
        }

        //get the total servings
        float servings;
        if (!servingsET.getText().toString().equals(""))
        {
            servings = Float.parseFloat(servingsET.getText().toString());
        }
        else
        {
            servings = 0;
        }

        //send over the information to the MealManager
        MealManager.addPlannedMeal(selectedMeal, CalendarPage.currentDate, servings);
        finish(); //close the XML page
    }

    //sort through the meals, and add the textview/button group to the scrollView
    public void addViews()
    {
        //go through every one of the meals for the day
        for (int i = 0; i < dailyMeals.size(); i++) {
            View view = getLayoutInflater().inflate(R.layout.activity_edit_planned_meal_view, null, false);
            TextView text = view.findViewById(R.id.nameTV);
            text.setText(dailyMeals.get(i).meal.name);
            Button button = view.findViewById(R.id.deleteButton);
            button.setTag(mealPosList.get(i));
            button.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    //find the current item and send the data over to MealManager
                    Log.e("editing", "I am here!1");
                    int currentItem = (int) view.getTag();
                    dailyMeals.remove(mealPosList.indexOf(currentItem));
                    mealPosList.remove(mealPosList.indexOf(currentItem));
                    MealManager.deletePlannedMeal(currentItem);
                    updateViews();
                }
            });
            layout.addView(view);
        }

    }

    //remove old views and add them again
    private void updateViews()
    {
        layout.removeAllViews();
        addViews();
    }

}
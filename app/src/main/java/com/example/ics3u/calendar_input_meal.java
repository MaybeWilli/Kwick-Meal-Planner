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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_input_meal);
        initWidgets();
        dateTV.setText("Date: "+ CalendarPage.currentDate);
        Log.e("hmm", "Hello1");

        //set the spinner
        mealInputSpinner = findViewById(R.id.mealInputSpinner);
        for (int i = 0; i < MealManager.meals.size(); i++)
        {
            arrayList.add(MealManager.meals.get(i).name);
        }

        Log.e("hmm", "Hello2");
        //*/

        //arrayList.add("Hello");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealInputSpinner.setAdapter(arrayAdapter);

        Log.e("hmm", "Hello3");
        //set the meals and meal summary thing
        String mealText = "";
        float totalCalories = 0;

        float vegetables = 0;
        float grains = 0;
        float dairy = 0;
        float meat = 0;
        float other = 0;
        //float totalServings;

        Boolean hasMeals = Boolean.FALSE;
        Log.e("hmm", "Hello3.1"+MealManager.plannedMeals.size());
        if (MealManager.plannedMeals.size() != 0)
        {
            for (int i = 0; i < MealManager.plannedMeals.size(); i++)
            {
                Float totalServings = 0f;
                Log.e("hmm", "Whyyyy");
                Log.e("hmm", MealManager.plannedMeals.get(i).date);
                Log.e("hmm", "oh");
                if (MealManager.plannedMeals.get(i).date.equals(CalendarPage.currentDate))
                {
                    mealText = mealText + "\n-"+MealManager.plannedMeals.get(i).meal.name;
                    //totalCalories += MealManager.plannedMeals.get(i).meal.calories * MealManager.plannedMeals.get(i).servings;
                    hasMeals = Boolean.TRUE;
                    dailyMeals.add(MealManager.plannedMeals.get(i));
                    mealPosList.add(i);

                    for (int j = 0; j < MealManager.plannedMeals.get(i).meal.servings.size(); j++)
                    {
                        totalServings += MealManager.plannedMeals.get(i).meal.servings.get(j);
                    }

                    for (int j = 0; j < MealManager.plannedMeals.get(i).meal.servings.size(); j++)
                    {
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
            //mealText = "Here are today's meals:" + mealText;
            //mealText += "\nToday's total calories: "+totalCalories;
            String value = "Daily Summary:\n";
            value += "\nVegetable servings per serving: "+(Math.round(vegetables*100.0)/100.0);
            value += "\nGrain servings per serving: "+(Math.round(grains*100.0)/100.0);
            value += "\nDairy servings per serving: "+(Math.round(dairy*100.0)/100.0);
            value += "\nMeat servings per serving: "+(Math.round(meat*100.0)/100.0);
            value += "\nOther servings per serving: "+(Math.round(other*100.0)/100.0);
            value += "\nToday's total calories: "+(Math.round(totalCalories*100.0)/100.0)+"\n\n";
            servingsTV.setText(value);
        }
        Log.e("hmm", "Hello4");
        //dailyMeal.setText(mealText);
        Log.e("hmm", "Hello5");

        addViews();
    }

    private void initWidgets()
    {
        calenderET = findViewById(R.id.mealInputET);
        dateTV = findViewById(R.id.dateTV);
        //dailyMeal = findViewById(R.id.dailyMeals);
        servingsET = findViewById(R.id.servingsET);
        servingsTV = findViewById(R.id.servingsTV);
        layout = findViewById(R.id.editLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        Log.e("hmm", "Well, not here!");
        float servings;
        if (!servingsET.getText().toString().equals(""))
        {
            servings = Float.parseFloat(servingsET.getText().toString());
        }
        else
        {
            servings = 0;
        }
        MealManager.addPlannedMeal(selectedMeal, CalendarPage.currentDate, servings);
        Log.e("hmm", "wacky");
        finish();
    }

    public void addViews()
    {
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
                    //do things

                    //find the current item
                    Log.e("editing", "I am here!1");
                    int currentItem = (int) view.getTag();
                    dailyMeals.remove(mealPosList.indexOf(currentItem));
                    mealPosList.remove(mealPosList.indexOf(currentItem));
                    MealManager.deletePlannedMeal(currentItem);
                    updateViews();
                    /*TextView textView = view.findViewById(R.id.nameTV);
                    String name = textView.getText().toString();
                    for (int i = 0; i < MealManager.meals.size(); i++)
                    {
                        if (name.equals(MealManager.meals.get(i).name))
                        {
                            currentItem = i;
                            break;
                        }
                    }*/

                    //delete meal thing
                }
            });
            layout.addView(view);
        }

    }

    private void updateViews()
    {
        layout.removeAllViews();
        addViews();
    }

}
/*
* This java file is for the page that opens when the user enters a meal name in the
* MealInputPage and presses enter. It saves the user's input, and sends over the
* data to the MealManager, for it to save into an arrayList and database.
 */
package com.example.ics3u;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ingredient_input_page extends AppCompatActivity {

    private Spinner foodGroupSpinner;
    private EditText ingredientNameET;
    private EditText ingredientServingsET;
    private EditText ingredientCaloriesET;

    private ArrayList<String> ingredientArrayList = new ArrayList<String>();
    private ArrayList<String> foodGroupsArrayList = new ArrayList<String>();
    private float totalCalories = 0;
    private ArrayList<Float> servingsArrayList = new ArrayList<Float>();
    private ArrayList<Float> caloriesList = new ArrayList<Float>();

    //set up the page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_input_page);

        foodGroupSpinner = findViewById(R.id.foodGroupSpinner);
        ingredientNameET = findViewById(R.id.ingredientET);
        ingredientServingsET = findViewById(R.id.servingsET);
        ingredientCaloriesET = findViewById(R.id.ingredientCaloriesET);

        ArrayList<String> foodGroups = new ArrayList<String>();
        foodGroups.add("Other");
        foodGroups.add("Vegetables");
        foodGroups.add("Grains");
        foodGroups.add("Dairy");
        foodGroups.add("Meat");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, foodGroups);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodGroupSpinner.setAdapter(arrayAdapter);
    }

    //add ingredient data to arraylists
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveIngredient(View view)
    {
        //calories
        totalCalories += Float.parseFloat(ingredientCaloriesET.getText().toString()) * Float.parseFloat(ingredientServingsET.getText().toString());

        //ingredient name
        ingredientArrayList.add(ingredientNameET.getText().toString());

        //food groups
        foodGroupsArrayList.add(foodGroupSpinner.getSelectedItem().toString());

        //servings
        servingsArrayList.add(Float.parseFloat(ingredientServingsET.getText().toString()));
        Log.e("ingredient", "wow");

        //calorie list
        caloriesList.add(Float.parseFloat(ingredientCaloriesET.getText().toString()));

        //clear out the editTexts
        ingredientCaloriesET.getText().clear();
        ingredientServingsET.getText().clear();
        ingredientNameET.getText().clear();
    }

    //send data to MealManager and close page
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void closeIngredientPage(View view)
    {
        //get total servings
        float totalServings = 0;
        for (int i = 0; i < servingsArrayList.size(); i++)
        {
            totalServings += servingsArrayList.get(i);
        }
        totalCalories = totalCalories/totalServings;

        //send to MealManager
        MealManager.addMeal(MealInputPage.mealName, ingredientArrayList, totalCalories, foodGroupsArrayList, servingsArrayList, caloriesList, totalServings);
        finish(); //close page
    }
}
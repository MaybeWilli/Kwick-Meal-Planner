/*
* This java file controls the first half of the meal inputing process.
* All it does is record the meal's name, and open up the ingredient
* input page (activity_ingredient_input_page)
 */
package com.example.ics3u;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MealInputPage extends AppCompatActivity {

    private EditText mealET;
    private Spinner mealInputSpinner;
    private EditText ingredientET;
    private EditText calorieET;
    private ArrayList<String> ingredients = new ArrayList<>();
    public static String mealName = "";

    //set variables and setup page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_input_page);
        mealET = findViewById(R.id.mealET);
        mealInputSpinner = findViewById(R.id.mealInputSpinner);
    }

    //close the page
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void closeInputPage(View view)
    {
        finish();
    }

    //record the meal's name, and open the activity_ingredient_input_page
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addMealFromET(View view)
    {
        mealName = mealET.getText().toString();
        Intent intent = new Intent(this, ingredient_input_page.class);
        startActivity(intent);
        finish();
    }
}
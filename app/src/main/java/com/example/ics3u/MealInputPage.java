package com.example.ics3u;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
    private ArrayList<String> ingredients = new ArrayList<>();

    //private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_input_page);
        mealET = findViewById(R.id.mealET);
        mealInputSpinner = findViewById(R.id.mealInputSpinner);
        ingredientET = findViewById(R.id.ingredientET);


        /*for (int i = 0; i < MealManager.meals.length; i++)
        {
            arrayList.add(MealManager.meals[i].name);
        }
        /*/

        //arrayList.add("Hello");
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void closeInputPage(View view)
    {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addMealFromET(View view)
    {
        MealManager.addMeal(mealET.getText().toString(), ingredients);
        ingredients.clear();
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addIngredientFromET(View view)
    {
        Log.e("hmm", "Pourquoi?");
        String ingredient = ingredientET.getText().toString();
        Log.e("hmm", "Why? "+ingredient);
        ingredients.add(ingredient);
        Log.e("hmm", "Nani?");
        ingredientET.getText().clear();
        Log.e("hmm", "Impossible!?");
    }
}
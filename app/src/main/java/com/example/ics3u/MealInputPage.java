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

    //private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_input_page);
        mealET = findViewById(R.id.mealET);
        mealInputSpinner = findViewById(R.id.mealInputSpinner);


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
        mealName = mealET.getText().toString();
        Intent intent = new Intent(this, ingredient_input_page.class);
        startActivity(intent);
        finish();
    }

    /*@RequiresApi(api = Build.VERSION_CODES.O)
    public void addIngredientFromET(View view)
    {
        Log.e("addIngredient", "Pourquoi?");
        String ingredient = ingredientET.getText().toString();
        Log.e("addIngredient", "Why? "+ingredient);
        ingredients.add(ingredient);
        Log.e("addIngredient", "Nani?");
        ingredientET.getText().clear();
        Log.e("addIngredient", "Impossible!?");
    }*/
}
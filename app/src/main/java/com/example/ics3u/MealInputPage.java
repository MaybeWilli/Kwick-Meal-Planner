package com.example.ics3u;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class MealInputPage extends AppCompatActivity {

    private EditText mealET;
    private Spinner mealInputSpinner;
    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_input_page);
        mealET = findViewById(R.id.mealET);
        mealInputSpinner = findViewById(R.id.mealInputSpinner);
        //for (int i = 0; i < MealManager.meals.length; )
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void closeInputPage(View view)
    {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addMealFromET(View view)
    {
        MealManager.addMeal(mealET.getText().toString());
        finish();
    }
}
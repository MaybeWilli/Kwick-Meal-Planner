package com.example.ics3u;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayMealPage extends AppCompatActivity {

    private EditText searchET;
    private Button searchButton;
    private TextView mealsTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_meal_page);

        searchET = findViewById(R.id.searchMealET);
        searchButton = findViewById(R.id.searchMealButton);
        mealsTV = findViewById(R.id.mealsTV);

        Log.e("hmm", "Why");
        mealsTV.setText(generateMealString());
    }

    private String generateMealString()
    {
        String value = "";

        for (int i = 0; i < MealManager.meals.size(); i++)
        {
            value += MealManager.meals.get(i).name;
            //Log.e("DisplayMeal", "Ingredient: "+MealManager.meals.get(i).ingredients.get(0));
            for (int j = 0; j < MealManager.meals.get(i).ingredients.size(); j++)
            {
                value += "\n    -" + MealManager.meals.get(i).ingredients.get(j);
            }
            value += "\nCalories per serving: "+MealManager.meals.get(i).calories+"\n\n";
        }

        return value;
    }

    /*@RequiresApi(api = Build.VERSION_CODES.O)
    public void searchMeal()
    {

    }*/
}
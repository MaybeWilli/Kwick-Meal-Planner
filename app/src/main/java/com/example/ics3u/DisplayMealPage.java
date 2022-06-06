package com.example.ics3u;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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

        //Log.e("hmm", "Why");
        mealsTV.setText(generateMealString());
        mealsTV.setMovementMethod(new ScrollingMovementMethod());
    }

    private String generateMealString()
    {
        String value = "";
        float vegetables = 0;
        float grains = 0;
        float dairy = 0;
        float meat = 0;
        float other = 0;

        for (int i = 0; i < MealManager.meals.size(); i++)
        {
            value += MealManager.meals.get(i).name;
            //Log.e("DisplayMeal", "Ingredient: "+MealManager.meals.get(i).ingredients.get(0));
            for (int j = 0; j < MealManager.meals.get(i).ingredients.size(); j++)
            {
                value += "\n    -" + MealManager.meals.get(i).ingredients.get(j);
            }
            for (int j = 0; j < MealManager.meals.get(i).groups.size(); j++)
            {
                if (MealManager.meals.get(i).groups.get(j).equals("Vegetables"))
                {
                    vegetables += MealManager.meals.get(i).servings.get(j);
                }
                else if (MealManager.meals.get(i).groups.get(j).equals("Grains"))
                {
                    grains += MealManager.meals.get(i).servings.get(j);
                }
                else if (MealManager.meals.get(i).groups.get(j).equals("Dairy"))
                {
                    dairy += MealManager.meals.get(i).servings.get(j);
                }
                else if (MealManager.meals.get(i).groups.get(j).equals("Meat"))
                {
                    meat += MealManager.meals.get(i).servings.get(j);
                }
                else if (MealManager.meals.get(i).groups.get(j).equals("Other"))
                {
                    other += MealManager.meals.get(i).servings.get(j);
                }
            }
            value += "\nCalories per serving: "+MealManager.meals.get(i).calories;
            value += "\nVegetable servings per serving: "+vegetables;
            value += "\nGrain servings per serving: "+grains;
            value += "\nDairy servings per serving: "+dairy;
            value += "\nMeat servings per serving: "+meat;
            value += "\nOther servings per serving: "+other+"\n\n";
        }

        return value;
    }

    /*@RequiresApi(api = Build.VERSION_CODES.O)
    public void searchMeal()
    {

    }*/
}
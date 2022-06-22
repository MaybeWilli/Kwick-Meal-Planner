/*
* Displays all of the meals that the user has inputed, along with
* how many servings of each food group are in one serving of total meal.
* Displays how many calories are in one serving of total meal.
* Also contains code for the search bar that filters down the meals
* using a keyword.
 */
package com.example.ics3u;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class DisplayMealPage extends AppCompatActivity {

    private EditText searchET;
    private Button searchButton;
    private TextView mealsTV;

    //Set the default text, and add the onClick method to the search button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_meal_page);

        //set variables
        searchET = findViewById(R.id.searchMealET);
        searchButton = findViewById(R.id.searchMealButton);
        mealsTV = findViewById(R.id.mealsTV);

        //make the textView scroll, and set the default text
        mealsTV.setText(generateMealString());
        mealsTV.setMovementMethod(new ScrollingMovementMethod());

        //make an onClick function that generates the meals that contain the "searchterm"
        //inputed in the search bar edit text
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchET.getText().toString();
                mealsTV.setText(generateMealString(searchTerm));
            }
        });
    }

    //return a string containing the names of all the meals, and the calorie and food group information
    private String generateMealString()
    {
        String value = "";

        //scroll through the meal list and add the information for each meal
        for (int i = 0; i < MealManager.meals.size(); i++)
        {
            value += MealManager.meals.get(i).name;
            float vegetables = 0;
            float grains = 0;
            float dairy = 0;
            float meat = 0;
            float other = 0;
            Float totalServings = 0f;
            for (int j = 0; j < MealManager.meals.get(i).ingredients.size(); j++)
            {
                value += "\n    -" + MealManager.meals.get(i).ingredients.get(j);
                totalServings += MealManager.meals.get(i).servings.get(j);
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

            //summarize the information by adding it to the string
            value += "\nCalories per serving: "+MealManager.meals.get(i).calories/totalServings;
            value += "\nVegetable servings per serving: "+(Math.round(vegetables/totalServings*100.0)/100.0);
            value += "\nGrain servings per serving: "+(Math.round(grains/totalServings*100.0)/100.0);
            value += "\nDairy servings per serving: "+(Math.round(dairy/totalServings*100.0)/100.0);
            value += "\nMeat servings per serving: "+(Math.round(meat/totalServings*100.0)/100.0);
            value += "\nOther servings per serving: "+(Math.round(other/totalServings*100.0)/100.0)+"\n\n";
        }

        return value;
    }

    //overload method for generateMealString. Does the same thing, but the meal must contain the searchterm
    //in order to be included
    private String generateMealString(String searchTerm)
    {
        String value = "";

        for (int i = 0; i < MealManager.meals.size(); i++)
        {
            if (MealManager.meals.get(i).name.toLowerCase(Locale.ROOT).contains(searchTerm))
            {
                value += MealManager.meals.get(i).name;
                float vegetables = 0;
                float grains = 0;
                float dairy = 0;
                float meat = 0;
                float other = 0;
                Float totalServings = 0f;
                for (int j = 0; j < MealManager.meals.get(i).ingredients.size(); j++) {
                    value += "\n    -" + MealManager.meals.get(i).ingredients.get(j);
                    totalServings += MealManager.meals.get(i).servings.get(j);
                }
                for (int j = 0; j < MealManager.meals.get(i).groups.size(); j++) {
                    if (MealManager.meals.get(i).groups.get(j).equals("Vegetables")) {
                        vegetables += MealManager.meals.get(i).servings.get(j);
                    } else if (MealManager.meals.get(i).groups.get(j).equals("Grains")) {
                        grains += MealManager.meals.get(i).servings.get(j);
                    } else if (MealManager.meals.get(i).groups.get(j).equals("Dairy")) {
                        dairy += MealManager.meals.get(i).servings.get(j);
                    } else if (MealManager.meals.get(i).groups.get(j).equals("Meat")) {
                        meat += MealManager.meals.get(i).servings.get(j);
                    } else if (MealManager.meals.get(i).groups.get(j).equals("Other")) {
                        other += MealManager.meals.get(i).servings.get(j);
                    }
                }
                value += "\nCalories per serving: " + MealManager.meals.get(i).calories / totalServings;
                value += "\nVegetable servings per serving: " + (Math.round(vegetables / totalServings * 100.0) / 100.0);
                value += "\nGrain servings per serving: " + (Math.round(grains / totalServings * 100.0) / 100.0);
                value += "\nDairy servings per serving: " + (Math.round(dairy / totalServings * 100.0) / 100.0);
                value += "\nMeat servings per serving: " + (Math.round(meat / totalServings * 100.0) / 100.0);
                value += "\nOther servings per serving: " + (Math.round(other / totalServings * 100.0) / 100.0) + "\n\n";
            }
        }

        return value;
    }
}
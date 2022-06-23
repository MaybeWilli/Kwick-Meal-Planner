/*
* Java class that adds an ediText and two buttons for every meal to a scrollView.
* Every meal has its name, and two buttons, one for deleting, and one for editing the meal.
 */
package com.example.ics3u;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class EditMealPage extends AppCompatActivity {

    LinearLayout layout;
    public static int currentItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meal_page);

        //define variables
        layout = findViewById(R.id.editLayout);

        //add the meals to the list
        addViews();
    }

    //add a textView and two buttons for every meal in the MealManager
    //the buttons delete and edit the meal
    private void addViews()
    {
        for (int i = 0; i < MealManager.meals.size(); i++) {
            //create the text and two buttons
            View view = getLayoutInflater().inflate(R.layout.activity_edit_meal_view, null, false);

            //set the texts and names of buttons
            TextView text = view.findViewById(R.id.nameTV);
            text.setText(MealManager.meals.get(i).name);
            Button button = view.findViewById(R.id.editButton);
            button.setTag(i); //tag the button so that it knows which meal it refers to
            Button button2 = view.findViewById(R.id.deleteButton);
            button2.setTag(i); //tag the button so that it knows which meal it refers to
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    //find the current item's position in the MealManager ArrayList
                    currentItem = (int) view.getTag();

                    //open the edit UI page
                    Intent intent = new Intent(EditMealPage.this, EditMealUI.class);
                    startActivity(intent);
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                //add a function that deletes the meal from the arrayList, database, and reloads the editPage
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    int i = (int) view.getTag();
                    MealManager.deleteMeal(i);
                    updateViews();
                }
            });

            //add the newly created view to the scrollView
            layout.addView(view);
        }
    }

    //remove all the views and add them back
    //that way, if something was changed in the database/arrayList, the changes are shown on this page
    public void updateViews()
    {
        layout.removeAllViews();
        addViews();
    }
}
package com.example.ics3u;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        addViews();
        /*Log.e("editing", "hmm");
        View view = View.inflate(this, R.layout.activity_edit_meal_page, layout);
        Log.e("editing", "hmm2");
        layout.addView(view, 0);
        Log.e("editing", "hmm3");*/

        //View view = getLayoutInflater().inflate(R.layout.activity_edit_meal_view, null, false);
        //view.
        //layout.addView(view);


        //creating views
        //LayoutInflater inflater = getLayoutInflater();
        //View thingy = inflater.inflate(R.layout.activity_edit_meal_page, EditMealView.class, layout);

    }

    private void addViews()
    {
        for (int i = 0; i < MealManager.meals.size(); i++) {
            View view = getLayoutInflater().inflate(R.layout.activity_edit_meal_view, null, false);
            TextView text = view.findViewById(R.id.nameTV);
            text.setText(MealManager.meals.get(i).name);
            view.setTag(i);
            view.findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    //do things
                    currentItem = (int) view.getTag();
                    Intent intent = new Intent(EditMealPage.this, CalendarPage.class);
                    startActivity(intent);
                }
            });
            layout.addView(view);
        }
    }
}
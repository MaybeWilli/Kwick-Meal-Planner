package com.example.ics3u;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EditMealPage extends AppCompatActivity {

    LinearLayout layout;
    public static int currentItem = 0;
    private ArrayList<View> views = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meal_page);

        //define variables
        layout = findViewById(R.id.editLayout);

        //layout.removeAllViews();
        //views.clear();
        addViews();
        Log.e("editing", "hmm");
        //View view = View.inflate(this, R.layout.activity_edit_meal_page, layout);
        Log.e("editing", "hmm2");
        //layout.addView(view, 0);
        Log.e("editing", "hmm3");//*/

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
            views.add(view);
            TextView text = view.findViewById(R.id.nameTV);
            text.setText(MealManager.meals.get(i).name);
            Button button = view.findViewById(R.id.editButton);
            button.setTag(i);
            Button button2 = view.findViewById(R.id.deleteButton);
            button2.setTag(i);
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    //do things

                    //find the current item
                    Log.e("editing", "I am here!1");
                    currentItem = (int) view.getTag();
                    Log.e("editing", "I am here!2");
                    /*TextView textView = view.findViewById(R.id.nameTV);
                    String name = textView.getText().toString();
                    for (int i = 0; i < MealManager.meals.size(); i++)
                    {
                        if (name.equals(MealManager.meals.get(i).name))
                        {
                            currentItem = i;
                            break;
                        }
                    }*/

                    Intent intent = new Intent(EditMealPage.this, EditMealUI.class);
                    startActivity(intent);
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    int i = (int) view.getTag();
                    MealManager.deleteMeal(i);
                    updateViews();
                }
            });
            layout.addView(view);
        }
    }

    public void updateViews()
    {
        layout.removeAllViews();
        addViews();
    }
}
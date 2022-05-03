package com.example.ics3u;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        button2 = (Button) findViewById(R.id.inputMealPageButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMealInputPage();
            }
        });
    }

    public void openActivity2() {
        Intent intent = new Intent(this, CalendarPage.class);
        startActivity(intent);
    }

    public void openMealInputPage()
    {
        Intent intent = new Intent(this, MealInputPage.class);
        startActivity(intent);
    }
};
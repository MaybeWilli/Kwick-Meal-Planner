package com.example.ics3u;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class calendar_input_meal extends AppCompatActivity {
    private EditText calenderET;
    private TextView dateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_input_meal);
        initWidgets();
        dateTV.setText("Date: "+ CalendarPage.selectedDate);
    }

    private void initWidgets()
    {
        calenderET = findViewById(R.id.mealInputET);
        dateTV = findViewById(R.id.dateTV);
    }

    public void calendarSaveMeal(View view) {
        finish();
    }
}
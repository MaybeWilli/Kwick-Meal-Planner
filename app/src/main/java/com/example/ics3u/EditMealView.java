package com.example.ics3u;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditMealView extends AppCompatActivity {
    public TextView nameTV;
    public int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meal_view);
        nameTV = findViewById(R.id.nameTV);
        nameTV.setText("Foods!");
        Log.e("hmm", "bruuuh!");
    }
}
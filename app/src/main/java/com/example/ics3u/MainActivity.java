package com.example.ics3u;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    public static SavedMealDatabase savedMealDatabase;// = Room.databaseBuilder(getApplicationContext(), SavedMealDatabase.class, "savedMealDatabase").build();
    public static MealDao mealDao;
    public static SavedMealDatabase savedPlannedMealDatabase;// = Room.databaseBuilder(getApplicationContext(), SavedMealDatabase.class, "savedMealDatabase").build();
    public static MealDao plannedMealDao;
    public static MediaPlayer mediaPlayer;
    //public MealDao mealDao = savedMealDatabase.mealDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("hmm", "Hello");

        //Deal with database stuff
        savedMealDatabase = Room.databaseBuilder(getApplicationContext(), SavedMealDatabase.class, "savedMealDatabase").build();
        mealDao = savedMealDatabase.mealDao();
        savedPlannedMealDatabase = Room.databaseBuilder(getApplicationContext(), SavedMealDatabase.class, "savedPlannedMealDatabase").build();
        plannedMealDao = savedPlannedMealDatabase.mealDao();
        FetchData fetchData = new FetchData();
        fetchData.thread.start();

        mediaPlayer = MediaPlayer.create(this, R.raw.il_vento_doro);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        //mealDao.insertOneMeal(new SavedMeal());
        //List<SavedMeal> savedMeals = mealDao.getAll();
        //SavedMeal[] savedMeals = mealDao.getAll();
        //MealManager.meals.clear();

        /*for (int i = 0; i < savedMeals.size(); i++)
        {
            MealManager.meals.add(new Meal(savedMeals.get(i).name));
        }*/


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

        button3 = (Button) findViewById(R.id.displayMealPageButton);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDisplayMealPage();
            }
        });

        button4 = (Button) findViewById(R.id.editMealPageButton);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditMealPage();
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

    public void openDisplayMealPage()
    {
        Intent intent = new Intent(this, DisplayMealPage.class);
        startActivity(intent);
    }
    public void openEditMealPage()
    {
        Intent intent = new Intent(this, EditMealPage.class);
        startActivity(intent);
    }

    class FetchData implements Runnable {
        Thread thread = new Thread(this, "fetch_data");

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            MealManager.InstantiateArrays();
        }
    }
};